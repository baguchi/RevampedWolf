package baguchan.revampedwolf.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfHeldItemLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
	private final ItemInHandRenderer itemInHandRenderer;

	public WolfHeldItemLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> p_116994_, ItemInHandRenderer p_234839_) {
		super(p_116994_);
		this.itemInHandRenderer = p_234839_;
	}

	public void render(PoseStack poseStack, MultiBufferSource p_117008_, int p_117009_, Wolf p_117010_, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
		if (this.getParentModel() instanceof HeadedModel headedModel) {
			boolean flag = p_117010_.isSleeping();
			boolean flag1 = p_117010_.isBaby();
			poseStack.pushPose();
			headedModel.getHead().translateAndRotate(poseStack);
			float scale = flag1 ? 0.75F : 1.0F;
            poseStack.translate(0, 0.1F * (flag1 ? 4.0F : 1.25F), (flag1 ? -0.25F : -0.5F));
			poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
			ItemStack itemStack = p_117010_.getItemBySlot(EquipmentSlot.MAINHAND);
			this.itemInHandRenderer.renderItem(p_117010_, itemStack, ItemDisplayContext.GROUND, false, poseStack, p_117008_, p_117009_);
			poseStack.popPose();
		}
	}
}