package com.justtools.init;

import com.justtools.JustTools;
import com.justtools.item.ExcavatorItem;
import com.justtools.item.HammerItem;
import com.justtools.item.ModArmorItem;
import com.justtools.item.ModBowItem;
import com.justtools.item.ModCrossbowItem;
import com.justtools.item.PaxelItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JustTools.MODID);

    // Hardening Station BlockItem & Materials
    public static final DeferredItem<BlockItem> HARDENING_STATION = ITEMS.registerSimpleBlockItem("hardening_station", ModBlocks.HARDENING_STATION);
    public static final DeferredItem<Item> HARDENING_PLATE = ITEMS.registerItem("hardening_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> DEPTH_PLATE = ITEMS.registerItem("depth_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ECHO_PLATE = ITEMS.registerItem("echo_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> LAVA_PLATE = ITEMS.registerItem("lava_plate", Item::new, new Item.Properties().fireResistant());
    public static final DeferredItem<Item> OVERCLOCK_PLATE = ITEMS.registerItem("overclock_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> BREEZE_PLATE = ITEMS.registerItem("breeze_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> AUTO_SMELT_PLATE = ITEMS.registerItem("auto_smelt_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> REINFORCED_PLATE = ITEMS.registerItem("reinforced_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> AMETHYST_PLATE = ITEMS.registerItem("amethyst_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MOSS_PLATE = ITEMS.registerItem("moss_plate", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MAGNETIC_PLATE = ITEMS.registerItem("magnetic_plate", Item::new, new Item.Properties());

    // --- Copper Tools ---
    public static final DeferredItem<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModTiers.COPPER, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.COPPER, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModTiers.COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.COPPER, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModTiers.COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.COPPER, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModTiers.COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.COPPER, 6.0F, -3.1F))));
    public static final DeferredItem<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModTiers.COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.COPPER, -2.0F, -1.0F))));

    // --- Deepslate Tools ---
    public static final DeferredItem<SwordItem> DEEPSLATE_SWORD = ITEMS.register("deepslate_sword",
            () -> new SwordItem(ModTiers.DEEPSLATE, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.DEEPSLATE, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> DEEPSLATE_SHOVEL = ITEMS.register("deepslate_shovel",
            () -> new ShovelItem(ModTiers.DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.DEEPSLATE, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> DEEPSLATE_PICKAXE = ITEMS.register("deepslate_pickaxe",
            () -> new PickaxeItem(ModTiers.DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.DEEPSLATE, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> DEEPSLATE_AXE = ITEMS.register("deepslate_axe",
            () -> new AxeItem(ModTiers.DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.DEEPSLATE, 7.0F, -3.2F))));
    public static final DeferredItem<HoeItem> DEEPSLATE_HOE = ITEMS.register("deepslate_hoe",
            () -> new HoeItem(ModTiers.DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.DEEPSLATE, -1.0F, -2.0F))));

    // --- Hammers (Base) ---
    public static final DeferredItem<HammerItem> WOODEN_HAMMER = ITEMS.register("wooden_hammer",
            () -> new HammerItem(Tiers.WOOD, 200));
    public static final DeferredItem<HammerItem> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new HammerItem(Tiers.STONE, 400));
    public static final DeferredItem<HammerItem> DEEPSLATE_HAMMER = ITEMS.register("deepslate_hammer",
            () -> new HammerItem(ModTiers.DEEPSLATE, 650));
    public static final DeferredItem<HammerItem> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new HammerItem(ModTiers.COPPER, 600));
    public static final DeferredItem<HammerItem> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(Tiers.IRON, 800));
    public static final DeferredItem<HammerItem> GOLDEN_HAMMER = ITEMS.register("golden_hammer",
            () -> new HammerItem(Tiers.GOLD, 150));
    public static final DeferredItem<HammerItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(Tiers.DIAMOND, 3500));
    public static final DeferredItem<HammerItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
            () -> new HammerItem(Tiers.NETHERITE, 5000, new Item.Properties().fireResistant()));

    // --- Excavators (Base) ---
    public static final DeferredItem<ExcavatorItem> WOODEN_EXCAVATOR = ITEMS.register("wooden_excavator",
            () -> new ExcavatorItem(Tiers.WOOD, 200));
    public static final DeferredItem<ExcavatorItem> STONE_EXCAVATOR = ITEMS.register("stone_excavator",
            () -> new ExcavatorItem(Tiers.STONE, 400));
    public static final DeferredItem<ExcavatorItem> DEEPSLATE_EXCAVATOR = ITEMS.register("deepslate_excavator",
            () -> new ExcavatorItem(ModTiers.DEEPSLATE, 650));
    public static final DeferredItem<ExcavatorItem> COPPER_EXCAVATOR = ITEMS.register("copper_excavator",
            () -> new ExcavatorItem(ModTiers.COPPER, 600));
    public static final DeferredItem<ExcavatorItem> IRON_EXCAVATOR = ITEMS.register("iron_excavator",
            () -> new ExcavatorItem(Tiers.IRON, 800));
    public static final DeferredItem<ExcavatorItem> GOLDEN_EXCAVATOR = ITEMS.register("golden_excavator",
            () -> new ExcavatorItem(Tiers.GOLD, 150));
    public static final DeferredItem<ExcavatorItem> DIAMOND_EXCAVATOR = ITEMS.register("diamond_excavator",
            () -> new ExcavatorItem(Tiers.DIAMOND, 3500));
    public static final DeferredItem<ExcavatorItem> NETHERITE_EXCAVATOR = ITEMS.register("netherite_excavator",
            () -> new ExcavatorItem(Tiers.NETHERITE, 5000, new Item.Properties().fireResistant()));

    // --- Paxels (Base) ---
    public static final DeferredItem<PaxelItem> WOODEN_PAXEL = ITEMS.register("wooden_paxel",
            () -> new PaxelItem(Tiers.WOOD, 160));
    public static final DeferredItem<PaxelItem> STONE_PAXEL = ITEMS.register("stone_paxel",
            () -> new PaxelItem(Tiers.STONE, 350));
    public static final DeferredItem<PaxelItem> DEEPSLATE_PAXEL = ITEMS.register("deepslate_paxel",
            () -> new PaxelItem(ModTiers.DEEPSLATE, 550));
    public static final DeferredItem<PaxelItem> COPPER_PAXEL = ITEMS.register("copper_paxel",
            () -> new PaxelItem(ModTiers.COPPER, 500));
    public static final DeferredItem<PaxelItem> IRON_PAXEL = ITEMS.register("iron_paxel",
            () -> new PaxelItem(Tiers.IRON, 650));
    public static final DeferredItem<PaxelItem> GOLDEN_PAXEL = ITEMS.register("golden_paxel",
            () -> new PaxelItem(Tiers.GOLD, 100));
    public static final DeferredItem<PaxelItem> DIAMOND_PAXEL = ITEMS.register("diamond_paxel",
            () -> new PaxelItem(Tiers.DIAMOND, 3000));
    public static final DeferredItem<PaxelItem> NETHERITE_PAXEL = ITEMS.register("netherite_paxel",
            () -> new PaxelItem(Tiers.NETHERITE, 4500, new Item.Properties().fireResistant()));

    // --- Hardened Wood ---
    public static final DeferredItem<SwordItem> HARDENED_WOODEN_SWORD = ITEMS.register("hardened_wooden_sword",
            () -> new SwordItem(ModTiers.HARDENED_WOOD, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_WOOD, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_WOODEN_SHOVEL = ITEMS.register("hardened_wooden_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_WOOD, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_WOODEN_PICKAXE = ITEMS.register("hardened_wooden_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_WOOD, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_WOODEN_AXE = ITEMS.register("hardened_wooden_axe",
            () -> new AxeItem(ModTiers.HARDENED_WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_WOOD, 6.0F, -3.2F))));
    public static final DeferredItem<HoeItem> HARDENED_WOODEN_HOE = ITEMS.register("hardened_wooden_hoe",
            () -> new HoeItem(ModTiers.HARDENED_WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_WOOD, 0.0F, -3.0F))));

    // --- Hardened Stone ---
    public static final DeferredItem<SwordItem> HARDENED_STONE_SWORD = ITEMS.register("hardened_stone_sword",
            () -> new SwordItem(ModTiers.HARDENED_STONE, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_STONE, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_STONE_SHOVEL = ITEMS.register("hardened_stone_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_STONE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_STONE, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_STONE_PICKAXE = ITEMS.register("hardened_stone_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_STONE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_STONE, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_STONE_AXE = ITEMS.register("hardened_stone_axe",
            () -> new AxeItem(ModTiers.HARDENED_STONE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_STONE, 7.0F, -3.2F))));
    public static final DeferredItem<HoeItem> HARDENED_STONE_HOE = ITEMS.register("hardened_stone_hoe",
            () -> new HoeItem(ModTiers.HARDENED_STONE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_STONE, -1.0F, -2.0F))));

    // --- Hardened Deepslate ---
    public static final DeferredItem<SwordItem> HARDENED_DEEPSLATE_SWORD = ITEMS.register("hardened_deepslate_sword",
            () -> new SwordItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_DEEPSLATE, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_DEEPSLATE_SHOVEL = ITEMS.register("hardened_deepslate_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DEEPSLATE, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_DEEPSLATE_PICKAXE = ITEMS.register("hardened_deepslate_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DEEPSLATE, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_DEEPSLATE_AXE = ITEMS.register("hardened_deepslate_axe",
            () -> new AxeItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DEEPSLATE, 7.0F, -3.2F))));
    public static final DeferredItem<HoeItem> HARDENED_DEEPSLATE_HOE = ITEMS.register("hardened_deepslate_hoe",
            () -> new HoeItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DEEPSLATE, -1.0F, -2.0F))));

    // --- Hardened Copper ---
    public static final DeferredItem<SwordItem> HARDENED_COPPER_SWORD = ITEMS.register("hardened_copper_sword",
            () -> new SwordItem(ModTiers.HARDENED_COPPER, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_COPPER, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_COPPER_SHOVEL = ITEMS.register("hardened_copper_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_COPPER, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_COPPER_PICKAXE = ITEMS.register("hardened_copper_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_COPPER, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_COPPER_AXE = ITEMS.register("hardened_copper_axe",
            () -> new AxeItem(ModTiers.HARDENED_COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_COPPER, 6.0F, -3.1F))));
    public static final DeferredItem<HoeItem> HARDENED_COPPER_HOE = ITEMS.register("hardened_copper_hoe",
            () -> new HoeItem(ModTiers.HARDENED_COPPER, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_COPPER, -2.0F, -1.0F))));

    // --- Hardened Iron ---
    public static final DeferredItem<SwordItem> HARDENED_IRON_SWORD = ITEMS.register("hardened_iron_sword",
            () -> new SwordItem(ModTiers.HARDENED_IRON, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_IRON, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_IRON_SHOVEL = ITEMS.register("hardened_iron_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_IRON, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_IRON, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_IRON_PICKAXE = ITEMS.register("hardened_iron_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_IRON, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_IRON, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_IRON_AXE = ITEMS.register("hardened_iron_axe",
            () -> new AxeItem(ModTiers.HARDENED_IRON, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_IRON, 6.0F, -3.1F))));
    public static final DeferredItem<HoeItem> HARDENED_IRON_HOE = ITEMS.register("hardened_iron_hoe",
            () -> new HoeItem(ModTiers.HARDENED_IRON, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_IRON, -2.0F, -1.0F))));

    // --- Hardened Gold ---
    public static final DeferredItem<SwordItem> HARDENED_GOLDEN_SWORD = ITEMS.register("hardened_golden_sword",
            () -> new SwordItem(ModTiers.HARDENED_GOLD, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_GOLD, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_GOLDEN_SHOVEL = ITEMS.register("hardened_golden_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_GOLD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_GOLD, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_GOLDEN_PICKAXE = ITEMS.register("hardened_golden_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_GOLD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_GOLD, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_GOLDEN_AXE = ITEMS.register("hardened_golden_axe",
            () -> new AxeItem(ModTiers.HARDENED_GOLD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_GOLD, 6.0F, -3.0F))));
    public static final DeferredItem<HoeItem> HARDENED_GOLDEN_HOE = ITEMS.register("hardened_golden_hoe",
            () -> new HoeItem(ModTiers.HARDENED_GOLD, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_GOLD, 0.0F, -3.0F))));

    // --- Hardened Diamond ---
    public static final DeferredItem<SwordItem> HARDENED_DIAMOND_SWORD = ITEMS.register("hardened_diamond_sword",
            () -> new SwordItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.HARDENED_DIAMOND, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_DIAMOND_SHOVEL = ITEMS.register("hardened_diamond_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DIAMOND, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_DIAMOND_PICKAXE = ITEMS.register("hardened_diamond_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DIAMOND, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_DIAMOND_AXE = ITEMS.register("hardened_diamond_axe",
            () -> new AxeItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DIAMOND, 5.0F, -3.0F))));
    public static final DeferredItem<HoeItem> HARDENED_DIAMOND_HOE = ITEMS.register("hardened_diamond_hoe",
            () -> new HoeItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_DIAMOND, -3.0F, 0.0F))));

    // --- Hardened Netherite ---
    public static final DeferredItem<SwordItem> HARDENED_NETHERITE_SWORD = ITEMS.register("hardened_netherite_sword",
            () -> new SwordItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant().attributes(SwordItem.createAttributes(ModTiers.HARDENED_NETHERITE, 3, -2.4F))));
    public static final DeferredItem<ShovelItem> HARDENED_NETHERITE_SHOVEL = ITEMS.register("hardened_netherite_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_NETHERITE, 1.5F, -3.0F))));
    public static final DeferredItem<PickaxeItem> HARDENED_NETHERITE_PICKAXE = ITEMS.register("hardened_netherite_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_NETHERITE, 1.0F, -2.8F))));
    public static final DeferredItem<AxeItem> HARDENED_NETHERITE_AXE = ITEMS.register("hardened_netherite_axe",
            () -> new AxeItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_NETHERITE, 5.0F, -3.0F))));
    public static final DeferredItem<HoeItem> HARDENED_NETHERITE_HOE = ITEMS.register("hardened_netherite_hoe",
            () -> new HoeItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant().attributes(DiggerItem.createAttributes(ModTiers.HARDENED_NETHERITE, -4.0F, 0.0F))));

    // --- Hardened Hammers ---
    public static final DeferredItem<HammerItem> HARDENED_WOODEN_HAMMER = ITEMS.register("hardened_wooden_hammer",
            () -> new HammerItem(ModTiers.HARDENED_WOOD, 600));
    public static final DeferredItem<HammerItem> HARDENED_STONE_HAMMER = ITEMS.register("hardened_stone_hammer",
            () -> new HammerItem(ModTiers.HARDENED_STONE, 1200));
    public static final DeferredItem<HammerItem> HARDENED_DEEPSLATE_HAMMER = ITEMS.register("hardened_deepslate_hammer",
            () -> new HammerItem(ModTiers.HARDENED_DEEPSLATE, 1600));
    public static final DeferredItem<HammerItem> HARDENED_COPPER_HAMMER = ITEMS.register("hardened_copper_hammer",
            () -> new HammerItem(ModTiers.HARDENED_COPPER, 1800));
    public static final DeferredItem<HammerItem> HARDENED_IRON_HAMMER = ITEMS.register("hardened_iron_hammer",
            () -> new HammerItem(ModTiers.HARDENED_IRON, 2400));
    public static final DeferredItem<HammerItem> HARDENED_GOLDEN_HAMMER = ITEMS.register("hardened_golden_hammer",
            () -> new HammerItem(ModTiers.HARDENED_GOLD, 500));
    public static final DeferredItem<HammerItem> HARDENED_DIAMOND_HAMMER = ITEMS.register("hardened_diamond_hammer",
            () -> new HammerItem(ModTiers.HARDENED_DIAMOND, 9000));
    public static final DeferredItem<HammerItem> HARDENED_NETHERITE_HAMMER = ITEMS.register("hardened_netherite_hammer",
            () -> new HammerItem(ModTiers.HARDENED_NETHERITE, 13000, new Item.Properties().fireResistant()));

    // --- Hardened Excavators ---
    public static final DeferredItem<ExcavatorItem> HARDENED_WOODEN_EXCAVATOR = ITEMS.register("hardened_wooden_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_WOOD, 600));
    public static final DeferredItem<ExcavatorItem> HARDENED_STONE_EXCAVATOR = ITEMS.register("hardened_stone_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_STONE, 1200));
    public static final DeferredItem<ExcavatorItem> HARDENED_DEEPSLATE_EXCAVATOR = ITEMS.register("hardened_deepslate_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_DEEPSLATE, 1600));
    public static final DeferredItem<ExcavatorItem> HARDENED_COPPER_EXCAVATOR = ITEMS.register("hardened_copper_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_COPPER, 1800));
    public static final DeferredItem<ExcavatorItem> HARDENED_IRON_EXCAVATOR = ITEMS.register("hardened_iron_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_IRON, 2400));
    public static final DeferredItem<ExcavatorItem> HARDENED_GOLDEN_EXCAVATOR = ITEMS.register("hardened_golden_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_GOLD, 500));
    public static final DeferredItem<ExcavatorItem> HARDENED_DIAMOND_EXCAVATOR = ITEMS.register("hardened_diamond_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_DIAMOND, 9000));
    public static final DeferredItem<ExcavatorItem> HARDENED_NETHERITE_EXCAVATOR = ITEMS.register("hardened_netherite_excavator",
            () -> new ExcavatorItem(ModTiers.HARDENED_NETHERITE, 13000, new Item.Properties().fireResistant()));

    // --- Hardened Paxels ---
    public static final DeferredItem<PaxelItem> HARDENED_WOODEN_PAXEL = ITEMS.register("hardened_wooden_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_WOOD, 480));
    public static final DeferredItem<PaxelItem> HARDENED_STONE_PAXEL = ITEMS.register("hardened_stone_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_STONE, 1000));
    public static final DeferredItem<PaxelItem> HARDENED_DEEPSLATE_PAXEL = ITEMS.register("hardened_deepslate_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_DEEPSLATE, 1400));
    public static final DeferredItem<PaxelItem> HARDENED_COPPER_PAXEL = ITEMS.register("hardened_copper_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_COPPER, 1500));
    public static final DeferredItem<PaxelItem> HARDENED_IRON_PAXEL = ITEMS.register("hardened_iron_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_IRON, 1950));
    public static final DeferredItem<PaxelItem> HARDENED_GOLDEN_PAXEL = ITEMS.register("hardened_golden_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_GOLD, 350));
    public static final DeferredItem<PaxelItem> HARDENED_DIAMOND_PAXEL = ITEMS.register("hardened_diamond_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_DIAMOND, 7500));
    public static final DeferredItem<PaxelItem> HARDENED_NETHERITE_PAXEL = ITEMS.register("hardened_netherite_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_NETHERITE, 11000, new Item.Properties().fireResistant()));

    // --- Helper methods for armor ---
    private static DeferredItem<ModArmorItem> registerArmor(String name, net.minecraft.core.Holder<ArmorMaterial> material, ArmorItem.Type type, int multiplier) {
        return ITEMS.register(name, () -> new ModArmorItem(material, type, new Item.Properties().durability(type.getDurability(multiplier))));
    }

    private static DeferredItem<ModArmorItem> registerFireResistantArmor(String name, net.minecraft.core.Holder<ArmorMaterial> material, ArmorItem.Type type, int multiplier) {
        return ITEMS.register(name, () -> new ModArmorItem(material, type, new Item.Properties().durability(type.getDurability(multiplier)).fireResistant()));
    }

    // --- Base Armor: Copper ---
    public static final DeferredItem<ModArmorItem> COPPER_HELMET = registerArmor("copper_helmet", ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, 11);
    public static final DeferredItem<ModArmorItem> COPPER_CHESTPLATE = registerArmor("copper_chestplate", ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, 11);
    public static final DeferredItem<ModArmorItem> COPPER_LEGGINGS = registerArmor("copper_leggings", ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, 11);
    public static final DeferredItem<ModArmorItem> COPPER_BOOTS = registerArmor("copper_boots", ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, 11);

    // --- Base Armor: Deepslate ---
    public static final DeferredItem<ModArmorItem> DEEPSLATE_HELMET = registerArmor("deepslate_helmet", ModArmorMaterials.DEEPSLATE, ArmorItem.Type.HELMET, 18);
    public static final DeferredItem<ModArmorItem> DEEPSLATE_CHESTPLATE = registerArmor("deepslate_chestplate", ModArmorMaterials.DEEPSLATE, ArmorItem.Type.CHESTPLATE, 18);
    public static final DeferredItem<ModArmorItem> DEEPSLATE_LEGGINGS = registerArmor("deepslate_leggings", ModArmorMaterials.DEEPSLATE, ArmorItem.Type.LEGGINGS, 18);
    public static final DeferredItem<ModArmorItem> DEEPSLATE_BOOTS = registerArmor("deepslate_boots", ModArmorMaterials.DEEPSLATE, ArmorItem.Type.BOOTS, 18);

    // --- Hardened Armor: Leather ---
    public static final DeferredItem<ModArmorItem> HARDENED_LEATHER_HELMET = registerArmor("hardened_leather_helmet", ModArmorMaterials.HARDENED_LEATHER, ArmorItem.Type.HELMET, 15);
    public static final DeferredItem<ModArmorItem> HARDENED_LEATHER_CHESTPLATE = registerArmor("hardened_leather_chestplate", ModArmorMaterials.HARDENED_LEATHER, ArmorItem.Type.CHESTPLATE, 15);
    public static final DeferredItem<ModArmorItem> HARDENED_LEATHER_LEGGINGS = registerArmor("hardened_leather_leggings", ModArmorMaterials.HARDENED_LEATHER, ArmorItem.Type.LEGGINGS, 15);
    public static final DeferredItem<ModArmorItem> HARDENED_LEATHER_BOOTS = registerArmor("hardened_leather_boots", ModArmorMaterials.HARDENED_LEATHER, ArmorItem.Type.BOOTS, 15);

    // --- Hardened Armor: Copper ---
    public static final DeferredItem<ModArmorItem> HARDENED_COPPER_HELMET = registerArmor("hardened_copper_helmet", ModArmorMaterials.HARDENED_COPPER, ArmorItem.Type.HELMET, 30);
    public static final DeferredItem<ModArmorItem> HARDENED_COPPER_CHESTPLATE = registerArmor("hardened_copper_chestplate", ModArmorMaterials.HARDENED_COPPER, ArmorItem.Type.CHESTPLATE, 30);
    public static final DeferredItem<ModArmorItem> HARDENED_COPPER_LEGGINGS = registerArmor("hardened_copper_leggings", ModArmorMaterials.HARDENED_COPPER, ArmorItem.Type.LEGGINGS, 30);
    public static final DeferredItem<ModArmorItem> HARDENED_COPPER_BOOTS = registerArmor("hardened_copper_boots", ModArmorMaterials.HARDENED_COPPER, ArmorItem.Type.BOOTS, 30);

    // --- Hardened Armor: Deepslate ---
    public static final DeferredItem<ModArmorItem> HARDENED_DEEPSLATE_HELMET = registerArmor("hardened_deepslate_helmet", ModArmorMaterials.HARDENED_DEEPSLATE, ArmorItem.Type.HELMET, 46);
    public static final DeferredItem<ModArmorItem> HARDENED_DEEPSLATE_CHESTPLATE = registerArmor("hardened_deepslate_chestplate", ModArmorMaterials.HARDENED_DEEPSLATE, ArmorItem.Type.CHESTPLATE, 46);
    public static final DeferredItem<ModArmorItem> HARDENED_DEEPSLATE_LEGGINGS = registerArmor("hardened_deepslate_leggings", ModArmorMaterials.HARDENED_DEEPSLATE, ArmorItem.Type.LEGGINGS, 46);
    public static final DeferredItem<ModArmorItem> HARDENED_DEEPSLATE_BOOTS = registerArmor("hardened_deepslate_boots", ModArmorMaterials.HARDENED_DEEPSLATE, ArmorItem.Type.BOOTS, 46);

    // --- Hardened Armor: Chainmail ---
    public static final DeferredItem<ModArmorItem> HARDENED_CHAINMAIL_HELMET = registerArmor("hardened_chainmail_helmet", ModArmorMaterials.HARDENED_CHAINMAIL, ArmorItem.Type.HELMET, 38);
    public static final DeferredItem<ModArmorItem> HARDENED_CHAINMAIL_CHESTPLATE = registerArmor("hardened_chainmail_chestplate", ModArmorMaterials.HARDENED_CHAINMAIL, ArmorItem.Type.CHESTPLATE, 38);
    public static final DeferredItem<ModArmorItem> HARDENED_CHAINMAIL_LEGGINGS = registerArmor("hardened_chainmail_leggings", ModArmorMaterials.HARDENED_CHAINMAIL, ArmorItem.Type.LEGGINGS, 38);
    public static final DeferredItem<ModArmorItem> HARDENED_CHAINMAIL_BOOTS = registerArmor("hardened_chainmail_boots", ModArmorMaterials.HARDENED_CHAINMAIL, ArmorItem.Type.BOOTS, 38);

    // --- Hardened Armor: Iron ---
    public static final DeferredItem<ModArmorItem> HARDENED_IRON_HELMET = registerArmor("hardened_iron_helmet", ModArmorMaterials.HARDENED_IRON, ArmorItem.Type.HELMET, 42);
    public static final DeferredItem<ModArmorItem> HARDENED_IRON_CHESTPLATE = registerArmor("hardened_iron_chestplate", ModArmorMaterials.HARDENED_IRON, ArmorItem.Type.CHESTPLATE, 42);
    public static final DeferredItem<ModArmorItem> HARDENED_IRON_LEGGINGS = registerArmor("hardened_iron_leggings", ModArmorMaterials.HARDENED_IRON, ArmorItem.Type.LEGGINGS, 42);
    public static final DeferredItem<ModArmorItem> HARDENED_IRON_BOOTS = registerArmor("hardened_iron_boots", ModArmorMaterials.HARDENED_IRON, ArmorItem.Type.BOOTS, 42);

    // --- Hardened Armor: Gold ---
    public static final DeferredItem<ModArmorItem> HARDENED_GOLDEN_HELMET = registerArmor("hardened_golden_helmet", ModArmorMaterials.HARDENED_GOLD, ArmorItem.Type.HELMET, 22);
    public static final DeferredItem<ModArmorItem> HARDENED_GOLDEN_CHESTPLATE = registerArmor("hardened_golden_chestplate", ModArmorMaterials.HARDENED_GOLD, ArmorItem.Type.CHESTPLATE, 22);
    public static final DeferredItem<ModArmorItem> HARDENED_GOLDEN_LEGGINGS = registerArmor("hardened_golden_leggings", ModArmorMaterials.HARDENED_GOLD, ArmorItem.Type.LEGGINGS, 22);
    public static final DeferredItem<ModArmorItem> HARDENED_GOLDEN_BOOTS = registerArmor("hardened_golden_boots", ModArmorMaterials.HARDENED_GOLD, ArmorItem.Type.BOOTS, 22);

    // --- Hardened Armor: Diamond ---
    public static final DeferredItem<ModArmorItem> HARDENED_DIAMOND_HELMET = registerArmor("hardened_diamond_helmet", ModArmorMaterials.HARDENED_DIAMOND, ArmorItem.Type.HELMET, 85);
    public static final DeferredItem<ModArmorItem> HARDENED_DIAMOND_CHESTPLATE = registerArmor("hardened_diamond_chestplate", ModArmorMaterials.HARDENED_DIAMOND, ArmorItem.Type.CHESTPLATE, 85);
    public static final DeferredItem<ModArmorItem> HARDENED_DIAMOND_LEGGINGS = registerArmor("hardened_diamond_leggings", ModArmorMaterials.HARDENED_DIAMOND, ArmorItem.Type.LEGGINGS, 85);
    public static final DeferredItem<ModArmorItem> HARDENED_DIAMOND_BOOTS = registerArmor("hardened_diamond_boots", ModArmorMaterials.HARDENED_DIAMOND, ArmorItem.Type.BOOTS, 85);

    // --- Hardened Armor: Netherite ---
    public static final DeferredItem<ModArmorItem> HARDENED_NETHERITE_HELMET = registerFireResistantArmor("hardened_netherite_helmet", ModArmorMaterials.HARDENED_NETHERITE, ArmorItem.Type.HELMET, 100);
    public static final DeferredItem<ModArmorItem> HARDENED_NETHERITE_CHESTPLATE = registerFireResistantArmor("hardened_netherite_chestplate", ModArmorMaterials.HARDENED_NETHERITE, ArmorItem.Type.CHESTPLATE, 100);
    public static final DeferredItem<ModArmorItem> HARDENED_NETHERITE_LEGGINGS = registerFireResistantArmor("hardened_netherite_leggings", ModArmorMaterials.HARDENED_NETHERITE, ArmorItem.Type.LEGGINGS, 100);
    public static final DeferredItem<ModArmorItem> HARDENED_NETHERITE_BOOTS = registerFireResistantArmor("hardened_netherite_boots", ModArmorMaterials.HARDENED_NETHERITE, ArmorItem.Type.BOOTS, 100);

    // --- Bows (Base & Hardened) ---
    public static final DeferredItem<ModBowItem> HARDENED_BOW = ITEMS.register("hardened_bow",
            () -> new ModBowItem(1150, 1.0F, 1.25F, () -> Ingredient.of(ItemTags.PLANKS), new Item.Properties()));
    public static final DeferredItem<ModBowItem> COPPER_BOW = ITEMS.register("copper_bow",
            () -> new ModBowItem(300, 0.5F, 1.15F, () -> Ingredient.of(Items.COPPER_INGOT), new Item.Properties()));
    public static final DeferredItem<ModBowItem> HARDENED_COPPER_BOW = ITEMS.register("hardened_copper_bow",
            () -> new ModBowItem(900, 1.5F, 1.35F, () -> Ingredient.of(Items.COPPER_INGOT), new Item.Properties()));
    public static final DeferredItem<ModBowItem> DEEPSLATE_BOW = ITEMS.register("deepslate_bow",
            () -> new ModBowItem(450, 1.5F, 0.95F, () -> Ingredient.of(Items.COBBLED_DEEPSLATE), new Item.Properties()));
    public static final DeferredItem<ModBowItem> HARDENED_DEEPSLATE_BOW = ITEMS.register("hardened_deepslate_bow",
            () -> new ModBowItem(1350, 2.5F, 1.10F, () -> Ingredient.of(Items.COBBLED_DEEPSLATE), new Item.Properties()));
    public static final DeferredItem<ModBowItem> IRON_BOW = ITEMS.register("iron_bow",
            () -> new ModBowItem(550, 1.0F, 1.10F, () -> Ingredient.of(Items.IRON_INGOT), new Item.Properties()));
    public static final DeferredItem<ModBowItem> HARDENED_IRON_BOW = ITEMS.register("hardened_iron_bow",
            () -> new ModBowItem(1650, 2.0F, 1.30F, () -> Ingredient.of(Items.IRON_INGOT), new Item.Properties()));
    public static final DeferredItem<ModBowItem> DIAMOND_BOW = ITEMS.register("diamond_bow",
            () -> new ModBowItem(1800, 2.0F, 1.25F, () -> Ingredient.of(Items.DIAMOND), new Item.Properties()));
    public static final DeferredItem<ModBowItem> HARDENED_DIAMOND_BOW = ITEMS.register("hardened_diamond_bow",
            () -> new ModBowItem(4500, 3.5F, 1.50F, () -> Ingredient.of(Items.DIAMOND), new Item.Properties()));
    public static final DeferredItem<ModBowItem> NETHERITE_BOW = ITEMS.register("netherite_bow",
            () -> new ModBowItem(2500, 3.0F, 1.35F, () -> Ingredient.of(Items.NETHERITE_INGOT), new Item.Properties().fireResistant()));
    public static final DeferredItem<ModBowItem> HARDENED_NETHERITE_BOW = ITEMS.register("hardened_netherite_bow",
            () -> new ModBowItem(6000, 4.5F, 1.60F, () -> Ingredient.of(Items.NETHERITE_INGOT), new Item.Properties().fireResistant()));

    // --- Crossbows (Base & Hardened) ---
    public static final DeferredItem<ModCrossbowItem> HARDENED_CROSSBOW = ITEMS.register("hardened_crossbow",
            () -> new ModCrossbowItem(1400, 1.5F, 20, () -> Ingredient.of(Items.IRON_INGOT), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> COPPER_CROSSBOW = ITEMS.register("copper_crossbow",
            () -> new ModCrossbowItem(350, 0.5F, 21, () -> Ingredient.of(Items.COPPER_INGOT), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> HARDENED_COPPER_CROSSBOW = ITEMS.register("hardened_copper_crossbow",
            () -> new ModCrossbowItem(1050, 1.5F, 17, () -> Ingredient.of(Items.COPPER_INGOT), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> DEEPSLATE_CROSSBOW = ITEMS.register("deepslate_crossbow",
            () -> new ModCrossbowItem(500, 2.0F, 26, () -> Ingredient.of(Items.COBBLED_DEEPSLATE), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> HARDENED_DEEPSLATE_CROSSBOW = ITEMS.register("hardened_deepslate_crossbow",
            () -> new ModCrossbowItem(1500, 3.0F, 21, () -> Ingredient.of(Items.COBBLED_DEEPSLATE), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> IRON_CROSSBOW = ITEMS.register("iron_crossbow",
            () -> new ModCrossbowItem(600, 1.0F, 22, () -> Ingredient.of(Items.IRON_INGOT), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> HARDENED_IRON_CROSSBOW = ITEMS.register("hardened_iron_crossbow",
            () -> new ModCrossbowItem(1800, 2.0F, 18, () -> Ingredient.of(Items.IRON_INGOT), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> DIAMOND_CROSSBOW = ITEMS.register("diamond_crossbow",
            () -> new ModCrossbowItem(2000, 2.5F, 19, () -> Ingredient.of(Items.DIAMOND), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> HARDENED_DIAMOND_CROSSBOW = ITEMS.register("hardened_diamond_crossbow",
            () -> new ModCrossbowItem(5000, 4.0F, 15, () -> Ingredient.of(Items.DIAMOND), new Item.Properties()));
    public static final DeferredItem<ModCrossbowItem> NETHERITE_CROSSBOW = ITEMS.register("netherite_crossbow",
            () -> new ModCrossbowItem(2800, 3.5F, 17, () -> Ingredient.of(Items.NETHERITE_INGOT), new Item.Properties().fireResistant()));
    public static final DeferredItem<ModCrossbowItem> HARDENED_NETHERITE_CROSSBOW = ITEMS.register("hardened_netherite_crossbow",
            () -> new ModCrossbowItem(6500, 5.0F, 13, () -> Ingredient.of(Items.NETHERITE_INGOT), new Item.Properties().fireResistant()));

    // --- Hardening Upgrade Mapping ---
    private static Map<Item, Supplier<? extends Item>> HARDENING_UPGRADES = null;

    private static void initHardeningMap() {
        if (HARDENING_UPGRADES != null) return;
        HARDENING_UPGRADES = new HashMap<>();

        // Vanilla Wood
        HARDENING_UPGRADES.put(Items.WOODEN_SWORD, HARDENED_WOODEN_SWORD);
        HARDENING_UPGRADES.put(Items.WOODEN_SHOVEL, HARDENED_WOODEN_SHOVEL);
        HARDENING_UPGRADES.put(Items.WOODEN_PICKAXE, HARDENED_WOODEN_PICKAXE);
        HARDENING_UPGRADES.put(Items.WOODEN_AXE, HARDENED_WOODEN_AXE);
        HARDENING_UPGRADES.put(Items.WOODEN_HOE, HARDENED_WOODEN_HOE);

        // Vanilla Stone
        HARDENING_UPGRADES.put(Items.STONE_SWORD, HARDENED_STONE_SWORD);
        HARDENING_UPGRADES.put(Items.STONE_SHOVEL, HARDENED_STONE_SHOVEL);
        HARDENING_UPGRADES.put(Items.STONE_PICKAXE, HARDENED_STONE_PICKAXE);
        HARDENING_UPGRADES.put(Items.STONE_AXE, HARDENED_STONE_AXE);
        HARDENING_UPGRADES.put(Items.STONE_HOE, HARDENED_STONE_HOE);

        // Deepslate Tools
        HARDENING_UPGRADES.put(DEEPSLATE_SWORD.get(), HARDENED_DEEPSLATE_SWORD);
        HARDENING_UPGRADES.put(DEEPSLATE_SHOVEL.get(), HARDENED_DEEPSLATE_SHOVEL);
        HARDENING_UPGRADES.put(DEEPSLATE_PICKAXE.get(), HARDENED_DEEPSLATE_PICKAXE);
        HARDENING_UPGRADES.put(DEEPSLATE_AXE.get(), HARDENED_DEEPSLATE_AXE);
        HARDENING_UPGRADES.put(DEEPSLATE_HOE.get(), HARDENED_DEEPSLATE_HOE);

        // Copper Tools
        HARDENING_UPGRADES.put(COPPER_SWORD.get(), HARDENED_COPPER_SWORD);
        HARDENING_UPGRADES.put(COPPER_SHOVEL.get(), HARDENED_COPPER_SHOVEL);
        HARDENING_UPGRADES.put(COPPER_PICKAXE.get(), HARDENED_COPPER_PICKAXE);
        HARDENING_UPGRADES.put(COPPER_AXE.get(), HARDENED_COPPER_AXE);
        HARDENING_UPGRADES.put(COPPER_HOE.get(), HARDENED_COPPER_HOE);

        // Vanilla Iron
        HARDENING_UPGRADES.put(Items.IRON_SWORD, HARDENED_IRON_SWORD);
        HARDENING_UPGRADES.put(Items.IRON_SHOVEL, HARDENED_IRON_SHOVEL);
        HARDENING_UPGRADES.put(Items.IRON_PICKAXE, HARDENED_IRON_PICKAXE);
        HARDENING_UPGRADES.put(Items.IRON_AXE, HARDENED_IRON_AXE);
        HARDENING_UPGRADES.put(Items.IRON_HOE, HARDENED_IRON_HOE);

        // Vanilla Gold
        HARDENING_UPGRADES.put(Items.GOLDEN_SWORD, HARDENED_GOLDEN_SWORD);
        HARDENING_UPGRADES.put(Items.GOLDEN_SHOVEL, HARDENED_GOLDEN_SHOVEL);
        HARDENING_UPGRADES.put(Items.GOLDEN_PICKAXE, HARDENED_GOLDEN_PICKAXE);
        HARDENING_UPGRADES.put(Items.GOLDEN_AXE, HARDENED_GOLDEN_AXE);
        HARDENING_UPGRADES.put(Items.GOLDEN_HOE, HARDENED_GOLDEN_HOE);

        // Vanilla Diamond
        HARDENING_UPGRADES.put(Items.DIAMOND_SWORD, HARDENED_DIAMOND_SWORD);
        HARDENING_UPGRADES.put(Items.DIAMOND_SHOVEL, HARDENED_DIAMOND_SHOVEL);
        HARDENING_UPGRADES.put(Items.DIAMOND_PICKAXE, HARDENED_DIAMOND_PICKAXE);
        HARDENING_UPGRADES.put(Items.DIAMOND_AXE, HARDENED_DIAMOND_AXE);
        HARDENING_UPGRADES.put(Items.DIAMOND_HOE, HARDENED_DIAMOND_HOE);

        // Vanilla Netherite
        HARDENING_UPGRADES.put(Items.NETHERITE_SWORD, HARDENED_NETHERITE_SWORD);
        HARDENING_UPGRADES.put(Items.NETHERITE_SHOVEL, HARDENED_NETHERITE_SHOVEL);
        HARDENING_UPGRADES.put(Items.NETHERITE_PICKAXE, HARDENED_NETHERITE_PICKAXE);
        HARDENING_UPGRADES.put(Items.NETHERITE_AXE, HARDENED_NETHERITE_AXE);
        HARDENING_UPGRADES.put(Items.NETHERITE_HOE, HARDENED_NETHERITE_HOE);

        // Hammers
        HARDENING_UPGRADES.put(WOODEN_HAMMER.get(), HARDENED_WOODEN_HAMMER);
        HARDENING_UPGRADES.put(STONE_HAMMER.get(), HARDENED_STONE_HAMMER);
        HARDENING_UPGRADES.put(DEEPSLATE_HAMMER.get(), HARDENED_DEEPSLATE_HAMMER);
        HARDENING_UPGRADES.put(COPPER_HAMMER.get(), HARDENED_COPPER_HAMMER);
        HARDENING_UPGRADES.put(IRON_HAMMER.get(), HARDENED_IRON_HAMMER);
        HARDENING_UPGRADES.put(GOLDEN_HAMMER.get(), HARDENED_GOLDEN_HAMMER);
        HARDENING_UPGRADES.put(DIAMOND_HAMMER.get(), HARDENED_DIAMOND_HAMMER);
        HARDENING_UPGRADES.put(NETHERITE_HAMMER.get(), HARDENED_NETHERITE_HAMMER);

        // Excavators
        HARDENING_UPGRADES.put(WOODEN_EXCAVATOR.get(), HARDENED_WOODEN_EXCAVATOR);
        HARDENING_UPGRADES.put(STONE_EXCAVATOR.get(), HARDENED_STONE_EXCAVATOR);
        HARDENING_UPGRADES.put(DEEPSLATE_EXCAVATOR.get(), HARDENED_DEEPSLATE_EXCAVATOR);
        HARDENING_UPGRADES.put(COPPER_EXCAVATOR.get(), HARDENED_COPPER_EXCAVATOR);
        HARDENING_UPGRADES.put(IRON_EXCAVATOR.get(), HARDENED_IRON_EXCAVATOR);
        HARDENING_UPGRADES.put(GOLDEN_EXCAVATOR.get(), HARDENED_GOLDEN_EXCAVATOR);
        HARDENING_UPGRADES.put(DIAMOND_EXCAVATOR.get(), HARDENED_DIAMOND_EXCAVATOR);
        HARDENING_UPGRADES.put(NETHERITE_EXCAVATOR.get(), HARDENED_NETHERITE_EXCAVATOR);

        // Paxels
        HARDENING_UPGRADES.put(WOODEN_PAXEL.get(), HARDENED_WOODEN_PAXEL);
        HARDENING_UPGRADES.put(STONE_PAXEL.get(), HARDENED_STONE_PAXEL);
        HARDENING_UPGRADES.put(DEEPSLATE_PAXEL.get(), HARDENED_DEEPSLATE_PAXEL);
        HARDENING_UPGRADES.put(COPPER_PAXEL.get(), HARDENED_COPPER_PAXEL);
        HARDENING_UPGRADES.put(IRON_PAXEL.get(), HARDENED_IRON_PAXEL);
        HARDENING_UPGRADES.put(GOLDEN_PAXEL.get(), HARDENED_GOLDEN_PAXEL);
        HARDENING_UPGRADES.put(DIAMOND_PAXEL.get(), HARDENED_DIAMOND_PAXEL);
        HARDENING_UPGRADES.put(NETHERITE_PAXEL.get(), HARDENED_NETHERITE_PAXEL);

        // Armor: Leather
        HARDENING_UPGRADES.put(Items.LEATHER_HELMET, HARDENED_LEATHER_HELMET);
        HARDENING_UPGRADES.put(Items.LEATHER_CHESTPLATE, HARDENED_LEATHER_CHESTPLATE);
        HARDENING_UPGRADES.put(Items.LEATHER_LEGGINGS, HARDENED_LEATHER_LEGGINGS);
        HARDENING_UPGRADES.put(Items.LEATHER_BOOTS, HARDENED_LEATHER_BOOTS);

        // Armor: Copper
        HARDENING_UPGRADES.put(COPPER_HELMET.get(), HARDENED_COPPER_HELMET);
        HARDENING_UPGRADES.put(COPPER_CHESTPLATE.get(), HARDENED_COPPER_CHESTPLATE);
        HARDENING_UPGRADES.put(COPPER_LEGGINGS.get(), HARDENED_COPPER_LEGGINGS);
        HARDENING_UPGRADES.put(COPPER_BOOTS.get(), HARDENED_COPPER_BOOTS);

        // Armor: Deepslate
        HARDENING_UPGRADES.put(DEEPSLATE_HELMET.get(), HARDENED_DEEPSLATE_HELMET);
        HARDENING_UPGRADES.put(DEEPSLATE_CHESTPLATE.get(), HARDENED_DEEPSLATE_CHESTPLATE);
        HARDENING_UPGRADES.put(DEEPSLATE_LEGGINGS.get(), HARDENED_DEEPSLATE_LEGGINGS);
        HARDENING_UPGRADES.put(DEEPSLATE_BOOTS.get(), HARDENED_DEEPSLATE_BOOTS);

        // Armor: Chainmail
        HARDENING_UPGRADES.put(Items.CHAINMAIL_HELMET, HARDENED_CHAINMAIL_HELMET);
        HARDENING_UPGRADES.put(Items.CHAINMAIL_CHESTPLATE, HARDENED_CHAINMAIL_CHESTPLATE);
        HARDENING_UPGRADES.put(Items.CHAINMAIL_LEGGINGS, HARDENED_CHAINMAIL_LEGGINGS);
        HARDENING_UPGRADES.put(Items.CHAINMAIL_BOOTS, HARDENED_CHAINMAIL_BOOTS);

        // Armor: Iron
        HARDENING_UPGRADES.put(Items.IRON_HELMET, HARDENED_IRON_HELMET);
        HARDENING_UPGRADES.put(Items.IRON_CHESTPLATE, HARDENED_IRON_CHESTPLATE);
        HARDENING_UPGRADES.put(Items.IRON_LEGGINGS, HARDENED_IRON_LEGGINGS);
        HARDENING_UPGRADES.put(Items.IRON_BOOTS, HARDENED_IRON_BOOTS);

        // Armor: Gold
        HARDENING_UPGRADES.put(Items.GOLDEN_HELMET, HARDENED_GOLDEN_HELMET);
        HARDENING_UPGRADES.put(Items.GOLDEN_CHESTPLATE, HARDENED_GOLDEN_CHESTPLATE);
        HARDENING_UPGRADES.put(Items.GOLDEN_LEGGINGS, HARDENED_GOLDEN_LEGGINGS);
        HARDENING_UPGRADES.put(Items.GOLDEN_BOOTS, HARDENED_GOLDEN_BOOTS);

        // Armor: Diamond
        HARDENING_UPGRADES.put(Items.DIAMOND_HELMET, HARDENED_DIAMOND_HELMET);
        HARDENING_UPGRADES.put(Items.DIAMOND_CHESTPLATE, HARDENED_DIAMOND_CHESTPLATE);
        HARDENING_UPGRADES.put(Items.DIAMOND_LEGGINGS, HARDENED_DIAMOND_LEGGINGS);
        HARDENING_UPGRADES.put(Items.DIAMOND_BOOTS, HARDENED_DIAMOND_BOOTS);

        // Armor: Netherite
        HARDENING_UPGRADES.put(Items.NETHERITE_HELMET, HARDENED_NETHERITE_HELMET);
        HARDENING_UPGRADES.put(Items.NETHERITE_CHESTPLATE, HARDENED_NETHERITE_CHESTPLATE);
        HARDENING_UPGRADES.put(Items.NETHERITE_LEGGINGS, HARDENED_NETHERITE_LEGGINGS);
        HARDENING_UPGRADES.put(Items.NETHERITE_BOOTS, HARDENED_NETHERITE_BOOTS);

        // Bows
        HARDENING_UPGRADES.put(Items.BOW, HARDENED_BOW);
        HARDENING_UPGRADES.put(COPPER_BOW.get(), HARDENED_COPPER_BOW);
        HARDENING_UPGRADES.put(DEEPSLATE_BOW.get(), HARDENED_DEEPSLATE_BOW);
        HARDENING_UPGRADES.put(IRON_BOW.get(), HARDENED_IRON_BOW);
        HARDENING_UPGRADES.put(DIAMOND_BOW.get(), HARDENED_DIAMOND_BOW);
        HARDENING_UPGRADES.put(NETHERITE_BOW.get(), HARDENED_NETHERITE_BOW);

        // Crossbows
        HARDENING_UPGRADES.put(Items.CROSSBOW, HARDENED_CROSSBOW);
        HARDENING_UPGRADES.put(COPPER_CROSSBOW.get(), HARDENED_COPPER_CROSSBOW);
        HARDENING_UPGRADES.put(DEEPSLATE_CROSSBOW.get(), HARDENED_DEEPSLATE_CROSSBOW);
        HARDENING_UPGRADES.put(IRON_CROSSBOW.get(), HARDENED_IRON_CROSSBOW);
        HARDENING_UPGRADES.put(DIAMOND_CROSSBOW.get(), HARDENED_DIAMOND_CROSSBOW);
        HARDENING_UPGRADES.put(NETHERITE_CROSSBOW.get(), HARDENED_NETHERITE_CROSSBOW);
    }

    public static Item getHardenedVariant(Item baseItem) {
        initHardeningMap();
        Supplier<? extends Item> supplier = HARDENING_UPGRADES.get(baseItem);
        return supplier != null ? supplier.get() : null;
    }
}
