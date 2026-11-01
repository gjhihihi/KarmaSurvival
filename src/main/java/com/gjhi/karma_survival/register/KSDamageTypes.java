package com.gjhi.karma_survival.register;

import com.gjhi.karma.Karma;
import com.gjhi.karma_survival.KarmaSurvival;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class KSDamageTypes {
    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, KarmaSurvival.getResource(name));
    }
    public static void bootstrap(BootstapContext<DamageType> context) {
        /*context.register(
                KARMA_SKELETON,
                new DamageType("mob", 0.1f)
        );*/
    }
}
