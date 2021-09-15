package baguchan.revampedwolf.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfHeldItemLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
	public WolfHeldItemLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> p_116994_) {
		super(p_116994_);
	}

	public void render(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, Wolf p_117010_, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
		if (this.getParentModel() instanceof HeadedModel) {
			boolean flag = p_117010_.isSleeping();
			boolean flag1 = p_117010_.isBaby();
			p_117007_.pushPose();
			if (flag1) {
				float f = 0.75F;
				p_117007_.scale(0.75F, 0.75F, 0.75F);
				p_117007_.translate(0.0D, 0.65D, 0.0D);
			}

			p_117007_.translate((double) (((HeadedModel) this.getParentModel()).getHead().x / 16.0F), (double) (((HeadedModel) this.getParentModel()).getHead().y / 16.0F), (double) (((HeadedModel) this.getParentModel()).getHead().z / 16.0F));
			float f1 = p_117010_.getHeadRollAngle(p_117013_);
			p_117007_.mulPose(Vector3f.ZP.rotation(f1));
			p_117007_.mulPose(Vector3f.YP.rotationDegrees(p_117015_));
			p_117007_.mulPose(Vector3f.XP.rotationDegrees(p_117016_));
			p_117007_.translate(0.059D, 0.15D, -0.42D);
			p_117007_.mulPose(Vector3f.XP.rotationDegrees(90.0F));

			ItemStack itemstack = p_117010_.getItemBySlot(EquipmentSlot.MAINHAND);
			Minecraft.getInstance().getItemInHandRenderer().renderItem(p_117010_, itemstack, ItemTransforms.TransformType.GROUND, false, p_117007_, p_117008_, p_117009_);
			p_117007_.popPose();
		}
	}
}