package com.cleardragonf.ourmod.events;

import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.Map;

@Mod.EventBusSubscriber
public class anvilEnchantmetCombine {

    public static int mergeCost = 1;

    @SubscribeEvent
    public static void onEnchanting(AnvilUpdateEvent event){
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if(!left.isEmpty() && !right.isEmpty()) {
            if(left.getItem() == Items.ENCHANTED_BOOK){
                if(right.getItem() == Items.ENCHANTED_BOOK){
                    handleTome(left,right,event);
                }else{
                    handleEnchant(right,left,event);
                }
            }else{
                if(right.getItem() == Items.ENCHANTED_BOOK){
                    handleEnchant(left, right, event);
                }else if(right.getItem() == Items.BOOK){
                    handleDishenchant(left,right,event);
                }
            }

            if ((right.getItem() == Items.ENCHANTED_BOOK) && (left.getItem() == Items.ENCHANTED_BOOK)) {
                handleTome(left, right, event);
            }else if((right.getItem() == Items.ENCHANTED_BOOK) && (left.getItem() instanceof SwordItem  || left.getItem() instanceof ToolItem || left.getItem() instanceof ArmorItem)){
                handleEnchant(left, right, event);
            }
        }
    }

    private static void handleEnchant(ItemStack item, ItemStack book, AnvilUpdateEvent event) {

        Map<Enchantment, Integer> enchantsItem= EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> enchantsTome = EnchantmentHelper.getEnchantments(book);
        mergeCost = 1;

        if (enchantsTome == null) {
            return;
        }

        for (Map.Entry<Enchantment, Integer> entry : enchantsTome.entrySet()) {
            enchantsItem.put(entry.getKey(), entry.getValue() + 1);
            mergeCost += 1;
        }

        ItemStack output = new ItemStack(item.getStack().getItem());
        for (Map.Entry<Enchantment, Integer> entry : enchantsItem.entrySet()){
            output.enchant(entry.getKey(), entry.getValue());
            mergeCost +=1;
        }


        event.setOutput(output);
        event.setCost(mergeCost);
    }

    private static void handleTome(ItemStack book1, ItemStack book2, AnvilUpdateEvent event) {
            Map<Enchantment, Integer> enchantBook1 = EnchantmentHelper.getEnchantments(book1);
            Map<Enchantment, Integer> enchantBook2 = EnchantmentHelper.getEnchantments(book2);

            ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
            mergeCost = 1;
            if (enchantBook2 == null) {
                return;
            }

            //new code
            for(Map.Entry<Enchantment, Integer> entry : enchantBook1.entrySet()){
                if(enchantBook2.containsKey(entry.getKey())){
                    if(enchantBook2.get(entry.getKey()).intValue() == enchantBook1.get(entry.getKey()).intValue()){
                        enchantBook1.put(entry.getKey(), enchantBook1.get(entry.getKey()).intValue() +1);
                        mergeCost += 1;
                    }
                }
            }
            for(Map.Entry<Enchantment, Integer> entry : enchantBook2.entrySet()){
                if(!(enchantBook1.containsKey(entry.getKey()))){
                    enchantBook1.put(entry.getKey(), enchantBook2.get(entry.getKey()).intValue());
                    mergeCost +=1;
                }
            }

            for (Map.Entry<Enchantment, Integer> entry : enchantBook1.entrySet()){
                EnchantedBookItem.addEnchantment(output, new EnchantmentData(entry.getKey(),entry.getValue()));
            }

            event.setOutput(output);
            event.setCost(mergeCost);
        }

    private static void handleDishenchant(ItemStack item, ItemStack book, AnvilUpdateEvent event){
        Map<Enchantment, Integer> enchantedItem = EnchantmentHelper.getEnchantments(item);

        ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
        mergeCost = 1;
        if (book == null) {
            return;
        }

        for (Map.Entry<Enchantment, Integer> entry : enchantedItem.entrySet()){
            EnchantedBookItem.addEnchantment(output, new EnchantmentData(entry.getKey(),entry.getValue()));
            mergeCost += entry.getValue();
        }

        event.setOutput(output);
        event.setCost(mergeCost);
    }

}
