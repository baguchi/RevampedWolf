package baguchan.revampedwolf.registry;

import baguchan.revampedwolf.data.WolfEquipmentModels;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

public interface WolfArmorMaterials {
    ArmorMaterial LEATHER = new ArmorMaterial(5, Util.make(new EnumMap<>(ArmorType.class), p_371485_ -> {
        p_371485_.put(ArmorType.BOOTS, 1);
        p_371485_.put(ArmorType.LEGGINGS, 2);
        p_371485_.put(ArmorType.CHESTPLATE, 3);
        p_371485_.put(ArmorType.HELMET, 1);
        p_371485_.put(ArmorType.BODY, 6);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR, WolfEquipmentModels.LEATHER);
    ArmorMaterial IRON = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), p_371378_ -> {
        p_371378_.put(ArmorType.BOOTS, 2);
        p_371378_.put(ArmorType.LEGGINGS, 5);
        p_371378_.put(ArmorType.CHESTPLATE, 6);
        p_371378_.put(ArmorType.HELMET, 2);
        p_371378_.put(ArmorType.BODY, 14);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, WolfEquipmentModels.IRON);
    ArmorMaterial GOLD = new ArmorMaterial(7, Util.make(new EnumMap<>(ArmorType.class), p_371284_ -> {
        p_371284_.put(ArmorType.BOOTS, 1);
        p_371284_.put(ArmorType.LEGGINGS, 3);
        p_371284_.put(ArmorType.CHESTPLATE, 5);
        p_371284_.put(ArmorType.HELMET, 2);
        p_371284_.put(ArmorType.BODY, 10);
    }), 25, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, ItemTags.REPAIRS_GOLD_ARMOR, WolfEquipmentModels.GOLD);
    ArmorMaterial DIAMOND = new ArmorMaterial(33, Util.make(new EnumMap<>(ArmorType.class), p_371445_ -> {
        p_371445_.put(ArmorType.BOOTS, 3);
        p_371445_.put(ArmorType.LEGGINGS, 6);
        p_371445_.put(ArmorType.CHESTPLATE, 8);
        p_371445_.put(ArmorType.HELMET, 3);
        p_371445_.put(ArmorType.BODY, 20);
    }), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 4.0F, 0.0F, ItemTags.REPAIRS_DIAMOND_ARMOR, WolfEquipmentModels.DIAMOND);
    ArmorMaterial NETHERITE = new ArmorMaterial(37, Util.make(new EnumMap<>(ArmorType.class), p_371743_ -> {
        p_371743_.put(ArmorType.BOOTS, 3);
        p_371743_.put(ArmorType.LEGGINGS, 6);
        p_371743_.put(ArmorType.CHESTPLATE, 8);
        p_371743_.put(ArmorType.HELMET, 3);
        p_371743_.put(ArmorType.BODY, 22);
    }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 6.0F, 0.5F, ItemTags.REPAIRS_NETHERITE_ARMOR, WolfEquipmentModels.NETHERITE);
}
