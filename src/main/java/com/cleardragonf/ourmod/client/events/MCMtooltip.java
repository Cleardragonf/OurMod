package com.cleardragonf.ourmod.client.events;

import com.cleardragonf.ourmod.MCM.MCMValueCapability;
import com.cleardragonf.ourmod.OurMod;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value= {Dist.CLIENT}, modid = OurMod.MOD_ID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class MCMtooltip {
    public final static MCMValueCapability mcmValue = new MCMValueCapability();
    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event) {
        //even.getItemStack().getItem().toString()...brings out the "fire_mana_ore"....
        //((MCMValueCapability)fireEnergy).setPlayerTemp2(Config.COMMON_CONFIG.get(event.getItemStack().getItem().getRegistryName().toString()));
        try {
            ((MCMValueCapability)mcmValue).setPlayerTemp2(100);
            String testA = "MCMValue: " + mcmValue.mcmValue();
            event.getToolTip().add(new StringTextComponent(testA));
        }catch(NullPointerException e) {

        }


    }
}
