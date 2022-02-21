package thepoultryman.pigeons.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.registry.ItemRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PigeonEntity extends TameableEntity implements IAnimatable, Flutterer {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<String> TYPE = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> ACCESSORY = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> IDLE = DataTracker.registerData(PigeonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final List<String> TYPES = List.of("city", "antwerp_smerle_brown", "antwerp_smerle_gray", "egyptian_swift");
    private static final HashMap<String, Item> TYPE_DROP_MAP = new HashMap<>();
    private static final List<Item> DROPS = List.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.RAW_IRON, Items.DIRT);
    private static final List<String> ACCESSORIES = List.of("none", "top_hat", "beanie");

    public PigeonEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
        TYPE_DROP_MAP.put(TYPES.get(0), Items.DIAMOND);
        TYPE_DROP_MAP.put(TYPES.get(1), Items.RAW_IRON);
        TYPE_DROP_MAP.put(TYPES.get(2), Items.RAW_COPPER);
        TYPE_DROP_MAP.put(TYPES.get(3), Items.BEEF);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1D, 30f, 7f, true));
        this.goalSelector.add(1, new FlyRandomly(this, 1D));
        this.goalSelector.add(2, new TemptGoal(this, 1.1D, Ingredient.fromTag(Pigeons.PIGEON_LOVE_FOODS), false));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1D));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(TYPE, TYPES.get(this.random.nextInt(4)));
        this.dataTracker.startTracking(ACCESSORY, ACCESSORIES.get(0));
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(IDLE, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isInAir() && event.isMoving() && !this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.fly", true));
            return PlayState.CONTINUE;
        } else if (!this.isInAir() && event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.walk", false));
            return PlayState.CONTINUE;
        } else if (this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.sit", true));
            return PlayState.CONTINUE;
        } else if (this.getIdle() > 0 && !this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pigeon.idle" + this.getIdle().toString(), false));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static DefaultAttributeContainer.Builder createPigeonAttributes() {
        return PigeonEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 7).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.65D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65f;
    }

    @Override
    protected void mobTick() {
        if (!this.moveControl.isMoving() && this.random.nextInt(100) == 0) {
            setIdle(this.random.nextInt(5));
        }

        int chance = 17000;
        if (this.world.isNight()) {
            chance = 5700;
        }

        if (!this.moveControl.isMoving() && this.isTamed() && this.random.nextInt(chance) == 0) {
            ItemStack spawnItem;

            if (TYPE_DROP_MAP.containsKey(this.getPigeonTypeString()) && this.random.nextInt(100) == 0) {
                spawnItem = new ItemStack(TYPE_DROP_MAP.get(this.getPigeonTypeString()));
            } else {
                spawnItem = new ItemStack(DROPS.get(this.random.nextInt(6)));
            }

            ItemScatterer.spawn(world, this.getX(), this.getY(), this.getZ(), spawnItem);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if (!this.isTamed() && this.isBreedingItem(stackInHand)) {
            if (stackInHand.isIn(Pigeons.PIGEON_LIKE_FOODS)) {
                if (stackInHand.getItem().isFood() && this.world.random.nextInt(Math.max(7 - Objects.requireNonNull(stackInHand.getItem().getFoodComponent()).getHunger(), 1)) == 0) {
                    this.world.sendEntityStatus(this, (byte) 7);
                    this.navigation.stop();
                    this.setOwner(player);
                } else if (this.world.random.nextInt(10) == 0) {
                    this.world.sendEntityStatus(this, (byte) 7);
                    this.navigation.stop();
                    this.setOwner(player);
                }
                stackInHand.decrement(1);
                return ActionResult.SUCCESS;
            } else if (this.random.nextInt(2) == 0) {
                this.world.sendEntityStatus(this, (byte) 7);
                this.navigation.stop();
                this.setOwner(player);
            }
        } else if (this.isOwner(player) && !this.isBreedingItem(stackInHand) && stackInHand.isEmpty()) {
            this.setSitting(!this.isSitting());
            this.jumping = false;
            this.navigation.stop();
            this.setIdle(0);
            return ActionResult.SUCCESS;
        } else if (ACCESSORIES.contains(stackInHand.getItem().toString()) && this.getAccessory().equals("none")) {
        	if(!this.world.isClient) {
        		this.setAccessory(stackInHand.copy());
        		stackInHand.decrement(1);
        	}
        	return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(Pigeons.PIGEON_LIKE_FOODS) || stack.isIn(Pigeons.PIGEON_LOVE_FOODS);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected boolean hasWings() {
        return true;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        PigeonEntity pigeonEntity = Pigeons.PIGEON_ENTITY_TYPE.create(world);
        if (pigeonEntity != null) {
            String type = "city";
            if (entity != null) {
                pigeonEntity.setOwnerUuid(this.getOwnerUuid());
                pigeonEntity.setTamed(true);
                pigeonEntity.setBaby(true);
                type = ((PigeonEntity) entity).getPigeonTypeString();
            }
            pigeonEntity.setPigeonType(type);
            pigeonEntity.setPersistent();
        }

        return pigeonEntity;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {

    }

    private void setPigeonType(String name) {
        if (TYPES.contains(name)) this.dataTracker.set(TYPE, name);
    }

    public String getPigeonTypeString() {
        return this.getDataTracker().get(TYPE);
    }

    @Override
    public void setSitting(boolean sitting) {
        dataTracker.set(SITTING, sitting);
    }

    @Override
    public boolean isSitting() {
        return dataTracker.get(SITTING);
    }

    public String getAccessory() {
    	return this.dataTracker.get(ACCESSORY);
    }

    public void setAccessory(ItemStack accessory) {
    	Item item = accessory.getItem();
    	if (item.equals(ItemRegistry.BEANIE) || item.equals(ItemRegistry.TOP_HAT)) {
    		this.dataTracker.set(ACCESSORY, item.toString());
    	}
    }

    public void setAccessoryFromString(String accessory) {
        switch (accessory) {
            case "top_hat" -> this.setAccessory(new ItemStack(ItemRegistry.TOP_HAT));
            case "beanie" -> this.setAccessory(new ItemStack(ItemRegistry.BEANIE));
        }
    }

    public void setIdle(int idle) {
        this.dataTracker.set(IDLE, idle);
    }

    public Integer getIdle() {
        return dataTracker.get(IDLE);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d velocity = this.getVelocity();
        if (!this.onGround && velocity.y < 0.0D) {
            this.setVelocity(velocity.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    public static boolean canSpawn(EntityType<PigeonEntity> pigeonEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return true;
    }

    private static class FlyRandomly extends FlyOntoTreeGoal {
        public FlyRandomly(PathAwareEntity pathAwareEntity, double d) {
            super(pathAwareEntity, d);
        }

        @Override
        public boolean canStart() {
            if (this.mob instanceof TameableEntity tameableEntity) {
                return !tameableEntity.isSitting() && super.canStart();
            } else {
                return false;
            }
        }

        @Nullable
        protected Vec3d getWanderTarget() {
            Vec3d wanderTarget = null;
            if (this.mob.isTouchingWater())
                wanderTarget = FuzzyTargeting.find(this.mob, 15, 15);
            else if (this.mob.getRandom().nextFloat() >= 0.1)
                wanderTarget = this.getMovePosition();

            return wanderTarget == null ? super.getWanderTarget() : wanderTarget;
        }

        private Vec3d getMovePosition() {
            BlockPos entityPos = this.mob.getBlockPos();
            BlockPos.Mutable newPos = new BlockPos.Mutable();

            newPos.setX(entityPos.getX() + this.mob.getRandom().nextInt(30) - 15);
            newPos.setY(entityPos.getY() + this.mob.getRandom().nextInt(30) - 15);
            newPos.setZ(entityPos.getZ() + this.mob.getRandom().nextInt(30) - 15);

            return Vec3d.ofBottomCenter(newPos);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString("PigeonType", this.getPigeonTypeString());
        nbt.putBoolean("Sitting", this.isSitting());
        nbt.putString("PigeonAccessory", this.getAccessory());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.contains("PigeonType"))
            this.setPigeonType(nbt.getString("PigeonType"));
        if (nbt.contains("Sitting"))
            this.setSitting(nbt.getBoolean("Sitting"));
        if (nbt.contains("PigeonAccessory"))
			this.setAccessoryFromString(nbt.getString("PigeonAccessory"));
    }
}
