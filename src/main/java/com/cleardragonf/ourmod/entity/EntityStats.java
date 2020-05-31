package com.cleardragonf.ourmod.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

public class EntityStats {
    public static void addStatsOnSpawn(PlayerEntity player){
        CompoundNBT compound = player.getPersistentData();
        String name = player.getScoreboardName();
        if(player.attackable()){
            if(!compound.contains("ourmod:Thirst")){
                setThirst((LivingEntity)player, 20.0d);
            }
            if(!compound.contains("ourmod:Temperature")){
                setTemperature((LivingEntity)player, 37.0d);
            }
            if(!compound.contains("ourmod:AwakeTime")){
                setAwakeTime((LivingEntity)player, 0);
            }
        }
    }

    //Thirst Portion
    public static void setThirst(LivingEntity entity, double thirst){
        CompoundNBT compound = entity.getPersistentData();
        compound.putDouble("ourmod:Thirst", thirst);
    }

    public static double getThirst(LivingEntity entity){
        CompoundNBT compound = entity.getPersistentData();
        if(compound != null && compound.contains("ourmod:Thirst")){
            return compound.getDouble("ourmod:Thirst");
        }
        return 0.0d;
    }

    public static boolean addThirst(LivingEntity entity, double thirst){
        CompoundNBT compound = entity.getPersistentData();
        if (compound != null && compound.contains("ourmod:Thirst")){
            PlayerEntity player = (PlayerEntity)entity;
            if(getThirst(entity) + thirst > 0.0d && getThirst(entity) + thirst < 20.0d){
                setThirst(entity, MathHelper.clamp(getThirst(entity) + thirst, 0.0d, 20.0d));
                return true;
            }
            if (getThirst(entity) + thirst >= 20.0D) {
                if (getThirst(entity) == 20.0D && thirst > 0.0D)
                    return false;
                setThirst(entity, 20.0D);
                return true;
            }
            if (getThirst(entity) + thirst <= 0.0D) {
                if (getThirst(entity) == 0.0D && thirst < 0.0D)
                    return false;
                setThirst(entity, 0.0D);
                return true;
            }
        } else {
            return true;
        }
    return false;
    }


    //Temperature Portion
    public static void setTemperature(LivingEntity entity, double temperature){
        CompoundNBT compound = entity.getPersistentData();
        compound.putDouble("ourmod:Temperature", temperature);
    }

    public static double getTemperature(LivingEntity entity){
        CompoundNBT compound = entity.getPersistentData();
        if(compound != null && compound.contains("ourmod:Temperature")){
            return compound.getDouble("ourmod:Temperature");
        }
        return 0.0d;
    }

    public static boolean addTemperature(LivingEntity player, double temperature){
        CompoundNBT compound = player.getPersistentData();
        if(compound != null && compound.contains("ourmod:Temperature")){
            double defaultTemp = 25.0D;
            double maxHeat1 = 40.0D;
            double maxHeat2 = 45.0D;
            double maxHeat3 = 50.0D;
            double maxCold1 = 10.0D;
            double maxCold2 = 5.0D;
            double maxCold3 = 0.0D;
            if (getTemperature(player) > defaultTemp && getTemperature(player) <= maxHeat1) {
                if (temperature < 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature * 1.5D);
                } else {
                    setTemperature(player, getTemperature(player) + temperature);
                }
            } else if (getTemperature(player) > maxHeat1 && getTemperature(player) <= maxHeat2) {
                if (temperature < 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature);
                } else {
                    setTemperature(player, getTemperature(player) + temperature / 10.0D);
                }
            } else if (getTemperature(player) > maxHeat2 && getTemperature(player) <= maxHeat3) {
                if (temperature < 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature / 10.0D);
                } else {
                    setTemperature(player, getTemperature(player) + temperature / 100.0D);
                }
            } else if (getTemperature(player) > maxHeat3) {
                if (temperature < 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature / 100.0D);
                } else {
                    setTemperature(player, maxHeat3);
                }
            } else if (getTemperature(player) < defaultTemp && getTemperature(player) <= maxCold1) {
                if (temperature > 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature * 1.5D);
                } else {
                    setTemperature(player, getTemperature(player) + temperature);
                }
            } else if (getTemperature(player) < maxCold1 && getTemperature(player) >= maxCold2) {
                if (temperature > 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature);
                } else {
                    setTemperature(player, getTemperature(player) + temperature / 10.0D);
                }
            } else if (getTemperature(player) < maxCold2 && getTemperature(player) >= maxCold3) {
                if (temperature > 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature / 10.0D);
                } else {
                    setTemperature(player, getTemperature(player) + temperature / 100.0D);
                }
            } else if (getTemperature(player) < maxCold3) {
                if (temperature < 0.0D) {
                    setTemperature(player, getTemperature(player) + temperature / 100.0D);
                } else {
                    setTemperature(player, maxCold3);
                }
            } else {
                setTemperature(player, getTemperature(player) + temperature);
            }
            return true;
        }
        return false;
    }


    //AwakeTime Portion
    public static void setAwakeTime(LivingEntity entity, int awakeTime){
        CompoundNBT compound = entity.getPersistentData();
        compound.putInt("ourmod:AwakeTime", awakeTime);
    }

    public static int getAwakeTime(LivingEntity entity){
        CompoundNBT compound = entity.getPersistentData();
        if(compound != null && compound.contains("ourmod:AwakeTime")){
            return compound.getInt("ourmod:AwakeTime");
        }
        return 0;
    }

    public static boolean addAwakeTime(LivingEntity entity, int awakeTime){
        CompoundNBT compound = entity.getPersistentData();
        if (compound != null && compound.contains("ourmod:AwakeTime")) {
            setAwakeTime(entity, getAwakeTime(entity) + awakeTime);
            if (getAwakeTime(entity) < 0)
                setAwakeTime(entity, 0);
            return true;
        }
        return false;
    }
}
