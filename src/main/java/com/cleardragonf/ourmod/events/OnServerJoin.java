package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.OurMod;

import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = OurMod.MOD_ID, bus = Bus.FORGE)
public class OnServerJoin 
{
	@SubscribeEvent
	public static void  OnServerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		event.getPlayer().sendMessage(new TranslationTextComponent("hello"), event.getPlayer().getUniqueID());


		ItemStack item = new ItemStack( ItemInitNew.ASURA_BOOK.get());
		ItemEntity entity = new ItemEntity(event.getPlayer().world, event.getPlayer().getPosX() + .5, event.getPlayer().getPosY(), event.getPlayer().getPosZ() + .5, item);
		event.getPlayer().world.addEntity(entity);
	}
}
