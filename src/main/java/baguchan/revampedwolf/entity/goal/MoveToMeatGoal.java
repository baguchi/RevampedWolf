package baguchan.revampedwolf.entity.goal;

import baguchan.revampedwolf.api.IHunger;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class MoveToMeatGoal extends Goal {
	private static final Predicate<? super ItemEntity> ALLOWED_ITEMS = (p_213616_0_) -> {
		return p_213616_0_.getItem().is(ItemTags.MEAT);
	};
	private final TamableAnimal mob;

	public MoveToMeatGoal(TamableAnimal p_i50572_2_) {
		this.mob = p_i50572_2_;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	public boolean canUse() {
		if (!this.mob.isTame() && this.mob instanceof IHunger && ((IHunger) this.mob).getHunger() <= 0) {
			List<ItemEntity> list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0D, 4.0D, 8.0D), ALLOWED_ITEMS);
			if (!list.isEmpty() && this.mob.hasLineOfSight(list.get(0))) {
				return this.mob.getNavigation().moveTo(list.get(0), (double) 1.1F);
			}
		}

		return false;
	}

	@Override
	public void start() {
		super.start();
		if (this.mob instanceof IHunger) {
			((IHunger) this.mob).setHunger(1200);
		}
	}
}