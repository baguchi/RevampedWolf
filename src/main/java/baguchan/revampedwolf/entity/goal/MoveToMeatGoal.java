package baguchan.revampedwolf.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class MoveToMeatGoal extends Goal {
	private static final Predicate<? super ItemEntity> ALLOWED_ITEMS = (p_213616_0_) -> {
		return p_213616_0_.getItem().getItem().getFoodProperties() != null && p_213616_0_.getItem().isEdible() && p_213616_0_.getItem().getItem().getFoodProperties().isMeat();
	};
	private final PathfinderMob mob;

	public MoveToMeatGoal(PathfinderMob p_i50572_2_) {
		this.mob = p_i50572_2_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	public boolean canUse() {
		List<ItemEntity> list = this.mob.level.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0D, 4.0D, 8.0D), ALLOWED_ITEMS);
		if (!list.isEmpty() && this.mob.hasLineOfSight(list.get(0))) {
			return this.mob.getNavigation().moveTo(list.get(0), (double) 1.1F);
		}

		return false;
	}
}