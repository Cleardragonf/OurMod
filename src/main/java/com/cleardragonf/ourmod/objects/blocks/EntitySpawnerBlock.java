package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.tileentity.EntitySpawnerTileEntity;
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
public class EntitySpawnerBlock extends Block {

	public EntitySpawnerBlock() {
		super(Properties.create(Material.IRON)
				.sound(SoundType.METAL)
				.hardnessAndResistance(2.0f)
		);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new EntitySpawnerTileEntity();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entity, stack);

		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof EntitySpawnerTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((EntitySpawnerTileEntity)tileEntity).readRestorableNBT(tag);
				world.notifyBlockUpdate(pos, getDefaultState(), getDefaultState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}


	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof EntitySpawnerTileEntity) {

				CompoundNBT tag;
				ItemStack item = player.getHeldItem(hand);
				if(item.hasTag()){
					tag = item.getTag();
				}else{
					tag = new CompoundNBT();
				}
				if(tag.contains("energypos")){
					EntitySpawnerTileEntity tileEntity = (EntitySpawnerTileEntity) world.getTileEntity(pos);
					tileEntity.energyblocks = tag.get("energypos");
					tileEntity.markDirty();
					tileEntity.updateBlock();
				}



				NetworkHooks.openGui((ServerPlayerEntity) player, (EntitySpawnerTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof EntitySpawnerTileEntity) {
			EntitySpawnerTileEntity tile = (EntitySpawnerTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((EntitySpawnerTileEntity)tileEntity).write(tag);

			item.setTag(tag);

			ItemEntity entity = new ItemEntity(worldIn, pos.getX() + .5, pos.getY(), pos.getZ() + .5, item);
			worldIn.addEntity(entity);
		}
	}

	public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
		Vector3d vec = entity.getPositionVec();
		return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()), (float) (vec.y - clickedBlock.getY()), (float) (vec.z - clickedBlock.getZ()));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
	}
}