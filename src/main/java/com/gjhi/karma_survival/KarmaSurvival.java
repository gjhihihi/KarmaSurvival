package com.gjhi.karma_survival;

import com.gjhi.karma_survival.data.*;
import com.gjhi.karma_survival.register.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("removal")
@Mod(KarmaSurvival.MODID)
public class KarmaSurvival {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "karma_survival";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public KarmaSurvival() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the Deferred Register to the mod event bus so items get registered
        KSItems.ITEMS.register(bus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        KSTabs.TABS.register(bus);
        KSEntityTypes.ENTITY_TYPES.register(bus);
        KSMobEffects.MOB_EFFECTS.register(bus);
        KSMobEffects.POTIONS.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        KSBiomeModifiers.init();

        // Register the item to a creative tab
        bus.addListener(this::setup);
        bus.addListener(this::addCreative);
        bus.addListener(this::gatherData);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, KSConfig.SPEC);
    }
    private void setup(FMLCommonSetupEvent event) {
        KSMobEffects.recipesInit();
    }


    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(KSItems.ADJUDICATOR_SPAWN_EGG);
        }
    }
    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        boolean server = event.includeServer();
        boolean client = event.includeClient();
        generator.addProvider(server, new KSDamageTypesTagProvider(output, registries, helper));
        generator.addProvider(client, new KSItemModelProvider(output, helper));
        generator.addProvider(server, new LootTableProvider(output, Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(KSLootTableProvider::new, LootContextParamSets.ENTITY)
                )
        ));

        RegistrySetBuilder builder = new RegistrySetBuilder();
        KSWorldgenProvider.register(builder);
        KSDamageTypeProvider.register(builder);
        DatapackBuiltinEntriesProvider generalProvider = new DatapackBuiltinEntriesProvider(output, registries, builder, Set.of(MODID));
        generator.addProvider(server, generalProvider);
    }
    public static ResourceLocation getResource(String id){
        return new ResourceLocation(MODID, id);
    }
    public static String prefix(String name) {
        return MODID + "." + name.toLowerCase(Locale.US);
    }
}
