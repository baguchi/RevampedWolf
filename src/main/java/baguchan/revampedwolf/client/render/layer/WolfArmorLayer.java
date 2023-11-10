package baguchan.revampedwolf.client.render.layer;

import baguchan.revampedwolf.api.IHasArmor;
import baguchan.revampedwolf.client.ModModelLayers;
import baguchan.revampedwolf.item.DyedWolfArmorItem;
import baguchan.revampedwolf.item.WolfArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfArmorLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
	private final WolfModel<Wolf> model;

	public WolfArmorLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> p_174496_, EntityModelSet p_174497_) {
		super(p_174496_);
		this.model = new WolfModel<>(p_174497_.bakeLayer(ModModelLayers.WOLF_ARMOR));
	}

	public void render(PoseStack p_117032_, MultiBufferSource p_117033_, int p_117034_, Wolf p_117035_, float p_117036_, float p_117037_, float p_117038_, float p_117039_, float p_117040_, float p_117041_) {
		if (p_117035_ instanceof IHasArmor) {
			ItemStack itemstack = ((IHasArmor) p_117035_).getArmor();
			if (itemstack.getItem() instanceof WolfArmorItem) {
				WolfArmorItem wolfarmor = (WolfArmorItem) itemstack.getItem();
				this.getParentModel().copyPropertiesTo(this.model);
				this.model.prepareMobModel(p_117035_, p_117036_, p_117037_, p_117038_);
				this.model.setupAnim(p_117035_, p_117036_, p_117037_, p_117039_, p_117040_, p_117041_);
				float f;
				float f1;
				float f2;
				if (wolfarmor instanceof DyedWolfArmorItem) {
					int i = ((DyedWolfArmorItem) wolfarmor).getColor(itemstack);
					f = (float) (i >> 16 & 255) / 255.0F;
					f1 = (float) (i >> 8 & 255) / 255.0F;
					f2 = (float) (i & 255) / 255.0F;
				} else {
					f = 1.0F;
					f1 = 1.0F;
					f2 = 1.0F;
				}

				VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(p_117033_, RenderType.armorCutoutNoCull(wolfarmor.getTexture()), false, itemstack.hasFoil());
				this.model.renderToBuffer(p_117032_, vertexconsumer, p_117034_, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
			}
		}
	}
}