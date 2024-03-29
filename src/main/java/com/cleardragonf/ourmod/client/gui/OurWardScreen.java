package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class OurWardScreen extends Screen {

    public OurWardScreen(ITextComponent titleIn) {
        super(titleIn);
    }
    private static final ResourceLocation GUI = new ResourceLocation(OurMod.MOD_ID, "textures/gui/asurabook.png");
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
    public void render(MatrixStack matrixStack,int mouseX, int mouseY, float partialTicks) {
        if(tick < 200){
            tick++;
        }else{
            tick = 1;
        }
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(GUI);
        int x = (this.width) / 2;
        int y = (this.height - this.ySize) / 2;
        Button nextButton = new Button(x + 100, y + 200, 50, 20, new TranslationTextComponent("Next Page"), (button) -> {
            PageTurn("Next");
        });
        Button prevButton = new Button (x - 100, y + 200, 50, 20, new TranslationTextComponent("Prev Page"), (button) -> {
            PageTurn("Prev");
        });
        this.addButton(nextButton);
        this.addButton(prevButton);
        nextButton.visible = false;
        nextButton.visible = false;
        switch (pageNumber){
            case 1:
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.font.draw(matrixStack,"Our Wards", x + ((this.xSize/2) - (font.width("OurMod")/2)), y + 10, 4210752);
                this.font.draw(matrixStack,"Welcome Mage, to the Mystical Arts", x + 5, y + 20, 4210752);
                this.font.draw(matrixStack,"of Warding! Within this book you'll ", x + 5, y + 30, 4210752);
                this.font.draw(matrixStack,"find many secrets and wonders ", x + 5, y + 40,4210752);
                this.font.draw(matrixStack,"about the Mystical Art of wards.", x + 5, y + 50,4210752);
                this.font.draw(matrixStack,"This Book is solely dedicated", x + 5, y + 60,4210752);
                this.font.draw(matrixStack,"towards the betterment of your ", x + 5, y + 70,4210752);
                this.font.draw(matrixStack,"knowledge on wards.  Protect", x + 5, y + 80,4210752);
                this.font.draw(matrixStack,"this book with all your power", x + 5, y + 90,4210752);
                this.font.draw(matrixStack,"for there may be no way of", x + 5, y + 100,4210752);
                this.font.draw(matrixStack,"recovering it.", x + 5, y + 110,4210752);
                nextButton.visible = true;
                prevButton.visible = false;
                break;
            case 2:
                nextButton.visible = true;
                prevButton.visible = true;
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                leftPageText(matrixStack,"String","String","Wards are a basis feature that", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String","are apart of Asura Magic.  There", x, y,2, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "are wards for different features,", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "effects etc.", x, y, 4, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "As you advance further this book", x, y, 5, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "will start to fill up with more", x, y, 6, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "information.  Don't loose it.", x, y, 7, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "", x, y, 8, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String","Stacking Wards will increase", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String","their effects and will see an ", x, y,10, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "increase in the cost. However you", x, y, 11, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "should also be careful that you ", x, y, 12, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "do not make the ward too weak.", x, y, 13, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "As for some wards that won't do", x, y, 14, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "specifically what it's intended", x, y, 15, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "to do.", x, y, 16, null, null, null, null, null, null, null, null, null, null);

                rightPageText(matrixStack,"String","String", "Chapter 1: Alteration Wards", x, y, 0, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "Alteration Wards, are amongst the", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "only worlds that allow you to ", x, y, 2, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "manipulate the world around you.", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "From Gravity, to light; from ", x, y, 4, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "climate to accessability.", x, y, 5, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "These wards will manipulate any-", x, y, 6, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "thing around you to your hearts", x, y, 7, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "content.", x, y, 8, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "To get started on these wards", x, y, 10, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "go ahead and craft one after the ", x, y, 11, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "other, soon you'll gain a ", x, y, 12, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "collection of wards you ca", x, y, 13, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "create.", x, y, 14, null, null, null, null, null, null, null, null, null, null);
                break;
            case 3:
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                leftPageText(matrixStack,"String","String", "Temperature Ward", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"GUI","String", "null", x, y, 2, null, ItemInitNew.WATER_ESSENCE.getDefaultInstance(), null, null, Items.PAPER.getDefaultInstance(), null, null, ItemInitNew.FIRE_ESSENCE.getDefaultInstance(), null, ItemInitNew.WARD_STONES_TEMPERATURE.get().getDefaultInstance());
                leftPageText(matrixStack,"String","GUI", "The Temperature ward is used to ", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "regulate the temperature within", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "the ward. This will prevent the ", x, y, 10, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "players within and creatures from", x, y, 11, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "experience anything but the best.", x, y, 12, null, null, null, null, null, null, null, null, null, null);
                nextButton.visible = true;
                prevButton.visible = true;
                break;
            case 4:
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                nextButton.visible = true;
                prevButton.visible = true;
                break;
            case 5:
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                nextButton.visible = true;
                prevButton.visible = true;
                break;
            case 6:
                nextButton.visible = true;
                prevButton.visible = true;
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                leftPageText(matrixStack,"String","String", "Chapter 2: Restoration Wards", x, y, 0, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "The Restoration Ward type is a ", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "magic that allows for using magic", x, y, 2, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "to give boosts to entities within", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "your wards. Like flying, healing", x, y, 4, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "endless hunger, never ending", x, y, 5, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "darkness etc.  These wards you", x, y, 6, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "will find will make you", x, y, 7, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "practically untouchable within", x, y, 8, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "your home.  But becareful, your", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "enemies could easily also", x, y, 10, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "benifit if you are not wary.", x, y, 11, null, null, null, null, null, null, null, null, null, null);

                rightPageText(matrixStack,"String","String", "Anti-Hunger Wards", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"GUI","String", "null", x, y, 2, null, ItemInitNew.WATER_ESSENCE.getDefaultInstance(), null, null, Items.PAPER.getDefaultInstance(), null, null, ItemInitNew.EARTH_ESSENCE.getDefaultInstance(), null, ItemInitNew.WARD_STONES_ANTI_HUNGER.get().getDefaultInstance());
                rightPageText(matrixStack,"String","GUI", "The Anti-Hunger Ward is the basis", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "of the Restoration Wards.  With ", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "it and several others you are", x, y, 10, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "able to control when an entity ", x, y, 11, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "within the wards is hungry.", x, y, 12, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "", x, y, 13, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "The Purpose of this ward is to", x, y, 14, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "stop hunger from affecting the ", x, y, 15, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "players within.", x, y, 16, null, null, null, null, null, null, null, null, null, null);

                break;
            case 7:
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                nextButton.visible = true;
                prevButton.visible = true;
                break;
            case 8:
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                nextButton.visible = true;
                prevButton.visible = true;
                break;
            case 9:
                nextButton.visible = false;
                prevButton.visible = true;
                this.blit(matrixStack,x, y, 0, 0, this.xSize, this.ySize);
                this.blit(matrixStack,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
                leftPageText(matrixStack,"String","String", "Chapter 3: Arcane Wards", x, y, 0, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "Arcane Wards are the things right", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "out of fantasy, when you think ", x, y, 2, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "about the limitations or lack ", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "there of, Arcane is there with a", x, y, 4, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "answer.  The Arcane is debatably", x, y, 5, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "the largest of the Wardic Schools", x, y, 6, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "with Features from everything to ", x, y, 7, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "Defending your Home, to Cursing ", x, y, 8, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "Individuals that attempt to harm", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "you.  But beware...some things", x, y, 10, null, null, null, null, null, null, null, null, null, null);
                leftPageText(matrixStack,"String","String", "were not meant to be messed with.", x, y, 11, null, null, null, null, null, null, null, null, null, null);

                rightPageText(matrixStack,"String","String", "Anti-Hostile Ward", x, y, 1, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"GUI","String", "null", x, y, 2, Items.BONE.getDefaultInstance(), null, Items.BONE.getDefaultInstance(), null, Items.PAPER.getDefaultInstance(), null, Items.BONE.getDefaultInstance(), null, Items.BONE.getDefaultInstance(), ItemInitNew.WARD_STONES_ANTI_HOSTILE.get().getDefaultInstance());
                rightPageText(matrixStack,"String","GUI", "The Anti-Hostile Ward is a Arcane", x, y, 3, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "Ward that specializes in the ", x, y, 9, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "removal of unwanted MOBS within ", x, y, 10, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "the wards, IE, Creeper, Skeleton,", x, y, 11, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "Slime. Pretty much anything ", x, y, 12, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "outside of Human Life wanting to", x, y, 13, null, null, null, null, null, null, null, null, null, null);
                rightPageText(matrixStack,"String","String", "kill you.", x, y, 14, null, null, null, null, null, null, null, null, null, null);
                break;
        }

        super.render(matrixStack,mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    private void rightPageText(MatrixStack matrixStack,String type, String prevType, String string, int x, int y, int line, ItemStack item1,ItemStack item2,ItemStack item3,ItemStack item4,ItemStack item5,ItemStack item6,ItemStack item7,ItemStack item8,ItemStack item9,ItemStack endItem) {
        int var = 0;
        if(type == "String"){
            if(prevType == "String"){
                this.font.draw(matrixStack,string, x +5, y + (line * 10 + 10), 4210752);
            }
            else if(prevType == "GUI"){
                this.font.draw(matrixStack,string, x +5, y + (line * 10 + 60), 4210752);
            }
            else if(prevType == "Image"){
                this.font.draw(matrixStack,string, x +5, y + (line *10 + 100), 4210752);
            }
        }
        else if(type == "GUI"){
            if(item1 == null)
                item1 = Items.AIR.getDefaultInstance();
            if(item2 == null)
                item2 = Items.AIR.getDefaultInstance();
            if(item3 == null)
                item3 = Items.AIR.getDefaultInstance();
            if(item4 == null)
                item4 = Items.AIR.getDefaultInstance();
            if(item5 == null)
                item5 = Items.AIR.getDefaultInstance();
            if(item6 == null)
                item6 = Items.AIR.getDefaultInstance();
            if(item7 == null)
                item7 = Items.AIR.getDefaultInstance();
            if(item8 == null)
                item8 = Items.AIR.getDefaultInstance();
            if(item9 == null)
                item9 = Items.AIR.getDefaultInstance();
            if(endItem == null)
                endItem = Items.AIR.getDefaultInstance();
            guiRecipeRender(matrixStack,x, y, item1.getItem().getDefaultInstance() , item2.getItem().getDefaultInstance(), item3.getItem().getDefaultInstance(), item4.getItem().getDefaultInstance(), item5.getItem().getDefaultInstance(), item6.getItem().getDefaultInstance(), item7.getItem().getDefaultInstance(), item8.getItem().getDefaultInstance(), item9.getItem().getDefaultInstance(), endItem.getItem().getDefaultInstance(), "Right", line, prevType);
        }

    }

    private void leftPageText(MatrixStack matrixStack,String type, String prevType, String string, int x, int y, int line, ItemStack item1,ItemStack item2,ItemStack item3,ItemStack item4,ItemStack item5,ItemStack item6,ItemStack item7,ItemStack item8,ItemStack item9,ItemStack endItem) {
        int var = 0;
        if(type == "String"){
            if(prevType == "String"){
                this.font.draw(matrixStack,string, x - 180, y + (line * 10 + 10), 4210752);
            }
            else if(prevType == "GUI"){
                this.font.draw(matrixStack,string, x - 180, y + (line * 10 + 60), 4210752);
            }
            else if(prevType == "Image"){
                this.font.draw(matrixStack,string, x -180, y + (line *10 + 100), 4210752);
            }
        }
        else if(type == "GUI"){
            if(item1 == null)
                item1 = Items.AIR.getDefaultInstance();
            if(item2 == null)
                item2 = Items.AIR.getDefaultInstance();
            if(item3 == null)
                item3 = Items.AIR.getDefaultInstance();
            if(item4 == null)
                item4 = Items.AIR.getDefaultInstance();
            if(item5 == null)
                item5 = Items.AIR.getDefaultInstance();
            if(item6 == null)
                item6 = Items.AIR.getDefaultInstance();
            if(item7 == null)
                item7 = Items.AIR.getDefaultInstance();
            if(item8 == null)
                item8 = Items.AIR.getDefaultInstance();
            if(item9 == null)
                item9 = Items.AIR.getDefaultInstance();
            if(endItem == null)
                endItem = Items.AIR.getDefaultInstance();
                //this.font.drawString(string, x -180, y + (line * 10 + 10), )
                guiRecipeRender(matrixStack,x, y, item1.getItem().getDefaultInstance() , item2.getItem().getDefaultInstance(), item3.getItem().getDefaultInstance(), item4.getItem().getDefaultInstance(), item5.getItem().getDefaultInstance(), item6.getItem().getDefaultInstance(), item7.getItem().getDefaultInstance(), item8.getItem().getDefaultInstance(), item9.getItem().getDefaultInstance(), endItem.getItem().getDefaultInstance(), "Left", line, prevType);
        }

    }

    //Portion that determins the previous Item so that the GUI can display with enough space around it
    public void guiRecipeRender(MatrixStack matrixStack,int x, int y, ItemStack item1,  ItemStack item2,  ItemStack item3,  ItemStack item4,  ItemStack item5,  ItemStack item6,  ItemStack item7,
                                ItemStack item8,  ItemStack item9,  ItemStack endItem, String side, int line, String prevType){
            if(prevType == "String"){
                this.minecraft.textureManager.bind(BLANK_CRAFTING);

                int gridX = x - 180;
                int gridY = y + (line * 10 + 10);
                if(side =="Right"){
                    gridX = x + 5;
                }
                this.blit(matrixStack,gridX, gridY, 0,0,130,50);
                guiDisplayManager(matrixStack,item1, item2, item3, item4, item5, item6, item7, item8, item9, endItem, gridX, gridY);


            }
            else if(prevType == "GUI"){
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrixStack,x -180, y + (line * 10 + 65), 0,0,130,50);
                int gridX = x - 180;
                if(side == "Right"){
                    gridX = x + 5;
                }
                int gridY = y + (line * 10 + 65);
                guiDisplayManager(matrixStack,item1, item2, item3, item4, item5, item6, item7, item8, item9, endItem, gridX, gridY);
            }
            else if(prevType == "Image"){

            }
    }

    //portion of Tick Display that displays the recipe then the End Item
    public void guiDisplayManager(MatrixStack matrixStack,ItemStack item1,ItemStack item2,ItemStack item3,ItemStack item4,ItemStack item5,
                                  ItemStack item6,ItemStack item7,ItemStack item8,ItemStack item9,ItemStack endItem, int gridX, int gridY){
        if(tick<120){
            this.itemRenderer.renderGuiItem(item1.getItem().getDefaultInstance(), gridX + 1, gridY + 1);
            this.itemRenderer.renderGuiItem(item2.getItem().getDefaultInstance(), gridX + 18, gridY + 1);
            this.itemRenderer.renderGuiItem(item3.getItem().getDefaultInstance(), gridX + 35, gridY + 1);
            this.itemRenderer.renderGuiItem(item4.getItem().getDefaultInstance(), gridX + 1, gridY + 18);
            this.itemRenderer.renderGuiItem(item5.getItem().getDefaultInstance(), gridX + 18, gridY + 18);
            this.itemRenderer.renderGuiItem(item6.getItem().getDefaultInstance(), gridX + 35, gridY + 18);
            this.itemRenderer.renderGuiItem(item7.getItem().getDefaultInstance(), gridX + 1, gridY + 35);
            this.itemRenderer.renderGuiItem(item8.getItem().getDefaultInstance(), gridX + 18, gridY + 35);
            this.itemRenderer.renderGuiItem(item9.getItem().getDefaultInstance(), gridX + 35, gridY + 35);
            this.minecraft.textureManager.bind(BLANK_CRAFTING);
            this.blit(matrixStack,gridX + 58, gridY + 7,0,52,34-(34/tick), 24);
        }else{
            this.itemRenderer.renderGuiItem(endItem.getItem().getDefaultInstance(), gridX + 105, gridY + 16);
        }
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
    public void tooltipText(String string, int x, int y, MatrixStack matrixStack) {
        switch (string) {
            case "Fire: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Fire: '" + "'"), x, y);
                break;
            case "Water: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Water: '" + "'"), x, y);
                break;
            case "Air: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Air: '" + "'"), x, y);
                break;
            case "Earth: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Earth: '" + "'"), x, y);
                break;
            case "Dark: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Dark: '" + "'"), x, y);
                break;
            case "Light: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Light: '" + "'"), x, y);
                break;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
