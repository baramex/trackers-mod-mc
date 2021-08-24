package ch.baramex.trackersmod.network;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.container.IItemStackContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class packetSyncItemStack implements IMessage {

	private ItemStack item;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		item = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, item);
	}
	
	public packetSyncItemStack() {
		
	}
	
	public packetSyncItemStack(ItemStack item) {
		this.item = item;
	}
	
	public static class Handler implements IMessageHandler<packetSyncItemStack, IMessage> {

		@Override
		public IMessage onMessage(packetSyncItemStack message, MessageContext ctx) {
			Main.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(packetSyncItemStack message, MessageContext ctx) {
			EntityPlayer player = Main.proxy.getClientPlayer();
			if(player.openContainer instanceof IItemStackContainer) {
				((IItemStackContainer) player.openContainer).syncPlayerItem(message.item, player.getName());
			}
		}
	}
}
