package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class MasterWardStoneBlock extends Block{

	public MasterWardStoneBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.MASTER_WARD_STONE.get().create();
	}

}
