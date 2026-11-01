package com.gjhi.karma_survival.events;

import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.content.entity.KarmaSkeleton;
import com.gjhi.karma_survival.register.KSEntityTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = KarmaSurvival.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class KSModEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(
                KSEntityTypes.ADJUDICATOR.get(),
                KarmaSkeleton.createAttributes().build()
        );
    }
    @SubscribeEvent
    public static void onRegisterSpawn(SpawnPlacementRegisterEvent event) {
        event.register(
                KSEntityTypes.ADJUDICATOR.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }
}
