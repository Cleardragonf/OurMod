package com.cleardragonf.ourmod.network.server;

import com.cleardragonf.ourmod.entity.EntityStats;
import com.cleardragonf.ourmod.network.client.ClientStuffDower;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SurvivalStatsPacket {
    private double temperature;

    private double thirst;

    private int awakeTimer;

    private UUID uuid;

    public SurvivalStatsPacket(double temperature, double thirst, int awakeTimer, UUID uuid) {
        this.temperature = temperature;
        this.thirst = thirst;
        this.awakeTimer = awakeTimer;
        this.uuid = uuid;
    }

    public SurvivalStatsPacket(ServerPlayerEntity player) {
        this(
                EntityStats.getTemperature((LivingEntity)player),
                EntityStats.getThirst((LivingEntity)player),
                EntityStats.getAwakeTime((LivingEntity)player), player.getUniqueID());
    }

    public static void encode(SurvivalStatsPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeDouble(msg.temperature);
        packetBuffer.writeDouble(msg.thirst);
        packetBuffer.writeInt(msg.awakeTimer);
        packetBuffer.writeLong(msg.uuid.getMostSignificantBits());
        packetBuffer.writeLong(msg.uuid.getLeastSignificantBits());
    }

    public static SurvivalStatsPacket decode(PacketBuffer packetBuffer) {
        double temperature = packetBuffer.readDouble();
        double thirst = packetBuffer.readDouble();
        int awakeTimer = packetBuffer.readInt();
        return new SurvivalStatsPacket(temperature, thirst, awakeTimer, new UUID(packetBuffer

                .readLong(), packetBuffer.readLong()));
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->ClientStuffDower.update(this.temperature, this.thirst, this.awakeTimer, this.uuid)));
        context.setPacketHandled(true);
    }


}
