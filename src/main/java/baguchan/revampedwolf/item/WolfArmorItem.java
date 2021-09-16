package baguchan.revampedwolf.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WolfArmorItem extends Item {
	private final int defense;
	private final float toughness;
	protected final float knockbackResistance;
	private final ResourceLocation texture;

	public WolfArmorItem(int defense, ResourceLocation texture, Item.Properties properties) {
		super(properties);
		this.defense = defense;
		this.toughness = 0;
		this.knockbackResistance = 0;
		this.texture = texture;
	}

	public WolfArmorItem(int defense, float toughness, float knockbackResistance, ResourceLocation texture, Item.Properties properties) {
		super(properties);
		this.defense = defense;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.texture = texture;
	}

	public int getDefense() {
		return this.defense;
	}

	public float getToughness() {
		return this.toughness;
	}

	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getTexture() {
		return texture;
	}
}
