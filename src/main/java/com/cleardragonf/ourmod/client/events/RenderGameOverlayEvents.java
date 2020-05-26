package com.cleardragonf.ourmod.client.events;

import com.cleardragonf.ourmod.entity.EntityStats;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class RenderGameOverlayEvents extends AbstractGui {
    public static final ResourceLocation GUI_ICONS = new ResourceLocation("survive", "textures/gui/icons.png");

    static Minecraft mc = Minecraft.getInstance();

    static IngameGui ingameGUI = mc.ingameGUI;

    static long healthUpdateCounter;

    static int playerHealth;

    static long lastSystemTime;

    static int lastPlayerHealth;

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {
        PlayerEntity player = !(mc.player instanceof  PlayerEntity) ? null : (PlayerEntity)mc.player;

        if((event.getType() != RenderGameOverlayEvent.ElementType.ALL)){
            return;
        }
        mc.fontRenderer.drawString("Player Thirst" + EntityStats.getThirst(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 30, 0xfffff);
        mc.fontRenderer.drawString("Player Temp" + EntityStats.getTemperature(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 40, 0xfffff);
        mc.fontRenderer.drawString("Player Sleepy" + EntityStats.getAwakeTime(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 50, 0xfffff);

    }

}
