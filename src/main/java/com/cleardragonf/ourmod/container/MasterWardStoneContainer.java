package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Objects;

public class MasterWardStoneContainer extends Container {

	public final MasterWardStoneTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;
	private IItemHandler playerInventory;

	public MasterWardStoneContainer(final int windowId, final PlayerInventory playerInventory,
                                    final MasterWardStoneTileEntity tileEntity) {
		super(ModContainerTypes.MASTER_WARD_STONE.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());

		this.playerInventory = new InvWrapper(playerInventory);

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


		int startX = 8;
		int startY = 173;
		int slotSizePlus2 = 18;

		//MainSlots
		addSlot(new SlotItemHandler(((MasterWardStoneTileEntity) tileEntity).inventory, 0,80,12));
		addSlot(new SlotItemHandler(((MasterWardStoneTileEntity) tileEntity).inventory, 1,134,66));
		addSlot(new SlotItemHandler(((MasterWardStoneTileEntity) tileEntity).inventory, 2,80,120));
		addSlot(new SlotItemHandler(((MasterWardStoneTileEntity) tileEntity).inventory, 3,26,66));
		addSlot(new SlotItemHandler(((MasterWardStoneTileEntity) tileEntity).inventory, 4,80,66));

		layoutPlayerInventorySlots(8,173);
	}
	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for (int i = 0 ; i < amount ; i++) {
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for (int j = 0 ; j < verAmount ; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}

	private void layoutPlayerInventorySlots(int leftCol, int topRow) {
		// Player inventory
		addSlotBox(playerInventory, 9, leftCol, 174, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, 232, 9, 18);
	}

	private static MasterWardStoneTileEntity getTileEntity(final PlayerInventory playerInventory,
														 final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof MasterWardStoneTileEntity) {
			return (MasterWardStoneTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public MasterWardStoneContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, BlockInitNew.MASTER_WARD_STONE.get());
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