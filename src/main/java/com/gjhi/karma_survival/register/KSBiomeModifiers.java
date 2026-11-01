package com.gjhi.karma_survival.register;

import com.gjhi.karma_survival.KarmaSurvival;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class KSBiomeModifiers {
    public static void init(){}
    public static final ResourceKey<BiomeModifier> spawnAdjudicatorOverworld = key(ForgeRegistries.Keys.BIOME_MODIFIERS, "spawn_adjudicator_overworld");
    public static final ResourceKey<BiomeModifier> spawnAdjudicatorNether = key(ForgeRegistries.Keys.BIOME_MODIFIERS, "spawn_adjudicator_nether");
    protected static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, KarmaSurvival.getResource(name));
    }
}
