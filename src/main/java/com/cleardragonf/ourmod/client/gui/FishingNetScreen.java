package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.FishingNetContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FishingNetScreen extends ContainerScreen<FishingNetContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(OurMod.MOD_ID, "textures/gui/fishingnet.png");

    public FishingNetScreen(FishingNetContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 183;
    }

    @Override
    public void render(MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack,mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack,int mouseX, int mouseY) {
        super.renderLabels(matrixStack,mouseX, mouseY);
        this.font.draw(matrixStack,this.title.getContents(), 8.0f, 6.0f, 4210752);
        this.font.draw(matrixStack,this.inventory.getDisplayName().getContents(), 8.0f, 90.0f, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack,float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack,x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
