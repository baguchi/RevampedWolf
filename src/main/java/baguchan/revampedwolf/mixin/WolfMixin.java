package baguchan.revampedwolf.mixin;

import baguchan.revampedwolf.api.IHunger;
import baguchan.revampedwolf.api.IHunt;
import baguchan.revampedwolf.entity.goal.HuntTargetGoal;
import baguchan.revampedwolf.entity.goal.MoveToMeatGoal;
import baguchan.revampedwolf.entity.goal.WolfAvoidEntityGoal;
import baguchan.revampedwolf.item.RevampedWolfArmorItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Predicate;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal implements NeutralMob, IHunt, IHunger {
	private int huntCooldown;
	private int eatTick;
	private int hungerTick;
	private float saturation;

	@Shadow
	@Final
	public static Predicate<LivingEntity> PREY_SELECTOR;

	@Shadow
	public abstract boolean hasArmor();

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

	@Inject(method = "canArmorAbsorb", at = @At(value = "HEAD"), cancellable = true)
	private void canArmorAbsorb(DamageSource p_331524_, CallbackInfoReturnable<Boolean> cir) {
		if (this.getBodyArmorItem().getItem() instanceof RevampedWolfArmorItem) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "mobInteract", at = @At(value = "HEAD"), cancellable = true)
	public void mobInteract(Player p_30412_, InteractionHand p_30413_, CallbackInfoReturnable<InteractionResult> cir) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Item item = itemstack.getItem();
		if (itemstack.getItem() instanceof RevampedWolfArmorItem && this.isOwnedBy(p_30412_) && !this.hasArmor() && !this.isBaby()) {
			this.setBodyArmorItem(itemstack.copyWithCount(1));
			itemstack.consume(1, p_30412_);
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}

	@Inject(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Wolf;heal(F)V", shift = At.Shift.AFTER, ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	public void mobInteractHeal(Player p_30412_, InteractionHand p_30413_, CallbackInfoReturnable<InteractionResult> cir, ItemStack itemstack) {
		this.saturation = Mth.clamp(this.saturation + itemstack.getItem().getFoodProperties(itemstack, this).nutrition() * itemstack.getItem().getFoodProperties(itemstack, this).saturation() * 2.0F, 0, 20);
	}

	@Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
	public void aiStep(CallbackInfo callbackInfo) {
		if (!this.level().isClientSide && this.isAlive()) {
			if (this.hungerTick > 0) {
				this.hungerTick--;
			}

			ItemStack mainhand = this.getItemInHand(InteractionHand.MAIN_HAND);

			if (!this.isUsingItem() && this.getItemInHand(InteractionHand.MAIN_HAND).getItem().getFoodProperties(mainhand, this) != null && this.getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.MEAT)) {
				if (this.getHealth() < this.getMaxHealth() || !this.isTame()) {
					this.eatTick++;
					if (this.eatTick > 200) {
						if (!mainhand.isEmpty()) {
							this.startUsingItem(InteractionHand.MAIN_HAND);
						}
					}
				}
			} else {
				this.eatTick = 0;
			}
		}
		if (this.getHealth() < this.getMaxHealth()) {
			if (saturation > 0) {
				this.saturation = Mth.clamp(this.saturation - 0.5F, 0, 20);
				if (saturation >= 0.5F) {
					heal(1);
				}
			}
		}
	}

	@Override
	protected void completeUsingItem() {
		InteractionHand hand = this.getUsedItemHand();
		if (this.useItem.equals(this.getItemInHand(hand))) {
			if (!this.useItem.isEmpty() && this.isUsingItem()) {
				ItemStack copy = this.useItem.copy();

				if (copy.getItem().getFoodProperties(useItem, this) != null) {
					this.heal(copy.getItem().getFoodProperties(useItem, this).nutrition());
					this.saturation = Mth.clamp(this.saturation + copy.getItem().getFoodProperties(useItem, this).nutrition() * copy.getItem().getFoodProperties(useItem, this).saturation() * 2.0F, 0, 20);
				}
			}
		}
		super.completeUsingItem();
	}

	private void spitOutItem(ItemStack p_28602_) {
		if (!p_28602_.isEmpty() && !this.level().isClientSide) {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, p_28602_);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this);
			this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
			this.level().addFreshEntity(itementity);
		}
	}

	private void dropItemStack(ItemStack p_28606_) {
		ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), p_28606_);
		this.level().addFreshEntity(itementity);
	}

	public boolean canHoldItem(ItemStack p_28578_) {
		Item item = p_28578_.getItem();
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		return itemstack.isEmpty() && p_28578_.is(ItemTags.MEAT);
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
		p_213281_1_.putInt("HuntingCooldown", this.huntCooldown);
		p_213281_1_.putInt("EatTick", this.eatTick);
		p_213281_1_.putInt("HungerTick", this.hungerTick);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"), cancellable = true)
	public void readAdditionalSaveData(CompoundTag p_70037_1_, CallbackInfo callbackInfo) {
		this.huntCooldown = p_70037_1_.getInt("HuntingCooldown");
		this.eatTick = p_70037_1_.getInt("EatTick");
		this.hungerTick = p_70037_1_.getInt("HungerTick");
		this.setCanPickUpLoot(true);
	}

	@Override
	public boolean killedEntity(ServerLevel p_216988_, LivingEntity p_216989_) {
		this.setHuntCooldown(1200);
		return super.killedEntity(p_216988_, p_216989_);
	}

	@Override
	public void setHuntCooldown(int cooldown) {
		this.huntCooldown = cooldown;
	}

	@Override
	public int getHuntCooldown() {
		return this.huntCooldown;
	}

	@Override
	public boolean isHunted() {
		return this.huntCooldown > 0;
	}

	@Override
	public int getHunger() {
		return this.hungerTick;
	}

	@Override
	public void setHunger(int hunger) {
		this.hungerTick = hunger;
	}
}
