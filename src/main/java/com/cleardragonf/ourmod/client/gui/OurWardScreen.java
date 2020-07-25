package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OurWardScreen extends Screen {

    public OurWardScreen(ITextComponent titleIn) {
        super(titleIn);
    }
    private static final ResourceLocation GUI = new ResourceLocation(OurMod.MOD_ID, "textures/gui/ourbook.png");
    private static final ResourceLocation BLANK_CRAFTING = new ResourceLocation(OurMod.MOD_ID, "textures/gui/blankcrafting.png");
    int xSize = 185;
    int ySize = 245;
    public int pageNumber = 1;

    @Override
    public void init() {
        super.init();
        this.buttons.clear();

    }
    protected Slot hoveredSlot;

    public int tick = 1;

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if(tick < 200){
            tick++;
        }else{
            tick = 1;
        }
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(GUI);
        int x = (this.width) / 2;
        int y = (this.height - this.ySize) / 2;
        Button nextButton = new Button(x + 100, y + 200, 50, 20, "Next Page", (button) -> {
            PageTurn("Next");
        });
        Button prevButton = new Button (x - 100, y + 200, 50, 20, "Prev Page", (button) -> {
            PageTurn("Prev");
        });
        this.addButton(nextButton);
        this.addButton(prevButton);
        nextButton.visible = false;
        nextButton.visible = false;
        if(pageNumber == 1){
            this.blit(x, y, 0, 0, this.xSize, this.ySize);
            this.font.drawString("Our Wards", x + ((this.xSize/2) - (font.getStringWidth("OurMod")/2)), y + 10, 4210752);
            this.font.drawString("Welcome Mage, to the Mystical Arts", x + 5, y + 20, 4210752);
            this.font.drawString("of Warding! Within this book you'll ", x + 5, y + 30, 4210752);
            this.font.drawString("find many secrets and wonders ", x + 5, y + 40,4210752);
            this.font.drawString("about the Mystical Art of wards.", x + 5, y + 50,4210752);
            this.font.drawString("This Book is solely dedicated", x + 5, y + 60,4210752);
            this.font.drawString("towards the betterment of your ", x + 5, y + 70,4210752);
            this.font.drawString("knowledge on wards.  Protect", x + 5, y + 80,4210752);
            this.font.drawString("this book with all your power", x + 5, y + 90,4210752);
            this.font.drawString("for there may be no way of", x + 5, y + 100,4210752);
            this.font.drawString("recovering it.", x + 5, y + 110,4210752);
            nextButton.visible = true;
            prevButton.visible = false;
        }
        else{
            this.blit(x, y, 0, 0, this.xSize, this.ySize);
            this.blit(x-this.xSize, y, 0, 0, this.xSize, this.ySize);
            if(pageNumber ==2) {

            }
            else if(pageNumber ==3) {

            }
            else if(pageNumber ==4) {

            }
            else if(pageNumber ==5) {

            }
            else if(pageNumber ==6) {

            }
            else if(pageNumber ==7) {

            }
            else if(pageNumber ==8) {

            }
            else if(pageNumber ==9){

            }
            else if(pageNumber ==10){

            }
            nextButton.visible = true;
            prevButton.visible = true;
        }
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }



    private void PageTurn(String direction) {
        if(direction == "Next"){
            if (pageNumber < 10){
                pageNumber++;
            }
        }else{
            if(pageNumber > 1){
                pageNumber--;
            }
        }
    }

    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        int relX = (this.width - this.xSize) /2;
        int relY = (this.height - this.ySize) /2;
        if(trueZone(relX + 148, relY + 34, 8,100,mouseX,mouseY)) {

        }
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
            case "Fire: ":
                this.renderTooltip("Fire: '" + "'", x, y);
                break;
            case "Water: ":
                this.renderTooltip("Water: '"+ "'", x, y);
                break;
            case "Air: ":
                this.renderTooltip("Air: '" +"'", x, y);
                break;
            case "Earth: ":
                this.renderTooltip("Earth: '" +"'", x, y);
                break;
            case "Dark: ":
                this.renderTooltip("Dark: '" + "'", x, y);
                break;
            case "Light: ":
                this.renderTooltip("Light: '" + "'", x, y);
                break;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
