package com.cleardragonf.ourmod.objects.items;

import java.awt.event.InputEvent;
import java.util.List;
import java.util.Map;

import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.objects.blocks.*;
import com.cleardragonf.ourmod.util.helpers.KeyboardHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
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
import net.minecraftforge.fml.RegistryObject;

public class Sickle extends Item{

	protected static final Map<Block, BlockState> SICKLE_LOOKUP = Maps.newHashMap(ImmutableMap.of(BlockInitNew.TOMATO_CROP.get(), BlockInitNew.TOMATO_CROP.get().defaultBlockState()));

	public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
	
	
	public Sickle(Properties properties) {
		super(properties);
	}

	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean hasCraftingRemainingItem() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack stack = itemStack.copy();
		if(stack.hurt(1, random, null)){
			stack.shrink(1);
			stack.setDamageValue(0);
		}
		return stack;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(KeyboardHelper.isHoldingShift()) {
			tooltip.add(new StringTextComponent("Test Information"));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
	      BlockPos blockpos = context.getClickedPos();
	      int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
	      if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
	      if (context.getClickedFace() != Direction.DOWN && world.isEmptyBlock(blockpos.above())) {
	 		BlockState blockstate = world.getBlockState(blockpos);
			Block block = blockstate.getBlock();
	         if (blockstate != null) {
		            PlayerEntity playerentity = context.getPlayer();
		            world.playSound(playerentity, blockpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
	     		//Checks what type of crop it is.
	            if(block instanceof CropsBlock) {
	            	//TODO: working within here to try and figure out the blocks FINAL class so that i can cast to the block for wildcard
	            	
	            }
	            if(block instanceof TomatoCrop) {
	    			block = (TomatoCrop)block;
	    				ItemStack stack = new ItemStack(ItemInitNew.TOMATO.get().asItem());
	    				ItemStack item = context.getItemInHand();
	    				resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);	
	    				context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
	                        p_220043_1_.broadcastBreakEvent(context.getHand());
	                     });
	            }
				 if(block instanceof OnionCrop) {
					 block = (OnionCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.ONION.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof CornCrop) {
					 block = (CornCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.CORN.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof CucumberCrop) {
					 block = (CucumberCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.CUCUMBER.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof BroccoliCrop) {
					 block = (BroccoliCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.BROCCOLI.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof LettuceCrop) {
					 block = (LettuceCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.LETTUCE.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof PeanutCrop) {
					 block = (PeanutCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.PEANUT.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof PepperCrop) {
					 block = (PepperCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.PEPPER.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof CauliflowerCrop) {
					 block = (CauliflowerCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.CAULIFLOWER.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
				 if(block instanceof EggplantCrop) {
					 block = (EggplantCrop)block;
					 ItemStack stack = new ItemStack(ItemInitNew.EGGPLANT.get().asItem());
					 ItemStack item = context.getItemInHand();
					 resetCrop(block, stack, world, context.getPlayer(), blockpos, blockstate);
					 context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
						 p_220043_1_.broadcastBreakEvent(context.getHand());
					 });
				 }
	            
	            return ActionResultType.SUCCESS;
	      }
	      }
	      return ActionResultType.PASS;
	}

	
	private void resetCrop(Block block, ItemStack stack, World world, PlayerEntity player, BlockPos blockpos,BlockState blockstate) {
		if(blockstate.getValue(AGE) >= 7) {
			block.playerDestroy(world, player, blockpos, blockstate, null, stack);
			world.destroyBlock(blockpos, false);
			world.setBlockAndUpdate(blockpos, blockstate.getBlock().defaultBlockState());
		}
	}

	private void automateSickle(ItemUseContext context, BlockPos blockpos, World world) {
		BlockState blockstate = world.getBlockState(blockpos);
		Block block = blockstate.getBlock();
		if(block instanceof TomatoCrop) {
			block = (TomatoCrop)block;
			if(((TomatoCrop) block).getAgeProperty() == BlockStateProperties.AGE_7){
				ItemStack stack = new ItemStack(ItemInitNew.TOMATO.get().asItem());
				block.playerDestroy(world, context.getPlayer(), blockpos, blockstate, null, stack);
			}else {
				context.getPlayer().sendMessage(new TranslationTextComponent(((TomatoCrop) block).getAgeProperty().toString()),context.getPlayer().getUUID());
			}
		}else {
		}
		
	}

}
