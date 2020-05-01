package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.OurMod.OurModItemGroup;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInitNew {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, OurMod.MOD_ID);
	
	public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> BROCOLI = ITEMS.register("brocoli", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> RIE = ITEMS.register("rice", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));

}
