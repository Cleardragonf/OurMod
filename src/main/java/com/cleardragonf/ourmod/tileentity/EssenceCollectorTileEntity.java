package com.cleardragonf.ourmod.tileentity;

import javax.annotation.Nullable;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.util.helpers.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class EssenceCollectorTileEntity extends TileEntity implements ITickableTileEntity{

	public int x, y, z, tick;
	boolean initialized = false;
	
	public EssenceCollectorTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public EssenceCollectorTileEntity() {
		this(ModTileEntityTypes.ESSENCE_COLLECTOR.get());
	}

	@Override
	public void tick() {
		System.out.print("essence");
	}
	

	
}
