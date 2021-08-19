package ch.baramex.trakersmod.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;

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
}
