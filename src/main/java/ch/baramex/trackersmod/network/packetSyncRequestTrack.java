package ch.baramex.trackersmod.network;

import ch.baramex.trackersmod.tileentity.TileEntityTracker;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class packetSyncRequestTrack implements IMessage {
	
	private boolean requestTrack;
	private String player;
	private BlockPos pos;

	public packetSyncRequestTrack() {
		
	}
	
	public packetSyncRequestTrack(boolean requestTrack, BlockPos pos, String player) {
		this.requestTrack = requestTrack;
		this.pos = pos;
		this.player = player;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		requestTrack = buf.readBoolean();
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		player = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(requestTrack);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		ByteBufUtils.writeUTF8String(buf, player);
	}
	
	public static class Handler implements IMessageHandler<packetSyncRequestTrack, IMessage> {

		@Override
		public IMessage onMessage(packetSyncRequestTrack message, MessageContext ctx) {
			if(ctx.side == Side.SERVER) {
				FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			}
			return null;
		}
		
		void processMessage(packetSyncRequestTrack message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
			if(te != null) {
				if(te instanceof TileEntityTracker) {
					((TileEntityTracker) te).requestTrack = message.requestTrack;
					((TileEntityTracker) te).playerName = message.player;
				}
				if(te instanceof TileEntityTrackerAdv) {
					((TileEntityTrackerAdv) te).requestTrack = message.requestTrack;
					((TileEntityTrackerAdv) te).playerName = message.player;
				}
			}
		}
	}
}
