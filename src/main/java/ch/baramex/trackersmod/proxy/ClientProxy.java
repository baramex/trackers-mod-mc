package ch.baramex.trackersmod.proxy;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.init.ModItems;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.TextComponents;
import ch.baramex.trackersmod.utils.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

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
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		EntityPlayer player = e.player;
		
		ITextComponent text = new TextComponentTranslation("message." + Reference.MODID + ".login");
		text.appendSibling(new TextComponentString(" tracker's mod ").setStyle(TextComponents.styleLink("https://www.curseforge.com/minecraft/mc-mods/trackers-mod-by-baramex", "tracker's mod curse forge")));
		text.appendSibling(new TextComponentTranslation("message." + Reference.MODID + ".dev"));
		text.appendSibling(new TextComponentString(" baramex").setStyle(TextComponents.styleLink("https://github.com/baramex", "baramex github")));
		text.getStyle().setColor(TextFormatting.GREEN);
		
		player.sendMessage(text);
		
		if(Main.versionChecker.isLatestVersion()) {
			ITextComponent uptodate = new TextComponentTranslation("message." + Reference.MODID + ".uptodate");
        	uptodate.getStyle().setColor(TextFormatting.DARK_GREEN);
        	
        	player.sendMessage(uptodate);
		}
		else {
			ITextComponent release = new TextComponentTranslation("message." + Reference.MODID + ".newrelease");
        	release.appendSibling(new TextComponentString(" " + Main.versionChecker.getLatestVersion() + ", " + new TextComponentTranslation("message." + Reference.MODID + ".download").getFormattedText()).setStyle(TextComponents.styleLink("https://www.curseforge.com/minecraft/mc-mods/trackers-mod-by-baramex/files", "tracker's mod curse forge")));
        	release.getStyle().setColor(TextFormatting.RED);
        	
        	player.sendMessage(release);
		}
	}
}