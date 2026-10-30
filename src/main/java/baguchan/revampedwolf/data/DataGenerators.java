package baguchan.revampedwolf.data;

import baguchan.revampedwolf.RevampedWolf;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = RevampedWolf.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getWorldLookupProvider();
        generator.addProvider(true, new WolfEquipmentModelProvider(packOutput));
    }

    @SubscribeEvent
    public static void gatherRegistry(GatherDataRegistryEntriesEvent event) {
        event.add(RecipeProvider.asBootstrap(CraftingGenerator::new));
    }
}