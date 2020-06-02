package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class MCMChestContainer extends Container {

	public final MCMChestTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	public MCMChestContainer(final int windowId, final PlayerInventory playerInventory,
							 final MCMChestTileEntity tileEntity) {
		super(ModContainerTypes.MCM_CHEST.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

		//Output Slot
		this.addSlot(new Slot(tileEntity, 105, 134,31));

		// Input Inventory
		int inputstartX = 62;
		int inputstartY = 17;
		int inputslotSizePlus2 = 18;
		for (int row = 0; row < 2; ++row) {
			for (int column = 0; column < 2; ++column) {
				this.addSlot(new Slot(tileEntity, 100 +(row * 9) + column, inputstartX + (column * inputslotSizePlus2),
						inputstartY + (row * inputslotSizePlus2)));
			}
		}

		//Main Inventory
		int startX = 8;
		int startY = 61;
		int slotSizePlus2 = 18;
		for (int row = 0; row < 6; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(tileEntity, (row * 9) + column, startX + (column * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}


		// Main Player Inventory
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, 8 + (column * slotSizePlus2),
						174 + (row * slotSizePlus2)));
			}
		}

		// Hotbar
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, 8 + (column * slotSizePlus2), 232));
		}

	}

	private static MCMChestTileEntity getTileEntity(final PlayerInventory playerInventory,
													final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof MCMChestTileEntity) {
			return (MCMChestTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public MCMChestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInitNew.MCM_CHEST.get());
	}
/*
	public CustomEnergyStorage getAir(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.AirEnergy;
	}

	public CustomEnergyStorage getFire(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.FireEnergy;
	}
	public CustomEnergyStorage getWater(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.WaterEnergy;
	}
	public CustomEnergyStorage getEarth(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.EarthEnergy;
	}
	public CustomEnergyStorage getDark(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.DarkEnergy;
	}
	public CustomEnergyStorage getLight(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.LightEnergy;
	}

 */
}