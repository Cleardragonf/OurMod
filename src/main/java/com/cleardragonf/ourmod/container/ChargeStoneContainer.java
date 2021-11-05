package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.tileentity.ChargeStoneTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Objects;

public class ChargeStoneContainer extends Container {

	private ChargeStoneTileEntity tileEntity;
	private IItemHandler playerInventory;
	private final IWorldPosCallable canInteractWithCallable;



	public ChargeStoneContainer(final int windowId, final PlayerInventory playerInventory,
                                final ChargeStoneTileEntity tileEntity) {
		super(ModContainerTypes.CHARGE_STONE.get(), windowId);
		this.playerInventory = new InvWrapper(playerInventory);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
		/*Depressiated
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			addSlot(new SlotItemHandler(h, 0, 134, 31));
		});

		 */
		//Chest Mimic Slot(1)
		addSlot(new SlotItemHandler(((ChargeStoneTileEntity) tileEntity).inventory, 0,134,31));
		//Chest Contents
		addSlotBox(((ChargeStoneTileEntity) tileEntity).inventory, 5,8,61,9,18,6,18);
		//INPUT Slots (4)
		addSlotBox(((ChargeStoneTileEntity) tileEntity).inventory, 1,62,17,2,18,2,18);

		layoutPlayerInventorySlots(8, 70);

		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getMCM().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.MCMEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.MCMEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getMCM().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.MCMEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.MCMEnergy.setEnergy(energyStored | (value << 16));
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getAir().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.AirEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.AirEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getAir().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.AirEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.AirEnergy.setEnergy(energyStored | (value << 16));
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getDark().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.DarkEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.DarkEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getDark().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.DarkEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.DarkEnergy.setEnergy(energyStored | (value << 16));
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getEarth().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.EarthEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.EarthEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getEarth().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.EarthEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.EarthEnergy.setEnergy(energyStored | (value << 16));
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getFire().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.FireEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.FireEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getFire().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.FireEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.FireEnergy.setEnergy(energyStored | (value << 16));
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getLight().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.LightEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.LightEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getLight().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.LightEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.LightEnergy.setEnergy(energyStored | (value << 16));
			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return getWater().getEnergyStored() & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.WaterEnergy.getEnergyStored() & 0xffff0000;
				tileEntity.WaterEnergy.setEnergy(energyStored + (value & 0xffff));

			}
		});
		addDataSlot(new IntReferenceHolder() {
			@Override
			public int get() {
				return (getWater().getEnergyStored() >> 16) & 0xffff;
			}

			@Override
			public void set(int value) {
				int energyStored = tileEntity.WaterEnergy.getEnergyStored() & 0x0000ffff;
				tileEntity.WaterEnergy.setEnergy(energyStored | (value << 16));
			}
		});

	}

	public ChargeStoneContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	private static ChargeStoneTileEntity getTileEntity(final PlayerInventory playerInventory,
															 final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof ChargeStoneTileEntity) {
			return (ChargeStoneTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public int getEnergy(){
		return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
	}


	public CustomEnergyStorage getFire(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.FireEnergy;
	}
	public CustomEnergyStorage getWater(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.WaterEnergy;
	}
	public CustomEnergyStorage getAir(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.AirEnergy;
	}
	public CustomEnergyStorage getEarth(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.EarthEnergy;
	}
	public CustomEnergyStorage getLight(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.LightEnergy;
	}
	public CustomEnergyStorage getDark(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.DarkEnergy;
	}
	public CustomEnergyStorage getMCM(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.MCMEnergy;
	}

	public INBT getBlockEnergy(){
		ChargeStoneTileEntity tile = (ChargeStoneTileEntity) tileEntity;
		return tile.energyblocks;
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerIn, BlockInitNew.CHARGE_STONE.get());
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			itemstack = stack.copy();
			if (index == 0) {
				if (!this.moveItemStackTo(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stack, itemstack);
			} else {
				if (stack.getItem() == Items.DIAMOND) {
					if (!this.moveItemStackTo(stack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 28) {
					if (!this.moveItemStackTo(stack, 28, 37, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack);
		}

		return itemstack;
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
}