package com.gjhi.karma_survival.content.mob_effect;

import com.gjhi.karma.library.caps.KarmaHelper;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class CriminalEffect extends InstantenousMobEffect {
    public CriminalEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF55FF);
    }

    @Override
    public void applyEffectTick(LivingEntity target, int amplifier) {
        if (!target.level().isClientSide) {
            int amount = 8 << amplifier;
            KarmaHelper.addKarma(target, amount);
        }
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity target, int amplifier, double effectiveness) {
        if (!target.level().isClientSide) {
            int amount = (int) Math.round(effectiveness * (8 << amplifier));
            KarmaHelper.addKarma(target, amount);
        }
    }
}
