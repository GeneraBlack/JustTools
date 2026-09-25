package com.justtools.init;

import com.justtools.JustTools;
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

    // Hardening Station BlockItem & Material
    public static final DeferredItem<BlockItem> HARDENING_STATION = ITEMS.registerSimpleBlockItem("hardening_station", ModBlocks.HARDENING_STATION);
    public static final DeferredItem<Item> HARDENING_PLATE = ITEMS.registerItem("hardening_plate", Item::new, new Item.Properties());

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

    // --- Hammers (Base) ---
    public static final DeferredItem<HammerItem> WOODEN_HAMMER = ITEMS.register("wooden_hammer",
            () -> new HammerItem(Tiers.WOOD, new Item.Properties().durability(200)));
    public static final DeferredItem<HammerItem> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new HammerItem(Tiers.STONE, new Item.Properties().durability(400)));
    public static final DeferredItem<HammerItem> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new HammerItem(ModTiers.COPPER, new Item.Properties().durability(600)));
    public static final DeferredItem<HammerItem> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(Tiers.IRON, new Item.Properties().durability(800)));
    public static final DeferredItem<HammerItem> GOLDEN_HAMMER = ITEMS.register("golden_hammer",
            () -> new HammerItem(Tiers.GOLD, new Item.Properties().durability(150)));
    public static final DeferredItem<HammerItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(Tiers.DIAMOND, new Item.Properties().durability(3500)));
    public static final DeferredItem<HammerItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
            () -> new HammerItem(Tiers.NETHERITE, new Item.Properties().durability(5000).fireResistant()));

    // --- Paxels (Base) ---
    public static final DeferredItem<PaxelItem> WOODEN_PAXEL = ITEMS.register("wooden_paxel",
            () -> new PaxelItem(Tiers.WOOD, new Item.Properties().durability(160)));
    public static final DeferredItem<PaxelItem> STONE_PAXEL = ITEMS.register("stone_paxel",
            () -> new PaxelItem(Tiers.STONE, new Item.Properties().durability(350)));
    public static final DeferredItem<PaxelItem> COPPER_PAXEL = ITEMS.register("copper_paxel",
            () -> new PaxelItem(ModTiers.COPPER, new Item.Properties().durability(500)));
    public static final DeferredItem<PaxelItem> IRON_PAXEL = ITEMS.register("iron_paxel",
            () -> new PaxelItem(Tiers.IRON, new Item.Properties().durability(650)));
    public static final DeferredItem<PaxelItem> GOLDEN_PAXEL = ITEMS.register("golden_paxel",
            () -> new PaxelItem(Tiers.GOLD, new Item.Properties().durability(100)));
    public static final DeferredItem<PaxelItem> DIAMOND_PAXEL = ITEMS.register("diamond_paxel",
            () -> new PaxelItem(Tiers.DIAMOND, new Item.Properties().durability(3000)));
    public static final DeferredItem<PaxelItem> NETHERITE_PAXEL = ITEMS.register("netherite_paxel",
            () -> new PaxelItem(Tiers.NETHERITE, new Item.Properties().durability(4500).fireResistant()));

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
            () -> new HammerItem(ModTiers.HARDENED_WOOD, new Item.Properties().durability(600)));
    public static final DeferredItem<HammerItem> HARDENED_STONE_HAMMER = ITEMS.register("hardened_stone_hammer",
            () -> new HammerItem(ModTiers.HARDENED_STONE, new Item.Properties().durability(1200)));
    public static final DeferredItem<HammerItem> HARDENED_COPPER_HAMMER = ITEMS.register("hardened_copper_hammer",
            () -> new HammerItem(ModTiers.HARDENED_COPPER, new Item.Properties().durability(1800)));
    public static final DeferredItem<HammerItem> HARDENED_IRON_HAMMER = ITEMS.register("hardened_iron_hammer",
            () -> new HammerItem(ModTiers.HARDENED_IRON, new Item.Properties().durability(2400)));
    public static final DeferredItem<HammerItem> HARDENED_GOLDEN_HAMMER = ITEMS.register("hardened_golden_hammer",
            () -> new HammerItem(ModTiers.HARDENED_GOLD, new Item.Properties().durability(500)));
    public static final DeferredItem<HammerItem> HARDENED_DIAMOND_HAMMER = ITEMS.register("hardened_diamond_hammer",
            () -> new HammerItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().durability(9000)));
    public static final DeferredItem<HammerItem> HARDENED_NETHERITE_HAMMER = ITEMS.register("hardened_netherite_hammer",
            () -> new HammerItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().durability(13000).fireResistant()));

    // --- Hardened Paxels ---
    public static final DeferredItem<PaxelItem> HARDENED_WOODEN_PAXEL = ITEMS.register("hardened_wooden_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_WOOD, new Item.Properties().durability(480)));
    public static final DeferredItem<PaxelItem> HARDENED_STONE_PAXEL = ITEMS.register("hardened_stone_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_STONE, new Item.Properties().durability(1000)));
    public static final DeferredItem<PaxelItem> HARDENED_COPPER_PAXEL = ITEMS.register("hardened_copper_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_COPPER, new Item.Properties().durability(1500)));
    public static final DeferredItem<PaxelItem> HARDENED_IRON_PAXEL = ITEMS.register("hardened_iron_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_IRON, new Item.Properties().durability(1950)));
    public static final DeferredItem<PaxelItem> HARDENED_GOLDEN_PAXEL = ITEMS.register("hardened_golden_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_GOLD, new Item.Properties().durability(350)));
    public static final DeferredItem<PaxelItem> HARDENED_DIAMOND_PAXEL = ITEMS.register("hardened_diamond_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_DIAMOND, new Item.Properties().durability(7500)));
    public static final DeferredItem<PaxelItem> HARDENED_NETHERITE_PAXEL = ITEMS.register("hardened_netherite_paxel",
            () -> new PaxelItem(ModTiers.HARDENED_NETHERITE, new Item.Properties().durability(11000).fireResistant()));

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
        HARDENING_UPGRADES.put(COPPER_HAMMER.get(), HARDENED_COPPER_HAMMER);
        HARDENING_UPGRADES.put(IRON_HAMMER.get(), HARDENED_IRON_HAMMER);
        HARDENING_UPGRADES.put(GOLDEN_HAMMER.get(), HARDENED_GOLDEN_HAMMER);
        HARDENING_UPGRADES.put(DIAMOND_HAMMER.get(), HARDENED_DIAMOND_HAMMER);
        HARDENING_UPGRADES.put(NETHERITE_HAMMER.get(), HARDENED_NETHERITE_HAMMER);

        // Paxels
        HARDENING_UPGRADES.put(WOODEN_PAXEL.get(), HARDENED_WOODEN_PAXEL);
        HARDENING_UPGRADES.put(STONE_PAXEL.get(), HARDENED_STONE_PAXEL);
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
