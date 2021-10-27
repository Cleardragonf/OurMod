package com.cleardragonf.ourmod.util;


import net.minecraft.util.DamageSource;

public class CDamageSource {
    public static final DamageSource HYPOTHERMIA = (new DamageSource("hypothermia")).bypassMagic().setScalesWithDifficulty();

    public static final DamageSource HYPERTHERMIA = (new DamageSource("hyperthermia")).bypassMagic().setScalesWithDifficulty();

    public static final DamageSource DEHYDRATE = (new DamageSource("dehydrate")).bypassMagic().setScalesWithDifficulty();

    public static final DamageSource WARDS = (new DamageSource("wards")).bypassMagic().setScalesWithDifficulty();
}