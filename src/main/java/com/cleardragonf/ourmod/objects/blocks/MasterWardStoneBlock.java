package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.items.PowerEnscriber;
import com.cleardragonf.ourmod.objects.items.WardEnscriber;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
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
import net.minecraft.util.text.TranslationTextComponent;
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
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
											 Hand hand, BlockRayTraceResult result) {
		if (!world.isClientSide()) {
			TileEntity tile = world.getBlockEntity(pos);
			if (tile instanceof MasterWardStoneTileEntity) {

				CompoundNBT tag;
				ItemStack item = player.getItemInHand(hand);
				if(item.hasTag()){
					tag = item.getTag();
				}else{
					tag = new CompoundNBT();
				}
				if(tag.contains("wardshape")){
					MasterWardStoneTileEntity tileEntity = (MasterWardStoneTileEntity) world.getBlockEntity(pos);
					if(tileEntity.boundaryWardStones.size() < 4){
						tileEntity.addWardStone(tag.get("wardshape"));
						player.sendMessage(new TranslationTextComponent("Successfully connected boundary " + tileEntity.boundaryWardStones.size()),player.getUUID());
					}else{
						player.sendMessage(new TranslationTextComponent("You've tried to add too many boundaries...Master Ward has been reset"), player.getUUID());
						tileEntity.boundaryWardStones.clear();
					}

					tileEntity.setChanged();
					tileEntity.updateBlock();
				}
				if(tag.contains("energypos")){
					MasterWardStoneTileEntity tileEntity = (MasterWardStoneTileEntity) world.getBlockEntity(pos);
					tileEntity.energyblocks = tag.get("energypos");
					tileEntity.setChanged();
					tileEntity.updateBlock();
				}
				if(player.getItemInHand(hand).getItem() instanceof WardEnscriber || player.getItemInHand(hand).getItem() instanceof PowerEnscriber){

				}else{
					NetworkHooks.openGui((ServerPlayerEntity) player, (MasterWardStoneTileEntity) tile, pos);
				}

				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if(tileEntity instanceof MasterWardStoneTileEntity) {

			((MasterWardStoneTileEntity)tileEntity).rescendWard();
			MasterWardStoneTileEntity tile = (MasterWardStoneTileEntity) tileEntity;
			ItemStack item = new ItemStack(this);
			CompoundNBT tag = new CompoundNBT();
			((MasterWardStoneTileEntity)tileEntity).save(tag);

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
		if(tileEntity instanceof MasterWardStoneTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((MasterWardStoneTileEntity)tileEntity).readRestorableNBT(tag);
				worldIn.sendBlockUpdated(pos, defaultBlockState(), defaultBlockState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}
}
