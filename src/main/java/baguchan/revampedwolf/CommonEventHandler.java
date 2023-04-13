package baguchan.revampedwolf;

import baguchan.revampedwolf.api.IWolfTypes;
import baguchan.revampedwolf.api.WolfGroupData;
import baguchan.revampedwolf.api.WolfTypes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RevampedWolf.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {
    @SubscribeEvent
    public static void spawnEvent(MobSpawnEvent.FinalizeSpawn event) {
        if (event.getEntity() instanceof Wolf wolf) {
            if (event.getEntity() instanceof IWolfTypes wolfTypes) {
                WolfTypes type = WolfTypes.values()[event.getLevel().getRandom().nextInt(WolfTypes.values().length)];
                boolean flag = false;

                if (event.getLevel().getBiome(wolf.blockPosition()).is(Tags.Biomes.IS_COLD)) {
                    type = WolfTypes.WHITE;
                }

                if (event.getLevel().getBiome(wolf.blockPosition()).is(Tags.Biomes.IS_DESERT)) {
                    type = WolfTypes.BROWN;
                }

                if (event.getSpawnData() instanceof WolfGroupData groupdata) {
                    type = groupdata.type;
                    if (groupdata.getGroupSize() >= 2) {
                        flag = true;
                    }
                } else {
                    event.setSpawnData(new WolfGroupData(type));
                }

                if (flag) {
                    wolf.setAge(-24000);
                }

                wolfTypes.setVariant(type);
            }
        }
    }
}
