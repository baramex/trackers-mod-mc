package ch.baramex.trackersmod.network;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.container.IStringContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class packetSyncString implements IMessage {

	private String result;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		result = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, result);
	}
	
	public packetSyncString() {
		
	}
	
	public packetSyncString(String isGood) {
		this.result = isGood;
	}
	
	public static class Handler implements IMessageHandler<packetSyncString, IMessage> {

		@Override
		public IMessage onMessage(packetSyncString message, MessageContext ctx) {
			Main.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(packetSyncString message, MessageContext ctx) {
			EntityPlayer player = Main.proxy.getClientPlayer();
			if(player.openContainer instanceof IStringContainer) {
				((IStringContainer) player.openContainer).syncResult(message.result);
			}
		}
	}
}