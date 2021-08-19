package ch.baramex.trackersmod.network;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.container.IIsGoodContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class packetSyncGood implements IMessage {

	private String isGood;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		isGood = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, isGood);
	}
	
	public packetSyncGood() {
		
	}
	
	public packetSyncGood(String isGood) {
		this.isGood = isGood;
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
				((IIsGoodContainer) player.openContainer).syncGood(message.isGood, player.getName());
			}
		}
	}
}
