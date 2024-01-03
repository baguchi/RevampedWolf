package baguchan.revampedwolf.network;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.client.ClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientWolfScreenOpenPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(RevampedWolf.MODID, "wolf_screen");
    private final int containerId;
    private final int entityId;

    public ClientWolfScreenOpenPacket(int containerIdIn, int entityIdIn) {
        this.containerId = containerIdIn;
        this.entityId = entityIdIn;
    }

    public ClientWolfScreenOpenPacket(FriendlyByteBuf buf) {
        this(buf.readUnsignedByte(), buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        buf.writeInt(this.entityId);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }


    public static void handle(ClientWolfScreenOpenPacket message, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    LocalPlayer clientPlayer = minecraft.player;
            Entity entity = minecraft.level.getEntity(message.entityId);

                    if (entity instanceof Wolf) {
                        Wolf wolf = (Wolf) entity;

                        ClientPacketHandler.openWolfInventory(wolf, clientPlayer, message.containerId);
                    }
                }
        );

    }
}