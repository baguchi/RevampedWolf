package baguchan.revampedwolf.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WolfArmorItem extends Item {
	private final int defense;
	private final float toughness;
	protected final float knockbackResistance;
	private final ResourceLocation texture;

	public WolfArmorItem(int defense, ResourceLocation texture, Properties properties) {
		super(properties);
		this.defense = defense;
		this.toughness = 0;
		this.knockbackResistance = 0;
		this.texture = texture;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment.category == EnchantmentCategory.ARMOR || enchantment.category == EnchantmentCategory.ARMOR_CHEST;
	}

	@Override
	public boolean isEnchantable(ItemStack p_41456_) {
		return true;
	}

	public int getEnchantmentValue() {
		return 5;
	}

	public WolfArmorItem(int defense, float toughness, float knockbackResistance, ResourceLocation texture, Properties properties) {
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
