package baguchan.revampedwolf.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.recipes.RecipeProvider;

public class RegistryDataGenerator {
    public static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
            .add(RecipeProvider.asBootstrap(CraftingGenerator::new));

}
