package com.cleardragonf.ourmod.entity;

import com.cleardragonf.ourmod.OurMod;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class EntityEffects {
    public static List<Effect> EFFECT = new ArrayList<>();

    public static final Effect HYPOTHERMIA = register("hypothermia", new EntityEffect(EffectType.HARMFUL, 5750248));

    public static final Effect HYPERTHERMIA = register("hyperthermia", new EntityEffect(EffectType.HARMFUL, 16750592));

    public static final Effect THIRST = register("thirst", new EntityEffect(EffectType.HARMFUL, 5797459));

    public static final Effect HEAT_RESISTANCE = register("heat_resistance", new EntityEffect(EffectType.BENEFICIAL, 12221756)).addAttributeModifier(SurvivalAttributes.HEAT_RESISTANCE, "795606d6-4ac6-4ae7-8311-63ccdb293eb3", 25.0D, AttributeModifier.Operation.ADDITION);

    public static final Effect COLD_RESISTANCE = register("cold_resistance", new EntityEffect(EffectType.BENEFICIAL, 5750248)).addAttributeModifier(SurvivalAttributes.COLD_RESISTANCE, "5cebe402-4f28-4d41-8539-2496f900ef90", 25.0D, AttributeModifier.Operation.ADDITION);

    public static final Effect CHILLED = register("chilled", new EntityEffect(EffectType.BENEFICIAL, 5750248));

    public static final Effect HEATED = register("heated", new EntityEffect(EffectType.BENEFICIAL, 16750592));

    public  static final Effect TEMPERATURE_WARD = register("temperatureward", new EntityEffect(EffectType.BENEFICIAL, 5750248));

    public static final Effect THIRST_WARD = register("thirstward", new EntityEffect(EffectType.BENEFICIAL, 5750248));

    public static final Effect HUNGER_WARD = register("hungerward", new EntityEffect(EffectType.BENEFICIAL, 5750248));

    public static final Effect HEALING_WARD = register("healingward", new EntityEffect(EffectType.BENEFICIAL, 5750248));

    public static final Effect ANTI_PASSIVE_WARD = register("antipassiveward", new EntityEffect(EffectType.HARMFUL, 5750248));

    public static final Effect ANTI_HOSTILE_WARD = register("antihostileward", new EntityEffect(EffectType.HARMFUL, 5750248));

    public static final Effect ANTI_GRAVITY_WARD = register("antigravityward", new EntityEffect(EffectType.BENEFICIAL, 5750248));

    public static final Effect DAYLIGHT_WARD = register("daylightward", new EntityEffect(EffectType.BENEFICIAL, 5750248));




    public static void registerAll(IForgeRegistry<Effect> registry) {
        for (Effect effect : EFFECT) {
            registry.register(effect);

        }

    }

    public static Effect register(String name, Effect effect) {
        effect.setRegistryName(OurMod.location(name));
        EFFECT.add(effect);
        return effect;
    }
}
