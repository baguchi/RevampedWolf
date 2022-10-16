package baguchan.revampedwolf;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = RevampedWolf.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WolfConfigs {
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	static {
		Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static class Common {
		public final ForgeConfigSpec.BooleanValue disableWolfArmor;

		public Common(ForgeConfigSpec.Builder builder) {
			disableWolfArmor = builder
					.comment("Disable the Wolf Armor. [true / false]")
					.translation(RevampedWolf.MODID + ".config.disableArmor")
					.define("Disable Wolf Armor", false);
		}
	}

}