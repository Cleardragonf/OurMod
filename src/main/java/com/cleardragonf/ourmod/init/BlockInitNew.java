package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.objects.blocks.BlockQuarry;
import com.cleardragonf.ourmod.objects.blocks.PortableChest;
import com.cleardragonf.ourmod.objects.blocks.TomatoCrop;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInitNew {
	
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, OurMod.MOD_ID);
	
	public static final RegistryObject<Block> PORTABLE_CHEST = BLOCKS.register("portablechest", ()-> new PortableChest(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	public static final RegistryObject<Block> FORCEFIELD = BLOCKS.register("forcefield", ()-> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", ()-> new BlockQuarry(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0f, 15.0f)));
	
	//CROPS
	public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomatocrop", () -> new TomatoCrop(Block.Properties.from(Blocks.WHEAT)));
	}
