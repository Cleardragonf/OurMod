package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ForgeAnvil extends Block {

	public ForgeAnvil() {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(2.0f)
		);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(BlockStateProperties.POWERED) ? super.getLightValue(state,world ,pos) : 0;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new MCMChestTileEntity();
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		super.setPlacedBy(world, pos, state, entity, stack);

		TileEntity tileEntity = world.getBlockEntity(pos);
		if(tileEntity instanceof MCMChestTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((MCMChestTileEntity)tileEntity).readRestorableNBT(tag);
				world.sendBlockUpdated(pos, defaultBlockState(), defaultBlockState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}


	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
		if (!world.isClientSide()) {
			TileEntity tile = world.getBlockEntity(pos);
			if (tile instanceof MCMChestTileEntity) {

				CompoundNBT tag;
				ItemStack item = player.getItemInHand(hand);
				if(item.hasTag()){
					tag = item.getTag();
				}else{
					tag = new CompoundNBT();
				}
				if(tag.contains("energypos")){
					MCMChestTileEntity tileEntity = (MCMChestTileEntity) world.getBlockEntity(pos);
					tileEntity.energyblocks = tag.get("energypos");
					tileEntity.setChanged();
					tileEntity.updateBlock();
				}



				NetworkHooks.openGui((ServerPlayerEntity) player, (MCMChestTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if(tileEntity instanceof MCMChestTileEntity) {
			MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((MCMChestTileEntity)tileEntity).save(tag);

			item.setTag(tag);

			ItemEntity entity = new ItemEntity(worldIn, pos.getX() + .5, pos.getY(), pos.getZ() + .5, item);
			worldIn.addFreshEntity(entity);
		}
	}

	public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
		Vector3d vec = entity.position();
		return Direction.getNearest((float) (vec.x - clickedBlock.getX()), (float) (vec.y - clickedBlock.getY()), (float) (vec.z - clickedBlock.getZ()));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
	}
}
