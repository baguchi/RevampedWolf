package baguchan.revampedwolf.data;

import baguchan.revampedwolf.RevampedWolf;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

import static net.minecraft.world.item.equipment.EquipmentAssets.ROOT_ID;

public interface WolfEquipmentModels {
    ResourceKey<EquipmentAsset> LEATHER = createId("leather");
    ResourceKey<EquipmentAsset> IRON = createId("iron");
    ResourceKey<EquipmentAsset> GOLD = createId("gold");
    ResourceKey<EquipmentAsset> DIAMOND = createId("diamond");
    ResourceKey<EquipmentAsset> NETHERITE = createId("netherite");

    static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> p_371586_) {
        p_371586_.accept(LEATHER, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.onlyIfDyed(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "leather"), false))
                .addLayers(
                        EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.onlyIfDyed(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "leather_overlay"), true)
                ).build());
        p_371586_.accept(IRON, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, new EquipmentClientInfo.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "iron"))).build());
        p_371586_.accept(GOLD, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, new EquipmentClientInfo.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "gold"))).build());
        p_371586_.accept(DIAMOND, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, new EquipmentClientInfo.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "diamond"))).build());
        p_371586_.accept(NETHERITE, EquipmentClientInfo.builder().addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, new EquipmentClientInfo.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "netherite"))).build());
    }

    static ResourceKey<EquipmentAsset> createId(String p_386630_) {
        return ResourceKey.create(ROOT_ID, ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, p_386630_));
    }
}