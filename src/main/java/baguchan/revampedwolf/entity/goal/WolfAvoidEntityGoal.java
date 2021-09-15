package baguchan.revampedwolf.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;

public class WolfAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
	private final Wolf wolf;

	public WolfAvoidEntityGoal(Wolf p_30454_, Class<T> p_30455_, float p_30456_, double p_30457_, double p_30458_) {
		super(p_30454_, p_30455_, p_30456_, p_30457_, p_30458_);
		this.wolf = p_30454_;
	}

	public boolean canUse() {
		if (super.canUse() && this.toAvoid instanceof Llama) {
			return !this.wolf.isTame() && this.avoidLlama((Llama) this.toAvoid);
		} else {
			return false;
		}
	}

	private boolean avoidLlama(Llama p_30461_) {
		return p_30461_.getStrength() >= this.wolf.getRandom().nextInt(5);
	}

	public void start() {
		this.wolf.setTarget((LivingEntity) null);
		super.start();
	}

	public void tick() {
		this.wolf.setTarget((LivingEntity) null);
		super.tick();
	}
}