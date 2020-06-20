package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import com.cleardragonf.ourmod.tileentity.PortableChestTileEntity;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Objects;

public class MCMChestContainer extends Container {

	private MCMChestTileEntity tileEntity;
	private IItemHandler playerInventory;
	private final IWorldPosCallable canInteractWithCallable;



	public MCMChestContainer(final int windowId, final PlayerInventory playerInventory,
							 final MCMChestTileEntity tileEntity) {
		super(ModContainerTypes.MCM_CHEST.get(), windowId);
		this.playerInventory = new InvWrapper(playerInventory);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		/*Depressiated
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			addSlot(new SlotItemHandler(h, 0, 134, 31));
		});

		 */
		//Chest Mimic Slot(1)
		//((MCMChestTileEntity)tileEntity).handler.ifPresent(h ->addSlot(new SlotItemHandler(h, 0, 134, 31)));
		addSlot(new SlotItemHandler(((MCMChestTileEntity) tileEntity).inventory, 0,134,31));
		//Chest Contents
		//((MCMChestTileEntity) tileEntity).handler.ifPresent(h -> addSlotBox(h,5,8,61,9,18,6,18));
		addSlotBox(((MCMChestTileEntity) tileEntity).inventory, 5,8,61,9,18,6,18);
		//INPUT Slots (4)
		//((MCMChestTileEntity) tileEntity).handler.ifPresent(h -> addSlotBox(h,1,62,17,2,18,2,18));
		addSlotBox(((MCMChestTileEntity) tileEntity).inventory, 1,62,17,2,18,2,18);

		layoutPlayerInventorySlots(8, 70);


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
				return getMCM().getEnergyStored();
			}

			@Override
			public void set(int value) {
				tileEntity.MCMEnergy.setEnergy(value);
			}
		});
	}

	public MCMChestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
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



	public CustomEnergyStorage getFire(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.FireEnergy;
	}
	public CustomEnergyStorage getWater(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.WaterEnergy;
	}
	public CustomEnergyStorage getAir(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.AirEnergy;
	}
	public CustomEnergyStorage getEarth(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.EarthEnergy;
	}
	public CustomEnergyStorage getLight(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.LightEnergy;
	}
	public CustomEnergyStorage getDark(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.DarkEnergy;
	}
	public CustomEnergyStorage getMCM(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.MCMEnergy;
	}

	public String getBlockEnergy(){
		MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
		return tile.energyblocks;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, BlockInitNew.MCM_CHEST.get());
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			if (index == 0) {
				if (!this.mergeItemStack(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(stack, itemstack);
			} else {
				if (stack.getItem() == Items.DIAMOND) {
					if (!this.mergeItemStack(stack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 28) {
					if (!this.mergeItemStack(stack, 28, 37, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
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