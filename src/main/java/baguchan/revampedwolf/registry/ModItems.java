package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.item.RevampedWolfArmorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, RevampedWolf.MODID);

	public static final Supplier<Item> LEATHER_WOLF_ARMOR = ITEM_REGISTRY.register("leather_wolf_armor", () -> new RevampedWolfArmorItem(ArmorMaterials.LEATHER, AnimalArmorItem.BodyType.CANINE, true, new Item.Properties().stacksTo(1).durability(82)));
	public static final Supplier<Item> GOLD_WOLF_ARMOR = ITEM_REGISTRY.register("golden_wolf_armor", () -> new RevampedWolfArmorItem(ArmorMaterials.GOLD, AnimalArmorItem.BodyType.CANINE, false, new Item.Properties().stacksTo(1).durability(102)));
	public static final Supplier<Item> IRON_WOLF_ARMOR = ITEM_REGISTRY.register("iron_wolf_armor", () -> new RevampedWolfArmorItem(ArmorMaterials.IRON, AnimalArmorItem.BodyType.CANINE, false, new Item.Properties().stacksTo(1).durability(241)));
	public static final Supplier<Item> DIAMOND_WOLF_ARMOR = ITEM_REGISTRY.register("diamond_wolf_armor", () -> new RevampedWolfArmorItem(ArmorMaterials.DIAMOND, AnimalArmorItem.BodyType.CANINE, false, new Item.Properties().stacksTo(1).durability(529)));
	public static final Supplier<Item> NETHERITE_WOLF_ARMOR = ITEM_REGISTRY.register("netherite_wolf_armor", () -> new RevampedWolfArmorItem(ArmorMaterials.NETHERITE, AnimalArmorItem.BodyType.CANINE, false, (new Item.Properties()).stacksTo(1).durability(592).fireResistant()));
}