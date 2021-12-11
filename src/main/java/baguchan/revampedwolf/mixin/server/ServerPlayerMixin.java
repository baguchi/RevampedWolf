package baguchan.revampedwolf.mixin.server;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.api.IOpenWolfContainer;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import baguchan.revampedwolf.network.ClientWolfScreenOpenPacket;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements IOpenWolfContainer {
	@Shadow
	public int containerCounter;

	@Shadow
	@Final
	private ContainerListener containerListener;

	public ServerPlayerMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
		super(p_36114_, p_36115_, p_36116_, p_36117_);
	}

	@Override
	public void openWolfInventory(Wolf p_9059_, Container p_9060_) {
		if (this.containerMenu != this.inventoryMenu) {
			this.closeContainer();
		}

		this.nextContainerCounter();
		ClientWolfScreenOpenPacket message = new ClientWolfScreenOpenPacket(this.containerCounter, p_9059_.getId());
		RevampedWolf.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> p_9059_), message);
		this.containerMenu = new WolfInventoryMenu(this.containerCounter, this.getInventory(), p_9060_, p_9059_);
		this.containerMenu.addSlotListener(this.containerListener);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(this, this.containerMenu));
	}

	@Shadow
	public void nextContainerCounter() {

	}
}
