package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.EntitySpawnerContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EntitySpawnerScreen extends ContainerScreen<EntitySpawnerContainer> {

	private ResourceLocation GUI = new ResourceLocation(OurMod.MOD_ID, "textures/gui/entityspawner.png");

	public EntitySpawnerScreen(EntitySpawnerContainer container, PlayerInventory inv, ITextComponent name) {
		super(container, inv, name);
		this.xSize = 176;
		this.ySize = 256;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack,mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack,mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack,int mouseX, int mouseY) {
		//drawString(Minecraft.getInstance().fontRenderer, "Energy: " + this.container.getFire().getEnergyStored(), 10, 10, 0xffffff);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack,float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) / 2;
		int relY = (this.height - this.ySize) / 2;
		this.blit(matrixStack,relX, relY, 0, 0, this.xSize, this.ySize);
		int display = (int) this.container.getMCM().getEnergyStored();

		drawString(matrixStack,Minecraft.getInstance().fontRenderer, "Energy: " + display, 10, 10, 0xffffff);

	}
}
