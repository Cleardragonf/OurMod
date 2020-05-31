package com.cleardragonf.ourmod.essence;

import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
	public CustomEnergyStorage(final int capacity) {

		super(capacity);

	}

	public CustomEnergyStorage(final int capacity, final int maxTransfer) {

		super(capacity, maxTransfer);

	}


	public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract) {

		super(capacity, maxReceive, maxExtract);

	}


	public void addEnergy(int energy) {
		this.energy += energy;
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getEnergyStored();
		}
	}

	public void consumeEnergy(int energy) {
		this.energy -= energy;
		if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract, final int energy) {

		super(capacity, maxReceive, maxExtract, energy);

	}


	public int setEnergyStored(final int maxSet) {

		final int energyReceived = Math.min(this.capacity - this.energy, maxSet);

		this.energy += energyReceived;

		return energyReceived;

	}
}