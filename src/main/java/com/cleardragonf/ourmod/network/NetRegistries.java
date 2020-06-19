package com.cleardragonf.ourmod.network;

        import com.cleardragonf.ourmod.OurMod;
        import com.cleardragonf.ourmod.network.client.TakeBottleXP;
        import com.cleardragonf.ourmod.network.handler.XPMessageHandler;
        import com.cleardragonf.ourmod.network.server.SetBottleXP;
        import com.cleardragonf.ourmod.network.server.SurvivalStatsPacket;

public class NetRegistries {
    public static void registerMSG(){
        int netID = 0;
        OurMod.CHANNEL.registerMessage(netID++, SurvivalStatsPacket.class, SurvivalStatsPacket::encode, SurvivalStatsPacket::decode, SurvivalStatsPacket::handle);
        Networks.EXP_BOTTLING.registerMessage( netID++, SetBottleXP.class, SetBottleXP::encode, SetBottleXP::decode, XPMessageHandler::setBottlingExp );
        Networks.EXP_BOTTLING.registerMessage( netID++, TakeBottleXP.class, TakeBottleXP::encode, TakeBottleXP::decode, XPMessageHandler::takeBottledExp );
    }
}
