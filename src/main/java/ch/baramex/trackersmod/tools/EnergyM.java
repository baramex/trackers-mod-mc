package ch.baramex.trackersmod.tools;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyM extends EnergyStorage {

	public EnergyM(int capacity, int maxReceive) {
		super(capacity, maxReceive, capacity);
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public void consumePower(int energy) {
		this.energy -= energy;
		if(this.energy < 0) {
			this.energy = 0;
		}
	}
}
