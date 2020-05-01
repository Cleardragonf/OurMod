package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.OurMod.OurModItemGroup;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = OurMod.MOD_ID, bus = Bus.MOD)
@ObjectHolder(OurMod.MOD_ID)
public class ItemInit {


	public static final Item corn = null;
	public static final Item tomato = null;
	public static final Item onion = null;
	public static final Item cornoncob = null;
	public static final Item brocoli = null;
	public static final Item rice = null;
	public static final Item lettuce = null;
	
	
	
	
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(6).saturation(1.2f).effect(new EffectInstance(Effects.ABSORPTION, 60000, 5), 0.5f).build())).setRegistryName("cornoncob"));
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())).setRegistryName("tomato"));
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())).setRegistryName("corn"));
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())).setRegistryName("brocoli"));
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())).setRegistryName("lettuce"));
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())).setRegistryName("onion"));
		event.getRegistry().register(new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())).setRegistryName("rice"));
	
	}
}
