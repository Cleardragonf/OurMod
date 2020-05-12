package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.tileentity.EssenceCollectorTileEntity;
import com.cleardragonf.ourmod.tileentity.PortableChestTileEntity;
import com.cleardragonf.ourmod.tileentity.QuarryTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, OurMod.MOD_ID);
	
	public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = TILE_ENTITY_TYPES.register("quarry", () -> TileEntityType.Builder.create(QuarryTileEntity::new, BlockInitNew.QUARRY.get()).build(null));
	
	public static final RegistryObject<TileEntityType<PortableChestTileEntity>> PORTABLE_CHEST = TILE_ENTITY_TYPES.register("portablechest", () -> TileEntityType.Builder.create(PortableChestTileEntity::new, BlockInitNew.PORTABLE_CHEST.get()).build(null));
	
	public static final RegistryObject<TileEntityType<EssenceCollectorTileEntity>> ESSENCE_COLLECTOR = TILE_ENTITY_TYPES.register("essencecollector", () -> TileEntityType.Builder.create(EssenceCollectorTileEntity::new, BlockInitNew.ESSENCE_COLLECTOR.get()).build(null));
}
