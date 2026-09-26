package com.justtools.init;

import com.justtools.JustTools;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, JustTools.MODID);

    // --- Base Tiers ---
    public static final Holder<ArmorMaterial> COPPER = register(
            "copper",
            1, 4, 5, 2,
            13,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.COPPER_INGOT),
            0.0F, 0.0F
    );

    public static final Holder<ArmorMaterial> DEEPSLATE = register(
            "deepslate",
            2, 5, 6, 2,
            8,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.COBBLED_DEEPSLATE),
            1.0F, 0.05F
    );

    // --- Hardened Tiers ---
    public static final Holder<ArmorMaterial> HARDENED_LEATHER = register(
            "hardened_leather",
            2, 3, 4, 2,
            18,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Items.LEATHER),
            1.0F, 0.0F
    );

    public static final Holder<ArmorMaterial> HARDENED_COPPER = register(
            "hardened_copper",
            2, 5, 6, 3,
            16,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.COPPER_INGOT),
            1.0F, 0.0F
    );

    public static final Holder<ArmorMaterial> HARDENED_DEEPSLATE = register(
            "hardened_deepslate",
            3, 6, 7, 3,
            12,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.COBBLED_DEEPSLATE),
            2.0F, 0.1F
    );

    public static final Holder<ArmorMaterial> HARDENED_CHAINMAIL = register(
            "hardened_chainmail",
            2, 5, 6, 3,
            15,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            () -> Ingredient.of(Items.IRON_INGOT),
            1.0F, 0.0F
    );

    public static final Holder<ArmorMaterial> HARDENED_IRON = register(
            "hardened_iron",
            3, 6, 7, 3,
            16,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.IRON_INGOT),
            1.5F, 0.05F
    );

    public static final Holder<ArmorMaterial> HARDENED_GOLD = register(
            "hardened_gold",
            2, 4, 6, 3,
            26,
            SoundEvents.ARMOR_EQUIP_GOLD,
            () -> Ingredient.of(Items.GOLD_INGOT),
            1.0F, 0.0F
    );

    public static final Holder<ArmorMaterial> HARDENED_DIAMOND = register(
            "hardened_diamond",
            4, 7, 9, 4,
            14,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(Items.DIAMOND),
            3.0F, 0.05F
    );

    public static final Holder<ArmorMaterial> HARDENED_NETHERITE = register(
            "hardened_netherite",
            4, 7, 9, 4,
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(Items.NETHERITE_INGOT),
            4.0F, 0.15F
    );

    private static Holder<ArmorMaterial> register(
            String name,
            int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            Supplier<Ingredient> repairIngredient,
            float toughness,
            float knockbackResistance
    ) {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, bootsDefense);
        defense.put(ArmorItem.Type.LEGGINGS, leggingsDefense);
        defense.put(ArmorItem.Type.CHESTPLATE, chestplateDefense);
        defense.put(ArmorItem.Type.HELMET, helmetDefense);

        ResourceLocation assetName = ResourceLocation.fromNamespaceAndPath(JustTools.MODID, name);
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(assetName));

        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                defense, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockbackResistance
        ));
    }
}
