package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ItemInitNew;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CauliflowerCrop extends CropsBlock{

	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};


	public CauliflowerCrop(Properties builder) {
		super(builder);
	}
	
	@Override
	protected IItemProvider getBaseSeedId() {
		return ItemInitNew.CAULIFLOWER_SEED.get();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
	}
	
	public void resetAge() {
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
	}
	
	
	
}
