package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class OurBookScreen extends Screen {

    final ResourceLocation texture = new ResourceLocation(OurMod.MOD_ID, "textures/gui/ourbook.png");
    int guiHeight = 245;
    int guiWidth = 185;


    public OurBookScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().textureManager.bindTexture(texture);
        blit(0,0, 0,0,guiWidth, guiHeight);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
