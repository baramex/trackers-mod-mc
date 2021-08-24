package ch.baramex.trackersmod.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.util.concurrent.ListenableFuture;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.SoundsHandler;
import ch.baramex.trackersmod.gui.GuiProxy;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.tileentity.TileEntityServer;
import ch.baramex.trackersmod.tileentity.TileEntityTracker;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy {

	public void preInit() {
		ModBlocks.INSTANCE.registerTE();
		messages.registerMessage("trackersmod");
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiProxy());
		SoundsHandler.registerSounds();	
	}
	
	public void postInit() {
		
	}
	
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		throw new IllegalStateException("error");
	}
	
	public EntityPlayer getClientPlayer() {
		throw new IllegalStateException("error");
	}
	
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void onDIsconnect(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent e) {
		for(TileEntity te : e.player.world.loadedTileEntityList) {
			if(te instanceof TileEntityTracker) {
				TileEntityTracker tile = ((TileEntityTracker)te);
				tile.clientAntenne = new HashMap<String, Integer>();
				tile.clientIsGood = new HashMap<String, String>();
				tile.clientPlayerEntities = new HashMap<String, List<String>>();
				tile.clientResult = new HashMap<String, String>();
			}
			if(te instanceof TileEntityTrackerAdv) {
				TileEntityTrackerAdv tile = ((TileEntityTrackerAdv)te);
				tile.clientAntenne = new HashMap<String, Integer>();
				tile.clientIsGood = new HashMap<String, String>();
				tile.clientPlayerEntities = new HashMap<String, List<String>>();
				tile.clientResult = new HashMap<String, String>();
				tile.clientItemStack = new HashMap<String, ItemStack>();
			}
			if(te instanceof TileEntityServer) {
				TileEntityServer tile = ((TileEntityServer)te);
				tile.clientInt = new HashMap<String, Map<String, Integer>>();
			}
		}
	}
}
