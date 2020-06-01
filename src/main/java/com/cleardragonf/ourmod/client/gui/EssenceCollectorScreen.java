package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.EssenceCollectorContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EssenceCollectorScreen extends ContainerScreen<EssenceCollectorContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(OurMod.MOD_ID, "textures/gui/essencecollector.png");

	public EssenceCollectorScreen(EssenceCollectorContainer Container, PlayerInventory inv, ITextComponent titleIn) {
		super(Container, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 246;
	}
	
	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, 90.0f, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(x, y, 0, 0, this.xSize, this.ySize);
		//x,y,texturex,texturey,width,height @RyuShiTenshiKage
		//Air Essence GUI
		///this.blit((x + 17),y + 34, 179,33,8, this.container.getAir().getEnergyStored()/ 1000);
		this.blit((x + 17),(y + 133) - (this.container.getAir().getEnergyStored()/100), 179,132  - (this.container.getAir().getEnergyStored()/100),8, (this.container.getAir().getEnergyStored()/ 100));


		this.font.drawString("Wind: '" + this.container.getAir().getEnergyStored() + "'", 8.0f, 9.0f, 4210752);
	}
}
