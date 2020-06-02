package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.MCMChestContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MCMChestScreen extends ContainerScreen<MCMChestContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(OurMod.MOD_ID, "textures/gui/mcmchest.png");

	public MCMChestScreen(MCMChestContainer Container, PlayerInventory inv, ITextComponent titleIn) {
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

	}
	protected void renderHoveredToolTip(int x, int y){
		int relX = (this.width - this.xSize) /2;
		int relY = (this.height - this.ySize) /2;
		if(trueZone(relX + 17, relY + 34, 8,100,x,y)) {
			tooltipText("Fire: ", x, y);
		}
		if(trueZone(relX + 45, relY + 34, 8,100,x,y)) {
			tooltipText("Water: ", x, y);
		}
		if(trueZone(relX + 68, relY + 34, 8,100,x,y)) {
			tooltipText("Air: ", x, y);
		}
		if(trueZone(relX + 92, relY + 34, 8,100,x,y)) {
			tooltipText("Earth: ", x, y);
		}
		if(trueZone(relX + 120, relY + 34, 8,100,x,y)) {
			tooltipText("Dark: ", x, y);
		}
		if(trueZone(relX + 148, relY + 34, 8,100,x,y)) {
			tooltipText("Light: ", x, y);
		}
		super.renderHoveredToolTip(x, y);
	}

	private boolean trueZone(int OffsX, int OffsY, int Width, int Height, double MouseX, double MouseY){
		if(OffsX <= MouseX && MouseX <= OffsX + Width && OffsY <= MouseY && MouseY <= OffsY + Height){
			return true;
		}else{
			return false;
		}
	}

	public void tooltipText(String string, int x, int y) {
		switch (string) {
			/*
			case "Fire: ":
				this.renderTooltip("Fire: '" + this.container.getFire().getEnergyStored() + "'", x, y);
				break;
			case "Water: ":
				this.renderTooltip("Water: '" + this.container.getWater().getEnergyStored() + "'", x, y);
				break;
			case "Air: ":
				this.renderTooltip("Air: '" + this.container.getAir().getEnergyStored() + "'", x, y);
				break;
			case "Earth: ":
				this.renderTooltip("Earth: '" + this.container.getEarth().getEnergyStored() + "'", x, y);
				break;
			case "Dark: ":
				this.renderTooltip("Dark: '" + this.container.getDark().getEnergyStored() + "'", x, y);
				break;
			case "Light: ":
				this.renderTooltip("Light: '" + this.container.getLight().getEnergyStored() + "'", x, y);
				break;

			 */
		}
	}
}
