package baguchan.revampedwolf.entity.goal;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

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

            Optional<Vec3> optional = calculateJumpVectorForAngle(p_312651_, p_312758_, f, i, true);
            if (optional.isPresent()) {
                return optional;
            }
        }

        return Optional.empty();
    }

    //backport long jump
    public static Optional<Vec3> calculateJumpVectorForAngle(Mob p_311799_, Vec3 p_312876_, float p_312407_, int p_311991_, boolean p_312784_) {
        Vec3 vec3 = p_311799_.position();
        Vec3 vec31 = new Vec3(p_312876_.x - vec3.x, 0.0, p_312876_.z - vec3.z).normalize().scale(0.5);
        Vec3 vec32 = p_312876_.subtract(vec31);
        Vec3 vec33 = vec32.subtract(vec3);
        float f = (float) p_311991_ * (float) Math.PI / 180.0F;
        double d0 = Math.atan2(vec33.z, vec33.x);
        double d1 = vec33.subtract(0.0, vec33.y, 0.0).lengthSqr();
        double d2 = Math.sqrt(d1);
        double d3 = vec33.y;
        double d4 = p_311799_.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get());
        double d5 = Math.sin((double) (2.0F * f));
        double d6 = Math.pow(Math.cos((double) f), 2.0);
        double d7 = Math.sin((double) f);
        double d8 = Math.cos((double) f);
        double d9 = Math.sin(d0);
        double d10 = Math.cos(d0);
        double d11 = d1 * d4 / (d2 * d5 - 2.0 * d3 * d6);
        if (d11 < 0.0) {
            return Optional.empty();
        } else {
            double d12 = Math.sqrt(d11);
            if (d12 > (double) p_312407_) {
                return Optional.empty();
            } else {
                double d13 = d12 * d8;
                double d14 = d12 * d7;
                if (p_312784_) {
                    int i = Mth.ceil(d2 / d13) * 2;
                    double d15 = 0.0;
                    Vec3 vec34 = null;
                    EntityDimensions entitydimensions = p_311799_.getDimensions(Pose.LONG_JUMPING);

                    for (int j = 0; j < i - 1; j++) {
                        d15 += d2 / (double) i;
                        double d16 = d7 / d8 * d15 - Math.pow(d15, 2.0) * d4 / (2.0 * d11 * Math.pow(d8, 2.0));
                        double d17 = d15 * d10;
                        double d18 = d15 * d9;
                        Vec3 vec35 = new Vec3(vec3.x + d17, vec3.y + d16, vec3.z + d18);
                        if (vec34 != null && !isClearTransition(p_311799_, entitydimensions, vec34, vec35)) {
                            return Optional.empty();
                        }

                        vec34 = vec35;
                    }
                }

                return Optional.of(new Vec3(d13 * d10, d14, d13 * d9).scale(0.95F));
            }
        }
    }

    private static boolean isClearTransition(Mob p_312910_, EntityDimensions p_312734_, Vec3 p_311995_, Vec3 p_312896_) {
        Vec3 vec3 = p_312896_.subtract(p_311995_);
        double d0 = (double) Math.min(p_312734_.width, p_312734_.height);
        int i = Mth.ceil(vec3.length() / d0);
        Vec3 vec31 = vec3.normalize();
        Vec3 vec32 = p_311995_;

        for (int j = 0; j < i; j++) {
            vec32 = j == i - 1 ? p_312896_ : vec32.add(vec31.scale(d0 * 0.9F));
            if (!p_312910_.level().noCollision(p_312910_, p_312734_.makeBoundingBox(vec32))) {
                return false;
            }
        }

        return true;
    }
}
