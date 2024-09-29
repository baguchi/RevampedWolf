package baguchan.revampedwolf;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WolfConfig {
    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static {
        Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue disableWolfArmors;

        public Common(ModConfigSpec.Builder builder) {
            disableWolfArmors = builder
                    .comment("Disable Wolf Armors. [true / false]")
                    .translation(RevampedWolf.MODID + ".config.disable_wolf_armor")
                    .define("Disable Wolf Armors", false);
        }
    }
}