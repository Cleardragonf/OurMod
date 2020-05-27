package com.cleardragonf.ourmod.entity;

import com.cleardragonf.ourmod.util.CDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EntityEffect extends Effect {
    public EntityEffect(EffectType effectType, int liquidColorIn) {
        super(effectType, liquidColorIn);
    }

    public void func_76394_a(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == EntityEffects.THIRST && entityLivingBaseIn instanceof PlayerEntity) {
            EntityStats.addThirst(entityLivingBaseIn, -(0.005F * (amplifier + 1)));
        } else if (this == EntityEffects.HYPOTHERMIA && entityLivingBaseIn instanceof PlayerEntity) {
            if (!((PlayerEntity)entityLivingBaseIn).attackable()) {
                entityLivingBaseIn.attackEntityFrom(CDamageSource.HYPOTHERMIA, 0.4F);
            }
                entityLivingBaseIn.attackEntityFrom(CDamageSource.HYPOTHERMIA, 0.4F);
        } else if (this == EntityEffects.HYPERTHERMIA && entityLivingBaseIn instanceof PlayerEntity) {
            if (!((PlayerEntity)entityLivingBaseIn).attackable()) {
                entityLivingBaseIn.attackEntityFrom(CDamageSource.HYPERTHERMIA, 0.4F);
            }
                entityLivingBaseIn.attackEntityFrom(CDamageSource.HYPERTHERMIA, 0.4F);

        } else if (this ==EntityEffects.CHILLED && entityLivingBaseIn instanceof PlayerEntity) {
            EntityStats.addTemperature(entityLivingBaseIn, -(0.05F * (amplifier + 1)));
        } else if (this == EntityEffects.HEATED && entityLivingBaseIn instanceof PlayerEntity) {
            EntityStats.addTemperature(entityLivingBaseIn, (0.05F * (amplifier + 1)));
        }
        super.performEffect(entityLivingBaseIn, amplifier);
    }

    public boolean func_76397_a(int duration, int amplifier) {
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
        return (this == EntityEffects.THIRST);
    }
}
