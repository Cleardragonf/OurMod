package com.cleardragonf.ourmod.network.server;

import net.minecraft.network.PacketBuffer;

public class SetBottleXP {
    private int expValue;

    public SetBottleXP(int expValue) {
        this.expValue = expValue;
    }

    public int getExpValue() {
        return this.expValue;
    }

    public static void encode(SetBottleXP message, PacketBuffer buffer) {
        buffer.writeInt(message.expValue);
    }

    public static SetBottleXP decode(PacketBuffer buffer) {
        return new SetBottleXP(buffer.readInt());
    }
}