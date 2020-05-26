package com.cleardragonf.ourmod.events;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.EntityStats;
import com.cleardragonf.ourmod.entity.SurvivalAttributes;
import com.cleardragonf.ourmod.network.server.SurvivalStatsPacket;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

import java.io.Console;

@Mod.EventBusSubscriber
public class SurvivalEvents {
    public static Object2FloatMap<BlockState> BLOCK_HEAT_MAP = (Object2FloatMap<BlockState>)new Object2FloatOpenHashMap();
    public static Object2FloatMap<Biome> BIOME_HEAT_MAP = (Object2FloatMap<Biome>)new Object2FloatOpenHashMap();

    public static void registerHeatMap(){
        BLOCK_HEAT_MAP.put(Blocks.LAVA.getDefaultState(), 1000.0F);
        BLOCK_HEAT_MAP.put(Blocks.SAND.getDefaultState(), 100.0F);
        BLOCK_HEAT_MAP.put(Blocks.WATER.getDefaultState(), -25.0F);

    }

    public static void registerBiomeHeatMap(){
        BIOME_HEAT_MAP.put(Biomes.DESERT, 100.0F);
        BIOME_HEAT_MAP.put(Biomes.DESERT_HILLS, 100.0F);
        BIOME_HEAT_MAP.put(Biomes.DESERT_LAKES, 100.0F);
        BIOME_HEAT_MAP.put(Biomes.BEACH, 90.0F);
        BIOME_HEAT_MAP.put(Biomes.COLD_OCEAN, 0.0F);
        BIOME_HEAT_MAP.put(Biomes.DEEP_COLD_OCEAN, 0.0F);
        BIOME_HEAT_MAP.put(Biomes.DEEP_FROZEN_OCEAN, 0.0F);
        BIOME_HEAT_MAP.put(Biomes.PLAINS, 32.0F);
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
                EntityStats.addThirst(player, -0.05d);
            }
            if(player.isActualySwimming()){
                EntityStats.addThirst(player, -0.05d);
            }
            if(player.isOnLadder()){

            }
        }
    }



    @SubscribeEvent//this portion is to be used to give hyperthermia or hypothermia based on their temps....look into creating those Effets....
    public static void regulateTemperature(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity)event.getEntityLiving();
            double tempeature = EntityStats.getTemperature(player);

            double maxHeatStage1 = 37.0D + player.getAttribute(SurvivalAttributes.HEAT_RESISTANCE).getBaseValue() * 1.0D;
            double maxHeatStage2 = 37.0D + player.getAttribute(SurvivalAttributes.HEAT_RESISTANCE).getBaseValue() * 1.0D + 0.3333333333333333D;
            double maxHeatStage3 = 37.0D + player.getAttribute(SurvivalAttributes.HEAT_RESISTANCE).getBaseValue() * 1.0D + 0.4444444444444444D;
            double maxColdStage1 = 37.0D - player.getAttribute(SurvivalAttributes.COLD_RESISTANCE).getBaseValue() * 1.0D;
            double maxColdStage2 = 37.0D - player.getAttribute(SurvivalAttributes.COLD_RESISTANCE).getBaseValue() * 1.0D + 0.3333333333333333D;
            double maxColdStage3 = 37.0D - player.getAttribute(SurvivalAttributes.COLD_RESISTANCE).getBaseValue() * 1.0D + 0.4444444444444444D;

            if(EntityStats.getTemperature(player) != 0){
                if(tempeature > maxHeatStage1 && tempeature <= maxHeatStage2){
                    EntityStats.addThirst((LivingEntity)player, -0.05d);
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 0, false, false, false));
                }else if(tempeature > maxHeatStage2 && tempeature <= maxHeatStage3){
                    EntityStats.addThirst((LivingEntity)player, -0.1d);
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 1, false, false, false));
                }else if(tempeature > maxHeatStage3){
                    EntityStats.addThirst((LivingEntity)player, -0.2d);
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 2, false, false, false));
                }
            }
            if(EntityStats.getTemperature(player) != 100){
                if(tempeature < maxColdStage1 && tempeature >= maxColdStage2){
                    EntityStats.addThirst((LivingEntity)player, -0.05d);
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 0, false, false, false));
                }else if(tempeature < maxColdStage2 && tempeature >= maxColdStage3){
                    EntityStats.addThirst((LivingEntity)player, -0.1d);
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 1, false, false, false));
                }else if(tempeature < maxColdStage3){
                    EntityStats.addThirst((LivingEntity)player, -0.2d);
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 2, false, false, false));
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
            if(skyLight > 5.0F){
                return gameTime * 5.0F + 37.0F;
            }
            return 32.0F;
        }
        if(type == TempType.BIOME){
           if(BIOME_HEAT_MAP.containsKey(world.getBiome(pos))){
               return BIOME_HEAT_MAP.getFloat(world.getBiome(pos));
           }
           return blockLight / 3.0F + 37.0F;
        }
        if(type == TempType.BLOCK){
            if(BLOCK_HEAT_MAP.containsKey(world.getBlockState(pos))){
                return BLOCK_HEAT_MAP.getFloat(world.getBlockState(pos));
            }
            return blockLight / 3.0F + 37.0F;
        }
        if(type == TempType.SHADE){
            return skyLight / 1.5F - 5.0F +37.0F;
        }
        return 37.0F;//Default Temp
    }

    private enum TempType {
        BIOME(100, 990.0D),
        BLOCK(20, 750.0D),
        SHADE(20, 1000.0D),
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


}
