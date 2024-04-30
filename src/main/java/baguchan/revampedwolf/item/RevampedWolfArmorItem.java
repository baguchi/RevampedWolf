package baguchan.revampedwolf.item;

import com.google.common.base.Suppliers;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.UUID;
import java.util.function.Supplier;

public class RevampedWolfArmorItem extends AnimalArmorItem {
    private static final EnumMap<Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), p_323375_ -> {
        p_323375_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_323375_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_323375_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_323375_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
        p_323375_.put(ArmorItem.Type.BODY, UUID.fromString("C1C72771-8B8E-BA4A-ACE0-81A93C8928B2"));
    });
    private final Supplier<ItemAttributeModifiers> defaultModifiers;
    public final Holder<ArmorMaterial> armorMaterialHolder;
    private final ResourceLocation textureLocation;
    @javax.annotation.Nullable
    private final ResourceLocation overlayTextureLocation;

    public RevampedWolfArmorItem(Holder<ArmorMaterial> armorMaterial, BodyType p_324315_, boolean p_331679_, Properties p_316341_) {
        super(armorMaterial, p_324315_, p_331679_, p_316341_);
        this.armorMaterialHolder = armorMaterial;
        ResourceLocation resourcelocation = armorMaterial.unwrapKey().orElseThrow().location().withPath((p_323717_ -> "textures/entity/wolf/armor/wolf_armor_" + p_323717_));
        this.textureLocation = resourcelocation.withSuffix(".png");
        if (p_331679_) {
            this.overlayTextureLocation = resourcelocation.withSuffix("_overlay.png");
        } else {
            this.overlayTextureLocation = null;
        }
        //rewrite armor valve
        this.defaultModifiers = Suppliers.memoize(
                () -> {
                    int i = armorMaterial.value().getDefense(Type.BODY) * 2;
                    float f = armorMaterial.value().toughness() * 2;
                    ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
                    EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(Type.BODY.getSlot());
                    UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(Type.BODY);
                    itemattributemodifiers$builder.add(
                            Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double) i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup
                    );
                    itemattributemodifiers$builder.add(
                            Attributes.ARMOR_TOUGHNESS,
                            new AttributeModifier(uuid, "Armor toughness", (double) f, AttributeModifier.Operation.ADD_VALUE),
                            equipmentslotgroup
                    );
                    float f1 = armorMaterial.value().knockbackResistance() * 2;
                    if (f1 > 0.0F) {
                        itemattributemodifiers$builder.add(
                                Attributes.KNOCKBACK_RESISTANCE,
                                new AttributeModifier(uuid, "Armor knockback resistance", (double) f1, AttributeModifier.Operation.ADD_VALUE),
                                equipmentslotgroup
                        );
                    }

                    return itemattributemodifiers$builder.build();
                }
        );
    }

    @Override
    public ResourceLocation getTexture() {
        return this.textureLocation;
    }

    @Nullable
    @Override
    public ResourceLocation getOverlayTexture() {
        return this.overlayTextureLocation;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }


    @Override
    public boolean isEnchantable(ItemStack p_341697_) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.FIRE_PROTECTION || enchantment == Enchantments.BLAST_PROTECTION || enchantment == Enchantments.PROJECTILE_PROTECTION || enchantment == Enchantments.PROTECTION || enchantment == Enchantments.FEATHER_FALLING;
    }
}
