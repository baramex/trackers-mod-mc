package ch.baramex.trakersmod.network;

import ch.baramex.trakersmod.Main;
import ch.baramex.trakersmod.container.IIntContainer;
import ch.baramex.trakersmod.container.IIsGoodContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class packetSyncInt implements IMessage {

	private int in;
	private String param;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		in = buf.readInt();
		param = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(in);
		ByteBufUtils.writeUTF8String(buf, param);
	}
	
	public packetSyncInt() {
		
	}
	
	public packetSyncInt(int in, String param) {
		this.in = in;
		this.param = param;
	}
	
	public static class Handler implements IMessageHandler<packetSyncInt, IMessage> {

		@Override
		public IMessage onMessage(packetSyncInt message, MessageContext ctx) {
			Main.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(packetSyncInt message, MessageContext ctx) {
			EntityPlayer player = Main.proxy.getClientPlayer();
			if(player.openContainer instanceof IIntContainer) {
				((IIntContainer) player.openContainer).syncInt(message.in, player.getName(), message.param);
			}
		}
	}
}
