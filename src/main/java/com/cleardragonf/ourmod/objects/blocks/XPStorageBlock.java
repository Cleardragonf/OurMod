package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.tileentity.XPStorageTileEntity;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class XPStorageBlock
		extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public XPStorageBlock( Properties properties )
	{
		super( properties );
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_220071_1_,
										IBlockReader p_220071_2_,
										BlockPos p_220071_3_,
										ISelectionContext p_220071_4_ )
	{
		return VoxelShapes.fullCube();
	}

	@Override
	protected void fillStateContainer( StateContainer.Builder< Block, BlockState > builder )
	{
		builder.add( FACING );
	}

	@Override
	public BlockState getStateForPlacement( BlockItemUseContext context )
	{
		return getDefaultState().with( FACING,
				context.getPlacementHorizontalFacing()
						.getOpposite() );
	}

	@Override
	public boolean hasTileEntity( BlockState state )
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity( BlockState state, IBlockReader world )
	{
		return new XPStorageTileEntity();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state,
										   World world,
										   BlockPos pos,
										   PlayerEntity player,
										   Hand hand,
										   BlockRayTraceResult rayTraceResult )
	{
		if ( !world.isRemote() )
		{
			TileEntity tileEntity = world.getTileEntity( pos );
			if ( tileEntity instanceof XPStorageTileEntity)
			{
				XPStorageTileEntity bottlingMachine = ( XPStorageTileEntity )tileEntity;
				if ( player instanceof ServerPlayerEntity)
				{
					NetworkHooks.openGui( ( ServerPlayerEntity )player, bottlingMachine );
				}
			}
		}
		return ActionResultType.SUCCESS;
	}
}
