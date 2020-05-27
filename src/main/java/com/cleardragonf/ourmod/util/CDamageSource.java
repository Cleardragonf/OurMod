package com.cleardragonf.ourmod.util;


import net.minecraft.util.DamageSource;

public class CDamageSource {
    public static final DamageSource HYPOTHERMIA = (new DamageSource("hypothermia")).setDamageIsAbsolute().setDifficultyScaled();

    public static final DamageSource HYPERTHERMIA = (new DamageSource("hyperthermia")).setDamageIsAbsolute().setDifficultyScaled();

    public static final DamageSource DEHYDRATE = (new DamageSource("dehydrate")).setDamageIsAbsolute().setDifficultyScaled();
}