package com.cleardragonf.ourmod.network;

        import com.cleardragonf.ourmod.OurMod;
        import com.cleardragonf.ourmod.network.server.SurvivalStatsPacket;

public class NetRegistries {
    public static void registerMSG(){
        int netID = -1;
        OurMod.CHANNEL.registerMessage(netID++, SurvivalStatsPacket.class, SurvivalStatsPacket::encode, SurvivalStatsPacket::decode, SurvivalStatsPacket::handle);
    }
}
