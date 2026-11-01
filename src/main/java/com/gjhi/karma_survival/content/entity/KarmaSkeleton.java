package com.gjhi.karma_survival.content.entity;

import com.gjhi.karma.library.caps.KarmaHelper;
import com.gjhi.karma.register.KRAttributes;
import com.gjhi.karma_survival.register.KSMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class KarmaSkeleton extends Skeleton {
    public KarmaSkeleton(EntityType<? extends Skeleton> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0, true, 4, () -> false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractPiglin.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, true,
                entity -> !(
                        entity instanceof Player
                                || entity instanceof IronGolem
                                || entity instanceof Turtle
                                || entity instanceof AbstractVillager
                                || entity instanceof AbstractPiglin
                                || entity instanceof KarmaSkeleton
                                || entity.getPassengers().stream().anyMatch(target -> target instanceof KarmaSkeleton)
                )
        ));
        super.registerGoals();
    }

    protected void populateDefaultEquipmentSlots(RandomSource source, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(source, difficulty);
        super.populateDefaultEquipmentSlots(source, difficulty);
        switch (this.random.nextInt(2)) {
            case 0 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE));
            case 1 -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
    }

    @Override
    public boolean isFreezeConverting() {
        return false;
    }

    @Override
    protected AbstractArrow getArrow(ItemStack stack, float amount) {
        AbstractArrow abarrow = super.getArrow(stack, amount);
        if (abarrow instanceof Arrow arrow) {
            arrow.addEffect(new MobEffectInstance(KSMobEffects.CRIMINAL.get(), 1));
        }
        return abarrow;
    }

    @Override
    protected void doFreezeConversion() {
    }

    @Override
    public boolean canFreeze() {
        return !this.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                && !this.getItemBySlot(EquipmentSlot.HEAD).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
                && !this.getItemBySlot(EquipmentSlot.CHEST).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
                && !this.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.FREEZE_IMMUNE_WEARABLES)
                && !this.getItemBySlot(EquipmentSlot.FEET).is(ItemTags.FREEZE_IMMUNE_WEARABLES);
    }

    @Override
    public boolean isShaking() {
        return this.isFullyFrozen();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType type, @Nullable SpawnGroupData groupData, @Nullable CompoundTag nbt) {
        SpawnGroupData data = super.finalizeSpawn(accessor, difficulty, type, groupData, nbt);
        AttributeInstance attribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attribute != null) {
            attribute.setBaseValue(3);
        }
        attribute = this.getAttribute(KRAttributes.MAX_KARMA.get());
        if (attribute != null) {
            attribute.setBaseValue(24);
            KarmaHelper.setUsingSpecialMaxKarma(this);
        }
        attribute = this.getAttribute(KRAttributes.KARMA_ATTACK.get());
        if (attribute != null) {
            attribute.setBaseValue(4);
        }
        Level world = accessor.getLevel();
        if (this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.BOW)) {
            if (type.equals(MobSpawnType.SPAWN_EGG) || type.equals(MobSpawnType.NATURAL)) {
                CaveSpider spider = EntityType.CAVE_SPIDER.create(world);
                if (spider != null) {
                    AttributeInstance spiderAttr = spider.getAttribute(KRAttributes.MAX_KARMA.get());
                    if (spiderAttr != null) {
                        spiderAttr.setBaseValue(24);
                        KarmaHelper.setUsingSpecialMaxKarma(spider);
                    }
                    spider.moveTo(this.getX(), this.getY(), this.getZ());
                    world.addFreshEntity(spider);
                    this.startRiding(spider);
                }
            }
        }
        this.reassessWeaponGoal();
        return data;
    }
}
