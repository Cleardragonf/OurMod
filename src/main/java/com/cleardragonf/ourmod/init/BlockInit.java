package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.OurMod.OurModItemGroup;
import com.cleardragonf.ourmod.objects.blocks.BlockQuarry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(OurMod.MOD_ID)
@EventBusSubscriber(modid=OurMod.MOD_ID, bus = Bus.MOD)
public class BlockInit {
	
	public static final Block forcefield = null;
	public static final Block quarry = null;
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.METAL)).setRegistryName("forcefield"));
		event.getRegistry().register(new BlockQuarry(Block.Properties.create(Material.IRON)).setRegistryName("quarry"));
	}
	
	@SubscribeEvent
	public static void registerBlockItem(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new BlockItem(forcefield, new Item.Properties().maxStackSize(64).group(OurModItemGroup.instance)).setRegistryName("forcefield"));
		event.getRegistry().register(new BlockItem(quarry, new Item.Properties().group(OurModItemGroup.instance)).setRegistryName("quarry"));
	}

}
