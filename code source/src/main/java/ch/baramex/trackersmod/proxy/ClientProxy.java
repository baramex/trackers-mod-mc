package ch.baramex.trackersmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
		
		MinecraftForge.EVENT_BUS.register(ModBlocks.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ModItems.INSTANCE);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void postInit() {
		super.postInit();
	}
	
	public ClientProxy() {
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@Override
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
	}
	
	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}
}