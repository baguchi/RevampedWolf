package baguchan.revampedwolf.network;

import baguchan.revampedwolf.client.ClientPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

	public static void handle(ClientWolfScreenOpenPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
					Minecraft minecraft = Minecraft.getInstance();
					LocalPlayer clientPlayer = minecraft.player;
					Entity entity = null;
					if (clientPlayer != null) {
						entity = clientPlayer.level.getEntity(packet.entityId);
					}
					if (entity instanceof Wolf) {
						Wolf wolf = (Wolf) entity;

						ClientPacketHandler.openWolfInventory(wolf, clientPlayer, packet.containerId);
					}
				}
		);
		ctx.get().setPacketHandled(true);

	}
}