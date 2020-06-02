package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MCMChest extends Block {

	public MCMChest(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.MCM_Chest.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof MCMChestTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (MCMChestTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.RightClickItem event){
		if(event.getItemStack().getItem().equals(ItemInitNew.POWER_ENSCRIBER)){
			System.out.println("Yes");
		}else{
			System.out.println("No");
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof MCMChestTileEntity) {
			MCMChestTileEntity tile = (MCMChestTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((MCMChestTileEntity)tileEntity).write(tag);

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
		if(tileEntity instanceof MCMChestTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				//((MCMChestTileEntity)tileEntity).readRestorableNBT(tag);
				worldIn.notifyBlockUpdate(pos, getDefaultState(), getDefaultState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}

}
