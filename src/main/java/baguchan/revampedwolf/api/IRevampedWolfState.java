package baguchan.revampedwolf.api;

import net.minecraft.client.renderer.item.ItemStackRenderState;

public interface IRevampedWolfState {
    void setRevampedWolf$holdItem(ItemStackRenderState revampedWolf$holdItem);

    ItemStackRenderState getRevampedWolf$holdItem();
}
