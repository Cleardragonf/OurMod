package com.cleardragonf.ourmod.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class WardInside extends Block {

    public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 15);

    public WardInside() {
        super(Properties.of(Material.AIR)
        );
    }



    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(LIGHT);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public boolean isAir(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT, BlockStateProperties.POWERED, LIGHT);
    }
}