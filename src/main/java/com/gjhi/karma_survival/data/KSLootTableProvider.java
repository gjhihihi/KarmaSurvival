package com.gjhi.karma_survival.data;

import com.gjhi.karma_survival.register.KSEntityTypes;
import com.gjhi.karma_survival.register.KSMobEffects;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class KSLootTableProvider extends EntityLootSubProvider {
    public KSLootTableProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.add(
                KSEntityTypes.ADJUDICATOR.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.BONE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                                )
                        ).withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.TIPPED_ARROW)
                                        .apply(SetPotionFunction.setPotion(KSMobEffects.CRIMINAL_P.get()))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                                )
                        )
        );
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                KSEntityTypes.ADJUDICATOR.get()
        );
    }
}
