package baguchan.revampedwolf.mixin;

import baguchan.revampedwolf.RevampedWolf;
import baguchan.revampedwolf.WolfConfigs;
import baguchan.revampedwolf.api.*;
import baguchan.revampedwolf.entity.goal.HuntTargetGoal;
import baguchan.revampedwolf.entity.goal.MoveToMeatGoal;
import baguchan.revampedwolf.entity.goal.WolfAvoidEntityGoal;
import baguchan.revampedwolf.inventory.WolfInventoryMenu;
import baguchan.revampedwolf.item.WolfArmorItem;
import baguchan.revampedwolf.network.ClientWolfScreenOpenPacket;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal implements NeutralMob, IHunt, IHunger, IHasArmor, IHasInventory, IWolfTypes, ContainerListener {

	private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(Wolf.class, EntityDataSerializers.STRING);


	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	private static final UUID TOUGHNESS_ARMOR_MODIFIER_UUID = UUID.fromString("db9bf914-5933-474e-a184-e73040fb0789");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("7c036153-7d05-c9e5-2f29-c664ef413677");
	private int huntCooldown;
	private int eatTick;
	private int hungerTick;

	@Shadow
	@Final
	public static Predicate<LivingEntity> PREY_SELECTOR;

	protected SimpleContainer inventory;

	protected WolfMixin(EntityType<? extends TamableAnimal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void onConstructor(EntityType<? extends Wolf> p_27557_, Level p_27558_, CallbackInfo info) {
		this.setCanPickUpLoot(true);
		if (!WolfConfigs.COMMON.disableWolfArmor.get()) {
			this.createInventory();
		}
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	protected void defineSynchedData(CallbackInfo callbackInfo) {
		this.entityData.define(DATA_TYPE, WolfTypes.WHITE.type);
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

	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	public void mobInteract(Player p_30412_, InteractionHand p_30413_, CallbackInfoReturnable<InteractionResult> callbackInfo) {
		ItemStack itemstack = p_30412_.getItemInHand(p_30413_);
		Item item = itemstack.getItem();
		if (!WolfConfigs.COMMON.disableWolfArmor.get()) {
			if (p_30412_.isSecondaryUseActive() && this.isTame() && this.isOwnedBy(p_30412_)) {
				if (p_30412_ instanceof IOpenWolfContainer) {
					this.openInventory(p_30412_);
					this.gameEvent(GameEvent.ENTITY_INTERACT);
					callbackInfo.setReturnValue(InteractionResult.SUCCESS);
				}
			}
		}
	}

	public void openInventory(Player player) {
		Wolf wolf = (Wolf) ((Object) this);
		if (!this.level.isClientSide
				&& player instanceof IOpenWolfContainer) {
			ServerPlayer sp = (ServerPlayer) player;
			if (sp.containerMenu != sp.inventoryMenu) sp.closeContainer();

			sp.nextContainerCounter();
			RevampedWolf.CHANNEL.send(PacketDistributor.PLAYER.with(() -> sp), new ClientWolfScreenOpenPacket(sp.containerCounter, this.getId()));
			sp.containerMenu = new WolfInventoryMenu(sp.containerCounter, sp.getInventory(), this.inventory, wolf);
			sp.initMenu(sp.containerMenu);
			MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(sp, sp.containerMenu));
		}
	}

	@Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
	public void aiStep(CallbackInfo callbackInfo) {
		if (!this.level.isClientSide && this.isAlive()) {
			if (this.hungerTick > 0) {
				this.hungerTick--;
			}

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
		p_213281_1_.putString("Type", this.getVariant().getSerializedName());
		p_213281_1_.putInt("HuntingCooldown", this.huntCooldown);
		p_213281_1_.putInt("EatTick", this.eatTick);
		p_213281_1_.putInt("HungerTick", this.hungerTick);

		if (!WolfConfigs.COMMON.disableWolfArmor.get()) {
			if (!this.inventory.getItem(0).isEmpty()) {
				p_213281_1_.put("ArmorItem", this.inventory.getItem(0).save(new CompoundTag()));
			}
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"), cancellable = true)
	public void readAdditionalSaveData(CompoundTag p_70037_1_, CallbackInfo callbackInfo) {
		this.setVariant(WolfTypes.byType(p_70037_1_.getString("Type")));

		this.huntCooldown = p_70037_1_.getInt("HuntingCooldown");
		this.eatTick = p_70037_1_.getInt("EatTick");
		this.hungerTick = p_70037_1_.getInt("HungerTick");
		if (!WolfConfigs.COMMON.disableWolfArmor.get()) {
			if (p_70037_1_.contains("ArmorItem", 10)) {
				ItemStack itemstack = ItemStack.of(p_70037_1_.getCompound("ArmorItem"));
				if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
					this.inventory.setItem(0, itemstack);
				}
			}
		}
		this.updateContainerEquipment();
		this.setCanPickUpLoot(true);
	}

	@Override
	public boolean wasKilled(ServerLevel p_216988_, LivingEntity p_216989_) {
		this.setHuntCooldown(1200);
		return super.wasKilled(p_216988_, p_216989_);
	}

	protected void createInventory() {
		SimpleContainer simplecontainer = this.inventory;
		this.inventory = new SimpleContainer(this.getInventorySize());
		if (simplecontainer != null) {
			simplecontainer.removeListener(this);
			int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

			for (int j = 0; j < i; ++j) {
				ItemStack itemstack = simplecontainer.getItem(j);
				if (!itemstack.isEmpty()) {
					this.inventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.inventory.addListener(this);
		this.updateContainerEquipment();
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
	}

	protected void dropEquipment() {
		super.dropEquipment();
		if (this.inventory != null) {
			for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
				ItemStack itemstack = this.inventory.getItem(i);
				if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
					this.spawnAtLocation(itemstack);
				}
			}

		}
	}

	private int getInventorySize() {
		return 1;
	}

	private void updateContainerEquipment() {
		if (!WolfConfigs.COMMON.disableWolfArmor.get()) {
			if (!this.level.isClientSide) {
				this.setDropChance(EquipmentSlot.CHEST, 0.0F);

				ItemStack stack = this.inventory.getItem(0);
				if (this.isArmor(stack)) {
					AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
					if (armor != null) {
						armor.removeModifier(ARMOR_MODIFIER_UUID);
						int i = ((WolfArmorItem) stack.getItem()).getDefense();
						if (i != 0) {
							armor.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Wolf armor bonus", i, AttributeModifier.Operation.ADDITION));
						}

					}

					AttributeInstance toughness = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
					if (toughness != null) {
						toughness.removeModifier(TOUGHNESS_ARMOR_MODIFIER_UUID);

						float i = ((WolfArmorItem) stack.getItem()).getToughness();
						if (i != 0) {
							toughness.addTransientModifier(new AttributeModifier(TOUGHNESS_ARMOR_MODIFIER_UUID, "Wolf Toughness armor bonus", i, AttributeModifier.Operation.ADDITION));
						}
					}

					AttributeInstance knockback_resistance = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
					if (knockback_resistance != null) {
						knockback_resistance.removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);

						float i = ((WolfArmorItem) stack.getItem()).getToughness();
						if (i != 0) {
							knockback_resistance.addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID, "Wolf KnockBack Resistance bonus", i, AttributeModifier.Operation.ADDITION));
						}
					}
				}

			}
		}
	}

	public SlotAccess getSlot(int p_149743_) {
		if (WolfConfigs.COMMON.disableWolfArmor.get()) {
			return super.getSlot(p_149743_);
		}

		int i = p_149743_ - 300;
		return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(p_149743_);
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

	public boolean isWearingArmor() {
		return !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
	}

	@Override
	public boolean isArmor(ItemStack p_30645_) {
		return p_30645_.getItem() instanceof WolfArmorItem;
	}

	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
		if (this.isAlive() && capability == ForgeCapabilities.ITEM_HANDLER && itemHandler != null)
			return itemHandler.cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (itemHandler != null) {
			net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
			itemHandler = null;
			oldHandler.invalidate();
		}
	}

	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlot.CHEST);
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return Lists.newArrayList(this.inventory.getItem(0));
	}

	public ItemStack getItemBySlot(EquipmentSlot p_21467_) {
		if (WolfConfigs.COMMON.disableWolfArmor.get()) {
			return super.getItemBySlot(p_21467_);
		}

		switch (p_21467_.getType()) {
			case ARMOR:
				return this.inventory.getItem(0);
			default:
				return super.getItemBySlot(p_21467_);
		}
	}

	public void setItemSlot(EquipmentSlot p_21416_, ItemStack p_21417_) {
		this.verifyEquippedItem(p_21417_);
		switch (p_21416_.getType()) {
			case HAND:
				super.setItemSlot(p_21416_, p_21417_);
				break;
			case ARMOR:
				if (!WolfConfigs.COMMON.disableWolfArmor.get()) {

					this.inventory.setItem(0, p_21417_);
				}
		}
	}

	public boolean hasInventoryChanged(Container p_149512_) {
		return this.inventory != p_149512_;
	}

	@Override
	public SimpleContainer getContainer() {
		return this.inventory;
	}

	@Override
	public void containerChanged(Container p_18983_) {
		this.updateContainerEquipment();
		ItemStack itemstack = this.getArmor();
		if (this.tickCount > 20 && this.isArmor(itemstack)) {
			this.playSound(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
		}
	}

	public void setVariant(WolfTypes p_28929_) {
		this.entityData.set(DATA_TYPE, p_28929_.type);
	}


	public WolfTypes getVariant() {
		return WolfTypes.byType(this.entityData.get(DATA_TYPE));
	}

	@Inject(method = ("getBreedOffspring"), at = @At("RETURN"))
	public Wolf getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_, CallbackInfoReturnable<Wolf> ci) {
		Wolf wolf = ci.getReturnValue();
		((IWolfTypes) wolf).setVariant(getOffspringType((Wolf) p_146744_));
		return wolf;
	}

	private WolfTypes getOffspringType(Wolf p_28931_) {
		WolfTypes type = this.getVariant();
		WolfTypes type1 = ((IWolfTypes) p_28931_).getVariant();
		WolfTypes type2 = this.random.nextBoolean() ? type : type1;


		return type2;
	}
}
