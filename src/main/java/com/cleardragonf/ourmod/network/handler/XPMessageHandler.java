package com.cleardragonf.ourmod.network.handler;

import com.cleardragonf.ourmod.container.XPStorageContainer;
import com.cleardragonf.ourmod.network.client.TakeBottleXP;
import com.cleardragonf.ourmod.network.server.SetBottleXP;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class XPMessageHandler
{
    public static void setBottlingExp(SetBottleXP message, Supplier< NetworkEvent.Context > ctx )
    {
        NetworkEvent.Context _ctx = ctx.get();
        _ctx.enqueueWork( () -> {
            ServerPlayerEntity player = _ctx.getSender();
            if ( player != null && player.openContainer instanceof XPStorageContainer)
            {
                ( ( XPStorageContainer )player.openContainer ).setBottlingExp( message.getExpValue() );
            }
        } );
        _ctx.setPacketHandled( true );
    }

    public static void takeBottledExp(TakeBottleXP message, Supplier< NetworkEvent.Context > ctx )
    {
        NetworkEvent.Context _ctx = ctx.get();
        _ctx.enqueueWork( () -> {
            ServerPlayerEntity player = _ctx.getSender();
            if ( player != null && player.openContainer instanceof XPStorageContainer )
            {
                XPStorageContainer openContainer = ( XPStorageContainer )player.openContainer;
                openContainer.takeBottledExp( message.getDragType(), message.getClickType(), player );
            }
        } );
        _ctx.setPacketHandled( true );
    }
}