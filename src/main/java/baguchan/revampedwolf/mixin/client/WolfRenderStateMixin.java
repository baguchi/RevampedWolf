package baguchan.revampedwolf.mixin.client;

import baguchan.revampedwolf.api.IRevampedWolfState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WolfRenderState.class)
public class WolfRenderStateMixin extends LivingEntityRenderState implements IRevampedWolfState {
    @Unique
    public ItemStackRenderState revampedWolf$holdItem = new ItemStackRenderState();

    public void setRevampedWolf$holdItem(ItemStackRenderState revampedWolf$holdItem) {
        this.revampedWolf$holdItem = revampedWolf$holdItem;
    }

    public ItemStackRenderState getRevampedWolf$holdItem() {
        return revampedWolf$holdItem;
    }
}
