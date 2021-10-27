package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.tileentity.BoundaryWardStoneTileEntity;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;

import java.util.Objects;

public class BoundaryWardStoneContainer extends Container {

	public final BoundaryWardStoneTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	public BoundaryWardStoneContainer(final int windowId, final PlayerInventory playerInventory,
                                      final BoundaryWardStoneTileEntity tileEntity) {
		super(ModContainerTypes.BOUNDARY_WARD_STONE.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());

		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getFire().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.FireEnergy.setEnergy(value);
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getWater().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.WaterEnergy.setEnergy(value);
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getAir().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.AirEnergy.setEnergy(value);
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getEarth().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.EarthEnergy.setEnergy(value);
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getDark().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.DarkEnergy.setEnergy(value);
			}
		});
		addDataSlot(new IntReferenceHolder() {
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

	private static BoundaryWardStoneTileEntity getTileEntity(final PlayerInventory playerInventory,
														 final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof BoundaryWardStoneTileEntity) {
			return (BoundaryWardStoneTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public BoundaryWardStoneContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, BlockInitNew.MASTER_WARD_STONE.get());
	}

	public CustomEnergyStorage getAir(){
		BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
		return tile.AirEnergy;
	}

	public CustomEnergyStorage getFire(){
		BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
		return tile.FireEnergy;
	}
	public CustomEnergyStorage getWater(){
		BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
		return tile.WaterEnergy;
	}
	public CustomEnergyStorage getEarth(){
		BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
		return tile.EarthEnergy;
	}
	public CustomEnergyStorage getDark(){
		BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
		return tile.DarkEnergy;
	}
	public CustomEnergyStorage getLight(){
		BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
		return tile.LightEnergy;
	}
}