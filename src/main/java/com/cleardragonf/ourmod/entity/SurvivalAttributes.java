package com.cleardragonf.ourmod.entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.text.TranslationTextComponent;

public class SurvivalAttributes {
    public static final Attribute COLD_RESISTANCE = (Attribute)(new RangedAttribute("ourmod.coldResistance", 2.0D, 0.0D, 1024.0D)).setShouldWatch(true);

    public static final Attribute HEAT_RESISTANCE = (Attribute)(new RangedAttribute("ourmod.heatResistance", 2.0d, 0.0d, 1024.0d)).setShouldWatch(true);
}
