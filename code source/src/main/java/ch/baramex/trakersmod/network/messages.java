package ch.baramex.trakersmod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class messages {

	public static SimpleNetworkWrapper instance;
	
	public static void registerMessage(String channelMessage) {
		instance = NetworkRegistry.INSTANCE.newSimpleChannel(channelMessage);
		
		instance.registerMessage(packetSyncGood.Handler.class, packetSyncGood.class, 0, Side.CLIENT);
		instance.registerMessage(packetSyncInt.Handler.class, packetSyncInt.class, 2, Side.CLIENT);
		instance.registerMessage(packetSyncPlayerEntities.Handler.class, packetSyncPlayerEntities.class, 3, Side.CLIENT);
		instance.registerMessage(packetSyncString.Handler.class, packetSyncString.class, 4, Side.CLIENT);
		instance.registerMessage(packetSyncItemStack.Handler.class, packetSyncItemStack.class, 5, Side.CLIENT);
		
		instance.registerMessage(packetSyncRequestTrack.Handler.class, packetSyncRequestTrack.class, 1, Side.SERVER);
	}
}
