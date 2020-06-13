package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.objects.blocks.MCMChest;
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
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
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


            }
            else if(pageNumber ==3){
                this.font.drawString("Welcome Mage, to the Mystical Arts", x + 5, y + 20, 4210752);

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //left side
                this.font.drawString("Chapter 2: Essence Harnessing", (x-180) + ((this.xSize/2) - (font.getStringWidth("Chapter 2: Essence Harnessing")/2)), y + 10, 4210752);
                this.font.drawString("Essence and the Environment around", x - 180, y + 20, 4210752);
                this.font.drawString("us can lead to an aboundance of", x - 180, y + 30, 4210752);
                this.font.drawString("energies, also known as Essence.", x - 180, y + 40,4210752);
                this.font.drawString("These Essence allow us to", x - 180, y - 1800,4210752);
                this.font.drawString("manipulate the universe around", x - 180, y + 60,4210752);
                this.font.drawString("us.", x - 180, y + 70,4210752);
                this.font.drawString("In Order to first collect that", x - 180, y + 80,4210752);
                this.font.drawString("energy...we'll need to build", x - 180, y + 90,4210752);
                this.font.drawString("some sort of Essence Collector.", x - 180, y + 100,4210752);
                this.font.drawString("", x - 180, y + 110,4210752);
                this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                this.blit(x -180, y + 140, 0,0,130,50);
                int gridX = x - 180;
                int gridY = y + 140;
                if(tick<120){

                    this.itemRenderer.renderItemIntoGUI(Items.LAVA_BUCKET.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.STONE.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.WATER_BUCKET.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.DIRT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.SAND.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.WATER_BUCKET.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.OAK_PLANKS.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.LAVA_BUCKET.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                    this.blit(gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }
                else{
                    this.itemRenderer.renderItemIntoGUI(ItemInitNew.ESSENCE_COLLECTOR.getDefaultInstance(), gridX + 105, gridY + 16);
                }

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //right side
                this.font.drawString("Chapter 3: Quarry", x + ((this.xSize/2) - (font.getStringWidth("Chapter 3: Quarry")/2)), y + 10, 4210752);
                this.font.drawString("Quarries are the gifts", x + 5, y + 30, 4210752);
                this.font.drawString("from Mother Gaia.  Simply", x + 5, y + 40, 4210752);
                this.font.drawString("place on the ground and ", x + 5, y + 50,4210752);
                this.font.drawString("watch her bless you with ", x + 5, y + 60,4210752);
                this.font.drawString("quick and efficent Mining", x + 5, y + 70,4210752);
                this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                this.blit(x +5 , y + 140, 0,0,130,50);

                gridX = x + 5;
                if(tick<160){

                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.DIAMOND_PICKAXE.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                    this.blit(gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }
                else{
                    this.itemRenderer.renderItemIntoGUI(ItemInitNew.QUARRY.getDefaultInstance(), gridX + 105, gridY + 16);
                }

            }else if(pageNumber ==4){
                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //left side

                this.font.drawString("Chapter 4: Matter Conversion Basics", (x - 180) + ((this.xSize/2) - (font.getStringWidth("Chapter 4: Matter Conversion Basics")/2)), y + 10, 4210752);
                this.font.drawString("Matter in all blocks can be", x - 180, y + 30, 4210752);
                this.font.drawString("converted into a basic state. This", x - 180, y + 40,4210752);
                this.font.drawString("is called MCM or Matter Conversion", x - 180, y +50,4210752);
                this.font.drawString("Magic.  In this Chapter you'll", x - 180, y + 60,4210752);
                this.font.drawString("learn the basics of MCM and how", x - 180, y + 70,4210752);
                this.font.drawString("to harness it.", x - 180, y + 80,4210752);
                this.font.drawString("", x - 180, y + 90,4210752);
                this.font.drawString("To convert a item to MCM you will", x - 180, y + 100,4210752);
                this.font.drawString("need a MCM Chest", x  - 180, y + 110,4210752);
                this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                this.blit(x -180, y + 140, 0,0,130,50);
                int gridX = x - 180;
                int gridY = y + 140;
                if(tick<120){

                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(ItemInitNew.ESSENCE_COLLECTOR.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                    this.blit(gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }else{
                    this.itemRenderer.renderItemIntoGUI(ItemInitNew.MCM_CHEST.getDefaultInstance(), gridX + 105, gridY + 16);
                }

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //right side
                this.font.drawString("Chapter 5: Fishing Nets", x + ((this.xSize/2) - (font.getStringWidth("Chapter 5: Fishing Nets")/2)), y + 10, 4210752);
                this.font.drawString("Fishing Nets are powered by the", x + 5, y + 30, 4210752);
                this.font.drawString("Seas and Oceanic Tides. Neither", x + 5, y + 40,4210752);
                this.font.drawString("river nor pound will be enough.", x + 5, y - 1800,4210752);
                this.font.drawString("Simply place and watch Neptune's", x + 5, y + 60,4210752);
                this.font.drawString("blessing, flow into your nets.", x + 5, y + 70,4210752);
                this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                this.blit(x +5 , y + 140, 0,0,130,50);

                gridX = x + 5;
                if(tick<160){

                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.FISHING_ROD.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderItemIntoGUI(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bindTexture(BLANK_CRAFTING);
                    this.blit(gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }else{
                    this.itemRenderer.renderItemIntoGUI(ItemInitNew.FISHING_NET.getDefaultInstance(), gridX + 105, gridY + 16);
                }

            }
            else if(pageNumber ==5){

            }
            else if(pageNumber ==6){

            }
            else if(pageNumber ==7){

            }
            else if(pageNumber ==8){

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
