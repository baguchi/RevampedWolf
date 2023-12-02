package baguchan.revampedwolf.network;

import baguchan.revampedwolf.api.IWolfTypes;
import baguchan.revampedwolf.api.WolfTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.NetworkEvent;

public class WolfVariantPacket {
    private final int entityId;
    private final String variant;

    public WolfVariantPacket(int entityId, String variant) {
        this.entityId = entityId;
        this.variant = variant;
    }

    public static void write(WolfVariantPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.entityId);
        buf.writeUtf(packet.variant);
    }

    public static WolfVariantPacket read(FriendlyByteBuf buf) {
        return new WolfVariantPacket(buf.readInt(), buf.readUtf());
    }

    public void handle(NetworkEvent.Context context) {
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                Entity entity = Minecraft.getInstance().player.level().getEntity(this.entityId);
                if (entity instanceof IWolfTypes imoss) {
                    imoss.setVariant(WolfTypes.byType(variant));
                }
            });
        }
        context.setPacketHandled(true);
    }
}