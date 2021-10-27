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

	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OurMod.MOD_ID);

	public static final RegistryObject<Block> FORCEFIELD = BLOCKS.register("forcefield", ()-> new Block(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));
	public static final RegistryObject<Block> WARDBARRIER = BLOCKS.register("wardbarrier", ()-> new WardBarrier(Block.Properties.of(Material.METAL).strength(5000.0f, 5000.0f)));
	public static final RegistryObject<Block> WARDINSIDE = BLOCKS.register("wardinside",WardInside::new);


	//TileEntities
	public static final RegistryObject<Block> PORTABLE_CHEST = BLOCKS.register("portablechest", ()-> new PortableChest(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));
	public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", ()-> new BlockQuarry(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));
	public static final RegistryObject<Block> ESSENCE_COLLECTOR = BLOCKS.register("essencecollector", ()-> new EssenceCollector(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));
	public static final RegistryObject<Block> FISHING_NET = BLOCKS.register("fishingnet", ()-> new FishingNet(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));
	public static final RegistryObject<MCMChest> MCM_CHEST = BLOCKS.register("mcmchest", MCMChest::new);
	public static final RegistryObject<Block> MASTER_WARD_STONE = BLOCKS.register("masterwardstone", ()-> new MasterWardStoneBlock(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));
	public static final RegistryObject<Block> BOUNDARY_WARD_STONE = BLOCKS.register("boundarywardstone", ()-> new BoundaryWardStoneBlock(Block.Properties.of(Material.METAL).strength(10.0f, 15.0f)));

	//CROPS
	public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomatocrop", () -> new TomatoCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> RICE_CROP = BLOCKS.register("ricecrop", () -> new RiceCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> ONION_CROP  = BLOCKS.register("onioncrop", () -> new OnionCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> CORN_CROP  = BLOCKS.register("corncrop", () -> new CornCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> CUCUMBER_CROP  = BLOCKS.register("cucumbercrop", () -> new CucumberCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> BROCCOLI_CROP = BLOCKS.register("broccolicrop", () -> new BroccoliCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> LETTUCE_CROP = BLOCKS.register("lettucecrop", () -> new LettuceCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> PEANUT_CROP = BLOCKS.register("peanutcrop", () -> new PeanutCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> PEPPER_CROP = BLOCKS.register("peppercrop", () -> new PepperCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> CAULIFLOWER_CROP = BLOCKS.register("cauliflowercrop", () -> new CauliflowerCrop(Block.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> EGGPLANT_CROP = BLOCKS.register("eggplantcrop", () -> new EggplantCrop(Block.Properties.copy(Blocks.WHEAT)));

	//ORES
	public static final RegistryObject<Block> WATER_MANA = BLOCKS.register("watermana", () -> new Block(Block.Properties.copy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> FIRE_MANA = BLOCKS.register("firemana", () -> new Block(Block.Properties.copy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> AIR_MANA = BLOCKS.register("airmana", () -> new Block(Block.Properties.copy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> EARTH_MANA = BLOCKS.register("earthmana", () -> new Block(Block.Properties.copy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> DARK_MANA = BLOCKS.register("darkmana", () -> new Block(Block.Properties.copy(Blocks.COAL_ORE)));
	public static final RegistryObject<Block> LIGHT_MANA = BLOCKS.register("lightmana", () -> new Block(Block.Properties.copy(Blocks.COAL_ORE)));

	//public static Block WATER_MANA;

}
