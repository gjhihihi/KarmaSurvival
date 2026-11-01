package com.gjhi.karma_survival.register;

import com.gjhi.karma_survival.KarmaSurvival;
import com.gjhi.karma_survival.content.mob_effect.CriminalEffect;
import com.gjhi.karma_survival.library.recipe.KSBrewingRecipe;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KSMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, KarmaSurvival.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, KarmaSurvival.MODID);
    private static void potionBrewing(ItemStack inputPot, Potion pot, Item item) {
        BrewingRecipeRegistry.addRecipe(new KSBrewingRecipe(inputPot, Ingredient.of(item), PotionUtils.setPotion(new ItemStack(Items.POTION), pot)));
    }

    public static RegistryObject<MobEffect> CRIMINAL = MOB_EFFECTS.register("criminal", CriminalEffect::new);
    public static RegistryObject<Potion> CRIMINAL_P = POTIONS.register("criminal_potion", () -> new Potion(new MobEffectInstance(CRIMINAL.get())));
    public static RegistryObject<Potion> CRIMINAL_PS = POTIONS.register("criminal_potion_strong", () -> new Potion(new MobEffectInstance(CRIMINAL.get(), 0, 1)));

    public static void recipesInit() {
        potionBrewing(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), CRIMINAL_P.get(), Items.BONE);
        potionBrewing(PotionUtils.setPotion(new ItemStack(Items.POTION), CRIMINAL_P.get()), CRIMINAL_PS.get(), Items.GLOWSTONE_DUST);
    }
}
