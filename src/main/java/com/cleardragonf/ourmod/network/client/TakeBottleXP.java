package com.cleardragonf.ourmod.network.client;

import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.PacketBuffer;

public class TakeBottleXP {
    private int dragType;

    private int clickType;

    public TakeBottleXP(int dragType, int clickType) {
        this.dragType = dragType;
        this.clickType = clickType;
    }

    public TakeBottleXP(int dragType, ClickType clickType) {
        this(dragType, (clickType == ClickType.PICKUP) ? 0 : 1);
    }

    public int getDragType() {
        return this.dragType;
    }

    public ClickType getClickType() {
        return (this.clickType == 0) ? ClickType.PICKUP : ClickType.QUICK_MOVE;
    }

    public static void encode(TakeBottleXP message, PacketBuffer buffer) {
        buffer.writeInt(message.dragType);
        buffer.writeByte(message.clickType);
    }

    public static TakeBottleXP decode(PacketBuffer buffer) {
        return new TakeBottleXP(buffer.readInt(), buffer.readByte());
    }
}