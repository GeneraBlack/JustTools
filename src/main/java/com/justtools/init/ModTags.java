package com.justtools.init;

import com.justtools.JustTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MINEABLE_WITH_PAXEL = create("mineable/paxel");
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = create("incorrect_for_copper_tool");

        private static TagKey<Block> create(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(JustTools.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> HAMMERS = create("hammers");
        public static final TagKey<Item> EXCAVATORS = create("excavators");
        public static final TagKey<Item> PAXELS = create("paxels");
        public static final TagKey<Item> HARDENED_TOOLS = create("hardened_tools");

        private static TagKey<Item> create(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(JustTools.MODID, name));
        }
    }
}
