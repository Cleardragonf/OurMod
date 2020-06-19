package com.cleardragonf.ourmod.container;

import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.objects.items.XPBottle;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import com.cleardragonf.ourmod.tileentity.XPStorageTileEntity;
import com.cleardragonf.ourmod.util.ExperienceUtil;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class XPStorageContainer
		extends Container
{
	private final IInventory inputInventory;
	private final IInventory outputInventory;
	private final PlayerInventory playerInventory;
	private final IWorldPosCallable worldPosCallable;
	private int expValue;

	public XPStorageContainer(int id, PlayerInventory playerInventory, PacketBuffer data)
	{
		this( id, playerInventory, IWorldPosCallable.DUMMY );
	}

	public XPStorageContainer( int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable )
	{
		super( ModContainerTypes.XP_STORAGE.get(), id );
		this.inputInventory = new Inventory( 1 )
		{
			@Override
			public void markDirty()
			{
				super.markDirty();
				onCraftMatrixChanged( this );
			}
		};
		this.outputInventory = new CraftResultInventory();
		this.playerInventory = playerInventory;
		this.worldPosCallable = worldPosCallable;
		this.inputInventory.openInventory( playerInventory.player );

		addSlot( new Slot( this.inputInventory, 0, 17, 37 )
		{
			@Override
			public boolean isItemValid( ItemStack stack )
			{
				return stack.getItem() == net.minecraft.item.Items.GLASS_BOTTLE;
			}
		} );

		addSlot( new Slot( this.outputInventory, 0, 17, 78 )
		{
			@Override
			public boolean isItemValid( ItemStack stack )
			{
				return false;
			}

			@Override
			public int getSlotStackLimit()
			{
				return 1;
			}

			@Override
			public ItemStack onTake( PlayerEntity thePlayer, ItemStack stack )
			{
				onCraftMatrixChanged( outputInventory );
				return super.onTake( thePlayer, stack );
			}
		} );

		for ( int i = 0; i < 3; ++i )
		{
			for ( int j = 0; j < 9; ++j )
			{
				addSlot( new Slot( playerInventory, j + i * 9 + 9, 38 + j * 18, 122 + i * 18 ) );
			}
		}

		for ( int i = 0; i < 9; ++i )
		{
			addSlot( new Slot( playerInventory, i, 38 + i * 18, 180 ) );
		}
	}


	public void setBottlingExp( int expValue )
	{
		this.expValue = Math.max( expValue, 0 );
		onCraftMatrixChanged( this.inputInventory );
	}

	public boolean takeBottledExp(int dragType, ClickType clickTypeIn, PlayerEntity player )
	{
		if ( !inputInventory.getStackInSlot( 0 ).isEmpty() )
		{
			int playerExp = ExperienceUtil.getPlayerExp( player );
			ItemStack stackInSlot1 = outputInventory.getStackInSlot( 0 );
			ItemStack copy = stackInSlot1.copy();
			int tagExp = XPBottle.getTagExperience( stackInSlot1 );
			if ( tagExp > 0 && playerExp >= tagExp )
			{
				ItemStack slotClick = slotClick( 1, dragType, clickTypeIn, player );
				if ( slotClick.getItem() == copy.getItem() &&
						Objects.equals( slotClick.getTag(), copy.getTag() ) )
				{
					if ( !player.world.isRemote() )
					{
						ExperienceUtil.removeExpFromPlayer( player, tagExp );
					}
					inputInventory.decrStackSize( 0, 1 );
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canInteractWith( PlayerEntity playerIn )
	{
		return isWithinUsableDistance( worldPosCallable, playerIn, BlockInitNew.XP_STORAGE_BLOCK.get() );
	}

	@Override
	public void onContainerClosed( PlayerEntity playerIn )
	{
		super.onContainerClosed( playerIn );
		worldPosCallable.consume( ( world, blockPos ) -> {
			clearContainer( playerIn, world, this.inputInventory );
		} );
	}

	@Override
	public ItemStack transferStackInSlot( PlayerEntity playerIn, int index )
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get( index );
		if ( slot != null && slot.getHasStack() )
		{
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if ( index == 0 || index == 1 )
			{
				if ( !mergeItemStack( stackInSlot, 2, 38, true ) )
				{
					return ItemStack.EMPTY;
				}
			}
			else if ( !mergeItemStack( stackInSlot, 0, 1, false ) )
			{
				return ItemStack.EMPTY;
			}

			if ( stackInSlot.isEmpty() )
			{
				slot.putStack( ItemStack.EMPTY );
			}
			else
			{
				slot.onSlotChanged();
			}

			if ( stack.getCount() == stackInSlot.getCount() )
			{
				return ItemStack.EMPTY;
			}

			slot.onTake( playerIn, stack );
		}

		return stack;
	}

	@Override
	public void onCraftMatrixChanged( IInventory inventoryIn )
	{
		boolean flag = expValue > 0 && !inputInventory.getStackInSlot( 0 ).isEmpty();
		if ( flag )
		{
			PlayerEntity player = playerInventory.player;
			flag = ExperienceUtil.getPlayerExp( player ) >= expValue;
			if ( flag )
			{
				ItemStack stack = new ItemStack( Items.EXPERIENCE_BOTTLE );
				XPBottle.setTagExperience( stack, expValue );
				outputInventory.setInventorySlotContents( 0, stack );
			}
		}

		if ( !flag )
		{
			outputInventory.setInventorySlotContents( 1, ItemStack.EMPTY );
		}
		detectAndSendChanges();
	}

	@Override
	public ItemStack slotClick( int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player )
	{
		if ( slotId == 1 )
		{
			if ( clickTypeIn == ClickType.QUICK_MOVE )
			{
				ItemStack item = transferStackInSlot( player, slotId );
				detectAndSendChanges();
				return item;
			}
			else if ( clickTypeIn != ClickType.PICKUP )
			{
				return ItemStack.EMPTY;
			}
		}
		return super.slotClick( slotId, dragType, clickTypeIn, player );
	}

	@Override
	public boolean canMergeSlot( ItemStack stack, Slot slotIn )
	{
		return slotIn.slotNumber == 0;
	}

	@Override
	public boolean canDragIntoSlot( Slot slotIn )
	{
		return slotIn.slotNumber == 0;
	}
}