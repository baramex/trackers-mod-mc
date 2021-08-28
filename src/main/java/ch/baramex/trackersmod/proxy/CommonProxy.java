package ch.baramex.trackersmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.SoundsHandler;
import ch.baramex.trackersmod.gui.GuiProxy;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.utils.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public void preInit() {
		ModBlocks.INSTANCE.registerTE();
		messages.registerMessage(Reference.MODID);
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
}
