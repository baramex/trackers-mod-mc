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

import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.init.ModItems;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.TextComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
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
}