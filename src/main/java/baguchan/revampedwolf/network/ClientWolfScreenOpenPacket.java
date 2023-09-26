package baguchan.revampedwolf.network;

import baguchan.revampedwolf.client.ClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class ClientWolfScreenOpenPacket {
    private final int containerId;
    private final int entityId;

    public ClientWolfScreenOpenPacket(int containerIdIn, int entityIdIn) {
        this.containerId = containerIdIn;
        this.entityId = entityIdIn;
    }

    public static ClientWolfScreenOpenPacket read(FriendlyByteBuf buf) {
        int containerId = buf.readUnsignedByte();
        int entityId = buf.readInt();
        return new ClientWolfScreenOpenPacket(containerId, entityId);
    }

    public static void write(ClientWolfScreenOpenPacket packet, FriendlyByteBuf buf) {
        buf.writeByte(packet.containerId);
        buf.writeInt(packet.entityId);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    LocalPlayer clientPlayer = minecraft.player;
            Entity entity = minecraft.level.getEntity(entityId);

                    if (entity instanceof Wolf) {
                        Wolf wolf = (Wolf) entity;

                        ClientPacketHandler.openWolfInventory(wolf, clientPlayer, containerId);
                    }
                }
        );
        context.setPacketHandled(true);

    }
}