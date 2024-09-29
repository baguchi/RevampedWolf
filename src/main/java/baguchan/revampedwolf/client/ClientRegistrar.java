package baguchan.revampedwolf.client;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.client.render.WolfArmorModel;
import baguchan.revampedwolf.client.render.layer.WolfArmorLayer;
import baguchan.revampedwolf.client.render.layer.WolfHeldItemLayer;
import baguchan.revampedwolf.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RevampedWolf.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static void renderItemColor() {
		Minecraft.getInstance().getItemColors().register((p_92708_, p_92709_) -> {
			return p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_);
		}, ModItems.LEATHER_WOLF_ARMOR.get());
	}

	public static void setup(FMLCommonSetupEvent event) {
		renderItemColor();
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.WOLF_ARMOR, WolfArmorModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
		Minecraft.getInstance().getEntityRenderDispatcher().renderers.values().forEach(r -> {
			if (r instanceof WolfRenderer) {
				((WolfRenderer) r).addLayer(new WolfHeldItemLayer((RenderLayerParent<Wolf, WolfModel<Wolf>>) r, event.getContext().getItemInHandRenderer()));
				((WolfRenderer) r).addLayer(new WolfArmorLayer((WolfRenderer) r, event.getEntityModels()));
			}
		});
	}
}
