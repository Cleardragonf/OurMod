package com.cleardragonf.ourmod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class SomeOtherClass {
    public static void openTheGuiForMe(ITextComponent title) {
        Minecraft.getInstance().displayGuiScreen(new OurBookScreen(title));
    }
    public static void openTheGuiForWards(ITextComponent title){
        Minecraft.getInstance().displayGuiScreen(new OurWardScreen(title));
    }
}
