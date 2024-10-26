package baguchan.revampedwolf.data;

import baguchan.revampedwolf.RevampedWolf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentModel;

import java.util.function.BiConsumer;

public interface WolfEquipmentModels {
    ResourceLocation LEATHER = ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "leather");
    ResourceLocation IRON = ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "iron");
    ResourceLocation GOLD = ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "gold");
    ResourceLocation DIAMOND = ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "diamond");
    ResourceLocation NETHERITE = ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "netherite");

    static void bootstrap(BiConsumer<ResourceLocation, EquipmentModel> p_371586_) {
        p_371586_.accept(LEATHER, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, EquipmentModel.Layer.onlyIfDyed(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_leather"), false))
                .addLayers(
                        EquipmentModel.LayerType.WOLF_BODY, EquipmentModel.Layer.onlyIfDyed(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_leather_overlay"), true)
                ).build());
        p_371586_.accept(IRON, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_iron"))).build());
        p_371586_.accept(GOLD, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_gold"))).build());
        p_371586_.accept(DIAMOND, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_diamond"))).build());
        p_371586_.accept(NETHERITE, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_netherite"))).build());
    }
}