package com.justtools.init;

import com.justtools.JustTools;
import com.justtools.item.ExcavatorItem;
import com.justtools.item.HammerItem;
import com.justtools.item.PaxelItem;
import net.minecraft.world.item.*;
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
            () -> new SwordItem(ModTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<AxeItem> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModTiers.COPPER, new Item.Properties()));
    public static final DeferredItem<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModTiers.COPPER, new Item.Properties()));

    // --- Deepslate Tools ---
    public static final DeferredItem<SwordItem> DEEPSLATE_SWORD = ITEMS.register("deepslate_sword",
            () -> new SwordItem(ModTiers.DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<ShovelItem> DEEPSLATE_SHOVEL = ITEMS.register("deepslate_shovel",
            () -> new ShovelItem(ModTiers.DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> DEEPSLATE_PICKAXE = ITEMS.register("deepslate_pickaxe",
            () -> new PickaxeItem(ModTiers.DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<AxeItem> DEEPSLATE_AXE = ITEMS.register("deepslate_axe",
            () -> new AxeItem(ModTiers.DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<HoeItem> DEEPSLATE_HOE = ITEMS.register("deepslate_hoe",
            () -> new HoeItem(ModTiers.DEEPSLATE, new Item.Properties()));

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
            () -> new SwordItem(ModTiers.HARDENED_WOOD, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_WOODEN_SHOVEL = ITEMS.register("hardened_wooden_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_WOOD, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_WOODEN_PICKAXE = ITEMS.register("hardened_wooden_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_WOOD, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_WOODEN_AXE = ITEMS.register("hardened_wooden_axe",
            () -> new AxeItem(ModTiers.HARDENED_WOOD, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_WOODEN_HOE = ITEMS.register("hardened_wooden_hoe",
            () -> new HoeItem(ModTiers.HARDENED_WOOD, new Item.Properties()));

    // --- Hardened Stone ---
    public static final DeferredItem<SwordItem> HARDENED_STONE_SWORD = ITEMS.register("hardened_stone_sword",
            () -> new SwordItem(ModTiers.HARDENED_STONE, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_STONE_SHOVEL = ITEMS.register("hardened_stone_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_STONE, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_STONE_PICKAXE = ITEMS.register("hardened_stone_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_STONE, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_STONE_AXE = ITEMS.register("hardened_stone_axe",
            () -> new AxeItem(ModTiers.HARDENED_STONE, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_STONE_HOE = ITEMS.register("hardened_stone_hoe",
            () -> new HoeItem(ModTiers.HARDENED_STONE, new Item.Properties()));

    // --- Hardened Deepslate ---
    public static final DeferredItem<SwordItem> HARDENED_DEEPSLATE_SWORD = ITEMS.register("hardened_deepslate_sword",
            () -> new SwordItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_DEEPSLATE_SHOVEL = ITEMS.register("hardened_deepslate_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_DEEPSLATE_PICKAXE = ITEMS.register("hardened_deepslate_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_DEEPSLATE_AXE = ITEMS.register("hardened_deepslate_axe",
            () -> new AxeItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_DEEPSLATE_HOE = ITEMS.register("hardened_deepslate_hoe",
            () -> new HoeItem(ModTiers.HARDENED_DEEPSLATE, new Item.Properties()));

    // --- Hardened Copper ---
    public static final DeferredItem<SwordItem> HARDENED_COPPER_SWORD = ITEMS.register("hardened_copper_sword",
            () -> new SwordItem(ModTiers.HARDENED_COPPER, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_COPPER_SHOVEL = ITEMS.register("hardened_copper_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_COPPER, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_COPPER_PICKAXE = ITEMS.register("hardened_copper_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_COPPER, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_COPPER_AXE = ITEMS.register("hardened_copper_axe",
            () -> new AxeItem(ModTiers.HARDENED_COPPER, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_COPPER_HOE = ITEMS.register("hardened_copper_hoe",
            () -> new HoeItem(ModTiers.HARDENED_COPPER, new Item.Properties()));

    // --- Hardened Iron ---
    public static final DeferredItem<SwordItem> HARDENED_IRON_SWORD = ITEMS.register("hardened_iron_sword",
            () -> new SwordItem(ModTiers.HARDENED_IRON, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_IRON_SHOVEL = ITEMS.register("hardened_iron_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_IRON, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_IRON_PICKAXE = ITEMS.register("hardened_iron_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_IRON, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_IRON_AXE = ITEMS.register("hardened_iron_axe",
            () -> new AxeItem(ModTiers.HARDENED_IRON, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_IRON_HOE = ITEMS.register("hardened_iron_hoe",
            () -> new HoeItem(ModTiers.HARDENED_IRON, new Item.Properties()));

    // --- Hardened Gold ---
    public static final DeferredItem<SwordItem> HARDENED_GOLDEN_SWORD = ITEMS.register("hardened_golden_sword",
            () -> new SwordItem(ModTiers.HARDENED_GOLD, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_GOLDEN_SHOVEL = ITEMS.register("hardened_golden_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_GOLD, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_GOLDEN_PICKAXE = ITEMS.register("hardened_golden_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_GOLD, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_GOLDEN_AXE = ITEMS.register("hardened_golden_axe",
            () -> new AxeItem(ModTiers.HARDENED_GOLD, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_GOLDEN_HOE = ITEMS.register("hardened_golden_hoe",
            () -> new HoeItem(ModTiers.HARDENED_GOLD, new Item.Properties()));

    // --- Hardened Diamond ---
    public static final DeferredItem<SwordItem> HARDENED_DIAMOND_SWORD = ITEMS.register("hardened_diamond_sword",
            () -> new SwordItem(ModTiers.HARDENED_DIAMOND, new Item.Properties()));
    public static final DeferredItem<ShovelItem> HARDENED_DIAMOND_SHOVEL = ITEMS.register("hardened_diamond_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_DIAMOND, new Item.Properties()));
    public static final DeferredItem<PickaxeItem> HARDENED_DIAMOND_PICKAXE = ITEMS.register("hardened_diamond_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_DIAMOND, new Item.Properties()));
    public static final DeferredItem<AxeItem> HARDENED_DIAMOND_AXE = ITEMS.register("hardened_diamond_axe",
            () -> new AxeItem(ModTiers.HARDENED_DIAMOND, new Item.Properties()));
    public static final DeferredItem<HoeItem> HARDENED_DIAMOND_HOE = ITEMS.register("hardened_diamond_hoe",
            () -> new HoeItem(ModTiers.HARDENED_DIAMOND, new Item.Properties()));

    // --- Hardened Netherite ---
    public static final DeferredItem<SwordItem> HARDENED_NETHERITE_SWORD = ITEMS.register("hardened_netherite_sword",
            () -> new SwordItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant()));
    public static final DeferredItem<ShovelItem> HARDENED_NETHERITE_SHOVEL = ITEMS.register("hardened_netherite_shovel",
            () -> new ShovelItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant()));
    public static final DeferredItem<PickaxeItem> HARDENED_NETHERITE_PICKAXE = ITEMS.register("hardened_netherite_pickaxe",
            () -> new PickaxeItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant()));
    public static final DeferredItem<AxeItem> HARDENED_NETHERITE_AXE = ITEMS.register("hardened_netherite_axe",
            () -> new AxeItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant()));
    public static final DeferredItem<HoeItem> HARDENED_NETHERITE_HOE = ITEMS.register("hardened_netherite_hoe",
            () -> new HoeItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().fireResistant()));

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
    }

    public static Item getHardenedVariant(Item baseItem) {
        initHardeningMap();
        Supplier<? extends Item> supplier = HARDENING_UPGRADES.get(baseItem);
        return supplier != null ? supplier.get() : null;
    }
}
