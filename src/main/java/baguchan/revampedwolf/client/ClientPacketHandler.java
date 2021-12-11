package baguchan.revampedwolf.client;

import baguchan.revampedwolf.api.IHasInventory;
import baguchan.revampedwolf.client.screen.WolfInventoryScreen;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.animal.Wolf;

public class ClientPacketHandler {
	public static void openWolfInventory(Wolf wolf, LocalPlayer clientPlayer, int containerId) {
		if (wolf instanceof IHasInventory) {
			WolfInventoryMenu wolfInventoryContainer = new WolfInventoryMenu(containerId, clientPlayer.getInventory(), ((IHasInventory) wolf).getContainer(), wolf);
			clientPlayer.containerMenu = wolfInventoryContainer;
			WolfInventoryScreen wolfInventoryScreen = new WolfInventoryScreen(wolfInventoryContainer, clientPlayer.getInventory(), wolf);
			Minecraft.getInstance().setScreen(wolfInventoryScreen);
		}
	}
}
