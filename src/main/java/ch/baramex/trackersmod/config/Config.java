package ch.baramex.trackersmod.config;

import java.io.File;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.utils.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class Config {
	private static Configuration config;
	
	public static int MAX_STORED_ENERGY_SERVER = 1000000;
	public static int MAX_ENERGY_RECEIVE_PER_TICK_SERVER = 100000;
	
	public static int ENERGY_CONSUME_TRACKER = 50000;
	public static int MIN_RANGE_TRACKER = 400;
	public static int MAX_RANGE_TRACKER = 500;
	public static int MIN_PRECISION_TRACKER = 100;
	public static int MAX_PRECISION_TRACKER = 150;
	public static int MIN_ANTENNA = 1;
	public static int MAX_ANTENNA = 3;
	
	public static int ENERGY_CONSUME_TRACKER_ADV = 500000;
	public static int MIN_RANGE_TRACKER_ADV = 3500;
	public static int MAX_RANGE_TRACKER_ADV = 5000;
	public static int MIN_PRECISION_TRACKER_ADV = 5;
	public static int MAX_PRECISION_TRACKER_ADV = 50;
	public static int MIN_ANTENNA_ADV = 5;
	public static int MAX_ANTENNA_ADV = 10;
	
	public static void init(File file) {
		config = new Configuration(file);
		
		config.addCustomCategoryComment("Server Block", "Set server properties");
		MAX_STORED_ENERGY_SERVER = config.getInt("ENERGY_STORAGE_SERVER_MAX", "Server Block", 1000000, 10000, 10000000, "Energy stored in server block");
		MAX_ENERGY_RECEIVE_PER_TICK_SERVER = config.getInt("ENERGY_RECEIVE_PER_TICK_SERVER_MAX", "Server Block", 100000, 1000, 1000000, "The energy the server can receive per tick");
		
		config.addCustomCategoryComment("Tracker Block", "Set tracker properties");
		ENERGY_CONSUME_TRACKER = config.getInt("ENERGY_CONSUME_TRACKER", "Tracker Block", 50000, 1000, 1000000, "The energy the tracker consumes");
		MIN_RANGE_TRACKER = config.getInt("RANGE_TRACKER_MIN", "Tracker Block", 400, 50, 10000, "The tracker's range when it has the minimum antenna");
		MAX_RANGE_TRACKER = config.getInt("RANGE_TRACKER_MAX", "Tracker Block", 500, 50, 10000, "The tracker's range when it has the maximum antenna");
		MIN_PRECISION_TRACKER = config.getInt("PRECISION_TRACKER_MIN", "Tracker Block", 100, 0, 1000, "The best tracker's precision when it has the maximum antenna");
		MAX_PRECISION_TRACKER = config.getInt("PRECISION_TRACKER_MAX", "Tracker Block", 150, 0, 2500, "The worst tracker's precision when it has the minimum antenna");
		MIN_ANTENNA = config.getInt("ANTENNA_MIN", "Tracker Block", 1, 0, 50, "The minimum antenna for the tracker to work");
		MAX_ANTENNA = config.getInt("ANTENNA_MAX", "Tracker Block", 3, 0, 50, "The maximum antenna");
		
		config.addCustomCategoryComment("Advanced tracker Block", "Set advanced tracker properties");
		ENERGY_CONSUME_TRACKER_ADV = config.getInt("ENERGY_CONSUME_TRACKER", "Advanced tracker Block", 500000, 1000, 1000000, "The energy the tracker consumes");
		MIN_RANGE_TRACKER_ADV = config.getInt("RANGE_TRACKER_MIN", "Advanced tracker Block", 3500, 50, 10000, "The tracker's range when it has the minimum antenna");
		MAX_RANGE_TRACKER_ADV = config.getInt("RANGE_TRACKER_MAX", "Advanced tracker Block", 5000, 50, 10000, "The tracker's range when it has the maximum antenna");
		MIN_PRECISION_TRACKER_ADV = config.getInt("PRECISION_TRACKER_MIN", "Advanced tracker Block", 5, 0, 1000, "The best tracker's precision when it has the maximum antenna");
		MAX_PRECISION_TRACKER_ADV = config.getInt("PRECISION_TRACKER_MAX", "Advanced tracker Block", 50, 0, 2500, "The worst tracker's precision when it has the minimum antenna");
		MIN_ANTENNA_ADV = config.getInt("ANTENNA_MIN", "Advanced tracker Block", 5, 0, 50, "The minimum antenna for the tracker to work");
		MAX_ANTENNA_ADV = config.getInt("ANTENNA_MAX", "Advanced tracker Block", 10, 0, 50, "The maximum antenna");
		
		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event) {
		Main.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		Main.config.mkdirs();
		init(new File(Main.config.getPath(), Reference.MODID + ".cfg"));
	}
}
