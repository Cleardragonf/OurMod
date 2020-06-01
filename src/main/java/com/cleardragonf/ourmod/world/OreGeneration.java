package com.cleardragonf.ourmod.world;

import com.cleardragonf.ourmod.init.BlockInitNew;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {
    //countRangeConfig(Count is the common 1 rare - 500...everywhere 20 is coal, bottomoffset is the lowest, top offset is how high from the top of the world, maximum
    public static ConfiguredPlacement ore = Placement.COUNT_RANGE.configure(new CountRangeConfig(25,10,0,128));
    public static ConfiguredPlacement airore = Placement.COUNT_RANGE.configure(new CountRangeConfig(3,100,0,128));
    public static ConfiguredPlacement lightore = Placement.COUNT_RANGE.configure(new CountRangeConfig(1,75,0,128));
    public static void setupOreGeneration() {


        for(Biome biome : ForgeRegistries.BIOMES) {
            if(biome.equals(Biomes.PLAINS) || biome.equals(Biomes.DESERT) || biome.equals(Biomes.MOUNTAINS) ) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInitNew.EARTH_MANA.get().getDefaultState(),
                10)).withPlacement(ore));

            }
            else if(biome.equals(Biomes.COLD_OCEAN) || biome.equals(Biomes.DEEP_COLD_OCEAN) || biome.equals(Biomes.DEEP_FROZEN_OCEAN) || biome.equals(Biomes.DEEP_LUKEWARM_OCEAN)||
                biome.equals(Biomes.DEEP_OCEAN) || biome.equals(Biomes.DEEP_OCEAN) || biome.equals(Biomes.DEEP_WARM_OCEAN) || biome.equals(Biomes.DESERT_LAKES) || biome.equals(Biomes.FROZEN_OCEAN) ||
                biome.equals(Biomes.FROZEN_RIVER) || biome.equals(Biomes.LUKEWARM_OCEAN) || biome.equals(Biomes.OCEAN) || biome.equals(Biomes.RIVER) || biome.equals(Biomes.WARM_OCEAN)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInitNew.WATER_MANA.get().getDefaultState(),
                10)).withPlacement(ore));
            }
            else if(biome.equals(Biomes.DESERT) || biome.equals(Biomes.DESERT_HILLS) || biome.equals(Biomes.DESERT_LAKES)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                BlockInitNew.EARTH_MANA.get().getDefaultState(),
            10)).withPlacement(ore));
            }
            else if(biome.equals(Biomes.SNOWY_BEACH) || biome.equals(Biomes.SNOWY_MOUNTAINS) || biome.equals(Biomes.SNOWY_TAIGA)|| biome.equals(Biomes.SNOWY_TAIGA_HILLS)||
                biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) || biome.equals(Biomes.SNOWY_TUNDRA)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInitNew.WATER_MANA.get().getDefaultState(),
                10)).withPlacement(ore));
            }
            else if(biome.equals(Biomes.NETHER) || biome.equals(Biomes.DESERT) || biome.equals(Biomes.DESERT_HILLS) || biome.equals(Biomes.DESERT_LAKES)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInitNew.FIRE_MANA.get().getDefaultState(),
                10)).withPlacement(ore));
            }
            else if(biome.equals(Biomes.END_BARRENS) || biome.equals(Biomes.END_HIGHLANDS) || biome.equals(Biomes.END_MIDLANDS) ||biome.equals(Biomes.THE_END) || biome.equals(Biomes.SMALL_END_ISLANDS)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInitNew.DARK_MANA.get().getDefaultState(),
                    10)).withPlacement(ore));
            }
            else if(biome.equals(Biomes.MOUNTAIN_EDGE) ||biome.equals(Biomes.MOUNTAINS) ||biome.equals(Biomes.GRAVELLY_MOUNTAINS) ||biome.equals(Biomes.MODIFIED_GRAVELLY_MOUNTAINS) ||
                biome.equals(Biomes.SNOWY_MOUNTAINS) ||biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) ||biome.equals(Biomes.TAIGA_MOUNTAINS) ||biome.equals(Biomes.WOODED_MOUNTAINS)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    BlockInitNew.AIR_MANA.get().getDefaultState(),
                   10)).withPlacement(airore));
            }
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                            BlockInitNew.LIGHT_MANA.get().getDefaultState(),
                            10)).withPlacement(lightore));

        }
    }
}
