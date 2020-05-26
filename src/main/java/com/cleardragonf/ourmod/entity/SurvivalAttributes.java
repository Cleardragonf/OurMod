package com.cleardragonf.ourmod.entity;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class SurvivalAttributes {
    public static final IAttribute COLD_RESISTANCE = (IAttribute)(new RangedAttribute((IAttribute)null, "ourmod.coldResistance", 2.0D, 0.0D, 1024.0D)).setShouldWatch(true);

    public static final IAttribute HEAT_RESISTANCE = (IAttribute)(new RangedAttribute((IAttribute)null, "ourmod.heatResistance", 2.0d, 0.0d, 1024.0d)).setShouldWatch(true);
}
