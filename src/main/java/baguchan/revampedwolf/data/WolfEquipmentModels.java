package baguchan.revampedwolf.data;

import baguchan.revampedwolf.RevampedWolf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.EquipmentModels;

import java.util.function.BiConsumer;

public class WolfEquipmentModels {
    static void bootstrap(BiConsumer<ResourceLocation, EquipmentModel> p_371586_) {
        p_371586_.accept(EquipmentModels.LEATHER, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, EquipmentModel.Layer.onlyIfDyed(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_leather"), false))
                .addLayers(
                        EquipmentModel.LayerType.WOLF_BODY, EquipmentModel.Layer.onlyIfDyed(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_leather_overlay"), true)
                ).build());
        p_371586_.accept(EquipmentModels.IRON, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_iron"))).build());
        p_371586_.accept(EquipmentModels.GOLD, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_gold"))).build());
        p_371586_.accept(EquipmentModels.DIAMOND, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_diamond"))).build());
        p_371586_.accept(EquipmentModels.NETHERITE, EquipmentModel.builder().addLayers(EquipmentModel.LayerType.WOLF_BODY, new EquipmentModel.Layer(ResourceLocation.fromNamespaceAndPath(RevampedWolf.MODID, "wolf_armor_netherite"))).build());
    }
}