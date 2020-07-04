package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;

import java.util.Objects;

public class MasterWardStoneContainer extends Container {

	public final MasterWardStoneTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	public MasterWardStoneContainer(final int windowId, final PlayerInventory playerInventory,
                                    final MasterWardStoneTileEntity tileEntity) {
		super(ModContainerTypes.MASTER_WARD_STONE.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getFire().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.FireEnergy.setEnergy(value);
			}
		});
		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getWater().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.WaterEnergy.setEnergy(value);
			}
		});
		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getAir().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.AirEnergy.setEnergy(value);
			}
		});
		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getEarth().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.EarthEnergy.setEnergy(value);
			}
		});
		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getDark().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.DarkEnergy.setEnergy(value);
			}
		});
		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getLight().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.LightEnergy.setEnergy(value);
			}
		});


	}

	private static MasterWardStoneTileEntity getTileEntity(final PlayerInventory playerInventory,
														 final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof MasterWardStoneTileEntity) {
			return (MasterWardStoneTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public MasterWardStoneContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInitNew.MASTER_WARD_STONE.get());
	}

	public CustomEnergyStorage getAir(){
		MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
		return tile.AirEnergy;
	}

	public CustomEnergyStorage getFire(){
		MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
		return tile.FireEnergy;
	}
	public CustomEnergyStorage getWater(){
		MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
		return tile.WaterEnergy;
	}
	public CustomEnergyStorage getEarth(){
		MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
		return tile.EarthEnergy;
	}
	public CustomEnergyStorage getDark(){
		MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
		return tile.DarkEnergy;
	}
	public CustomEnergyStorage getLight(){
		MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
		return tile.LightEnergy;
	}
}