package ch.baramex.trackersmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.init.ModItems;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.TextComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
	
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent e) {
		if(e.getWorld().isRemote) {
			if(e.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) e.getEntity();
				
				ITextComponent text = new TextComponentTranslation("message." + Reference.MODID + ".login");
				text.appendSibling(new TextComponentString(" tracker's mod").setStyle(TextComponents.styleLink("https://www.curseforge.com/minecraft/mc-mods/trackers-mod-by-baramex", "tracker's mod curse forge")));
				text.appendSibling(new TextComponentString(" by"));
				text.appendSibling(new TextComponentString(" baramex").setStyle(TextComponents.styleLink("https://github.com/baramex", "baramex github")));
				
				player.sendMessage(text);
			}
		}
	}
}