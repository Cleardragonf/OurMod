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
	
	//CROPS
	public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomatocrop", () -> new TomatoCrop(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> RICE_CROP = BLOCKS.register("ricecrop", () -> new RiceCrop(Block.Properties.from(Blocks.WHEAT)));
	
}
