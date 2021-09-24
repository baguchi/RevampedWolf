package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.item.DyedWolfArmorItem;
import baguchan.revampedwolf.item.WolfArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = RevampedWolf.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final Item LEATHER_WOLF_ARMOR = new DyedWolfArmorItem(5, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_leather.png"), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC));
	public static final Item GOLD_WOLF_ARMOR = new WolfArmorItem(10, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_gold.png"), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC));
	public static final Item IRON_WOLF_ARMOR = new WolfArmorItem(16, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_iron.png"), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC));
	public static final Item DIAMOND_WOLF_ARMOR = new WolfArmorItem(20, 5, 0, new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/armor/wolf_armor_diamond.png"), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC));


	public static void register(IForgeRegistry<Item> registry, Item item, String id) {
		item.setRegistryName(new ResourceLocation(RevampedWolf.MODID, id));
		registry.register(item);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> registry) {
		register(registry.getRegistry(), LEATHER_WOLF_ARMOR, "leather_wolf_armor");
		register(registry.getRegistry(), GOLD_WOLF_ARMOR, "golden_wolf_armor");
		register(registry.getRegistry(), IRON_WOLF_ARMOR, "iron_wolf_armor");
		register(registry.getRegistry(), DIAMOND_WOLF_ARMOR, "diamond_wolf_armor");
	}
}