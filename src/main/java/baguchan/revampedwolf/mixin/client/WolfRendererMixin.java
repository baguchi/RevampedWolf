package baguchan.revampedwolf.mixin.client;

import baguchan.revampedwolf.client.render.layer.WolfHeldItemLayer;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfRenderer.class)
public abstract class WolfRendererMixin extends MobRenderer<Wolf, WolfModel<Wolf>> {
	private static final ResourceLocation WOLF_LOCATION = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

	public WolfRendererMixin(EntityRendererProvider.Context p_174452_) {
		super(p_174452_, new WolfModel<>(p_174452_.bakeLayer(ModelLayers.WOLF)), 0.5F);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	public void onConstructor(EntityRendererProvider.Context ctx, CallbackInfo info) {
		addLayer(new WolfHeldItemLayer(this));
	}
}
