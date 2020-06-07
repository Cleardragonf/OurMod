package com.cleardragonf.ourmod.objects.items;

import com.cleardragonf.ourmod.client.gui.OurBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class OurBook extends BookItem {
    public OurBook(Properties group) {
        super(group);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote){

        }else{
            ITextComponent title = new TranslationTextComponent("testing");
            Minecraft.getInstance().displayGuiScreen(new OurBookScreen(title));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
