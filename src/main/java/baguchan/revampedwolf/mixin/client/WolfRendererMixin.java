package baguchan.revampedwolf.mixin.client;

import baguchan.revampedwolf.api.IRevampedWolfState;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfRenderer.class)
public abstract class WolfRendererMixin extends AgeableMobRenderer<Wolf, WolfRenderState, WolfModel> {
    public WolfRendererMixin(EntityRendererProvider.Context p_360562_, WolfModel p_363747_, WolfModel p_361867_, float p_360815_) {
        super(p_360562_, p_363747_, p_361867_, p_360815_);
    }


    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/Wolf;Lnet/minecraft/client/renderer/entity/state/WolfRenderState;F)V", at = @At("TAIL"))
    public void extractRenderState(Wolf p_363274_, WolfRenderState p_363549_, float p_362105_, CallbackInfo ci) {
        if (p_363549_ instanceof IRevampedWolfState revampedWolfState) {
            this.itemModelResolver.updateForLiving(revampedWolfState.getRevampedWolf$holdItem(), p_363274_.getMainHandItem(), ItemDisplayContext.GROUND, false, p_363274_);
            revampedWolfState.setRevampedWolf$holdItem(revampedWolfState.getRevampedWolf$holdItem());
        }
    }
}
