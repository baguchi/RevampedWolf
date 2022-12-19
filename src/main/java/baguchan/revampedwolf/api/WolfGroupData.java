package baguchan.revampedwolf.api;

import net.minecraft.world.entity.AgeableMob;

public class WolfGroupData extends AgeableMob.AgeableMobGroupData {
	public final WolfTypes type;

	public WolfGroupData(WolfTypes p_28703_) {
		super(false);
		this.type = p_28703_;
	}
}