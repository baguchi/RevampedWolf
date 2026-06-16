package baguchan.revampedwolf.client;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.client.render.layer.WolfHeldItemLayer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = RevampedWolf.MODID, value = Dist.CLIENT)
public class ClientRegistrar {

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
		if (event.getRenderer(EntityTypes.WOLF) instanceof WolfRenderer r) {
			r.addLayer(new WolfHeldItemLayer(r));
		}
	}
}
