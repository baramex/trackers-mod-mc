package ch.baramex.trakersmod;

import ch.baramex.trakersmod.init.ModBlocks;
import ch.baramex.trakersmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterEvent {

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> e) {
		ModItems.INSTANCE.initItems();
		
		e.getRegistry().registerAll(ModItems.INSTANCE.getItems().toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> e) {
		ModBlocks.INSTANCE.init();
		
		e.getRegistry().registerAll(ModBlocks.INSTANCE.getBlocks().toArray(new Block[0]));
	}
}
