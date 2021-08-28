package ch.baramex.trackersmod.network;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.container.IIsGoodContainer;
import ch.baramex.trackersmod.utils.Status;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class packetSyncGood implements IMessage {

	private int isGood;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		isGood = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(isGood);
	}
	
	public packetSyncGood() {
		
	}
	
	public packetSyncGood(Status.TrackStatus isGood) {
		this.isGood = isGood.getValue();
	}
	
	public static class Handler implements IMessageHandler<packetSyncGood, IMessage> {

		@Override
		public IMessage onMessage(packetSyncGood message, MessageContext ctx) {
			Main.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(packetSyncGood message, MessageContext ctx) {
			EntityPlayer player = Main.proxy.getClientPlayer();
			if(player.openContainer instanceof IIsGoodContainer) {
				((IIsGoodContainer) player.openContainer).syncGood(Status.TrackStatus.getEnumValue(message.isGood));
			}
		}
	}
}
