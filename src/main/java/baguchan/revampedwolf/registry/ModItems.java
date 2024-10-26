package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.item.WolfArmorItem;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(RevampedWolf.MODID);

	public static final DeferredItem<Item> LEATHER_WOLF_ARMOR = ITEM_REGISTRY.registerItem("leather_wolf_armor", (properties) -> new WolfArmorItem(ArmorMaterials.LEATHER, AnimalArmorItem.BodyType.CANINE, properties.stacksTo(1).durability(82)));
	public static final DeferredItem<Item> GOLD_WOLF_ARMOR = ITEM_REGISTRY.registerItem("golden_wolf_armor", (properties) -> new WolfArmorItem(ArmorMaterials.GOLD, AnimalArmorItem.BodyType.CANINE, properties.stacksTo(1).durability(102)));
	public static final DeferredItem<Item> IRON_WOLF_ARMOR = ITEM_REGISTRY.registerItem("iron_wolf_armor", (properties) -> new WolfArmorItem(ArmorMaterials.IRON, AnimalArmorItem.BodyType.CANINE, properties.stacksTo(1).durability(241)));
	public static final DeferredItem<Item> DIAMOND_WOLF_ARMOR = ITEM_REGISTRY.registerItem("diamond_wolf_armor", (properties) -> new WolfArmorItem(ArmorMaterials.DIAMOND, AnimalArmorItem.BodyType.CANINE, properties.stacksTo(1).durability(529)));
	public static final DeferredItem<Item> NETHERITE_WOLF_ARMOR = ITEM_REGISTRY.registerItem("netherite_wolf_armor", (properties) -> new WolfArmorItem(ArmorMaterials.NETHERITE, AnimalArmorItem.BodyType.CANINE, properties.stacksTo(1).durability(592).fireResistant()));
}