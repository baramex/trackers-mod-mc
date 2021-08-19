package ch.baramex.trackersmod.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.baramex.trackersmod.machines.Antenne;
import ch.baramex.trackersmod.machines.Brouiller;
import ch.baramex.trackersmod.machines.ServerB;
import ch.baramex.trackersmod.machines.Tracker;
import ch.baramex.trackersmod.machines.TrackerAdv;
import ch.baramex.trackersmod.tileentity.TileEntityServer;
import ch.baramex.trackersmod.tileentity.TileEntityTracker;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import ch.baramex.trackersmod.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	private List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final ModBlocks INSTANCE = new ModBlocks();
	
	public static Block tracker = new Tracker(Material.IRON, "tracker", Arrays.asList(new String[]{"§eNeed to be connected to a server", "§2Tracking up to 500 blocks", "§eRequires 1 antenna", "§4Consumes 50,000 RF / tracking"}));
	public static Block server = new ServerB(Material.IRON, "server", Arrays.asList(new String[]{"§26 machines can be connected to it", "§eRequires energy"}));
	public static Block trackerAdv = new TrackerAdv(Material.IRON, "trackerAdv", Arrays.asList(new String[]{"§eNeed to be connected to a server", "§2Tracking up to 5000 blocks and get current item of the player", "§eRequires 5 antenna", "§4Consumes 500,000 RF / tracking"}));
	public static Block antenne = new Antenne(Material.IRON, "antenne", Arrays.asList(new String[]{"§2Can be placed in an area of 20 blocks around a server at any height"}));
	public static Block brouiller = new Brouiller(Material.IRON, "brouiller", Arrays.asList(new String[]{"§2Is effective against any tracker within 20 player blocks"}));
	
	public List<Block> getBlocks() {
		return BLOCKS;
	}
	
	public void init() {
		for(Block block : BLOCKS) {
			ItemBlock ib = new ItemBlock(block);
			ib.setRegistryName(block.getRegistryName());
			GameRegistry.findRegistry(Item.class).register(ib);
		}
	}
	
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent e) {
		for(Block block : BLOCKS) {
			registerModel(block);
		}
	}
	
	public void registerModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
	}
	
	public void registerTE() {
		GameRegistry.registerTileEntity(TileEntityTracker.class, Reference.MODID + ":tracker");
		GameRegistry.registerTileEntity(TileEntityServer.class, Reference.MODID + ":server");
		GameRegistry.registerTileEntity(TileEntityTrackerAdv.class, Reference.MODID + ":trackerAdv");
	}
}
