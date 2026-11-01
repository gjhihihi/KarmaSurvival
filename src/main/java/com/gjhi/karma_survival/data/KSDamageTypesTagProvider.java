package com.gjhi.karma_survival.data;

import com.gjhi.karma.register.KRTags;
import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.register.KSDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class KSDamageTypesTagProvider extends TagsProvider<DamageType> {
    public KSDamageTypesTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, provider, KarmaSurvival.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }
}
