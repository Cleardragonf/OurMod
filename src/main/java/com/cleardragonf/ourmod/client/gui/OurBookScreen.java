package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class OurBookScreen extends Screen {

    public OurBookScreen(ITextComponent titleIn) {
        super(titleIn);
    }
    private static final ResourceLocation TOTEM_GUI_TEXTURE = new ResourceLocation(OurMod.MOD_ID, "textures/gui/ourbook.png");
    int xSize = 185;
    int ySize = 245;

    @Override
    public void init() {
        super.init();
        this.buttons.clear();

    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(TOTEM_GUI_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
        this.blit(x-150, y+235, 187,5,26,22);
        this.blit(x+150, y+235, 213,5,26,22);
        super.render(mouseX, mouseY, partialTicks);
        /* //Button Based Code
        this.addButton(new Button (x + 100, y + 10, 50, 20, "Next Page", (button) -> {
            System.out.println("button testing");
        }));
        this.addButton(new Button (x - 100, y + 10, 50, 20, "Prev Page", (button) -> {
            System.out.println("button testing");
        }));
        */
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
