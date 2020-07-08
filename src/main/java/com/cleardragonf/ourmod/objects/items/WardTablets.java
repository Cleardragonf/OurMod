package com.cleardragonf.ourmod.objects.items;

import com.cleardragonf.ourmod.objects.special.EnumWards;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistry;


public class WardTablets extends Item{
    public WardTablets(Properties properties) {
        super(properties);
    }
    public void addRecipies(){
        //TODO:: Add the recipies for these
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        int metadata = stack.getDamage();
        EnumWards wards = EnumWards.values()[metadata];
        ITextComponent tag = new TranslationTextComponent("ward" + wards.getName().toLowerCase());
        return tag;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        for(EnumWards wards : EnumWards.values()){
            items.add(new ItemStack(this,1));
        }
    }

    //TODO:: begin working on the ENUM of this so that we can use only 1 Item class
}
