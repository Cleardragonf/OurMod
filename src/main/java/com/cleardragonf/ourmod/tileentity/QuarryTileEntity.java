package com.cleardragonf.ourmod.tileentity;

import javax.annotation.Nullable;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.util.helpers.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class QuarryTileEntity extends TileEntity implements ITickableTileEntity{

	public int x, y, z, tick;
	boolean initialized = false;
	
	public QuarryTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public QuarryTileEntity() {
		this(ModTileEntityTypes.QUARRY.get());
	}

	@Override
	public void tick() {
		if (!initialized)
			init();
		tick++;
		if (tick == 40) {
			tick = 0;
			if (y > 2)
				execute();
		}
	}
	
	private void init() {
		initialized = true;
		x = this.worldPosition.getX() - 1;
		y = this.worldPosition.getY() - 1;
		z = this.worldPosition.getZ() - 1;
		tick = 0;
	}

	private void execute() {
		int index = 0;
		Block[] blocksRemoved = new Block[9];
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				BlockPos posToBreak = new BlockPos(this.x + x, this.y, this.z + z);
				blocksRemoved[index] = this.level.getBlockState(posToBreak).getBlock();
				destroyBlock(posToBreak, true, null);
				index++;
			}
		}
		this.y--;
	}

	private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
		BlockState blockstate = level.getBlockState(pos);
		if(blockstate.isAir(level, pos))return false;
		else {
			FluidState ifluidstate = level.getFluidState(pos);
			level.levelEvent(2001, pos, Block.getId(blockstate));
			if(dropBlock) {
				TileEntity tileentity= blockstate.hasTileEntity() ? level.getBlockEntity(pos) : null;
				Block.dropResources(blockstate, level, this.worldPosition.offset(0, 1.5, 0), tileentity, entity, ItemStack.EMPTY);
			}
			return level.setBlock(pos, ifluidstate.getFluidState().createLegacyBlock(), 3);
		}
		
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.put("initvalues", NBTHelper.toNBT(this));
		return super.save(compound);
	}
	
	@Override
	public void load(BlockState state,CompoundNBT compound) {
		super.load(state,compound);
		CompoundNBT initValues = compound.getCompound("initvalues");
		if(initValues != null) {
			this.x = initValues.getInt("x");
			this.y = initValues.getInt("y");
			this.z = initValues.getInt("z");
			this.tick = 0;
			initialized = true;
			return;
		}
		init();
	}
	
}
