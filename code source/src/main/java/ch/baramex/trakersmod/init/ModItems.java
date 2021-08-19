package ch.baramex.trakersmod.init;

import java.util.ArrayList;
import java.util.List;

import ch.baramex.trakersmod.Main;
import ch.baramex.trakersmod.utils.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ModItems {
	
	public static final ModItems INSTANCE = new ModItems();
	
	private List<Item> items;
	
	public static Item anti_tracker;
	
	public void initItems() {
		items = new ArrayList<Item>();
		
		anti_tracker = new Item().setUnlocalizedName("anti_tracker").setRegistryName("anti_tracker").setCreativeTab(Main.tabsMod).setMaxStackSize(1);
		items.add(anti_tracker);
	}
	
	@SubscribeEvent
	public void textures(ModelRegistryEvent e) {
		for(Item item : items) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
		}
	}
	
	public List<Item> getItems() {
		return items;
	}
}
