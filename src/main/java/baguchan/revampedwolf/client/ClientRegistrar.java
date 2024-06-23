package baguchan.revampedwolf.client;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.client.render.WolfArmorModel;
import baguchan.revampedwolf.client.render.layer.WolfHeldItemLayer;
import baguchan.revampedwolf.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = RevampedWolf.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {

	@SubscribeEvent
	public static void registerColor(RegisterColorHandlersEvent.Item event) {
		event.register(
				(p_329705_, p_329706_) -> p_329706_ > 0 ? -1 : DyedItemColor.getOrDefault(p_329705_, -6265536),
				ModItems.LEATHER_WOLF_ARMOR.get()
		);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.WOLF_ARMOR, WolfArmorModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
		if (event.getRenderer(EntityType.WOLF) instanceof WolfRenderer r) {
			((WolfRenderer) r).addLayer(new WolfHeldItemLayer((WolfRenderer) r, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
		}
	}
}
