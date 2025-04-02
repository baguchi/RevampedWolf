package baguchan.revampedwolf.item;

import baguchan.revampedwolf.WolfConfig;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class WolfArmorItem extends Item {
    public WolfArmorItem(ArmorMaterial p_371643_, Properties p_316341_) {
        super(p_316341_.wolfArmor(p_371643_).enchantable(p_371643_.enchantmentValue()));
    }

    @Override
    public boolean isEnabled(FeatureFlagSet p_249172_) {
        return super.isEnabled(p_249172_) && !WolfConfig.COMMON.disableWolfArmors.get();
    }
}
