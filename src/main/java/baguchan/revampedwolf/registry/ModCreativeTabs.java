package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.RevampedWolf;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = RevampedWolf.MODID)
public class ModCreativeTabs {
	@SubscribeEvent
	public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(ModItems.LEATHER_WOLF_ARMOR.get());
			event.accept(ModItems.IRON_WOLF_ARMOR.get());
			event.accept(ModItems.GOLD_WOLF_ARMOR.get());
			event.accept(ModItems.DIAMOND_WOLF_ARMOR.get());
			event.accept(ModItems.NETHERITE_WOLF_ARMOR.get());
		}
	}
}