package com.justtools.init;

import com.justtools.JustTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JustTools.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> JUST_TOOLS_TAB = CREATIVE_MODE_TABS.register(
            "justtools_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.justtools"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.HARDENED_DIAMOND_HAMMER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // Workstation & Components
                        output.accept(ModItems.HARDENING_STATION.get());
                        output.accept(ModItems.HARDENING_PLATE.get());

                        // Copper Tools
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.COPPER_SHOVEL.get());
                        output.accept(ModItems.COPPER_PICKAXE.get());
                        output.accept(ModItems.COPPER_AXE.get());
                        output.accept(ModItems.COPPER_HOE.get());

                        // Hammers (Base)
                        output.accept(ModItems.WOODEN_HAMMER.get());
                        output.accept(ModItems.STONE_HAMMER.get());
                        output.accept(ModItems.COPPER_HAMMER.get());
                        output.accept(ModItems.IRON_HAMMER.get());
                        output.accept(ModItems.GOLDEN_HAMMER.get());
                        output.accept(ModItems.DIAMOND_HAMMER.get());
                        output.accept(ModItems.NETHERITE_HAMMER.get());

                        // Excavators (Base)
                        output.accept(ModItems.WOODEN_EXCAVATOR.get());
                        output.accept(ModItems.STONE_EXCAVATOR.get());
                        output.accept(ModItems.COPPER_EXCAVATOR.get());
                        output.accept(ModItems.IRON_EXCAVATOR.get());
                        output.accept(ModItems.GOLDEN_EXCAVATOR.get());
                        output.accept(ModItems.DIAMOND_EXCAVATOR.get());
                        output.accept(ModItems.NETHERITE_EXCAVATOR.get());

                        // Paxels (Base)
                        output.accept(ModItems.WOODEN_PAXEL.get());
                        output.accept(ModItems.STONE_PAXEL.get());
                        output.accept(ModItems.COPPER_PAXEL.get());
                        output.accept(ModItems.IRON_PAXEL.get());
                        output.accept(ModItems.GOLDEN_PAXEL.get());
                        output.accept(ModItems.DIAMOND_PAXEL.get());
                        output.accept(ModItems.NETHERITE_PAXEL.get());

                        // Hardened Basic Tools
                        // Wood
                        output.accept(ModItems.HARDENED_WOODEN_SWORD.get());
                        output.accept(ModItems.HARDENED_WOODEN_SHOVEL.get());
                        output.accept(ModItems.HARDENED_WOODEN_PICKAXE.get());
                        output.accept(ModItems.HARDENED_WOODEN_AXE.get());
                        output.accept(ModItems.HARDENED_WOODEN_HOE.get());
                        // Stone
                        output.accept(ModItems.HARDENED_STONE_SWORD.get());
                        output.accept(ModItems.HARDENED_STONE_SHOVEL.get());
                        output.accept(ModItems.HARDENED_STONE_PICKAXE.get());
                        output.accept(ModItems.HARDENED_STONE_AXE.get());
                        output.accept(ModItems.HARDENED_STONE_HOE.get());
                        // Copper
                        output.accept(ModItems.HARDENED_COPPER_SWORD.get());
                        output.accept(ModItems.HARDENED_COPPER_SHOVEL.get());
                        output.accept(ModItems.HARDENED_COPPER_PICKAXE.get());
                        output.accept(ModItems.HARDENED_COPPER_AXE.get());
                        output.accept(ModItems.HARDENED_COPPER_HOE.get());
                        // Iron
                        output.accept(ModItems.HARDENED_IRON_SWORD.get());
                        output.accept(ModItems.HARDENED_IRON_SHOVEL.get());
                        output.accept(ModItems.HARDENED_IRON_PICKAXE.get());
                        output.accept(ModItems.HARDENED_IRON_AXE.get());
                        output.accept(ModItems.HARDENED_IRON_HOE.get());
                        // Gold
                        output.accept(ModItems.HARDENED_GOLDEN_SWORD.get());
                        output.accept(ModItems.HARDENED_GOLDEN_SHOVEL.get());
                        output.accept(ModItems.HARDENED_GOLDEN_PICKAXE.get());
                        output.accept(ModItems.HARDENED_GOLDEN_AXE.get());
                        output.accept(ModItems.HARDENED_GOLDEN_HOE.get());
                        // Diamond
                        output.accept(ModItems.HARDENED_DIAMOND_SWORD.get());
                        output.accept(ModItems.HARDENED_DIAMOND_SHOVEL.get());
                        output.accept(ModItems.HARDENED_DIAMOND_PICKAXE.get());
                        output.accept(ModItems.HARDENED_DIAMOND_AXE.get());
                        output.accept(ModItems.HARDENED_DIAMOND_HOE.get());
                        // Netherite
                        output.accept(ModItems.HARDENED_NETHERITE_SWORD.get());
                        output.accept(ModItems.HARDENED_NETHERITE_SHOVEL.get());
                        output.accept(ModItems.HARDENED_NETHERITE_PICKAXE.get());
                        output.accept(ModItems.HARDENED_NETHERITE_AXE.get());
                        output.accept(ModItems.HARDENED_NETHERITE_HOE.get());

                        // Hardened Hammers
                        output.accept(ModItems.HARDENED_WOODEN_HAMMER.get());
                        output.accept(ModItems.HARDENED_STONE_HAMMER.get());
                        output.accept(ModItems.HARDENED_COPPER_HAMMER.get());
                        output.accept(ModItems.HARDENED_IRON_HAMMER.get());
                        output.accept(ModItems.HARDENED_GOLDEN_HAMMER.get());
                        output.accept(ModItems.HARDENED_DIAMOND_HAMMER.get());
                        output.accept(ModItems.HARDENED_NETHERITE_HAMMER.get());

                        // Hardened Excavators
                        output.accept(ModItems.HARDENED_WOODEN_EXCAVATOR.get());
                        output.accept(ModItems.HARDENED_STONE_EXCAVATOR.get());
                        output.accept(ModItems.HARDENED_COPPER_EXCAVATOR.get());
                        output.accept(ModItems.HARDENED_IRON_EXCAVATOR.get());
                        output.accept(ModItems.HARDENED_GOLDEN_EXCAVATOR.get());
                        output.accept(ModItems.HARDENED_DIAMOND_EXCAVATOR.get());
                        output.accept(ModItems.HARDENED_NETHERITE_EXCAVATOR.get());

                        // Hardened Paxels
                        output.accept(ModItems.HARDENED_WOODEN_PAXEL.get());
                        output.accept(ModItems.HARDENED_STONE_PAXEL.get());
                        output.accept(ModItems.HARDENED_COPPER_PAXEL.get());
                        output.accept(ModItems.HARDENED_IRON_PAXEL.get());
                        output.accept(ModItems.HARDENED_GOLDEN_PAXEL.get());
                        output.accept(ModItems.HARDENED_DIAMOND_PAXEL.get());
                        output.accept(ModItems.HARDENED_NETHERITE_PAXEL.get());
                    })
                    .build()
    );
}
