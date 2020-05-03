package com.cleardragonf.ourmod.objects.items;

import java.awt.event.InputEvent;
import java.util.List;
import java.util.Map;

import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.objects.blocks.TomatoCrop;
import com.cleardragonf.ourmod.util.helpers.KeyboardHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Sickle extends Item{

	protected static final Map<Block, BlockState> SICKLE_LOOKUP = Maps.newHashMap(ImmutableMap.of(BlockInitNew.TOMATO_CROP.get(), BlockInitNew.TOMATO_CROP.get().getDefaultState()));

	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
	
	
	public Sickle(Properties properties) {
		super(properties);
	}

	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean hasContainerItem() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack stack = itemStack.copy();
		if(stack.attemptDamageItem(1, random, null)){
			stack.shrink(1);
			stack.setDamage(0);
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(KeyboardHelper.isHoldingShift()) {
			tooltip.add(new StringTextComponent("Test Information"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
	      BlockPos blockpos = context.getPos();
	      int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
	      if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
	      if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
	 		BlockState blockstate = world.getBlockState(blockpos);
			Block block = blockstate.getBlock();
	         if (blockstate != null) {
		            PlayerEntity playerentity = context.getPlayer();
		            world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
	     		//Checks what type of crop it is.
	            
	            if(block instanceof TomatoCrop) {
	    			block = (TomatoCrop)block;
	    				ItemStack stack = new ItemStack(ItemInitNew.TOMATO.get().asItem());
	    				ItemStack item = context.getItem();
	    				resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);	
	    				context.getItem().damageItem(1, playerentity, (p_220043_1_) -> {
	                        p_220043_1_.sendBreakAnimation(context.getHand());
	                     });
	            }
	            
	            return ActionResultType.SUCCESS;
	      }
	      }
	      return ActionResultType.PASS;
	}

	
	private void resetCrop(Block block, ItemStack stack, World world, PlayerEntity player, BlockPos blockpos,BlockState blockstate) {
		if(blockstate.get(AGE) >= 7) {
			block.harvestBlock(world, player, blockpos, blockstate, null, stack);
			world.destroyBlock(blockpos, false);
			world.setBlockState(blockpos, blockstate.getBlock().getDefaultState());
		}
	}

	private void automateSickle(ItemUseContext context, BlockPos blockpos, World world) {
		BlockState blockstate = world.getBlockState(blockpos);
		Block block = blockstate.getBlock();
		if(block instanceof TomatoCrop) {
			block = (TomatoCrop)block;
			if(((TomatoCrop) block).getAgeProperty() == BlockStateProperties.AGE_0_7){
				ItemStack stack = new ItemStack(ItemInitNew.TOMATO.get().asItem());
				block.harvestBlock(world, context.getPlayer(), blockpos, blockstate, null, stack);
			}else {
				context.getPlayer().sendMessage(new TranslationTextComponent(((TomatoCrop) block).getAgeProperty().toString()));
			}
		}else {
		}
		
	}

}
