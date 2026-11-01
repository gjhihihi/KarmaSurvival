package com.gjhi.karma_survival.events;

import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.register.KSEntityTypes;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = KarmaSurvival.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class KSClientModEvents {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                KSEntityTypes.ADJUDICATOR.get(),
                SkeletonRenderer::new
        );
    }
}
