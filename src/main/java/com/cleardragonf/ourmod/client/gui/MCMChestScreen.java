package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.MCMChestContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MCMChestScreen extends ContainerScreen<MCMChestContainer> {

	private ResourceLocation GUI = new ResourceLocation(OurMod.MOD_ID, "textures/gui/mcmchest.png");

	public MCMChestScreen(MCMChestContainer container, PlayerInventory inv, ITextComponent name) {
		super(container, inv, name);
		this.xSize = 176;
		this.ySize = 256;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//drawString(Minecraft.getInstance().fontRenderer, "Energy: " + this.container.getFire().getEnergyStored(), 10, 10, 0xffffff);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) / 2;
		int relY = (this.height - this.ySize) / 2;
		this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
		int display = (int) this.container.getMCM().getEnergyStored();

		drawString(Minecraft.getInstance().fontRenderer, "Energy: " + display, 10, 10, 0xffffff);

	}
}
