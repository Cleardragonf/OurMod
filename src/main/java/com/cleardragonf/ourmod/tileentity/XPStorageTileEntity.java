package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.container.EssenceCollectorContainer;
import com.cleardragonf.ourmod.container.XPStorageContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.EssenceCollector;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class XPStorageTileEntity
		extends TileEntity
		implements INamedContainerProvider
{
	public XPStorageTileEntity()
	{
		super( ModTileEntityTypes.XP_STORAGE_BLOCK.get() );
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent( "container.exp_bottling.exp_bottling_machine" );
	}

	@Nullable
	@Override
	public Container createMenu( int id, PlayerInventory playerInventory, PlayerEntity player )
	{
		World world = Objects.requireNonNull( getWorld() );
		IWorldPosCallable worldPosCallable = IWorldPosCallable.of( world, getPos() );
		return new XPStorageContainer( id, playerInventory, worldPosCallable );
	}
}