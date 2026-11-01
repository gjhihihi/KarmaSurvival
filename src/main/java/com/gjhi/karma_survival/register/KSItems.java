package com.gjhi.karma_survival.register;

import com.gjhi.karma_survival.KarmaSurvival;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KSItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KarmaSurvival.MODID);

    public static final RegistryObject<Item> ADJUDICATOR_SPAWN_EGG = ITEMS.register("adjudicator_spawn_egg",
            () -> new ForgeSpawnEggItem(
                    KSEntityTypes.ADJUDICATOR,
                    0xC1C1C1,
                    0xFF55FF,
                    new Item.Properties()
            )
    );
}
