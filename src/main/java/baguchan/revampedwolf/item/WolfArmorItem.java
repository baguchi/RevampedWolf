package baguchan.revampedwolf.item;

import baguchan.revampedwolf.WolfConfig;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class WolfArmorItem extends AnimalArmorItem {
    public WolfArmorItem(ArmorMaterial p_371643_, BodyType p_324315_, Properties p_316341_) {
        super(p_371643_, p_324315_, p_316341_.enchantable(p_371643_.enchantmentValue()));
    }

    @Override
    public boolean isEnabled(FeatureFlagSet p_249172_) {
        return super.isEnabled(p_249172_) && !WolfConfig.COMMON.disableWolfArmors.get();
    }
}
