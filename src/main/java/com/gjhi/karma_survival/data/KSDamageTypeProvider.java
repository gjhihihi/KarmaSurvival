package com.gjhi.karma_survival.data;

import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.register.KSDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class KSDamageTypeProvider {

    /** Registers this provider with the data generator */
    public static void register(RegistrySetBuilder builder) {
        builder.add(Registries.DAMAGE_TYPE, KSDamageTypes::bootstrap);
    }
}
