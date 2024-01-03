package baguchan.revampedwolf.network;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.api.IWolfTypes;
import baguchan.revampedwolf.api.WolfTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class WolfVariantPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(RevampedWolf.MODID, "wolf_variant");
    private final int entityId;
    private final String variant;

    public WolfVariantPacket(int entityId, String variant) {
        this.entityId = entityId;
        this.variant = variant;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeUtf(this.variant);
    }

    public WolfVariantPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static void handle(WolfVariantPacket message, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            Entity entity = Minecraft.getInstance().player.level().getEntity(message.entityId);
                if (entity instanceof IWolfTypes imoss) {
                    imoss.setVariant(WolfTypes.byType(message.variant));
                }
            });
    }
}