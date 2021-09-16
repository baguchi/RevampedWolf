package baguchan.revampedwolf.network;

import baguchan.revampedwolf.api.IHasInventory;
import baguchan.revampedwolf.client.screen.WolfInventoryScreen;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientWolfScreenOpenPacket {
	private final int containerId;
	private final int size;
	private final int entityId;

	public ClientWolfScreenOpenPacket(int containerIdIn, int size, int entityIdIn) {
		this.containerId = containerIdIn;
		this.size = size;
		this.entityId = entityIdIn;
	}

	public static ClientWolfScreenOpenPacket read(FriendlyByteBuf buf) {
		int containerId = buf.readUnsignedByte();
		int size = buf.readVarInt();
		int entityId = buf.readInt();
		return new ClientWolfScreenOpenPacket(containerId, size, entityId);
	}

	public static void write(ClientWolfScreenOpenPacket packet, FriendlyByteBuf buf) {
		buf.writeByte(packet.containerId);
		buf.writeVarInt(packet.size);
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
						if (wolf instanceof IHasInventory) {
							WolfInventoryMenu wolfInventoryContainer = new WolfInventoryMenu(packet.containerId, clientPlayer.getInventory(), ((IHasInventory) wolf).getContainer(), wolf);
							clientPlayer.containerMenu = wolfInventoryContainer;
							WolfInventoryScreen wolfInventoryScreen = new WolfInventoryScreen(wolfInventoryContainer, clientPlayer.getInventory(), wolf);
							minecraft.setScreen(wolfInventoryScreen);
						}
					}
				}
		);
		ctx.get().setPacketHandled(true);
	}
}