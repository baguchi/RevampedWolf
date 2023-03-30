package baguchan.revampedwolf.mixin.server;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.api.IOpenWolfContainer;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import baguchan.revampedwolf.network.ClientWolfScreenOpenPacket;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements IOpenWolfContainer {
    @Shadow
    public int containerCounter;

    @Shadow
    @Final
    private ContainerListener containerListener;

    public ServerPlayerMixin(MinecraftServer p_215088_, ServerLevel p_215089_, GameProfile p_215090_, @Nullable ProfilePublicKey p_215091_) {
        super(p_215089_, p_215089_.getSharedSpawnPos(), p_215089_.getSharedSpawnAngle(), p_215090_, p_215091_);
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
