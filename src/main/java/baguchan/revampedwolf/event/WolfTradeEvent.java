package baguchan.revampedwolf.event;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.registry.ModItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;


@EventBusSubscriber(modid = RevampedWolf.MODID)
public class WolfTradeEvent {
    @SubscribeEvent
    public static void villagerTradeEvent(VillagerTradesEvent event) {
        List<VillagerTrades.ItemListing> trades = event.getTrades().get(1);
        List<VillagerTrades.ItemListing> trades2 = event.getTrades().get(2);
        List<VillagerTrades.ItemListing> trades3 = event.getTrades().get(3);
        if (event.getType() == VillagerProfession.LEATHERWORKER) {
            trades2.add(new WolfArmorItemsForEmeralds(ModItems.LEATHER_WOLF_ARMOR.get(), 3, 8, 8));

        }

        if (event.getType() == VillagerProfession.ARMORER) {
            trades.add(new WolfArmorItemsForEmeralds(ModItems.IRON_WOLF_ARMOR.get(), 4, 8, 3));

            trades3.add(new WolfArmorItemsForEmeralds(new ItemStack(ModItems.DIAMOND_WOLF_ARMOR.get()), 8, 12, 13, 0.05F));
        }
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class WolfArmorItemsForEmeralds implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public WolfArmorItemsForEmeralds(Item p_35752_, int p_35753_, int p_35755_, int p_35756_) {
            this(p_35752_.getDefaultInstance(), p_35753_, p_35755_, p_35756_, 0.05F);
        }

        public WolfArmorItemsForEmeralds(ItemStack p_35752_, int p_35753_, int p_35755_, int p_35756_) {
            this(p_35752_, p_35753_, p_35755_, p_35756_, 0.05F);
        }

        public WolfArmorItemsForEmeralds(ItemStack p_35758_, int p_35759_, int p_35761_, int p_35762_, float p_35763_) {
            this.itemStack = p_35758_;
            this.emeraldCost = p_35759_;
            this.maxUses = p_35761_;
            this.villagerXp = p_35762_;
            this.priceMultiplier = p_35763_;
        }

        public MerchantOffer getOffer(Entity p_35771_, RandomSource p_35772_) {
            int i = 2 + p_35772_.nextInt(8);
            ItemStack itemstack = EnchantmentHelper.enchantItem(
                    p_35771_.level().enabledFeatures(), p_35772_, new ItemStack(this.itemStack.getItem()), i, false
            );
            int j = Math.min(this.emeraldCost + i, 64);
            return new MerchantOffer(new ItemCost(Items.EMERALD, j), itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }
}
