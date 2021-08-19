package ch.baramex.trakersmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import ch.baramex.trakersmod.init.ModBlocks;
import ch.baramex.trakersmod.init.ModItems;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

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
