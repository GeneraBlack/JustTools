package com.justtools.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final Tier COPPER = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            190,
            5.0F,
            1.5F,
            13,
            () -> Ingredient.of(Items.COPPER_INGOT)
    );

    public static final Tier HARDENED_WOOD = new SimpleTier(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            180,
            3.5F,
            0.5F,
            16,
            () -> Ingredient.of(ItemTags.PLANKS)
    );

    public static final Tier HARDENED_STONE = new SimpleTier(
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            390,
            5.0F,
            1.5F,
            8,
            () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)
    );

    public static final Tier HARDENED_COPPER = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            570,
            6.0F,
            2.0F,
            15,
            () -> Ingredient.of(Items.COPPER_INGOT)
    );

    public static final Tier HARDENED_IRON = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            750,
            7.0F,
            2.5F,
            16,
            () -> Ingredient.of(Items.IRON_INGOT)
    );

    public static final Tier HARDENED_GOLD = new SimpleTier(
            BlockTags.INCORRECT_FOR_GOLD_TOOL,
            150,
            13.0F,
            1.0F,
            24,
            () -> Ingredient.of(Items.GOLD_INGOT)
    );

    public static final Tier HARDENED_DIAMOND = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            4000,
            9.0F,
            3.5F,
            12,
            () -> Ingredient.of(Items.DIAMOND)
    );

    public static final Tier HARDENED_NETHERITE = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            5500,
            10.5F,
            4.5F,
            18,
            () -> Ingredient.of(Items.NETHERITE_INGOT)
    );
}
