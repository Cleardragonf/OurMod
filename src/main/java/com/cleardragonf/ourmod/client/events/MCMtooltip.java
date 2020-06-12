package com.cleardragonf.ourmod.client.events;

import com.cleardragonf.ourmod.MCM.MCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValues;
import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.objects.blocks.MCMChest;
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
    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event) {

        try {
            MCMValues tile = new MCMValues();
                if(tile.getMap().containsKey(event.getItemStack().getItem())){
                   String testA = "MCMValue: " + tile.getMap().get(event.getItemStack().getItem());
                   event.getToolTip().add(new StringTextComponent(testA));
               }else{
                   String testA = "MCMValue: 1";
                   event.getToolTip().add(new StringTextComponent(testA));
               }


        }catch(NullPointerException e) {

        }


    }
}
