package com.gjhi.karma_survival.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.gjhi.karma_survival.KarmaSurvival.MODID;

@SuppressWarnings("removal")
public class KSItemModelProvider extends ItemModelProvider {
    public KSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        this.getBuilder("item/adjudicator_spawn_egg").parent(new ModelFile.ExistingModelFile(new ResourceLocation("item/template_spawn_egg"), existingFileHelper));
    }
}
