package baguchan.revampedwolf.item;

import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class WolfArmorItem extends AnimalArmorItem {
    public WolfArmorItem(ArmorMaterial p_371643_, BodyType p_324315_, Properties p_316341_) {
        super(p_371643_, p_324315_, p_316341_.enchantable(p_371643_.enchantmentValue()));
    }
}
