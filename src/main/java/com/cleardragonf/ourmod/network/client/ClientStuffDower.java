package com.cleardragonf.ourmod.network.client;

import com.cleardragonf.ourmod.entity.EntityStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ClientStuffDower {
    public static void update(double temperature, double thirst, int awakeTimer, UUID uuid) {
        if (uuid.equals(PlayerEntity.createPlayerUUID(Minecraft.getInstance().player.getGameProfile()))) {
            EntityStats.setTemperature((LivingEntity)(Minecraft.getInstance()).player, temperature);
            EntityStats.setThirst((LivingEntity)(Minecraft.getInstance()).player, thirst);
            EntityStats.setAwakeTime((LivingEntity)(Minecraft.getInstance().player), awakeTimer);
        }
    }
}
