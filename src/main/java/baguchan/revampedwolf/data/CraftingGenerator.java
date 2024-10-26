package baguchan.revampedwolf.data;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(HolderLookup.Provider lookupProvider, RecipeOutput output) {
        super(lookupProvider, output);
    }

    @Override
    protected void buildRecipes() {
        HolderLookup<Item> lookup = this.registries.lookupOrThrow(Registries.ITEM);


        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, ModItems.LEATHER_WOLF_ARMOR.get(), 1)
                .pattern(" LL")
                .pattern("L L")
                .define('L', Items.LEATHER)
                .unlockedBy("has_item", has(Items.LEATHER))
                .save(this.output);
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, ModItems.IRON_WOLF_ARMOR.get(), 1)
                .pattern(" II")
                .pattern("I I")
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, ModItems.GOLD_WOLF_ARMOR.get(), 1)
                .pattern(" II")
                .pattern("I I")
                .define('I', Items.GOLD_INGOT)
                .unlockedBy("has_item", has(Items.GOLD_INGOT))
                .save(this.output);
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, ModItems.DIAMOND_WOLF_ARMOR.get(), 1)
                .pattern(" II")
                .pattern("I I")
                .define('I', Items.DIAMOND)
                .unlockedBy("has_item", has(Items.DIAMOND))
                .save(this.output);

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ModItems.DIAMOND_WOLF_ARMOR.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.COMBAT, ModItems.NETHERITE_WOLF_ARMOR.get()).unlocks("has_item", has(Items.NETHERITE_INGOT)).save(this.output, prefix("netherite_wolf_armor"));
    }

    protected ResourceKey<Recipe<?>> prefix(String name) {
        return ResourceKey.create(Registries.RECIPE, RevampedWolf.prefix(name));
    }
}
