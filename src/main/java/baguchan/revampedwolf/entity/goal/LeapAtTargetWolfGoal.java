package baguchan.revampedwolf.entity.goal;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

public class LeapAtTargetWolfGoal extends Goal {
    private static final ObjectArrayList<Integer> ALLOWED_ANGLES = new ObjectArrayList<>(Lists.newArrayList(40, 45, 50, 55, 58));

    private final Mob mob;
    protected final float maxJumpVelocityMultiplier;
    private LivingEntity target;
    private int jumpCooldown;

    public LeapAtTargetWolfGoal(Mob p_25492_, float maxJumpVelocityMultiplier) {
        this.mob = p_25492_;
        this.maxJumpVelocityMultiplier = maxJumpVelocityMultiplier;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasControllingPassenger()) {
            return false;
        } else {
            this.target = this.mob.getTarget();
            if (this.target == null) {
                return false;
            } else {
                if (this.jumpCooldown > 0) {
                    this.jumpCooldown--;
                }
                double d0 = this.mob.distanceTo(this.target);
                if (d0 < 0.5 || d0 > 3) {
                    return false;
                } else {
                    return this.mob.onGround() && this.jumpCooldown <= 0;
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.onGround();
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.setDiscardFriction(false);
        this.jumpStop(this.mob);
    }

    @Override
    public void start() {
        this.jumpCooldown = 20 + this.mob.getRandom().nextInt(20);
        BlockPos blockpos = snapToSurface(this.mob, this.target.position());
        if (blockpos != null) {
            BlockState blockstate = this.mob.level().getBlockState(blockpos.below());
            if (this.mob.getType().isBlockDangerous(blockstate)) {
            } else if (!hasLineOfSight(this.mob, blockpos.getCenter())
                    && !hasLineOfSight(this.mob, blockpos.above(4).getCenter())) {
            } else {
                Vec3 vec32 = calculateOptimalJumpVector(this.mob, this.mob.getRandom(), Vec3.atBottomCenterOf(blockpos)).orElse(null);
                if (vec32 == null) {
                    return;
                } else {
                    this.mob.setDiscardFriction(true);

                    this.mob.setDeltaMovement(vec32);
                }
            }
        }
        this.jumpStart(this.mob);
    }

    public void jumpStart(Mob mob) {
        this.mob.playSound(SoundEvents.GOAT_LONG_JUMP);
    }

    public void jumpStop(Mob mob) {
    }


    public static boolean hasLineOfSight(LivingEntity p_316785_, Vec3 p_316249_) {
        Vec3 vec3 = new Vec3(p_316785_.getX(), p_316785_.getY(), p_316785_.getZ());
        return p_316249_.distanceTo(vec3) > 50.0
                ? false
                : p_316785_.level().clip(new ClipContext(vec3, p_316249_, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_316785_)).getType()
                == HitResult.Type.MISS;
    }

    @Nullable
    private static BlockPos snapToSurface(LivingEntity p_311909_, Vec3 p_312597_) {
        ClipContext clipcontext = new ClipContext(
                p_312597_, p_312597_.relative(Direction.DOWN, 10.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_311909_
        );
        HitResult hitresult = p_311909_.level().clip(clipcontext);
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            return BlockPos.containing(hitresult.getLocation()).above();
        } else {
            ClipContext clipcontext1 = new ClipContext(
                    p_312597_, p_312597_.relative(Direction.UP, 10.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_311909_
            );
            HitResult hitresult1 = p_311909_.level().clip(clipcontext1);
            return hitresult1.getType() == HitResult.Type.BLOCK ? BlockPos.containing(hitresult1.getLocation()).above() : null;
        }
    }

    private Optional<Vec3> calculateOptimalJumpVector(Mob p_312651_, RandomSource p_312364_, Vec3 p_312758_) {
        for (int i : Util.shuffledCopy(ALLOWED_ANGLES, p_312364_)) {
            float f = (float) (p_312651_.getAttributeValue(Attributes.JUMP_STRENGTH) * (double) this.maxJumpVelocityMultiplier);

            Optional<Vec3> optional = LongJumpUtil.calculateJumpVectorForAngle(p_312651_, p_312758_, f, i, true);
            if (optional.isPresent()) {
                return optional;
            }
        }

        return Optional.empty();
    }
}
