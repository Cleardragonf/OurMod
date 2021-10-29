package com.cleardragonf.ourmod.objects.items;

import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.objects.blocks.EssenceCollector;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PowerEnscriber extends Item{
    public PowerEnscriber(Item.Properties group) {
        super(group);
    }



    public ActionResultType useOn(ItemUseContext context) {

        CompoundNBT tag;
        ItemStack itemStack = context.getItemInHand().getStack();
        if(itemStack.hasTag()){
            tag = itemStack.getTag();
        }else{
            tag = new CompoundNBT();
        }

        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        TileEntity tileEntity = world.getBlockEntity(blockpos);
        if(tileEntity instanceof MCMChestTileEntity){
            if(tag.contains("energypos")){
                ITextComponent text = new TranslationTextComponent("Connecting to: " + tag.get("energypos"));
                context.getPlayer().sendMessage(text, context.getPlayer().getUUID());
            }else{
                ITextComponent text = new TranslationTextComponent("Please select a Energy Source First");
                context.getPlayer().sendMessage(text, context.getPlayer().getUUID());
            }
            return ActionResultType.SUCCESS;
        }
        else if(tileEntity instanceof MasterWardStoneTileEntity){
            if(tag.contains("energypos")){
                ITextComponent text = new TranslationTextComponent("Connecting to: " + tag.get("energypos"));
                context.getPlayer().sendMessage(text, context.getPlayer().getUUID());
            }else{
                ITextComponent text = new TranslationTextComponent("Please select a Energy Source First");
                context.getPlayer().sendMessage(text, context.getPlayer().getUUID());
            }
            return ActionResultType.SUCCESS;
        }
        else if(tileEntity instanceof EssenceCollectorTileEntity){
            CompoundNBT pos = new CompoundNBT();
                pos.putInt("x", tileEntity.getBlockPos().getX());
                pos.putInt("y", tileEntity.getBlockPos().getY());
                pos.putInt("z", tileEntity.getBlockPos().getZ());
            tag.put("energypos", pos);
            ITextComponent text = new TranslationTextComponent("setting to: " + tag.get("energypos") + tag.get("earthenergy"));
            context.getPlayer().sendMessage(text, context.getPlayer().getUUID());
            itemStack.setTag(tag);
            return ActionResultType.SUCCESS;
        }else{
            return ActionResultType.PASS;
        }
    }



}
