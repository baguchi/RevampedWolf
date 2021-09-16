package baguchan.revampedwolf.client.screen;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Inventory;

public class WolfInventoryScreen extends AbstractContainerScreen<WolfInventoryMenu> {
	private static final ResourceLocation HORSE_INVENTORY_LOCATION = new ResourceLocation(RevampedWolf.MODID, "textures/gui/container/wolf.png");
	private final Wolf wolf;
	private float xMouse;
	private float yMouse;

	public WolfInventoryScreen(WolfInventoryMenu wolfInventoryContainer, Inventory inventory, Wolf wolf) {
		super(wolfInventoryContainer, inventory, wolf.getDisplayName());
		this.wolf = wolf;
		this.passEvents = false;
	}


	protected void renderBg(PoseStack p_98821_, float p_98822_, int p_98823_, int p_98824_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, HORSE_INVENTORY_LOCATION);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(p_98821_, i, j, 0, 0, this.imageWidth, this.imageHeight);

		InventoryScreen.renderEntityInInventory(i + 51, j + 60, 17, (float) (i + 51) - this.xMouse, (float) (j + 75 - 50) - this.yMouse, this.wolf);
	}

	public void render(PoseStack p_98826_, int p_98827_, int p_98828_, float p_98829_) {
		this.renderBackground(p_98826_);
		this.xMouse = (float) p_98827_;
		this.yMouse = (float) p_98828_;
		super.render(p_98826_, p_98827_, p_98828_, p_98829_);
		this.renderTooltip(p_98826_, p_98827_, p_98828_);
	}
}
