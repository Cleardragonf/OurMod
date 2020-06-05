package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.MCM.MCMValueProvider;
import com.cleardragonf.ourmod.OurMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= OurMod.MOD_ID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class CustomCapabilityEventHandler {
    public static final ResourceLocation MCMValue = new ResourceLocation(OurMod.MOD_ID, "mcmvalue");

    @SubscribeEvent
    public static void onAttachCapabilitesToItems(AttachCapabilitiesEvent<ItemStack> a) {
        ItemStack item = a.getObject();
        if(item instanceof ItemStack) {
            a.addCapability(MCMValue, new MCMValueProvider());
            //a.addCapability(MCMValue, new MCMValueProvider());
        }
    }
}
