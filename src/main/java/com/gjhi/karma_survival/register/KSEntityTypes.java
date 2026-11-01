package com.gjhi.karma_survival.register;

import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.content.entity.KarmaSkeleton;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class KSEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, KarmaSurvival.MODID);


    public static final RegistryObject<EntityType<KarmaSkeleton>> ADJUDICATOR = ENTITY_TYPES.register(
            "adjudicator", () -> EntityType.Builder
                    .of(KarmaSkeleton::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F).clientTrackingRange(8)
                    .build(KarmaSurvival.getResource("adjudicator").toString())
    );
}
