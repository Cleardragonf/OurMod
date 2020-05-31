package com.cleardragonf.ourmod.client.events;

import com.cleardragonf.ourmod.entity.EntityEffect;
import com.cleardragonf.ourmod.entity.EntityStats;
import com.cleardragonf.ourmod.entity.SurvivalAttributes;
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

import java.awt.*;
import java.util.Random;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class RenderGameOverlayEvents extends AbstractGui {
    public static final ResourceLocation GUI_ICONS = new ResourceLocation("ourmod", "textures/overlay/icons.png");

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
        //mc.fontRenderer.drawString("Player Thirst" + EntityStats.getThirst(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 30, 0xfffff);
        //mc.fontRenderer.drawString("Player Temp" + EntityStats.getTemperature(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 40, 0xfffff);
        mc.fontRenderer.drawString("Player Sleepy" + EntityStats.getAwakeTime(player), mc.getMainWindow().getScaledWidth() -100, mc.getMainWindow().getScaledHeight() - 60, 0xfffff);
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();

        renderThirstOverlay(scaledWidth, scaledHeight, player);
        renderTemperatureOverlay(scaledWidth, scaledHeight,  player);
       // renderAwakeOverlay(scaledWidth, scaledHeight, player);




    }

    private static void renderThirstOverlay(int scaledWidth, int scaledHeight, PlayerEntity player) {
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(GUI_ICONS);
        int yStart = (height-150)/2 ;
        int yPosition = (mc.getMainWindow().getScaledHeight() -mc.getMainWindow().getScaledHeight()) + (mc.getMainWindow().getScaledHeight() - 10);
        int xPosition = (mc.getMainWindow().getScaledWidth() - 100);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        int thirst = (int)EntityStats.getThirst((LivingEntity)player);
        //x,y,tx,ty,width,height @RyuShiTenshiKage

        switch (thirst){
            case 20:
                fullthirstBarCounter((int)EntityStats.getThirst((LivingEntity)player), xPosition, yPosition);
                break;
            case 19:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                break;
            case 18:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition,  yPosition);
                break;
            case 17:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 16:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 15:
                fullthirstBarCounter(thirst , xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 14:
                fullthirstBarCounter(thirst , xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 13:
                fullthirstBarCounter(thirst , xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 12:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 11:
                fullthirstBarCounter(thirst , xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 10:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 9:
                fullthirstBarCounter(thirst , xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 8:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 7:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 6:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 5:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 4:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 3:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 2:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 1:
                fullthirstBarCounter(thirst, xPosition, yPosition);
                halfthirstBarCounter(thirst, xPosition, yPosition);
                OddemptyThirstCounter(thirst, xPosition, yPosition);
                break;
            case 0:
                EvenemptyThirstCounter(thirst, xPosition, yPosition);
                break;

        }

        mc.fontRenderer.drawString("Thirst: " + (int)EntityStats.getThirst((LivingEntity)player), xPosition, yPosition -40 , 0x00ccff);
    }

    private static void EvenemptyThirstCounter(int thirst, int xPosition, int yPosition) {
        int empty = (20 - thirst)/2;
        for (int i = 0; i < empty; i++) {
            mc.ingameGUI.blit(xPosition + ((thirst/2) * 10) + (i * 10), yPosition - 30, 31, 4, 10, 15);
        }

    }
    private static void OddemptyThirstCounter(int thirst, int xPosition, int yPosition) {
        int empty = (20 - thirst)/2;
        for (int i = 0; i < empty + 1; i++) {
            mc.ingameGUI.blit(xPosition + ((thirst/2) * 10) + (i * 10), yPosition - 30, 31, 4, 10, 15);
        }

    }
    private static void halfthirstBarCounter(int thirst, int xPosition, int yPosition) {
        mc.ingameGUI.blit(xPosition + ((thirst /2)* 10), yPosition - 30, 19, 4, 10, 15);

    }
    public static void fullthirstBarCounter(int i, int xPosition, int yPosition){
        //for (int j = 0; j < i; j++) {
        //    mc.ingameGUI.blit(xPosition + (i * 10), yPosition -20, 7,4, 10,15);
        //}
            int amount = 20-i;
            int remainder = (20 - (20 - i))/2;
            for (int j = 0; j < remainder; j++) {
                mc.ingameGUI.blit(xPosition + (j * 10), yPosition - 30, 7, 4, 10, 15);
            }
    }

    private static void renderTemperatureOverlay(int scaledWidth, int scaledHeight, PlayerEntity player) {
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(GUI_ICONS);
        int yStart = (height-150)/2 ;
        int yPosition = (mc.getMainWindow().getScaledHeight() -mc.getMainWindow().getScaledHeight()) + (mc.getMainWindow().getScaledHeight() - 10);
        int xPosition = (mc.getMainWindow().getScaledWidth() - 100);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();

        //x,y,tx,ty,width,height @RyuShiTenshiKage
            mc.ingameGUI.blit(xPosition, yPosition, 7,34, 100,6);
            mc.ingameGUI.blit(xPosition + ((int)EntityStats.getTemperature((LivingEntity)player) *2), yPosition -1, 7, 41, 5, 8);

            mc.fontRenderer.drawString("Temperature: " + (int)EntityStats.getTemperature((LivingEntity)player), xPosition, yPosition -10 , 0x00ccff);
    }


}
