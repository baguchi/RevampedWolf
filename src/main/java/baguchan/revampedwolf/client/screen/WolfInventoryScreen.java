package baguchan.revampedwolf.client.screen;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfInventoryScreen extends AbstractContainerScreen<WolfInventoryMenu> {
	private static final ResourceLocation HORSE_INVENTORY_LOCATION = new ResourceLocation(RevampedWolf.MODID, "textures/gui/container/wolf.png");
	private final Wolf wolf;
	private float xMouse;
	private float yMouse;

	public WolfInventoryScreen(WolfInventoryMenu wolfInventoryContainer, Inventory inventory, Wolf wolf) {
		super(wolfInventoryContainer, inventory, wolf.getDisplayName());
		this.wolf = wolf;
	}

	public boolean isPauseScreen() {
		return false;
	}


	protected void renderBg(GuiGraphics p_98821_, float p_98822_, int p_98823_, int p_98824_) {
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		p_98821_.blit(HORSE_INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

		InventoryScreen.renderEntityInInventoryFollowsMouse(p_98821_, i + 26, j + 18, i + 77, j + 69, 30, 0.0625F, this.xMouse, this.yMouse, this.wolf);
	}

	public void render(GuiGraphics p_98826_, int p_98827_, int p_98828_, float p_98829_) {
		this.renderBackground(p_98826_, p_98827_, p_98828_, p_98829_);
		this.xMouse = (float) p_98827_;
		this.yMouse = (float) p_98828_;
		super.render(p_98826_, p_98827_, p_98828_, p_98829_);
		this.renderTooltip(p_98826_, p_98827_, p_98828_);
	}
}
