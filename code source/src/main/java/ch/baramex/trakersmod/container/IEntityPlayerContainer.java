package ch.baramex.trakersmod.container;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public interface IEntityPlayerContainer {
	
	void syncEntityPlayer(List<String> players, String name);
}
