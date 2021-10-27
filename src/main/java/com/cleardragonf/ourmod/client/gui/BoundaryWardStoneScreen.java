package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.BoundaryWardStoneContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoundaryWardStoneScreen extends ContainerScreen<BoundaryWardStoneContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(OurMod.MOD_ID, "textures/gui/boundarywardstone.png");

	public BoundaryWardStoneScreen(BoundaryWardStoneContainer Container, PlayerInventory inv, ITextComponent titleIn) {
		super(Container, inv, titleIn);
		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 176;
		this.imageHeight = 246;
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
		//x,y,texturex,texturey,width,height @RyuShiTenshiKage
		//Air Essence GUI
		///this.blit((x + 17),y + 34, 179,33,8, this.container.getAir().getEnergyStored()/ 1000);
		this.blit(matrixStack,(x + 17),(y + 134) - (this.menu.getFire().getEnergyStored()/1000), 179,132  - (this.menu.getFire().getEnergyStored()/1000),8, (this.menu.getFire().getEnergyStored()/ 1000));
		this.blit(matrixStack,(x + 45),(y + 134) - (this.menu.getWater().getEnergyStored()/1000), 179,132  - (this.menu.getWater().getEnergyStored()/1000),8, (this.menu.getWater().getEnergyStored()/ 1000));
		this.blit(matrixStack,(x + 68),(y + 134) - (this.menu.getAir().getEnergyStored()/1000), 179,132  - (this.menu.getAir().getEnergyStored()/1000),8, (this.menu.getAir().getEnergyStored()/ 1000));
		this.blit(matrixStack,(x + 92),(y + 134) - (this.menu.getEarth().getEnergyStored()/1000), 179,132  - (this.menu.getEarth().getEnergyStored()/1000),8, (this.menu.getEarth().getEnergyStored()/ 1000));
		this.blit(matrixStack,(x + 120),(y + 134) - (this.menu.getDark().getEnergyStored()/1000), 179,132  - (this.menu.getDark().getEnergyStored()/1000),8, (this.menu.getDark().getEnergyStored()/ 1000));
		this.blit(matrixStack,(x + 148),(y + 134) - (this.menu.getLight().getEnergyStored()/1000), 179,132  - (this.menu.getLight().getEnergyStored()/1000),8, (this.menu.getLight().getEnergyStored()/ 1000));


		this.font.draw(matrixStack,"Wind: '" + this.menu.getAir().getEnergyStored() + "'", 8.0f, 9.0f, 4210752);
	}
	protected void renderHoveredToolTip(MatrixStack matrixStack,int x, int y){
		int relX = (this.width - this.imageWidth) /2;
		int relY = (this.height - this.imageHeight) /2;
		if(trueZone(relX + 17, relY + 34, 8,100,x,y)) {
			tooltipText(matrixStack,"Fire: ", x, y);
		}
		if(trueZone(relX + 45, relY + 34, 8,100,x,y)) {
			tooltipText(matrixStack,"Water: ", x, y);
		}
		if(trueZone(relX + 68, relY + 34, 8,100,x,y)) {
			tooltipText(matrixStack,"Air: ", x, y);
		}
		if(trueZone(relX + 92, relY + 34, 8,100,x,y)) {
			tooltipText(matrixStack,"Earth: ", x, y);
		}
		if(trueZone(relX + 120, relY + 34, 8,100,x,y)) {
			tooltipText(matrixStack,"Dark: ", x, y);
		}
		if(trueZone(relX + 148, relY + 34, 8,100,x,y)) {
			tooltipText(matrixStack,"Light: ", x, y);
		}
		super.renderTooltip(matrixStack,x, y);
	}

	private boolean trueZone(int OffsX, int OffsY, int Width, int Height, double MouseX, double MouseY){
		if(OffsX <= MouseX && MouseX <= OffsX + Width && OffsY <= MouseY && MouseY <= OffsY + Height){
			return true;
		}else{
			return false;
		}
	}

	public void tooltipText(MatrixStack matrixStack,String string, int x, int y) {
		switch (string) {
			case "Fire: ":
				this.renderTooltip(matrixStack,new TranslationTextComponent("Fire: '" + this.menu.getFire().getEnergyStored() + "'"), x, y);
				break;
			case "Water: ":
				this.renderTooltip(matrixStack,new TranslationTextComponent("Water: '" + this.menu.getWater().getEnergyStored() + "'"), x, y);
				break;
			case "Air: ":
				this.renderTooltip(matrixStack,new TranslationTextComponent("Air: '" + this.menu.getAir().getEnergyStored() + "'"), x, y);
				break;
			case "Earth: ":
				this.renderTooltip(matrixStack,new TranslationTextComponent("Earth: '" + this.menu.getEarth().getEnergyStored() + "'"), x, y);
				break;
			case "Dark: ":
				this.renderTooltip(matrixStack,new TranslationTextComponent("Dark: '" + this.menu.getDark().getEnergyStored() + "'"), x, y);
				break;
			case "Light: ":
				this.renderTooltip(matrixStack,new TranslationTextComponent("Light: '" + this.menu.getLight().getEnergyStored() + "'"), x, y);
				break;
		}
	}
}
