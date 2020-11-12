package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.MasterWardStoneContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javafx.scene.control.ScrollPane;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.swing.*;
import java.awt.*;

//TODO: add Scroll bar, scroll pane etc.

@OnlyIn(Dist.CLIENT)
public class MasterWardStoneScreen extends ContainerScreen<MasterWardStoneContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(OurMod.MOD_ID, "textures/gui/masterwardstone.png");

	public MasterWardStoneScreen(MasterWardStoneContainer Container, PlayerInventory inv, ITextComponent titleIn) {
		super(Container, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	public void render(MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack,mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack,mouseX, mouseY);


	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack,int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(matrixStack,mouseX, mouseY);
		this.font.drawString(matrixStack,this.title.getUnformattedComponentText(), 8.0f, 6.0f, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack,float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);

		//Display of Internal Energy
		this.font.drawString(matrixStack,"Fire: '" + this.container.getFire().getEnergyStored() + "'", x+180.0f, y+190.0f, 4210752);
		this.font.drawString(matrixStack,"Water: '" + this.container.getWater().getEnergyStored() + "'", x+180.0f, y+198.0f, 4210752);
		this.font.drawString(matrixStack,"Air: '" + this.container.getAir().getEnergyStored() + "'", x+180.0f, y+206.0f, 4210752);
		this.font.drawString(matrixStack,"Earth: '" + this.container.getEarth().getEnergyStored() + "'", x+180.0f, y+214.0f, 4210752);
		this.font.drawString(matrixStack,"Light: '" + this.container.getLight().getEnergyStored() + "'", x+180.0f, y+222.0f, 4210752);
		this.font.drawString(matrixStack,"Dark: '" + this.container.getDark().getEnergyStored() + "'", x+180.0f, y+230.0f, 4210752);

	}
}
