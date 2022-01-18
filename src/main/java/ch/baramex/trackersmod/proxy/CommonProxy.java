package ch.baramex.trackersmod.proxy;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.SoundsHandler;
import ch.baramex.trackersmod.gui.GuiProxy;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.TextComponents;
import ch.baramex.trackersmod.utils.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		//ERROR HERE
		Main.versionChecker = new VersionChecker();
		Thread versionCheckerThread = new Thread(Main.versionChecker, "Version Checker");
		versionCheckerThread.start();
	}
	
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		throw new IllegalStateException("error");
	}
	
	public EntityPlayer getClientPlayer() {
		throw new IllegalStateException("error");
	}
}