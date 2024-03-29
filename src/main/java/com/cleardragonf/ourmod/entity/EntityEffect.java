package com.cleardragonf.ourmod.entity;

import com.cleardragonf.ourmod.util.CDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class EntityEffect extends Effect {
    public EntityEffect(EffectType effectType, int liquidColorIn) {
        super(effectType, liquidColorIn);
    }

    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EntityEffects.THIRST && entityLivingBaseIn instanceof PlayerEntity) {
            EntityStats.addThirst(entityLivingBaseIn, -(0.005F * (amplifier + 1)));
        }
        else if (this == EntityEffects.HYPOTHERMIA && entityLivingBaseIn instanceof PlayerEntity) {
            if (!((PlayerEntity)entityLivingBaseIn).attackable()) {
                entityLivingBaseIn.hurt(CDamageSource.HYPOTHERMIA, 0.4F);
            }
            if(((PlayerEntity) entityLivingBaseIn).getFoodData().getFoodLevel() > 0){

            }else{
                entityLivingBaseIn.hurt(CDamageSource.HYPOTHERMIA, 1);
            }

        }
        else if (this == EntityEffects.HYPERTHERMIA && entityLivingBaseIn instanceof PlayerEntity) {
            if (!((PlayerEntity)entityLivingBaseIn).attackable()) {
                entityLivingBaseIn.hurt(CDamageSource.HYPERTHERMIA, 0.4F);
            }
            if(EntityStats.getThirst((PlayerEntity)entityLivingBaseIn) > 0){

            }else{
                entityLivingBaseIn.hurt(CDamageSource.HYPERTHERMIA, 1);
            }


        }
        else if (this ==EntityEffects.CHILLED && entityLivingBaseIn instanceof PlayerEntity) {
            EntityStats.addTemperature(entityLivingBaseIn, -(0.05F * (amplifier + 1)));
        }
        else if (this == EntityEffects.HEATED && entityLivingBaseIn instanceof PlayerEntity) {
            EntityStats.addTemperature(entityLivingBaseIn, (0.05F * (amplifier + 1)));
        }
        else if (this == EntityEffects.ANTI_HOSTILE_WARD && entityLivingBaseIn instanceof MonsterEntity){
            if (!((MonsterEntity)entityLivingBaseIn).attackable()) {
                entityLivingBaseIn.hurt(CDamageSource.WARDS, 50.0F);
            }
                entityLivingBaseIn.hurt(CDamageSource.WARDS, 50.0f);

        }
        else if (this == EntityEffects.ANTI_PASSIVE_WARD && entityLivingBaseIn instanceof AnimalEntity){
            if (!((AnimalEntity)entityLivingBaseIn).attackable()) {
                entityLivingBaseIn.hurt(CDamageSource.WARDS, 50.0F);
            }
            entityLivingBaseIn.hurt(CDamageSource.WARDS, 50.0f);

        }else{
            System.out.println(entityLivingBaseIn.getClass());
        }
        super.applyEffectTick(entityLivingBaseIn, amplifier);
    }

    public boolean isReady(int duration, int amplifier) {
        if (this == EntityEffects.CHILLED) {
            int k = 60 >> amplifier;
            if (k > 0)
                return (duration % k == 0);
            return true;
        }
        if (this == EntityEffects.HEATED) {
            int k = 60 >> amplifier;
            if (k > 0)
                return (duration % k == 0);
            return true;
        }
        if (this == EntityEffects.HYPERTHERMIA) {
            int k = 40 >> amplifier;
            if (k > 0)
                return (duration % k == 0);
            return true;
        }
        if (this == EntityEffects.HYPOTHERMIA) {
            int k = 40 >> amplifier;
            if (k > 0)
                return (duration % k == 0);
            return true;
        }
        if (this == EntityEffects.ANTI_HOSTILE_WARD) {
            int k = 40 >> amplifier;
            if (k > 0)
                return (duration % k == 0);
            return true;
        }
        if (this == EntityEffects.ANTI_PASSIVE_WARD) {
            int k = 40 >> amplifier;
            if (k > 0)
                return (duration % k == 0);
            return true;
        }
        return (this == EntityEffects.THIRST);
    }
}
