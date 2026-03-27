package baguchan.revampedwolf.client.render.layer;

import baguchan.revampedwolf.api.IRevampedWolfState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.animal.wolf.WolfModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class WolfHeldItemLayer extends RenderLayer<WolfRenderState, WolfModel> {
	public WolfHeldItemLayer(RenderLayerParent<WolfRenderState, WolfModel> p_116994_) {
		super(p_116994_);
	}

	@Override
    public void submit(PoseStack poseStack, SubmitNodeCollector multiBufferSource, int i, WolfRenderState wolfRenderState, float v, float v1) {
        if (this.getParentModel() instanceof HeadedModel headedModel && wolfRenderState instanceof IRevampedWolfState revampedWolfState) {

            if (!revampedWolfState.getRevampedWolf$holdItem().isEmpty()) {
				boolean flag1 = wolfRenderState.isBaby;
				poseStack.pushPose();
				headedModel.getHead().translateAndRotate(poseStack);
				float scale = flag1 ? 0.75F : 1.0F;
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
				poseStack.translate(0, (flag1 ? -0.3F : -0.45F), (flag1 ? -0.05F : -0.15F));
                revampedWolfState.getRevampedWolf$holdItem().submit(poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY, wolfRenderState.outlineColor);
				poseStack.popPose();
			}
		}
	}
}