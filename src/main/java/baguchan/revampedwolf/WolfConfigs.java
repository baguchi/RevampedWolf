package baguchan.revampedwolf;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WolfConfigs {
	public static final Common COMMON;
	public static final ModConfigSpec COMMON_SPEC;

	static {
		Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static class Common {
		public final ModConfigSpec.BooleanValue disableWolfArmor;

		public Common(ModConfigSpec.Builder builder) {
			disableWolfArmor = builder
					.comment("Disable the Wolf Armor. [true / false]")
					.translation(RevampedWolf.MODID + ".config.disableArmor")
					.define("Disable Wolf Armor", false);
		}
	}

}