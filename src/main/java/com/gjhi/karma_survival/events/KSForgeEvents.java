package com.gjhi.karma_survival.events;

import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.content.entity.KarmaSkeleton;
import com.gjhi.karma_survival.register.KSEntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = KarmaSurvival.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class KSForgeEvents {
    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof EntityHitResult hitResult) {
           if (hitResult.getEntity() instanceof LivingEntity living) {
               if (event.getProjectile() instanceof Arrow arrow) {
               }
           }
        }
    }
}
