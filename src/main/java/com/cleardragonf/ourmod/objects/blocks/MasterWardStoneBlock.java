package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

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

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
											 Hand hand, BlockRayTraceResult result) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof MasterWardStoneTileEntity) {

				CompoundNBT tag;
				ItemStack item = player.getHeldItem(hand);
				if(item.hasTag()){
					tag = item.getTag();
				}else{
					tag = new CompoundNBT();
				}
				if(tag.contains("wardshape")){
					MasterWardStoneTileEntity tileEntity = (MasterWardStoneTileEntity) world.getTileEntity(pos);
					tileEntity.addWardStone(tag.get("wardshape"));
					tileEntity.markDirty();
					tileEntity.updateBlock();
				}



				NetworkHooks.openGui((ServerPlayerEntity) player, (MasterWardStoneTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof MasterWardStoneTileEntity) {
			MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((MasterWardStoneTileEntity)tileEntity).write(tag);

			item.setTag(tag);

			ItemEntity entity = new ItemEntity(worldIn, pos.getX() + .5, pos.getY(), pos.getZ() + .5, item);
			worldIn.addEntity(entity);
		}
		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof MasterWardStoneTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((MasterWardStoneTileEntity)tileEntity).readRestorableNBT(tag);
				worldIn.notifyBlockUpdate(pos, getDefaultState(), getDefaultState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}
}
