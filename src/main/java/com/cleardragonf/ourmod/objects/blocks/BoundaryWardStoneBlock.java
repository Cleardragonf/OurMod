package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.items.WardEnscriber;
import com.cleardragonf.ourmod.tileentity.BoundaryWardStoneTileEntity;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

import java.util.stream.Stream;

import static net.minecraft.block.DirectionalBlock.FACING;

public class BoundaryWardStoneBlock extends Block{


	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	private static final VoxelShape SHAPE_N = Stream.of(
			Block.box(4, 0, 4, 12, 4, 12),
			Block.box(5, 4, 6, 11, 7, 10),
			Block.box(6, 7, 7, 10, 9, 9)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_S = Stream.of(
			Block.box(4, 0, 4, 12, 4, 12),
			Block.box(5, 4, 6, 11, 7, 10),
			Block.box(6, 7, 7, 10, 9, 9)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_E = Stream.of(
			Block.box(4, 0, 4, 12, 4, 12),
			Block.box(6, 4, 5, 10, 7, 11),
			Block.box(7, 7, 6, 9, 9, 10)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_W = Stream.of(
			Block.box(4, 0, 4, 12, 4, 12),
			Block.box(6, 4, 5, 10, 7, 11),
			Block.box(7, 7, 6, 9, 9, 10)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	public BoundaryWardStoneBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)){
			case NORTH:
				return SHAPE_N;
			case SOUTH:
				return SHAPE_S;
			case EAST:
				return SHAPE_E;
			case WEST:
				return SHAPE_W;
			default:
				return	SHAPE_N;
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.BOUNDARY_WARD_STONE.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
											 Hand hand, BlockRayTraceResult result) {
		if (!worldIn.isClientSide()) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof BoundaryWardStoneTileEntity) {
				if(player.getItemInHand(hand).getItem() instanceof WardEnscriber){

				}else{
					//NetworkHooks.openGui((ServerPlayerEntity) player, (BoundaryWardStoneTileEntity) tile, pos);
				}

				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if(tileEntity instanceof BoundaryWardStoneTileEntity) {
			BoundaryWardStoneTileEntity tile = (BoundaryWardStoneTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((BoundaryWardStoneTileEntity)tileEntity).save(tag);

			item.setTag(tag);

			ItemEntity entity = new ItemEntity(worldIn, pos.getX() + .5, pos.getY(), pos.getZ() + .5, item);
			worldIn.addFreshEntity(entity);
		}
		super.onRemove(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);

		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if(tileEntity instanceof BoundaryWardStoneTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((BoundaryWardStoneTileEntity)tileEntity).readRestorableNBT(tag);
				worldIn.sendBlockUpdated(pos, defaultBlockState(), defaultBlockState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

}
