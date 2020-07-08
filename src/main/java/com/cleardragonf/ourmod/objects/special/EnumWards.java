package com.cleardragonf.ourmod.objects.special;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumWards implements IStringSerializable {
    HELP(0,"Help"),
    HEALING(1,"Healing"),
    DAYLIGHT(2,"Daylight"),
    ALWAYSNIGHT(3,"Always Night");

    private final int meta;
    private final String name;

    private EnumWards(int meta, String name){
        this.meta = meta;
        this.name = name;
    }

    public int getMeta(){
        return this.meta;
    }

    public String getName(){
        return this.name;
    }

    public static int count(){
        return values().length;
    }
}