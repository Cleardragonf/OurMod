package com.cleardragonf.ourmod.objects.items;

import com.cleardragonf.ourmod.tileentity.BoundaryWardStoneTileEntity;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import com.cleardragonf.ourmod.tileentity.MCMChestTileEntity;
import com.cleardragonf.ourmod.tileentity.MasterWardStoneTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class WardEnscriber extends Item{
    public WardEnscriber(Properties group) {
        super(group);
    }



    public ActionResultType onItemUse(ItemUseContext context) {

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
        if(tileEntity instanceof MasterWardStoneTileEntity){
            if(tag.contains("wardshape")){

            }else{
                ITextComponent text = new TranslationTextComponent("Please select a Energy Source First");
                context.getPlayer().sendMessage(text, context.getPlayer().getUUID());
            }
            return ActionResultType.SUCCESS;
        }
        else if(tileEntity instanceof BoundaryWardStoneTileEntity){
            CompoundNBT pos = new CompoundNBT();
                pos.putInt("x", tileEntity.getBlockPos().getX());
                pos.putInt("y", tileEntity.getBlockPos().getY());
                pos.putInt("z", tileEntity.getBlockPos().getZ());
            tag.put("wardshape", pos);

            itemStack.setTag(tag);
            return ActionResultType.SUCCESS;
        }else{
            return ActionResultType.PASS;
        }
    }



}
