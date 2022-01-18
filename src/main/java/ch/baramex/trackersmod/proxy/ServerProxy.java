package ch.baramex.trackersmod.proxy;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.TextComponents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ServerProxy extends CommonProxy {
	@Override
	public void preInit() {
		super.preInit();
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void postInit() {
		super.postInit();
	}
	
	public ServerProxy() {
		FMLCommonHandler.instance().bus().register(this);
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
