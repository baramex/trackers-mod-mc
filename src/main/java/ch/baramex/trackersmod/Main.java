package ch.baramex.trackersmod;

import java.io.File;

import ch.baramex.trackersmod.capabilities.ModCapabilities;
import ch.baramex.trackersmod.config.Config;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.proxy.CommonProxy;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.VersionChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/*
#####         tracker's mod          #####
#          Developed by Baramex          #
### github: https://github.com/baramex ###

License: lgpl-3.0
*/


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	public static VersionChecker versionChecker;
	public static boolean alreadyVersionWarned = false;
	public static File config;
	
	@Mod.Instance(Reference.MODID)
	public static Main instance;
	
	public static final CreativeTabs tabsMod = new CreativeTabs("trackersmod") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.tracker);
		}
	};

	public Main() {
		MinecraftForge.EVENT_BUS.register(new RegisterEvent());
	}
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit();
		ModCapabilities.registerCapabilities();
		Config.registerConfig(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit();
	}
}
