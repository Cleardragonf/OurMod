package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.OurMod.OurModItemGroup;
import com.cleardragonf.ourmod.objects.items.*;

import com.cleardragonf.ourmod.objects.items.wards.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ItemInitNew {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, OurMod.MOD_ID);
	
	public static final RegistryObject<Item> SICKLE = ITEMS.register("sickle", () -> new Sickle(new Item.Properties().defaultMaxDamage(100).group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> OUR_BOOK = ITEMS.register("ourbook", () -> new OurBook(new Item.Properties().group(OurModItemGroup.instance)));

	//FOOD
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> RICE = ITEMS.register("rice", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> BROCCOLI = ITEMS.register("broccoli", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> PEANUT = ITEMS.register("peanut", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> PEPPER = ITEMS.register("pepper", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> CAULIFLOWER = ITEMS.register("cauliflower", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	public static final RegistryObject<Item> EGGPLANT = ITEMS.register("eggplant", () -> new Item(new Item.Properties().group(OurModItemGroup.instance).food(new Food.Builder().hunger(1).build())));
	//SEEDS
	public static final RegistryObject<Item> TOMATO_SEED = ITEMS.register("tomatoseed", () -> new BlockItem(BlockInitNew.TOMATO_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> RICE_SEED = ITEMS.register("riceseed", () -> new BlockItem(BlockInitNew.RICE_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> ONION_SEED = ITEMS.register("onionseed", () -> new BlockItem(BlockInitNew.ONION_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> CORN_SEED = ITEMS.register("cornseed", () -> new BlockItem(BlockInitNew.CORN_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> CUCUMBER_SEED = ITEMS.register("cucumberseed", () -> new BlockItem(BlockInitNew.CUCUMBER_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> BROCCOLI_SEED= ITEMS.register("broccoliseed", () -> new BlockItem(BlockInitNew.BROCCOLI_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> LETTUCE_SEED = ITEMS.register("lettuceseed", () -> new BlockItem(BlockInitNew.LETTUCE_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> PEANUT_SEED= ITEMS.register("peanutseed", () -> new BlockItem(BlockInitNew.PEANUT_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> PEPPER_SEED= ITEMS.register("pepperseed", () -> new BlockItem(BlockInitNew.PEPPER_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> CAULIFLOWER_SEED= ITEMS.register("cauliflowerseed", () -> new BlockItem(BlockInitNew.CAULIFLOWER_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> EGGPLANT_SEED= ITEMS.register("eggplantseed", () -> new BlockItem(BlockInitNew.EGGPLANT_CROP.get(), new Item.Properties().group(OurModItemGroup.instance)));

	public static final RegistryObject<Item> POWER_ENSCRIBER = ITEMS.register("powerenscriber", () -> new PowerEnscriber(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_ENSCRIBER = ITEMS.register("wardenscriber", () -> new WardEnscriber(new Item.Properties().group(OurModItemGroup.instance)));

	//Wards
	public static final RegistryObject<Item> WARD_STONES_HEALING = ITEMS.register("healingward", () -> new HealingWardTablet(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_HUNGER = ITEMS.register("hungerward", () -> new HungerWardTablet(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_THIRST = ITEMS.register("thirstward", () -> new ThirstWardTablet(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_TEMPERATURE = ITEMS.register("temperatureward", () -> new TemperatureWardTablet(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_ANTI_HOSTILE = ITEMS.register("antihostileward", () -> new AntiHostileWard(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_ANTI_GRAVITY = ITEMS.register("antigravityward", () -> new AntiGravityWard(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_ANTI_PEACEFUL = ITEMS.register("antipeacefulward", () -> new AntiPassiveWard(new Item.Properties().group(OurModItemGroup.instance)));
	public static final RegistryObject<Item> WARD_STONES_DAYLIGHT = ITEMS.register("daytimeward", () -> new DaytimeWard(new Item.Properties().group(OurModItemGroup.instance)));


	/*
	public static <K, R extends IForgeRegistryEntry<R>, V extends R> Map<K, RegistryObject<V>> registerManyObjects(Iterable<K> keys, DeferredRegister<R> register, Function<K, String> nameGetter, Function<K, V> factory)
	{
		Map<K, RegistryObject<V>> map = new HashMap<>();

		for (K key : keys)
		{
			register.register(nameGetter.apply(key), () -> factory.apply(key));
		}

		return map;
	}

	 */

	//Blocks

	@ObjectHolder("ourmod:lightmana")
	public static final Item LIGHT_ESSENCE = null;

	@ObjectHolder("ourmod:darkmana")
	public static final Item DARK_ESSENCE = null;

	@ObjectHolder("ourmod:firemana")
	public static final Item FIRE_ESSENCE = null;

	@ObjectHolder("ourmod:watermana")
	public static final Item WATER_ESSENCE = null;

	@ObjectHolder("ourmod:earthmana")
	public static final Item EARTH_ESSENCE = null;

	@ObjectHolder("ourmod:airmana")
	public static final Item WIND_ESSENCE = null;

	@ObjectHolder("ourmod:mcmchest")
	public static final Item MCM_CHEST = null;

	@ObjectHolder("ourmod:fishingnet")
	public static final Item FISHING_NET = null;

	@ObjectHolder("ourmod:essencecollector")
	public static final Item ESSENCE_COLLECTOR = null;

	@ObjectHolder("ourmod:quarry")
	public static final Item QUARRY = null;

	@ObjectHolder("ourmod:boundarywardstone")
	public static final Item BOUNDARY_WARD_STONE = null;

	@ObjectHolder("ourmod:masterwardstone")
	public static final Item MASTER_WARD_STONE = null;

}
