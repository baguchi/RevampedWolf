package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.RevampedWolf;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = RevampedWolf.MODID)
public class ModCreativeTabs {
	@SubscribeEvent
	public static void registerCreativeTab(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.COMBAT) {
			event.accept(ModItems.LEATHER_WOLF_ARMOR.get());
			event.accept(ModItems.IRON_WOLF_ARMOR.get());
			event.accept(ModItems.GOLD_WOLF_ARMOR.get());
			event.accept(ModItems.DIAMOND_WOLF_ARMOR.get());
			event.accept(ModItems.NETHERITE_WOLF_ARMOR.get());
		}
	}
}