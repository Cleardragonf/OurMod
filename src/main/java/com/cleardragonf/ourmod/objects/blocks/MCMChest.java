package com.cleardragonf.ourmod.objects.blocks;

import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.items.PowerEnscriber;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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

	public MCMChest() {
		super(Properties.create(Material.IRON)
				.sound(SoundType.METAL)
				.hardnessAndResistance(2.0f)
				.lightValue(14)
		);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public int getLightValue(BlockState state) {
		return state.get(BlockStateProperties.POWERED) ? super.getLightValue(state) : 0;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new MCMChestTileEntity();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entity, stack);

		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof MCMChestTileEntity) {
			CompoundNBT tag = stack.getTag();
			if(tag != null) {
				((MCMChestTileEntity)tileEntity).readRestorableNBT(tag);
				world.notifyBlockUpdate(pos, getDefaultState(), getDefaultState(), Constants.BlockFlags.DEFAULT);
			}
		}
	}


	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof MCMChestTileEntity) {

				CompoundNBT tag;
				ItemStack item = player.getHeldItem(hand);
				if(item.hasTag()){
					tag = item.getTag();
				}else{
					tag = new CompoundNBT();
				}
				if(tag.contains("energypos")){
					MCMChestTileEntity tileEntity = (MCMChestTileEntity) world.getTileEntity(pos);
					tileEntity.energyblocks = tag.get("energypos");
					tileEntity.markDirty();
					tileEntity.updateBlock();
				}



				NetworkHooks.openGui((ServerPlayerEntity) player, (MCMChestTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
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
	}

	public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
		Vec3d vec = entity.getPositionVec();
		return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()), (float) (vec.y - clickedBlock.getY()), (float) (vec.z - clickedBlock.getZ()));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
	}
}
