package com.cleardragonf.ourmod.client.events;

import com.cleardragonf.ourmod.entity.EntityEffect;
import com.cleardragonf.ourmod.entity.EntityStats;
import com.cleardragonf.ourmod.entity.SurvivalAttributes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

    static IngameGui ingameGUI = mc.gui;

    static long healthUpdateCounter;

    static int playerHealth;

    static long lastSystemTime;

    static int lastPlayerHealth;

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {
        PlayerEntity player = !(mc.player instanceof  PlayerEntity) ? null : (PlayerEntity)mc.player;
        MatrixStack matrixStack = new MatrixStack();
        if((event.getType() != RenderGameOverlayEvent.ElementType.ALL)){
            return;
        }
        //mc.fontRenderer.drawString("Player Thirst" + EntityStats.getThirst(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 30, 0xfffff);
        //mc.fontRenderer.drawString("Player Temp" + EntityStats.getTemperature(player), mc.getMainWindow().getScaledWidth() + (10 -mc.getMainWindow().getScaledWidth()), mc.getMainWindow().getScaledHeight() - 40, 0xfffff);

        mc.font.draw(matrixStack,"Player Sleepy" + EntityStats.getAwakeTime(player), mc.getWindow().getGuiScaledWidth() -100, mc.getWindow().getGuiScaledHeight() - 60, 0xfffff);
        int scaledWidth = mc.getWindow().getGuiScaledWidth();
        int scaledHeight = mc.getWindow().getGuiScaledHeight();

        renderThirstOverlay(scaledWidth, scaledHeight, player, matrixStack);
        renderTemperatureOverlay(scaledWidth, scaledHeight,  player, matrixStack);
       // renderAwakeOverlay(scaledWidth, scaledHeight, player);




    }

    private static void renderThirstOverlay(int scaledWidth, int scaledHeight, PlayerEntity player, MatrixStack matrixStack) {
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        Minecraft.getInstance().getEntityRenderDispatcher().textureManager.bind(GUI_ICONS);
        int yStart = (height-150)/2 ;
        int yPosition = (mc.getWindow().getGuiScaledHeight() -mc.getWindow().getGuiScaledHeight()) + (mc.getWindow().getGuiScaledHeight() - 10);
        int xPosition = (mc.getWindow().getGuiScaledWidth() - 100);

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
                fullthirstBarCounter((int)EntityStats.getThirst((LivingEntity)player), xPosition, yPosition, matrixStack);
                break;
            case 19:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 18:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition,  yPosition, matrixStack);
                break;
            case 17:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 16:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 15:
                fullthirstBarCounter(thirst , xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 14:
                fullthirstBarCounter(thirst , xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 13:
                fullthirstBarCounter(thirst , xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 12:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 11:
                fullthirstBarCounter(thirst , xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 10:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 9:
                fullthirstBarCounter(thirst , xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 8:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 7:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 6:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 5:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 4:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 3:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 2:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 1:
                fullthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                halfthirstBarCounter(thirst, xPosition, yPosition, matrixStack);
                OddemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;
            case 0:
                EvenemptyThirstCounter(thirst, xPosition, yPosition, matrixStack);
                break;

        }

        mc.font.draw(matrixStack,"Thirst: " + (int)EntityStats.getThirst((LivingEntity)player), xPosition, yPosition -40 , 0x00ccff);
    }

    private static void EvenemptyThirstCounter(int thirst, int xPosition, int yPosition, MatrixStack matrixStack) {
        int empty = (20 - thirst)/2;
        for (int i = 0; i < empty; i++) {
            mc.gui.blit(matrixStack,xPosition + ((thirst/2) * 10) + (i * 10), yPosition - 30, 31, 4, 10, 15);
        }

    }
    private static void OddemptyThirstCounter(int thirst, int xPosition, int yPosition, MatrixStack matrixStack) {
        int empty = (20 - thirst)/2;
        for (int i = 0; i < empty + 1; i++) {
            mc.gui.blit(matrixStack,xPosition + ((thirst/2) * 10) + (i * 10), yPosition - 30, 31, 4, 10, 15);
        }

    }
    private static void halfthirstBarCounter(int thirst, int xPosition, int yPosition, MatrixStack matrixStack) {
        mc.gui.blit(matrixStack,xPosition + ((thirst /2)* 10), yPosition - 30, 19, 4, 10, 15);

    }
    public static void fullthirstBarCounter(int i, int xPosition, int yPosition, MatrixStack matrixStack){
        //for (int j = 0; j < i; j++) {
        //    mc.ingameGUI.blit(xPosition + (i * 10), yPosition -20, 7,4, 10,15);
        //}
            int amount = 20-i;
            int remainder = (20 - (20 - i))/2;
            for (int j = 0; j < remainder; j++) {
                mc.gui.blit(matrixStack,xPosition + (j * 10), yPosition - 30, 7, 4, 10, 15);
            }
    }

    private static void renderTemperatureOverlay(int scaledWidth, int scaledHeight, PlayerEntity player, MatrixStack matrixStack) {
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        Minecraft.getInstance().getEntityRenderDispatcher().textureManager.bind(GUI_ICONS);
        int yStart = (height-150)/2 ;
        int yPosition = (mc.getWindow().getGuiScaledHeight() -mc.getWindow().getGuiScaledHeight()) + (mc.getWindow().getGuiScaledHeight() - 10);
        int xPosition = (mc.getWindow().getGuiScaledWidth() - 100);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();

        //x,y,tx,ty,width,height @RyuShiTenshiKage
            mc.gui.blit(matrixStack,xPosition, yPosition, 7,34, 100,6);
            mc.gui.blit(matrixStack,xPosition + ((int)EntityStats.getTemperature((LivingEntity)player) *2), yPosition -1, 7, 41, 5, 8);

            mc.font.draw(matrixStack,"Temperature: " + (int)EntityStats.getTemperature((LivingEntity)player), xPosition, yPosition -10 , 0x00ccff);
    }


}
