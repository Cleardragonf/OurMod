package com.cleardragonf.ourmod.client.events;

import com.cleardragonf.ourmod.MCM.MCMValueCapability;
import com.cleardragonf.ourmod.OurMod;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value= {Dist.CLIENT}, modid = OurMod.MOD_ID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class MCMtooltip {
    public final static MCMValueCapability mcmValue = new MCMValueCapability();
    public static Map<Item, Integer> mcmList = new HashMap<>();
    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event) {

        //even.getItemStack().getItem().toString()...brings out the "fire_mana_ore"....
        //((MCMValueCapability)fireEnergy).setPlayerTemp2(Config.COMMON_CONFIG.get(event.getItemStack().getItem().getRegistryName().toString()));
        mcmList.put(Items.DIAMOND, 1000);mcmList.put(Items.DIAMOND_BLOCK, 9000);mcmList.put(Items.DIAMOND_ORE, 3000);
        mcmList.put(Items.EMERALD, 1000);mcmList.put(Items.EMERALD_BLOCK, 9000);mcmList.put(Items.EMERALD_ORE, 3000);
        mcmList.put(Items.GOLD_INGOT, 1000);mcmList.put(Items.GOLD_BLOCK, 9000);mcmList.put(Items.GOLD_ORE, 3000);
        mcmList.put(Items.GOLD_NUGGET, 500);mcmList.put(Items.IRON_BLOCK, 9000);mcmList.put(Items.IRON_INGOT, 1000);
        mcmList.put(Items.IRON_NUGGET, 500);mcmList.put(Items.IRON_ORE, 3000);mcmList.put(Items.REDSTONE, 1000);
        mcmList.put(Items.REDSTONE_BLOCK, 9000);mcmList.put(Items.REDSTONE_ORE, 3000);mcmList.put(Items.LAPIS_BLOCK, 9000);
        mcmList.put(Items.LAPIS_LAZULI, 1000);mcmList.put(Items.LAPIS_ORE, 3000);mcmList.put(Items.IRON_INGOT, 1000);
        //mcmList.put(Items.ITEM, 1000);mcmList.put(Items.ITEM, 1000);mcmList.put(Items.ITEM, 1000);


        try {
               if(mcmList.containsKey(event.getItemStack().getItem())){
                   ((MCMValueCapability)mcmValue).setPlayerTemp2(mcmList.get(event.getItemStack().getItem()).intValue());
                   String testA = "MCMValue: " + mcmValue.mcmValue();
                   event.getToolTip().add(new StringTextComponent(testA));
               }


        }catch(NullPointerException e) {

        }


    }
}
