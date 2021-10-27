package com.cleardragonf.ourmod.objects.items;

import com.cleardragonf.ourmod.client.gui.SomeOtherClass;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class WardsBook extends BookItem {
    public WardsBook(Properties group) {
        super(group);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ITextComponent title = new TranslationTextComponent("Wards by the Asura");
            SomeOtherClass.openTheGuiForWards(title);
        }


        return super.use(worldIn, playerIn, handIn);
    }
}
