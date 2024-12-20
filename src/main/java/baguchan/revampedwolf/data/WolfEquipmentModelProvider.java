package baguchan.revampedwolf.data;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WolfEquipmentModelProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public WolfEquipmentModelProvider(PackOutput p_371200_) {
        this.pathProvider = p_371200_.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/equipment");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput p_371294_) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        WolfEquipmentModels.bootstrap((p_371303_, p_371237_) -> {
            if (map.putIfAbsent(p_371303_, p_371237_) != null) {
                throw new IllegalStateException("Tried to register equipment model twice for id: " + p_371303_);
            }
        });
        return DataProvider.saveAll(p_371294_, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
    }

    @Override
    public String getName() {
        return "Equipment Model Definitions";
    }
}