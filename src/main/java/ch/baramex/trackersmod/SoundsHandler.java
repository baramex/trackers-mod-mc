package ch.baramex.trackersmod;

import ch.baramex.trackersmod.utils.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {
	
	public static SoundEvent BEEP;
	public static int BEEP_DURATION = 982;
	
	public static void registerSounds() {
		BEEP = registerSound("block.server.beep");
	}
	
	public static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}