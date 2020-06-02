package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.objects.blocks.*;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInitNew {
	
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, OurMod.MOD_ID);
	
	public static final RegistryObject<Block> FORCEFIELD = BLOCKS.register("forcefield", ()-> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	
	
	//TileEntities
	public static final RegistryObject<Block> PORTABLE_CHEST = BLOCKS.register("portablechest", ()-> new PortableChest(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", ()-> new BlockQuarry(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	public static final RegistryObject<Block> ESSENCE_COLLECTOR = BLOCKS.register("essencecollector", ()-> new EssenceCollector(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	public static final RegistryObject<Block> FISHING_NET = BLOCKS.register("fishingnet", ()-> new FishingNet(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	public static final RegistryObject<Block> MCM_CHEST = BLOCKS.register("mcmchest", ()-> new MCMChest(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	
	//CROPS
	public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomatocrop", () -> new TomatoCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> RICE_CROP = BLOCKS.register("ricecrop", () -> new RiceCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> ONION_CROP  = BLOCKS.register("onioncrop", () -> new OnionCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> CORN_CROP  = BLOCKS.register("corncrop", () -> new CornCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> CUCUMBER_CROP  = BLOCKS.register("cucumbercrop", () -> new CucumberCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> BROCCOLI_CROP = BLOCKS.register("broccolicrop", () -> new BroccoliCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> LETTUCE_CROP = BLOCKS.register("lettucecrop", () -> new LettuceCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> PEANUT_CROP = BLOCKS.register("peanutcrop", () -> new PeanutCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> PEPPER_CROP = BLOCKS.register("peppercrop", () -> new PepperCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> CAULIFLOWER_CROP = BLOCKS.register("cauliflowercrop", () -> new CauliflowerCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> EGGPLANT_CROP = BLOCKS.register("eggplantcrop", () -> new EggplantCrop(Block.Properties.from(Blocks.WHEAT)));

	//ORES
	public static final RegistryObject<Block> WATER_MANA = BLOCKS.register("watermana", () -> new Block(Block.Properties.from(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> FIRE_MANA = BLOCKS.register("firemana", () -> new Block(Block.Properties.from(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> AIR_MANA = BLOCKS.register("airmana", () -> new Block(Block.Properties.from(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> EARTH_MANA = BLOCKS.register("earthmana", () -> new Block(Block.Properties.from(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> DARK_MANA = BLOCKS.register("darkmana", () -> new Block(Block.Properties.from(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> LIGHT_MANA = BLOCKS.register("lightmana", () -> new Block(Block.Properties.from(Blocks.COAL_ORE)));

	//public static Block WATER_MANA;

}
