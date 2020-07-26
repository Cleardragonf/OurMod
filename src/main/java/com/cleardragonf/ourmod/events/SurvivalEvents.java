package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.EntityEffect;
import com.cleardragonf.ourmod.entity.EntityEffects;
import com.cleardragonf.ourmod.entity.EntityStats;
import com.cleardragonf.ourmod.entity.SurvivalAttributes;
import com.cleardragonf.ourmod.network.server.SurvivalStatsPacket;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

import java.io.Console;
import java.util.Random;

@Mod.EventBusSubscriber
public class SurvivalEvents {
    public static Object2FloatMap<BlockState> BLOCK_HEAT_MAP = (Object2FloatMap<BlockState>)new Object2FloatOpenHashMap();
    public static Object2FloatMap<Biome> BIOME_HEAT_MAP = (Object2FloatMap<Biome>)new Object2FloatOpenHashMap();

    public static void  registerHeatMap(){
        BLOCK_HEAT_MAP.put(Blocks.LAVA.getDefaultState(), 50.0f);
        BLOCK_HEAT_MAP.put(Blocks.WATER.getDefaultState(), 25.0f);

    }

    public static void registerBiomeHeatMap(){
        BIOME_HEAT_MAP.put(Biomes.BADLANDS, 45.0f);
        BIOME_HEAT_MAP.put(Biomes.BADLANDS_PLATEAU, 45.0f);
        BIOME_HEAT_MAP.put(Biomes.BAMBOO_JUNGLE, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.BAMBOO_JUNGLE_HILLS, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.BIRCH_FOREST, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.BIRCH_FOREST_HILLS, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.COLD_OCEAN, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.DEEP_FROZEN_OCEAN, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.DEEP_COLD_OCEAN, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.DESERT_LAKES, 49.0f);
        BIOME_HEAT_MAP.put(Biomes.DESERT, 49.0f);
        BIOME_HEAT_MAP.put(Biomes.DESERT_HILLS, 49.0f);
        BIOME_HEAT_MAP.put(Biomes.DARK_FOREST, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.DARK_FOREST_HILLS, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.DEEP_LUKEWARM_OCEAN, 27.0f);
        BIOME_HEAT_MAP.put(Biomes.DEEP_OCEAN, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.DEEP_WARM_OCEAN, 30.0f);
        BIOME_HEAT_MAP.put(Biomes.END_BARRENS, -5.0f);
        BIOME_HEAT_MAP.put(Biomes.END_HIGHLANDS, -5.0f);
        BIOME_HEAT_MAP.put(Biomes.END_MIDLANDS, -5.0f);
        BIOME_HEAT_MAP.put(Biomes.ERODED_BADLANDS, 45.0f);
        BIOME_HEAT_MAP.put(Biomes.FLOWER_FOREST, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.FOREST, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.FROZEN_OCEAN, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.FROZEN_RIVER, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.GIANT_SPRUCE_TAIGA, 20.0f);
        BIOME_HEAT_MAP.put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, 20.0f);
        BIOME_HEAT_MAP.put(Biomes.GIANT_TREE_TAIGA, 20.0f);
        BIOME_HEAT_MAP.put(Biomes.GIANT_TREE_TAIGA_HILLS, 20.0f);
        BIOME_HEAT_MAP.put(Biomes.GRAVELLY_MOUNTAINS, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.ICE_SPIKES, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.JUNGLE, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.JUNGLE_EDGE, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.JUNGLE_HILLS, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.LUKEWARM_OCEAN, 28.0f);
        BIOME_HEAT_MAP.put(Biomes.MODIFIED_BADLANDS_PLATEAU, 45.0f);
        BIOME_HEAT_MAP.put(Biomes.MODIFIED_GRAVELLY_MOUNTAINS, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.MODIFIED_JUNGLE, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.MODIFIED_JUNGLE_EDGE, 39.0f);
        BIOME_HEAT_MAP.put(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.MOUNTAIN_EDGE, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.MOUNTAINS, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.MUSHROOM_FIELD_SHORE, 30.0f);
        BIOME_HEAT_MAP.put(Biomes.MUSHROOM_FIELDS, 30.0f);
        BIOME_HEAT_MAP.put(Biomes.NETHER, 100.0f);
        BIOME_HEAT_MAP.put(Biomes.OCEAN, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.PLAINS, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.RIVER, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.SAVANNA, 42.0f);
        BIOME_HEAT_MAP.put(Biomes.SAVANNA_PLATEAU, 42.0f);
        BIOME_HEAT_MAP.put(Biomes.SHATTERED_SAVANNA, 42.0f);
        BIOME_HEAT_MAP.put(Biomes.SHATTERED_SAVANNA_PLATEAU, 42.0f);
        BIOME_HEAT_MAP.put(Biomes.SMALL_END_ISLANDS, -5.0f);
        BIOME_HEAT_MAP.put(Biomes.SNOWY_BEACH, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.SNOWY_MOUNTAINS, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.SNOWY_TAIGA, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.SNOWY_TAIGA_HILLS, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.SNOWY_TAIGA_MOUNTAINS, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.SNOWY_TUNDRA, 0.0f);
        BIOME_HEAT_MAP.put(Biomes.STONE_SHORE, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.SUNFLOWER_PLAINS, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.SWAMP, 30.0f);
        BIOME_HEAT_MAP.put(Biomes.SWAMP_HILLS, 30.0f);
        BIOME_HEAT_MAP.put(Biomes.TAIGA, 20.0f);
        BIOME_HEAT_MAP.put(Biomes.TAIGA_HILLS, 20.0f);
        BIOME_HEAT_MAP.put(Biomes.TAIGA_MOUNTAINS, 15.0f);
        BIOME_HEAT_MAP.put(Biomes.TALL_BIRCH_FOREST, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.TALL_BIRCH_HILLS, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.THE_END, -5.0f);
        BIOME_HEAT_MAP.put(Biomes.THE_VOID, 25.0f);
        BIOME_HEAT_MAP.put(Biomes.WARM_OCEAN, 30.0f);
        BIOME_HEAT_MAP.put(Biomes.WOODED_BADLANDS_PLATEAU, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.WOODED_HILLS, 35.0f);
        BIOME_HEAT_MAP.put(Biomes.WOODED_MOUNTAINS, 30.0f);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityEvent.EntityConstructing event){
        if(event.getEntity() instanceof PlayerEntity){
            ((PlayerEntity)event.getEntity()).getAttributes().registerAttribute(SurvivalAttributes.COLD_RESISTANCE);
            ((PlayerEntity)event.getEntity()).getAttributes().registerAttribute(SurvivalAttributes.HEAT_RESISTANCE);
        }
    }

    @SubscribeEvent
    public static void registerStats(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof  PlayerEntity){
            PlayerEntity player = (PlayerEntity)event.getEntityLiving();
            EntityStats.addStatsOnSpawn(player);
        }
    }


    //Sleeping Portions
    @SubscribeEvent
    public static void manageSleepStats(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            if(player.isSleeping()){
                EntityStats.addAwakeTime((LivingEntity)player, -player.getSleepTimer());
            }else{
                EntityStats.addAwakeTime((LivingEntity)player, 1);
            }
            for(int i = 0; i < 100; i++){
                addTiredEffect(player, i);
            }
        }
    }

    @SubscribeEvent
    public static void alwaysAllowSleep(SleepingTimeCheckEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            if(EntityStats.getAwakeTime((LivingEntity)player) > 1){
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    public static void addTiredEffect(ServerPlayerEntity player, int mul){
        if(EntityStats.getAwakeTime((LivingEntity)player) >= time(mul *6 + 0) && EntityStats.getAwakeTime((LivingEntity)player) < time(mul * 6 + 3)){
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, mul, false, false, true));
        }
        if(EntityStats.getAwakeTime((LivingEntity)player) >= time(mul * 6 + 1) && EntityStats.getAwakeTime((LivingEntity)player) < time(mul * 6 + 4)){
            player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 60, mul, false, false, true));
        }
        if(EntityStats.getAwakeTime((LivingEntity)player) >= time(mul * 6 + 2) && EntityStats.getAwakeTime((LivingEntity)player) < time(mul * 6 + 5)){
            player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60, mul, false, false, true));
        }
    }

    public static int time(int i){
        return 24000 + 8000 * i;
    }


    public static boolean thirstTimer(int i){
        for (int j = 0; j < i; j++) {
            if(j == i){
                j = 0;
                return true;
            }
            return false;
        }
        return false;
    }
    @SubscribeEvent
    public static void manageSleep(SleepFinishedTimeEvent event){
        for(PlayerEntity player : event.getWorld().getPlayers()){
            EntityStats.setAwakeTime((LivingEntity)player, 0);
        }
    }


    //UpdateClientSide
    @SubscribeEvent
    public static void sendToClient(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            OurMod.CHANNEL.sendTo(new SurvivalStatsPacket(player), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    //Environment and Player Events Driving the Stats
    @SubscribeEvent
    public static void regulateThirst(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            if(player.isSprinting()){
                EntityStats.addThirst(player, -0.01d);
            }
            if(player.isActualySwimming()){
                EntityStats.addThirst(player, -0.001d);
            }
            if(player.isOnLadder()){

            }
            if(player.isSwingInProgress){
                EntityStats.addThirst(player, -0.001D);
            }
        }
    }

    public static void applyThirst(LivingEntity entity){
        Random rand = new Random();
        int thirst = rand.nextInt(2);
    }

    @SubscribeEvent
    public static void drinkWaterFromBottle(LivingEntityUseItemEvent.Finish event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            if(PotionUtils.getPotionFromItem(event.getItem()) == Potions.WATER){
                applyThirst(event.getEntityLiving());
                EntityStats.addThirst(event.getEntityLiving(), 5.0D);
            }
        }
    }



    @SubscribeEvent//this portion is to be used to give hyperthermia or hypothermia based on their temps....look into creating those Effets....
    public static void regulateTemperature(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            double tempeature = EntityStats.getTemperature(player);

            double maxHeatStage1 = 40.0D;
            double maxHeatStage2 = 45.0d;
            double maxHeatStage3 = 50.0D;
            double maxColdStage1 = 10.0D;
            double maxColdStage2 = 5.0D;
            double maxColdStage3 = 0.0D;
            if(!player.isPotionActive(EntityEffects.HYPERTHERMIA)){
                if(tempeature > maxHeatStage1 && tempeature <= maxHeatStage2){
                    EntityStats.addThirst((LivingEntity)player, -0.01d);
                    player.addPotionEffect(new EffectInstance(EntityEffects.HYPERTHERMIA, 100, 0, false, false, false));
                }else if(tempeature > maxHeatStage2 && tempeature <= maxHeatStage3){
                    EntityStats.addThirst((LivingEntity)player, -0.5d);
                    player.addPotionEffect(new EffectInstance(EntityEffects.HYPERTHERMIA, 120, 1, false, false, false));
                }else if(tempeature > maxHeatStage3){
                    EntityStats.addThirst((LivingEntity)player, -0.1d);
                    player.addPotionEffect(new EffectInstance(EntityEffects.HYPERTHERMIA, 160, 2, false, false, false));
                }
            }
            if(!player.isPotionActive(EntityEffects.HYPOTHERMIA)){
                if(tempeature < maxColdStage1 && tempeature >= maxColdStage2){
                    int food = player.getFoodStats().getFoodLevel();
                    player.getFoodStats().setFoodLevel(food - 1);
                    player.addPotionEffect(new EffectInstance(EntityEffects.HYPOTHERMIA, 100, 0, false, false, false));
                }else if(tempeature < maxColdStage2 && tempeature >= maxColdStage3){
                    int food = player.getFoodStats().getFoodLevel();
                    player.getFoodStats().setFoodLevel(food - 1);
                    player.addPotionEffect(new EffectInstance(EntityEffects.HYPOTHERMIA, 120, 1, false, false, false));
                }else if(tempeature < maxColdStage3){
                    int food = player.getFoodStats().getFoodLevel();
                    player.getFoodStats().setFoodLevel(food - 1);
                    player.addPotionEffect(new EffectInstance(EntityEffects.HYPOTHERMIA, 160, 2, false, false, false));
                }
            }
        }
    }

    public static float getExactWorldTemperature(World world, BlockPos pos, TempType type){
        float biomeTemp = world.getBiome(pos).getDefaultTemperature();
        float gameTime = (float)(world.getGameTime());
        float skyLight = world.getLightFor(LightType.SKY, pos);
        float blockLight = world.getLight(pos);
        gameTime /= 66.0F;
        if(type == TempType.SUN){
            //if(skyLight > 5.0F){
             //   return gameTime * 5.0F + 37.0F;
            //}
            return 32.0F;
        }
        if(type == TempType.BIOME){
           if(BIOME_HEAT_MAP.containsKey(world.getBiome(pos))){

               return BIOME_HEAT_MAP.getFloat(world.getBiome(pos));
           }
           return blockLight / 3.0F + 37.0F;
        }
        if(type == TempType.BLOCK){
            //if(BLOCK_HEAT_MAP.containsKey(world.getBlockState(pos))){
             //   return BLOCK_HEAT_MAP.getFloat(world.getBlockState(pos));
            //}
            return blockLight / 3.0F + 37.0F;
        }
        if(type == TempType.SHADE){
            return skyLight / 1.5F - 5.0F +37.0F;
        }
        return 25.0F;//Default Temp
    }

    public static float getAverageWorldTemperature(){
        return 25.0F;
    }

    private enum TempType {
        BIOME(60, 10.0D),
        BLOCK(60, 750.0D),
        SHADE(200, 1000.0D),
        SUN(200, 1000.0D);

        double reductionAmount;

        int tickInterval;

        TempType(int tickIntervalIn, double reductionAmountIn) {
            this.tickInterval = tickIntervalIn;
            this.reductionAmount = reductionAmountIn;
        }

        public int getTickInterval() {
            return this.tickInterval;
        }

        public double getReductionAmount() {
            return this.reductionAmount;
        }
    }


    @SubscribeEvent
    public static void updateTemperature(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            for(TempType type : TempType.values()){
                double temperature;
                if(type == TempType.SHADE || type == TempType.SUN){
                    temperature = getExactWorldTemperature(player.world, player.getPosition(), type);
                }
                else if(type == TempType.BIOME){
                    temperature = getExactWorldTemperature(player.world, player.getPosition(), type);
                }
                else{
                    temperature = getAverageWorldTemperature();
                }
                double modifier = (temperature - EntityStats.getTemperature((LivingEntity)player)) / type.getReductionAmount();
                int modInt = (int)(modifier * 1000.0D);
                modifier = modInt / 1000.0D;
                if(player.ticksExisted % type.getTickInterval() == type.getTickInterval() - 1)
                    EntityStats.addTemperature((LivingEntity)player, modifier);

            }
        }
    }

    @SubscribeEvent
    public static void updateWards(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity) {
            if(event.getEntityLiving() instanceof MonsterEntity){
                MonsterEntity monster = (MonsterEntity) event.getEntityLiving();

                monster.getActivePotionEffects().forEach(e -> {
                    if(monster.isPotionActive(EntityEffects.ANTI_HOSTILE_WARD)){
                        if(e.getAmplifier() <= 10){
                            monster.setHealth(monster.getHealth() - e.getAmplifier());
                        }
                    }
                });
            }
            else if(event.getEntityLiving() instanceof AnimalEntity){
                AnimalEntity animal = (AnimalEntity)event.getEntityLiving();

                animal.getActivePotionEffects().forEach(e ->{
                    if(animal.isPotionActive(EntityEffects.ANTI_PASSIVE_WARD)){
                        if(e.getAmplifier() <=10){
                            animal.setHealth(animal.getHealth() - e.getAmplifier());
                        }
                    }
                });
            }
            else{
                ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();

                player.getActivePotionEffects().forEach(e -> {
                    if(player.isPotionActive(EntityEffects.HUNGER_WARD)){
                        if(e.getAmplifier() <= 10){
                            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + e.getAmplifier());
                        }
                        else if(e.getAmplifier() >= 11){
                            player.getFoodStats().setFoodLevel(20);
                        }
                    }
                    if(player.isPotionActive(EntityEffects.THIRST_WARD)){
                        if(e.getAmplifier() <= 10){
                            EntityStats.addThirst(player, e.getAmplifier());
                        }
                        else if(e.getAmplifier() >= 11){
                            EntityStats.setThirst(player, 20);
                        }
                    }
                    if(player.isPotionActive(EntityEffects.HEALING_WARD)){
                        if(e.getAmplifier() <= 10){
                            player.setHealth(player.getHealth() + e.getAmplifier());
                        }
                        else if(e.getAmplifier() >= 11){
                            player.setHealth(player.getMaxHealth());
                        }
                    }
                    if(player.isPotionActive(EntityEffects.TEMPERATURE_WARD)){
                        if(e.getAmplifier() <= 10){
                            EntityStats.setTemperature(player,25);
                        }
                    }
                    if(player.isPotionActive(EntityEffects.ANTI_GRAVITY_WARD)){
                        if(e.getAmplifier() <= 10){
                            player.abilities.allowFlying = true;
                        }
                    }
                });
            }

        }
    }
}
