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


    public static int applyCost = 10;
    public static int mergeCost = 10;

    @SubscribeEvent
    public static void onEnchanting(AnvilUpdateEvent event){
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if(!left.isEmpty() && !right.isEmpty()) {
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

        if (enchantsTome == null) {
            return;
        }

        for (Map.Entry<Enchantment, Integer> entry : enchantsTome.entrySet()) {
            enchantsItem.put(entry.getKey(), entry.getValue() + 1);
        }

        ItemStack output = new ItemStack(item.getStack().getItem());
        for (Map.Entry<Enchantment, Integer> entry : enchantsItem.entrySet()){
            output.addEnchantment(entry.getKey(), entry.getValue());
        }


        event.setOutput(output);
        event.setCost(mergeCost);
    }

    private static void handleTome(ItemStack book1, ItemStack book2, AnvilUpdateEvent event) {
            Map<Enchantment, Integer> enchantBook1 = EnchantmentHelper.getEnchantments(book1);
            Map<Enchantment, Integer> enchantBook2 = EnchantmentHelper.getEnchantments(book2);

            if (enchantBook2 == null) {
                return;
            }

            //This is what controls the Adding of the enchantment
        //book 1 Enchantment get enchantments from bookk 2 there value
            for (Map.Entry<Enchantment, Integer> entry : enchantBook2.entrySet()) {
                if(enchantBook1.containsKey(entry.getKey())) {
                    enchantBook1.put(entry.getKey(), enchantBook1.get(entry.getKey()).intValue() + 1);
                }
                else return;
            }

            ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
            for (Map.Entry<Enchantment, Integer> entry : enchantBook1.entrySet()){
                EnchantedBookItem.addEnchantment(output, new EnchantmentData(entry.getKey(),entry.getValue()));
            }


            event.setOutput(output);
            event.setCost(mergeCost);
        }
}
