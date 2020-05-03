package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.OurMod.OurModItemGroup;
import com.cleardragonf.ourmod.objects.items.Sickle;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInitNew {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, OurMod.MOD_ID);
	
	public static final RegistryObject<Item> SICKLE = ITEMS.register("sickle", () -> new Sickle(new Item.Properties().defaultMaxDamage(100).group(OurModItemGroup.instance)));
	//FOOD
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	//SEEDS
	public static final RegistryObject<Item> TOMATO_SEED = ITEMS.register("tomatoseed", () -> new BlockItem(BlockInitNew.TOMATO_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	
}
