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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
		
	}
	
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		throw new IllegalStateException("error");
	}
	
	public EntityPlayer getClientPlayer() {
		throw new IllegalStateException("error");
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent e) {
		if(e.getWorld().isRemote) {
			if(e.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) e.getEntity();
				if(!Minecraft.getMinecraft().player.getName().equals(player.getName())) return;
				
				ITextComponent text = new TextComponentTranslation("message." + Reference.MODID + ".login");
				text.appendSibling(new TextComponentString(" tracker's mod ").setStyle(TextComponents.styleLink("https://www.curseforge.com/minecraft/mc-mods/trackers-mod-by-baramex", "tracker's mod curse forge")));
				text.appendSibling(new TextComponentTranslation("message." + Reference.MODID + ".dev"));
				text.appendSibling(new TextComponentString(" baramex").setStyle(TextComponents.styleLink("https://github.com/baramex", "baramex github")));
				text.getStyle().setColor(TextFormatting.GREEN);
				
				player.sendMessage(text);
				
				final CloseableHttpClient httpClient = HttpClients.createDefault();
				
				HttpGet request = new HttpGet("https://api.github.com/repos/baramex/trackers-mod-mc/releases/latest");
				
				request.addHeader("Content-Type", "application/json");
				
				try (CloseableHttpResponse response = httpClient.execute(request)) {

		            HttpEntity entity = response.getEntity();
		            Header headers = entity.getContentType();

		            if (entity != null) {
		                String result = EntityUtils.toString(entity);
		                
		                JsonObject obj = new JsonParser().parse(result).getAsJsonObject();
		                
		                if(!obj.get("tag_name").getAsString().equals("v" + Reference.VERSION)) {
		                	ITextComponent release = new TextComponentTranslation("message." + Reference.MODID + ".newrelease");
		                	release.appendSibling(new TextComponentString(" " + obj.get("tag_name").getAsString() + ", " + new TextComponentTranslation("message." + Reference.MODID + ".download").getFormattedText()).setStyle(TextComponents.styleLink("https://www.curseforge.com/minecraft/mc-mods/trackers-mod-by-baramex/files", "tracker's mod curse forge")));
		                	release.getStyle().setColor(TextFormatting.RED);
		                	
		                	player.sendMessage(release);
		                }
		                else {
		                	ITextComponent uptodate = new TextComponentTranslation("message." + Reference.MODID + ".uptodate");
		                	uptodate.getStyle().setColor(TextFormatting.DARK_GREEN);
		                	
		                	player.sendMessage(uptodate);
		                }
		            }

		        } catch (Exception  e1) {
					e1.printStackTrace();
				}
				
				try {
					httpClient.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
