package baguchan.revampedwolf.client;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.client.render.WolfArmorModel;
import baguchan.revampedwolf.client.render.layer.WolfHeldItemLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = RevampedWolf.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.WOLF_ARMOR, WolfArmorModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
		Minecraft.getInstance().getEntityRenderDispatcher().renderers.values().forEach(r -> {
			if (r instanceof WolfRenderer) {
				((WolfRenderer) r).addLayer(new WolfHeldItemLayer((WolfRenderer) r, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
			}
		});
	}
}
