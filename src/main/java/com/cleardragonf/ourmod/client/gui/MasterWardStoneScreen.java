package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.MasterWardStoneContainer;
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
		///this.blit((x + 17),y + 34, 179,33,8, this.container.getAir().getEnergyStored()/ 1000);

		//x,y,texturex,texturey,width,height @RyuShiTenshiKage
		//Air Essence GUI
		///this.blit((x + 17),y + 34, 179,33,8, this.container.getAir().getEnergyStored()/ 1000);
		this.blit((x + 17),(y + 134) - (this.container.getFire().getEnergyStored()/1000), 179,132  - (this.container.getFire().getEnergyStored()/1000),8, (this.container.getFire().getEnergyStored()/ 1000));
		this.blit((x + 45),(y + 134) - (this.container.getWater().getEnergyStored()/1000), 179,132  - (this.container.getWater().getEnergyStored()/1000),8, (this.container.getWater().getEnergyStored()/ 1000));
		this.blit((x + 68),(y + 134) - (this.container.getAir().getEnergyStored()/1000), 179,132  - (this.container.getAir().getEnergyStored()/1000),8, (this.container.getAir().getEnergyStored()/ 1000));
		this.blit((x + 92),(y + 134) - (this.container.getEarth().getEnergyStored()/1000), 179,132  - (this.container.getEarth().getEnergyStored()/1000),8, (this.container.getEarth().getEnergyStored()/ 1000));
		this.blit((x + 120),(y + 134) - (this.container.getDark().getEnergyStored()/1000), 179,132  - (this.container.getDark().getEnergyStored()/1000),8, (this.container.getDark().getEnergyStored()/ 1000));
		this.blit((x + 148),(y + 134) - (this.container.getLight().getEnergyStored()/1000), 179,132  - (this.container.getLight().getEnergyStored()/1000),8, (this.container.getLight().getEnergyStored()/ 1000));

		this.font.drawString("Fire: '" + this.container.getFire().getEnergyStored() + "'", x+180.0f, y+190.0f, 4210752);
		this.font.drawString("Water: '" + this.container.getWater().getEnergyStored() + "'", x+180.0f, y+198.0f, 4210752);
		this.font.drawString("Air: '" + this.container.getAir().getEnergyStored() + "'", x+180.0f, y+206.0f, 4210752);
		this.font.drawString("Earth: '" + this.container.getEarth().getEnergyStored() + "'", x+180.0f, y+214.0f, 4210752);
		this.font.drawString("Light: '" + this.container.getLight().getEnergyStored() + "'", x+180.0f, y+222.0f, 4210752);
		this.font.drawString("Dark: '" + this.container.getDark().getEnergyStored() + "'", x+180.0f, y+230.0f, 4210752);

	}
}
