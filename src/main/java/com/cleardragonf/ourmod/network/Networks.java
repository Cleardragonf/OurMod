package com.cleardragonf.ourmod.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static com.cleardragonf.ourmod.OurMod.MOD_ID;

public class Networks
{
    private Networks()
    {
    }

    private static final ResourceLocation EXP_BOTTLING_NAME = new ResourceLocation( MOD_ID, "exp_bottling" );
    private static final String EXP_BOTTLING_PROTOCOL_VERSION = "1";

    public static SimpleChannel EXP_BOTTLING;

    public static void init()
    {
        EXP_BOTTLING = NetworkRegistry.newSimpleChannel( EXP_BOTTLING_NAME,
                () -> EXP_BOTTLING_PROTOCOL_VERSION,
                EXP_BOTTLING_PROTOCOL_VERSION::equals,
                EXP_BOTTLING_PROTOCOL_VERSION::equals );
    }
}
