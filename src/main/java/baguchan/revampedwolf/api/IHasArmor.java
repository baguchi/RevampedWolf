package baguchan.revampedwolf.api;

import net.minecraft.world.item.ItemStack;

public interface IHasArmor {
	ItemStack getArmor();

	boolean isWearingArmor();

	boolean isArmor(ItemStack p_30645_);
}
