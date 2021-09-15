package baguchan.revampedwolf.mixin;

import baguchan.revampedwolf.api.IHunt;
import baguchan.revampedwolf.entity.goal.HuntTargetGoal;
import baguchan.revampedwolf.entity.goal.MoveToMeatGoal;
import baguchan.revampedwolf.entity.goal.WolfAvoidEntityGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal implements NeutralMob, IHunt {

	private int cooldown;
	private int eatTick;

	@Shadow
	@Final
	public static Predicate<LivingEntity> PREY_SELECTOR;

	protected WolfMixin(EntityType<? extends TamableAnimal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void onConstructor(EntityType<? extends Wolf> p_27557_, Level p_27558_, CallbackInfo info) {
		this.setCanPickUpLoot(true);
	}

	@Inject(method = "registerGoals", at = @At("HEAD"), cancellable = true)
	protected void registerGoals(CallbackInfo callbackInfo) {
		Wolf wolf = (Wolf) ((Object) this);
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new WolfAvoidEntityGoal<>(wolf, Llama.class, 24.0F, 1.5D, 1.5D));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new MoveToMeatGoal(this));
		this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new BegGoal(wolf, 8.0F));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(5, new HuntTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
		this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
		callbackInfo.cancel();
	}

	@Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
	public void aiStep(CallbackInfo callbackInfo) {
		if (!this.level.isClientSide && this.isAlive()) {

			ItemStack mainhand = this.getItemInHand(InteractionHand.MAIN_HAND);

			if (!this.isUsingItem() && this.getItemInHand(InteractionHand.MAIN_HAND).getItem().getFoodProperties() != null && this.getItemInHand(InteractionHand.MAIN_HAND).getItem().getFoodProperties().isMeat()) {
				this.eatTick++;
				if (this.eatTick > 300) {
					if (!mainhand.isEmpty()) {
						this.startUsingItem(InteractionHand.MAIN_HAND);
					}
				}
			} else {
				this.eatTick = 0;
			}
		}
	}

	@Override
	protected void completeUsingItem() {
		InteractionHand hand = this.getUsedItemHand();
		if (this.useItem.equals(this.getItemInHand(hand))) {
			if (!this.useItem.isEmpty() && this.isUsingItem()) {
				ItemStack copy = this.useItem.copy();

				if (copy.getItem().getFoodProperties() != null) {
					this.heal(copy.getItem().getFoodProperties().getNutrition());
				}
			}
		}
		super.completeUsingItem();
	}

	private void spitOutItem(ItemStack p_28602_) {
		if (!p_28602_.isEmpty() && !this.level.isClientSide) {
			ItemEntity itementity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, p_28602_);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this.getUUID());
			this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
			this.level.addFreshEntity(itementity);
		}
	}

	private void dropItemStack(ItemStack p_28606_) {
		ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), p_28606_);
		this.level.addFreshEntity(itementity);
	}

	public boolean canHoldItem(ItemStack p_28578_) {
		Item item = p_28578_.getItem();
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		return itemstack.isEmpty() && item.isEdible() && item.getFoodProperties().isMeat();
	}

	@Override
	protected void pickUpItem(ItemEntity p_28514_) {
		if (!this.isTame()) {
			ItemStack itemstack = p_28514_.getItem();
			if (this.canHoldItem(itemstack)) {
				int i = itemstack.getCount();
				if (i > 1) {
					this.dropItemStack(itemstack.split(i - 1));
				}

				this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
				this.onItemPickup(p_28514_);
				this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
				this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
				this.take(p_28514_, itemstack.getCount());
				p_28514_.discard();
				this.eatTick = 0;
			}
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"), cancellable = true)
	public void addAdditionalSaveData(CompoundTag p_213281_1_, CallbackInfo callbackInfo) {
		p_213281_1_.putInt("HuntingCooldown", this.cooldown);
		p_213281_1_.putInt("EatTick", this.eatTick);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"), cancellable = true)
	public void readAdditionalSaveData(CompoundTag p_70037_1_, CallbackInfo callbackInfo) {
		this.cooldown = p_70037_1_.getInt("HuntingCooldown");
		this.eatTick = p_70037_1_.getInt("EatTick");
		this.setCanPickUpLoot(true);
	}

	@Override
	public void killed(ServerLevel p_241847_1_, LivingEntity p_241847_2_) {
		super.killed(p_241847_1_, p_241847_2_);
		this.setHuntCooldown(600);
	}

	@Override
	public void setHuntCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getHuntCooldown() {
		return this.cooldown;
	}

	@Override
	public boolean isHunted() {
		return this.cooldown > 0;
	}
}
