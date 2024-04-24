package baguchan.revampedwolf.item;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

public class RevampedWolfArmorItem extends AnimalArmorItem {
    public final Holder<ArmorMaterial> armorMaterialHolder;
    private final ResourceLocation textureLocation;
    @javax.annotation.Nullable
    private final ResourceLocation overlayTextureLocation;

    public RevampedWolfArmorItem(Holder<ArmorMaterial> p_323836_, BodyType p_324315_, boolean p_331679_, Properties p_316341_) {
        super(p_323836_, p_324315_, p_331679_, p_316341_);
        this.armorMaterialHolder = p_323836_;
        ResourceLocation resourcelocation = p_323836_.unwrapKey().orElseThrow().location().withPath((p_323717_ -> "textures/entity/wolf/armor/wolf_armor_" + p_323717_));
        this.textureLocation = resourcelocation.withSuffix(".png");
        if (p_331679_) {
            this.overlayTextureLocation = resourcelocation.withSuffix("_overlay.png");
        } else {
            this.overlayTextureLocation = null;
        }
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
}
