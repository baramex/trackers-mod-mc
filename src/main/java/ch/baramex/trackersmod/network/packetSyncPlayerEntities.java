package ch.baramex.trackersmod.network;

import java.util.ArrayList;
import java.util.List;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.container.IEntityPlayerContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class packetSyncPlayerEntities implements IMessage {

	private List<String> players;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int n = buf.readInt();
		players = new ArrayList<String>();
		for(int a = 0; a < n; a++) {
			String pl = ByteBufUtils.readUTF8String(buf);
			players.add(pl);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(players.size());
		for(String pl : players) {
			ByteBufUtils.writeUTF8String(buf, pl);
		}
	}
	
	public packetSyncPlayerEntities() {
		
	}
	
	public packetSyncPlayerEntities(List<String> players) {
		this.players = players;
	}
	
	public static class Handler implements IMessageHandler<packetSyncPlayerEntities, IMessage> {

		@Override
		public IMessage onMessage(packetSyncPlayerEntities message, MessageContext ctx) {
			Main.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(packetSyncPlayerEntities message, MessageContext ctx) {
			EntityPlayer player = Main.proxy.getClientPlayer();
			if(player.openContainer instanceof IEntityPlayerContainer) {
				((IEntityPlayerContainer) player.openContainer).syncEntityPlayer(message.players);
			}
		}
	}
}
