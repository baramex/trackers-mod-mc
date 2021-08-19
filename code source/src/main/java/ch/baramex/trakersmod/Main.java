package ch.baramex.trakersmod;

import ch.baramex.trakersmod.capabilities.ModCapabilities;
import ch.baramex.trakersmod.gui.GuiProxy;
import ch.baramex.trakersmod.init.ModBlocks;
import ch.baramex.trakersmod.proxy.CommonProxy;
import ch.baramex.trakersmod.utils.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	@Mod.Instance(Reference.MODID)
	public static Main instance;
	
	public static final CreativeTabs tabsMod = new CreativeTabs("trackersmod") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.tracker);
		}
	};
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
	public Main() {
		MinecraftForge.EVENT_BUS.register(new RegisterEvent());
	}
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit();
		ModCapabilities.registerCapabilities();
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
