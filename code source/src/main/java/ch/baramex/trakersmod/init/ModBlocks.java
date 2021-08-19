package ch.baramex.trakersmod.init;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ch.baramex.trakersmod.Main;
import ch.baramex.trakersmod.machines.Antenne;
import ch.baramex.trakersmod.machines.Brouiller;
import ch.baramex.trakersmod.machines.ServerB;
import ch.baramex.trakersmod.machines.Tracker;
import ch.baramex.trakersmod.machines.TrackerAdv;
import ch.baramex.trakersmod.tileentity.TileEntityServer;
import ch.baramex.trakersmod.tileentity.TileEntityTracker;
import ch.baramex.trakersmod.tileentity.TileEntityTrackerAdv;
import ch.baramex.trakersmod.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
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
	
	public static Block tracker = new Tracker(Material.IRON, "tracker");
	public static Block server = new ServerB(Material.IRON, "server");
	public static Block trackerAdv = new TrackerAdv(Material.IRON, "trackerAdv");
	public static Block antenne = new Antenne(Material.IRON, "antenne");
	public static Block brouiller = new Brouiller(Material.IRON, "brouiller");
	
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
