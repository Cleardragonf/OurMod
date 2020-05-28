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


    public static int applyCost = 35;
    public static int mergeCost = 35;

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
        System.out.println("step 1");

        Map<Enchantment, Integer> enchantsItem= EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> enchantsTome = EnchantmentHelper.getEnchantments(book);

        if (enchantsTome == null) {
            return;
        }

        for (Map.Entry<Enchantment, Integer> entry : enchantsTome.entrySet()) {
            System.out.println("step 2");

                System.out.println("step 3");
            enchantsItem.put(entry.getKey(), entry.getValue() + 1);
        }

        ItemStack output = new ItemStack(item.getStack().getItem());
        for (Map.Entry<Enchantment, Integer> entry : enchantsItem.entrySet()){
            System.out.println("1");
            output.addEnchantment(entry.getKey(), entry.getValue());
        }


        event.setOutput(output);
        event.setCost(mergeCost);
    }

    private static void handleTome(ItemStack book1, ItemStack book2, AnvilUpdateEvent event) {
            Map<Enchantment, Integer> enchantsBook = EnchantmentHelper.getEnchantments(book1);
            Map<Enchantment, Integer> enchantsTome = EnchantmentHelper.getEnchantments(book2);

            if (enchantsTome == null) {
                return;
            }

            for (Map.Entry<Enchantment, Integer> entry : enchantsTome.entrySet()) {
                if(enchantsBook.getOrDefault(entry.getKey(), 0).equals(entry.getValue())) {
                    enchantsBook.put(entry.getKey(), entry.getValue() + 1);
                }
                else return;
            }

            ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
            for (Map.Entry<Enchantment, Integer> entry : enchantsBook.entrySet())
                EnchantedBookItem.addEnchantment(output, new EnchantmentData(entry.getKey(),entry.getValue()));

            event.setOutput(output);
            event.setCost(mergeCost);
        }
}
