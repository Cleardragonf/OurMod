package com.cleardragonf.ourmod.world;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.setup.ClientProxy;
import com.cleardragonf.ourmod.setup.IProxy;
import com.cleardragonf.ourmod.setup.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class OreGeneration {
    private  static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<ConfiguredFeature<?, ?>>();
    private  static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();
    private  static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<ConfiguredFeature<?, ?>>();

    public static void registerOre(){
        overworldOres.add(register("earthmana", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockInitNew.EARTH_MANA.get().getDefaultState(), 4)) //Veing Size
                .range(64).square() //spawn height start
                .func_242731_b(16))); //Chunk Spawn Frequency
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature){
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, OurMod.MOD_ID + ":" + name, configuredFeature);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gen(BiomeLoadingEvent event){
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        if(event.getCategory().equals(Biomes.PLAINS)){
            generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, overworldOres.get(0));
        }
    }

    /*
    //countRangeConfig(Count is the common 1 rare - 500...everywhere 20 is coal, bottomoffset is the lowest, top offset is how high from the top of the world, maximum
    public static ConfiguredPlacement ore = Placement.RANGE.configure(new TopSolidRangeConfig(25,10,128));
    public static ConfiguredPlacement airore = Placement.RANGE.configure(new TopSolidRangeConfig(3,100,128));
    public static ConfiguredPlacement lightore = Placement.RANGE.configure(new TopSolidRangeConfig(1,75,128));
    public static void setupOreGeneration() {


        for(Biome biome : ForgeRegistries.BIOMES) {
            if(biome.equals(Biomes.PLAINS) || biome.equals(Biomes.DESERT) || biome.equals(Biomes.MOUNTAINS) ) {
                public void generateFeatures(net.minecraft.world.gen.feature.structure.StructureManager structureManager,
                             net.minecraft.world.gen.ChunkGenerator chunkGenerator,
                             net.minecraft.world.gen.WorldGenRegion worldGenRegion,
                             long seed,
                             net.minecraft.util.SharedSeedRandom rand,
                             net.minecraft.util.math.BlockPos pos)


                World world = Minecraft.getInstance().world;
                DimensionGeneratorSettings settings = new DimensionGeneratorSettings(world.seed);
                StructureManager structure = new StructureManager(world, settings);
                biome.generateFeatures(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
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

     */
}
