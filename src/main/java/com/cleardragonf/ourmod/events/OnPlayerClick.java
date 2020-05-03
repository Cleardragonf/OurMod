package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.OurMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OurMod.MOD_ID, bus = Bus.FORGE)
public class OnPlayerClick 
{
	@SubscribeEvent
	public static void  OnPlayerClick(PlayerInteractEvent.EntityInteractSpecific event) {
		if(event.getTarget() instanceof PlayerEntity) {
			String message = event.getPlayer().getName().getFormattedText() + " would like to trade";
			event.getTarget().sendMessage(new TranslationTextComponent(message));
		}
	}
}