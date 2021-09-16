package baguchan.revampedwolf.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;

public class DyedWolfArmorItem extends WolfArmorItem implements DyeableLeatherItem {
	public DyedWolfArmorItem(int defense, ResourceLocation texture, Properties properties) {
		super(defense, texture, properties);
	}

	public DyedWolfArmorItem(int defense, float toughness, float knockbackResistance, ResourceLocation texture, Properties properties) {
		super(defense, toughness, knockbackResistance, texture, properties);
	}
}
