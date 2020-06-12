package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OurBookScreen extends Screen {

    public OurBookScreen(ITextComponent titleIn) {
        super(titleIn);
    }
    private static final ResourceLocation GUI = new ResourceLocation(OurMod.MOD_ID, "textures/gui/ourbook.png");
    int xSize = 185;
    int ySize = 245;
    public int pageNumber = 1;

    @Override
    public void init() {
        super.init();
        this.buttons.clear();

    }
    protected Slot hoveredSlot;

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(GUI);
        int x = (this.width) / 2;
        int y = (this.height - this.ySize) / 2;
        Button nextButton = new Button(x + 100, y + 200, 50, 20, "Next Page", (button) -> {
            System.out.println("button testing");
            PageTurn("Next");
        });
        Button prevButton = new Button (x - 100, y + 200, 50, 20, "Prev Page", (button) -> {
            System.out.println("button testing");
            PageTurn("Prev");
        });
        this.addButton(nextButton);
        this.addButton(prevButton);
        nextButton.visible = false;
        nextButton.visible = false;
        if(pageNumber == 1){
            this.blit(x, y, 0, 0, this.xSize, this.ySize);
            this.font.drawString("OurMod", x + ((this.xSize/2) - (font.getStringWidth("OurMod")/2)), y + 10, 4210752);
            this.font.drawString("Welcome Mage, to the Mystical Arts", x + 5, y + 20, 4210752);
            this.font.drawString("of Asura! Within this book you'll ", x + 5, y + 30, 4210752);
            this.font.drawString("find many secrets and wonders ", x + 5, y + 40,4210752);
            this.font.drawString("about the Mystical Secs. Within", x + 5, y + 50,4210752);
            this.font.drawString("this book you'll find Different", x + 5, y + 60,4210752);
            this.font.drawString("tools that'll help you through", x + 5, y + 70,4210752);
            this.font.drawString("life. As you progress so too ", x + 5, y + 80,4210752);
            this.font.drawString("will this book. Horde this book", x + 5, y + 90,4210752);
            this.font.drawString("as in the wrong hands...you may", x + 5, y + 100,4210752);
            this.font.drawString("fall victim to your enemies.", x + 5, y + 110,4210752);
            nextButton.visible = true;
            prevButton.visible = false;
        }else{
            this.blit(x, y, 0, 0, this.xSize, this.ySize);
            this.blit(x-this.xSize, y, 0, 0, this.xSize, this.ySize);
            if(pageNumber ==2){
                //left side
                this.font.drawString("Chapter 1: Essence", (x -180 ) + ((this.xSize/2) - (font.getStringWidth("Chapter 1: Essence")/2)), y + 10, 4210752);
                this.font.drawString("Just like our universe has ", x - 180, y + 20, 4210752);
                this.font.drawString("essence so too does the mystic. ", x - 180, y + 30, 4210752);
                this.font.drawString("Each part of our world has a ", x - 180, y + 40,4210752);
                this.font.drawString("different essence to it.  The ", x - 180, y - 1800,4210752);
                this.font.drawString("Elements are: Fire, Wind, Water,", x - 180, y + 60,4210752);
                this.font.drawString("Earth, Light and Darkness.  Fire", x - 180, y + 70,4210752);
                this.font.drawString("dwells in the hottest of plac, ", x - 180, y + 80,4210752);
                this.font.drawString("wind above most others, Light ", x - 180, y + 90,4210752);
                this.font.drawString("can be found under the sky, and", x - 180, y + 100,4210752);
                this.font.drawString("darkness in the earth.", x - 180, y + 110,4210752);

                //right side
                this.font.drawString("List Of Essence", x + ((this.xSize/2) - (font.getStringWidth("List Of Essence")/2)), y + 10, 4210752);
                //x,y,texturex,texturey,width,height @RyuShiTenshiKage

                this.itemRenderer.renderItemIntoGUI(ItemInitNew.LIGHT_ESSENCE.getItem().getDefaultInstance(), x + 5, y +20);
                this.font.drawString("Found in places of light", x + 20, y + 20, 4210752);
                this.itemRenderer.renderItemIntoGUI(ItemInitNew.DARK_ESSENCE.getItem().getDefaultInstance(), x + 5, y + 40);
                this.font.drawString("Found in places of Darkness", x + 20, y + 40,4210752);
                this.itemRenderer.renderItemIntoGUI(ItemInitNew.FIRE_ESSENCE.getDefaultInstance(), x + 5, y + 60);
                this.font.drawString("Found in the Dimension of Fire", x + 20, y + 60,4210752);
                this.itemRenderer.renderItemIntoGUI(ItemInitNew.WATER_ESSENCE.getDefaultInstance(), x + 5, y + 80);
                this.font.drawString("Found beneath the Tides", x + 20, y + 80,4210752);
                this.itemRenderer.renderItemIntoGUI(ItemInitNew.EARTH_ESSENCE.getDefaultInstance(), x + 5, y + 100);
                this.font.drawString("Found inside the ground", x + 20, y + 100,4210752);
                this.itemRenderer.renderItemIntoGUI(ItemInitNew.WIND_ESSENCE.getDefaultInstance(), x + 5, y + 120);
                this.font.drawString("Found high above", x + 20, y + 120,4210752);


            }else if(pageNumber ==3){

            }else if(pageNumber ==4){

            }else if(pageNumber ==5){

            }else if(pageNumber ==6){

            }else if(pageNumber ==7){

            }else if(pageNumber ==8){

            }else if(pageNumber ==9){

            }else if(pageNumber ==10){

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
                System.out.println(pageNumber);
            }
        }else{
            if(pageNumber > 1){
                pageNumber--;
                System.out.println(pageNumber);
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
