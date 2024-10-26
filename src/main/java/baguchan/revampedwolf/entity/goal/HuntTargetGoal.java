package baguchan.revampedwolf.entity.goal;

import baguchan.revampedwolf.api.IHunt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;

public class HuntTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
	private final TamableAnimal tamableMob;

	public HuntTargetGoal(TamableAnimal p_26097_, Class<T> p_26098_, boolean p_26099_, @Nullable TargetingConditions.Selector p_26100_) {
		super(p_26097_, p_26098_, 10, p_26099_, false, p_26100_);
		this.tamableMob = p_26097_;
	}

	public boolean canUse() {
		return !this.tamableMob.isTame() && (!(this.tamableMob instanceof IHunt) || !((IHunt) this.tamableMob).isHunted()) && super.canUse();
	}

	public boolean canContinueToUse() {
		return this.targetConditions != null ? this.targetConditions.test(getServerLevel(this.mob), this.mob, this.target) : super.canContinueToUse();
	}
}