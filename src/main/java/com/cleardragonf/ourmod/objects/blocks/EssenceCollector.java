package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.items.PowerEnscriber;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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

public class EssenceCollector extends Block {

	public EssenceCollector(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.ESSENCE_COLLECTOR.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand hand, BlockRayTraceResult result) {
		if (!worldIn.isClientSide()) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof EssenceCollectorTileEntity) {
				if(player.getItemInHand(hand).getItem() instanceof PowerEnscriber){

				}else{
					NetworkHooks.openGui((ServerPlayerEntity) player, (EssenceCollectorTileEntity) tile, pos);
				}

				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if(tileEntity instanceof EssenceCollectorTileEntity) {
			EssenceCollectorTileEntity tile = (EssenceCollectorTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((EssenceCollectorTileEntity)tileEntity).save(tag);

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
		if(tileEntity instanceof EssenceCollectorTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((EssenceCollectorTileEntity)tileEntity).readRestorableNBT(tag);
				worldIn.sendBlockUpdated(pos, defaultBlockState(), defaultBlockState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}

}
