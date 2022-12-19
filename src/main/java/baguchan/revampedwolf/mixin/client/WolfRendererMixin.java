package baguchan.revampedwolf.mixin.client;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.api.IWolfTypes;
import baguchan.revampedwolf.api.WolfTypes;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfRenderer.class)
public class WolfRendererMixin {
	@Shadow
	@Final
	private static ResourceLocation WOLF_LOCATION = new ResourceLocation("textures/entity/wolf/wolf.png");
	@Shadow
	@Final
	private static ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	@Shadow
	@Final
	private static ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_angry.png");


	@Inject(method = ("getTextureLocation"), at = @At("HEAD"), cancellable = true)
	public void getTextureLocation(Wolf p_116526_, CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
		IWolfTypes wolfTypes = ((IWolfTypes) p_116526_);
		if (wolfTypes.getVariant() == WolfTypes.WHITE) {
			if (p_116526_.isTame()) {
				callbackInfoReturnable.setReturnValue(WOLF_TAME_LOCATION);
			} else {
				callbackInfoReturnable.setReturnValue(p_116526_.isAngry() ? WOLF_ANGRY_LOCATION : WOLF_LOCATION);
			}
		} else {
			if (p_116526_.isTame()) {
				callbackInfoReturnable.setReturnValue(new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/wolf_" + wolfTypes.getVariant().type + "_tame.png"));
			} else {
				if (p_116526_.isAngry()) {
					callbackInfoReturnable.setReturnValue(new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/wolf_" + wolfTypes.getVariant().type + "_angry.png"));
				} else {
					callbackInfoReturnable.setReturnValue(new ResourceLocation(RevampedWolf.MODID, "textures/entity/wolf/wolf_" + wolfTypes.getVariant().type + ".png"));
				}
			}
		}
	}
}
