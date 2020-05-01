package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.OurMod;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = OurMod.MOD_ID, bus = Bus.FORGE)
public class OnServerJoin 
{
	@SubscribeEvent
	public static void  OnServerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		event.getPlayer().sendMessage(new TranslationTextComponent("hello"));
	}
}
