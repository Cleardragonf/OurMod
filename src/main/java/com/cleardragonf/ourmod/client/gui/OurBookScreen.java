package com.cleardragonf.ourmod.client.gui;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.objects.blocks.MCMChest;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
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
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OurBookScreen extends Screen {

    public OurBookScreen(ITextComponent titleIn) {
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
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        if(tick < 200){
            tick++;
        }else{
            tick = 1;
        }
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBackground(matrix);
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
        if(pageNumber == 1){
            this.blit(matrix,x, y, 0, 0, this.xSize, this.ySize);
            this.font.draw(matrix,"OurMod", x + ((this.xSize/2) - (font.width("OurMod")/2)), y + 10, 4210752);
            this.font.draw(matrix,"Welcome Mage, to the Mystical Arts", x + 5, y + 20, 4210752);
            this.font.draw(matrix,"of Asura! Within this book you'll ", x + 5, y + 30, 4210752);
            this.font.draw(matrix,"find many secrets and wonders ", x + 5, y + 40,4210752);
            this.font.draw(matrix,"about the Mystical Secs. Within", x + 5, y + 50,4210752);
            this.font.draw(matrix,"this book you'll find Different", x + 5, y + 60,4210752);
            this.font.draw(matrix,"tools that'll help you through", x + 5, y + 70,4210752);
            this.font.draw(matrix,"life. As you progress so too ", x + 5, y + 80,4210752);
            this.font.draw(matrix,"will this book. Horde this book", x + 5, y + 90,4210752);
            this.font.draw(matrix,"as in the wrong hands...you may", x + 5, y + 100,4210752);
            this.font.draw(matrix,"fall victim to your enemies.", x + 5, y + 110,4210752);
            nextButton.visible = true;
            prevButton.visible = false;
        }else{
            this.blit(matrix,x, y, 0, 0, this.xSize, this.ySize);
            this.blit(matrix,x-this.xSize, y, 0, 0, this.xSize, this.ySize);
            if(pageNumber ==2){
                //left side
                this.font.draw(matrix,"Chapter 1: Essence", (x -180 ) + ((this.xSize/2) - (font.width("Chapter 1: Essence")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Just like our universe has ", x - 180, y + 20, 4210752);
                this.font.draw(matrix,"essence.json so too does the mystic. ", x - 180, y + 30, 4210752);
                this.font.draw(matrix,"Each part of our world has a ", x - 180, y + 40,4210752);
                this.font.draw(matrix,"different essence.json to it.  The ", x - 180, y - 1800,4210752);
                this.font.draw(matrix,"Elements are: Fire, Wind, Water,", x - 180, y + 60,4210752);
                this.font.draw(matrix,"Earth, Light and Darkness.  Fire", x - 180, y + 70,4210752);
                this.font.draw(matrix,"dwells in the hottest of plac, ", x - 180, y + 80,4210752);
                this.font.draw(matrix,"wind above most others, Light ", x - 180, y + 90,4210752);
                this.font.draw(matrix,"can be found under the sky, and", x - 180, y + 100,4210752);
                this.font.draw(matrix,"darkness in the earth.", x - 180, y + 110,4210752);

                //right side
                this.font.draw(matrix,"List Of Essence", x + ((this.xSize/2) - (font.width("List Of Essence")/2)), y + 10, 4210752);
                //x,y,texturex,texturey,width,height @RyuShiTenshiKage

                this.itemRenderer.renderGuiItem(ItemInitNew.LIGHT_ESSENCE.getItem().getDefaultInstance(), x + 5, y +20);
                this.font.draw(matrix,"Found in places of light", x + 20, y + 20, 4210752);
                this.itemRenderer.renderGuiItem(ItemInitNew.DARK_ESSENCE.getItem().getDefaultInstance(), x + 5, y + 40);
                this.font.draw(matrix,"Found in places of Darkness", x + 20, y + 40,4210752);
                this.itemRenderer.renderGuiItem(ItemInitNew.FIRE_ESSENCE.getDefaultInstance(), x + 5, y + 60);
                this.font.draw(matrix,"Found in the Dimension of Fire", x + 20, y + 60,4210752);
                this.itemRenderer.renderGuiItem(ItemInitNew.WATER_ESSENCE.getDefaultInstance(), x + 5, y + 80);
                this.font.draw(matrix,"Found beneath the Tides", x + 20, y + 80,4210752);
                this.itemRenderer.renderGuiItem(ItemInitNew.EARTH_ESSENCE.getDefaultInstance(), x + 5, y + 100);
                this.font.draw(matrix,"Found inside the ground", x + 20, y + 100,4210752);
                this.itemRenderer.renderGuiItem(ItemInitNew.WIND_ESSENCE.getDefaultInstance(), x + 5, y + 120);
                this.font.draw(matrix,"Found high above", x + 20, y + 120,4210752);


            }
            else if(pageNumber ==3){
                this.font.draw(matrix,"Welcome Mage, to the Mystical Arts", x + 5, y + 20, 4210752);

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //left side
                this.font.draw(matrix,"Chapter 2: Essence Harnessing", (x-180) + ((this.xSize/2) - (font.width("Chapter 2: Essence Harnessing")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Essence and the Environment around", x - 180, y + 20, 4210752);
                this.font.draw(matrix,"us can lead to an aboundance of", x - 180, y + 30, 4210752);
                this.font.draw(matrix,"energies, also known as Essence.", x - 180, y + 40,4210752);
                this.font.draw(matrix,"These Essence allow us to", x - 180, y - 1800,4210752);
                this.font.draw(matrix,"manipulate the universe around", x - 180, y + 60,4210752);
                this.font.draw(matrix,"us.", x - 180, y + 70,4210752);
                this.font.draw(matrix,"In Order to first collect that", x - 180, y + 80,4210752);
                this.font.draw(matrix,"energy...we'll need to build", x - 180, y + 90,4210752);
                this.font.draw(matrix,"some sort of Essence Collector.", x - 180, y + 100,4210752);
                this.font.draw(matrix,"", x - 180, y + 110,4210752);
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrix,x -180, y + 140, 0,0,130,50);
                int gridX = x - 180;
                int gridY = y + 140;
                if(tick<120){

                    this.itemRenderer.renderGuiItem(Items.LAVA_BUCKET.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.WATER_BUCKET.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.DIRT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.SAND.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.WATER_BUCKET.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.OAK_PLANKS.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.LAVA_BUCKET.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bind(BLANK_CRAFTING);
                    this.blit(matrix,gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }
                else{
                    this.itemRenderer.renderGuiItem(ItemInitNew.ESSENCE_COLLECTOR.getDefaultInstance(), gridX + 105, gridY + 16);
                }

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //right side
                this.font.draw(matrix,"Chapter 3: Quarry", x + ((this.xSize/2) - (font.width("Chapter 3: Quarry")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Quarries are the gifts", x + 5, y + 30, 4210752);
                this.font.draw(matrix,"from Mother Gaia.  Simply", x + 5, y + 40, 4210752);
                this.font.draw(matrix,"place on the ground and ", x + 5, y + 50,4210752);
                this.font.draw(matrix,"watch her bless you with ", x + 5, y + 60,4210752);
                this.font.draw(matrix,"quick and efficent Mining", x + 5, y + 70,4210752);
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrix,x +5 , y + 140, 0,0,130,50);

                gridX = x + 5;
                if(tick<160){

                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.DIAMOND_PICKAXE.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bind(BLANK_CRAFTING);
                    this.blit(matrix,gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }
                else{
                    this.itemRenderer.renderGuiItem(ItemInitNew.QUARRY.getDefaultInstance(), gridX + 105, gridY + 16);
                }

            }
            else if(pageNumber ==4){
                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //left side

                this.font.draw(matrix,"Chapter 4: Matter Conversion Basics", (x - 180) + ((this.xSize/2) - (font.width("Chapter 4: Matter Conversion Basics")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Matter in all blocks can be", x - 180, y + 30, 4210752);
                this.font.draw(matrix,"converted into a basic state. This", x - 180, y + 40,4210752);
                this.font.draw(matrix,"is called MCM or Matter Conversion", x - 180, y +50,4210752);
                this.font.draw(matrix,"Magic.  In this Chapter you'll", x - 180, y + 60,4210752);
                this.font.draw(matrix,"learn the basics of MCM and how", x - 180, y + 70,4210752);
                this.font.draw(matrix,"to harness it.", x - 180, y + 80,4210752);
                this.font.draw(matrix,"", x - 180, y + 90,4210752);
                this.font.draw(matrix,"To convert a item to MCM you will", x - 180, y + 100,4210752);
                this.font.draw(matrix,"need a MCM Chest", x  - 180, y + 110,4210752);
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrix,x -180, y + 140, 0,0,130,50);
                int gridX = x - 180;
                int gridY = y + 140;
                if(tick<120){

                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderGuiItem(ItemInitNew.ESSENCE_COLLECTOR.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bind(BLANK_CRAFTING);
                    this.blit(matrix,gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }else{
                    this.itemRenderer.renderGuiItem(ItemInitNew.MCM_CHEST.getDefaultInstance(), gridX + 105, gridY + 16);
                }

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //right side
                this.font.draw(matrix,"Chapter 5: Fishing Nets", x + ((this.xSize/2) - (font.width("Chapter 5: Fishing Nets")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Fishing Nets are powered by the", x + 5, y + 30, 4210752);
                this.font.draw(matrix,"Seas and Oceanic Tides. Neither", x + 5, y + 40,4210752);
                this.font.draw(matrix,"river nor pound will be enough.", x + 5, y - 1800,4210752);
                this.font.draw(matrix,"Simply place and watch Neptune's", x + 5, y + 60,4210752);
                this.font.draw(matrix,"blessing, flow into your nets.", x + 5, y + 70,4210752);
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrix,x +5 , y + 140, 0,0,130,50);

                gridX = x + 5;
                if(tick<160){

                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.FISHING_ROD.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bind(BLANK_CRAFTING);
                    this.blit(matrix,gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }else{
                    this.itemRenderer.renderGuiItem(ItemInitNew.FISHING_NET.getDefaultInstance(), gridX + 105, gridY + 16);
                }

            }
            else if(pageNumber ==5){
                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //left side

                this.font.draw(matrix,"Chapter 6: Power Enscriber...", (x - 180) + ((this.xSize/2) - (font.width("Chapter 4: Matter Conversion Basics")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Tool used to link MCM to Energy", x - 180, y + 30, 4210752);
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrix,x -180, y + 140, 0,0,130,50);
                int gridX = x - 180;
                int gridY = y + 140;
                if(tick<120){

                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderGuiItem(ItemInitNew.FIRE_ESSENCE.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.STONE.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bind(BLANK_CRAFTING);
                    this.blit(matrix,gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }else{
                    this.itemRenderer.renderGuiItem(ItemInitNew.MCM_CHEST.getDefaultInstance(), gridX + 105, gridY + 16);
                }

                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //right side
                this.font.draw(matrix,"Chapter 5: Fishing Nets", x + ((this.xSize/2) - (font.width("Chapter 5: Fishing Nets")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Fishing Nets are powered by the", x + 5, y + 30, 4210752);
                this.font.draw(matrix,"Seas and Oceanic Tides. Neither", x + 5, y + 40,4210752);
                this.font.draw(matrix,"river nor pound will be enough.", x + 5, y - 1800,4210752);
                this.font.draw(matrix,"Simply place and watch Neptune's", x + 5, y + 60,4210752);
                this.font.draw(matrix,"blessing, flow into your nets.", x + 5, y + 70,4210752);
                this.minecraft.textureManager.bind(BLANK_CRAFTING);
                this.blit(matrix,x +5 , y + 140, 0,0,130,50);

                gridX = x + 5;
                if(tick<160){

                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 1);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.FISHING_ROD.getDefaultInstance(), gridX + 18, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 18);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 1, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 18, gridY + 35);
                    this.itemRenderer.renderGuiItem(Items.IRON_INGOT.getDefaultInstance(), gridX + 35, gridY + 35);
                    //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                    this.minecraft.textureManager.bind(BLANK_CRAFTING);
                    this.blit(matrix,gridX + 58,gridY + 7,0,52,34 - (34/tick),24);
                }else{
                    this.itemRenderer.renderGuiItem(ItemInitNew.FISHING_NET.getDefaultInstance(), gridX + 105, gridY + 16);
                }



            }
            else if(pageNumber ==6){
                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //left side
                this.font.draw(matrix,"Chapter 7: Wards and their Use", (x-180)+ ((this.xSize/2) - (font.width("Chapter 7: Wards and their Use")/2)), y + 10, 4210752);
                this.font.draw(matrix,"Wards are used for a multitude of ", x - 180, y + 20, 4210752);
                this.font.draw(matrix,"things from protecting ones land, ", x - 180, y + 30, 4210752);
                this.font.draw(matrix,"to keepingone fed.  There are ", x - 180, y + 40,4210752);
                this.font.draw(matrix,"dozens of wayswards can be ", x - 180, y + 50,4210752);
                this.font.draw(matrix,"utilized.  But first like many ", x - 180, y + 60,4210752);
                this.font.draw(matrix,"things magical, you'll need ", x - 180, y + 70,4210752);
                this.font.draw(matrix,"Essence to be collected in a ", x - 180, y + 80,4210752);
                this.font.draw(matrix,"Collector Then you'll probably ", x - 180, y + 90,4210752);
                this.font.draw(matrix,"need a Master Ward stone...but ", x - 180, y + 100,4210752);
                this.font.draw(matrix,"how would it know how far to ", x - 180, y + 110,4210752);
                this.font.draw(matrix,"place the wards? Probably some ", x -180, y + 120,4210752);
                this.font.draw(matrix,"sort of boundary ward stone or", x -180, y + 130,4210752);
                this.font.draw(matrix," such?",  x -180, y + 140,4210752);


                //x,y,texturex,texturey,width,height @RyuShiTenshiKage
                //right side
                this.font.draw(matrix,"I'd think that placing them in the", x+5, y+10,4210752);
                this.font.draw(matrix,"cardinal points would be a good", x + 5, y + 30, 4210752);
                this.font.draw(matrix,"arrangement.  Then, we'll need", x + 5, y + 40, 4210752);
                this.font.draw(matrix,"to make some decent tools to help", x + 5, y + 50,4210752);
                this.font.draw(matrix,"with connecting, the boundaries to", x + 5, y + 60,4210752);
                this.font.draw(matrix,"the Master Stone. And the power", x + 5, y + 70,4210752);
                this.font.draw(matrix,"I'd  recommend trying to create",  x + 5, y + 80,4210752);
                this.font.draw(matrix,"the geneal tools.",  x + 5, y + 90,4210752);

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
        super.render(matrix,mouseX, mouseY, partialTicks);
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
    public void tooltipText(MatrixStack matrixStack,String string, int x, int y) {
        switch (string) {
            case "Fire: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Fire: '" + "'"), x, y);
                break;
            case "Water: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Water: '"+ "'"), x, y);
                break;
            case "Air: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Air: '" +"'"), x, y);
                break;
            case "Earth: ":
                this.renderTooltip(matrixStack, new TranslationTextComponent("Earth: '" +"'"), x, y);
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
