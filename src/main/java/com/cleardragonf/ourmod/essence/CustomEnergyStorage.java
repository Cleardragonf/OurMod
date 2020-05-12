package com.cleardragonf.ourmod.essence;

import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage{

	public CustomEnergyStorage(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
		// TODO Auto-generated constructor stub
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public void addEnergy(int energy) {
		this.energy += energy;
		if(this.energy > getMaxEnergyStored()) {
			this.energy = getEnergyStored();
		}
	}

}
