package baguchan.revampedwolf.client;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.client.render.WolfArmorModel;
import baguchan.revampedwolf.client.render.layer.WolfArmorLayer;
import baguchan.revampedwolf.client.render.layer.WolfHeldItemLayer;
import baguchan.revampedwolf.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.item.DyeableLeatherItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RevampedWolf.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {

	@SubscribeEvent
	public static void setup(RegisterColorHandlersEvent.Item event) {
		event.register((p_92708_, p_92709_) -> {
			return p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_);
		}, ModItems.LEATHER_WOLF_ARMOR.get());
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.WOLF_ARMOR, WolfArmorModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
		Minecraft.getInstance().getEntityRenderDispatcher().renderers.values().forEach(r -> {
			if (r instanceof WolfRenderer) {
				((WolfRenderer) r).addLayer(new WolfHeldItemLayer((WolfRenderer) r, Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()));
				((WolfRenderer) r).addLayer(new WolfArmorLayer((WolfRenderer) r, event.getEntityModels()));
			}
		});
	}
}
