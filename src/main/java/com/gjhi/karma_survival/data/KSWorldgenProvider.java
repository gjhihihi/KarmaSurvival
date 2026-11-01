package com.gjhi.karma_survival.data;

import com.gjhi.karma_survival.register.KSBiomeModifiers;
import com.gjhi.karma_survival.register.KSEntityTypes;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.holdersets.AndHolderSet;
import net.minecraftforge.registries.holdersets.NotHolderSet;
import net.minecraftforge.registries.holdersets.OrHolderSet;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class KSWorldgenProvider {

    /** Registers this provider with the data generator */
    public static void register(RegistrySetBuilder builder) {
        builder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, KSWorldgenProvider::registerBiomeModifiers);
    }

    /** Registers all biome modifiers */
    private static void registerBiomeModifiers(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderSet<Biome> overworld = biomes.getOrThrow(BiomeTags.IS_OVERWORLD);
        HolderSet<Biome> nether = biomes.getOrThrow(BiomeTags.IS_NETHER);
        HolderSet<Biome> end = biomes.getOrThrow(BiomeTags.IS_END);
        HolderSet<Biome> spawnless = HolderSet.direct(
                biomes.getOrThrow(Biomes.DEEP_DARK),
                biomes.getOrThrow(Biomes.MUSHROOM_FIELDS)
        );
        // spawns
        context.register(KSBiomeModifiers.spawnAdjudicatorOverworld,
                new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                        and(not(spawnless), overworld),
                        List.of(new MobSpawnSettings.SpawnerData(KSEntityTypes.ADJUDICATOR.get(), 50, 3, 5))
                )
        );
        context.register(KSBiomeModifiers.spawnAdjudicatorNether,
                new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                        nether,
                        List.of(new MobSpawnSettings.SpawnerData(KSEntityTypes.ADJUDICATOR.get(), 30, 1, 3))
                )
        );
    }


    /* Helpers */

    /** Ands the holder sets together */
    @SafeVarargs
    private static <T> AndHolderSet<T> and(HolderSet<T>... sets) {
        return new AndHolderSet<>(List.of(sets));
    }

    /** Ors the holder sets together */
    @SafeVarargs
    private static <T> OrHolderSet<T> or(HolderSet<T>... sets) {
        return new OrHolderSet<>(List.of(sets));
    }

    /** Nots the set */
    private static <T> NotHolderSet<T> not(HolderSet<T> set) {
        // passing in null as its impossible to create the object Forge demands of us during datagen, and seems it work without it
        return new SerializableNotHolderSet<>(set);
    }

    private static class SerializableNotHolderSet<T> extends NotHolderSet<T> {
        public SerializableNotHolderSet(HolderSet<T> value) {
            super(null, value);
        }

        @Override
        public boolean canSerializeIn(HolderOwner<T> holderOwner) {
            return true;
        }
    }


    /* Configured features */

    /** Registers a configured feature */
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }

    /** Registers a configured feature */
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, Supplier<F> feature, FC config) {
        register(context, key, feature.get(), config);
    }



    /* Placed features */

    /** Registers a placed feature */
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?,?>> configured, PlacementModifier... placement) {
        context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configured), List.of(placement)));
    }

    /* Structure sets */

    /** Saves a structure set */
    private static StructureSet structureSet(int spacing, int separation, RandomSpreadType spreadType, int salt, float frequency, StructureSet.StructureSelectionEntry... structures) {
        return new StructureSet(List.of(structures), new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, frequency, salt, Optional.empty(), spacing, separation, spreadType));
    }

    /** Creates an entry for a registry object */
    private static StructureSet.StructureSelectionEntry entry(HolderGetter<Structure> structures, ResourceKey<Structure> structure, int weight) {
        return new StructureSet.StructureSelectionEntry(structures.getOrThrow(structure), weight);
    }


    /* Biome modifiers */

    /** Creates a spawn override for a single mob */
    private static Map<MobCategory, StructureSpawnOverride> monsterOverride(EntityType<?> entity, int min, int max) {
        return Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(entity, 1, min, max))));
    }
}
