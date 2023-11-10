package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.item.DyedWolfArmorItem;
import baguchan.revampedwolf.item.WolfArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, RevampedWolf.MODID);

	public static final RegistryObject<Item> LEATHER_WOLF_ARMOR = ITEM_REGISTRY.register("leather_wolf_armor", () -> new DyedWolfArmorItem(5, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_leather.png"), (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> GOLD_WOLF_ARMOR = ITEM_REGISTRY.register("golden_wolf_armor", () -> new WolfArmorItem(10, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_gold.png"), (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> IRON_WOLF_ARMOR = ITEM_REGISTRY.register("iron_wolf_armor", () -> new WolfArmorItem(16, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_iron.png"), (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> DIAMOND_WOLF_ARMOR = ITEM_REGISTRY.register("diamond_wolf_armor", () -> new WolfArmorItem(20, 5, 0, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_diamond.png"), (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> NETHERITE_WOLF_ARMOR = ITEM_REGISTRY.register("netherite_wolf_armor", () -> new WolfArmorItem(20, 10, 4, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_netherite.png"), (new Item.Properties()).stacksTo(1).fireResistant()));
}