package com.justtools.event;

import com.justtools.JustTools;
import com.justtools.init.ModDataComponents;
import com.justtools.init.ModItems;
import com.justtools.item.ExcavatorItem;
import com.justtools.item.HammerItem;
import com.justtools.item.PaxelItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = JustTools.MODID, value = Dist.CLIENT)
public class JustToolsClientEvents {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        // 1. Upgrade component badges on tools
        if (stack.has(ModDataComponents.DEPTH_UPGRADE.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.depth_upgrade").withStyle(ChatFormatting.AQUA));
        }
        if (stack.has(ModDataComponents.AUTO_REPAIR.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.auto_repair").withStyle(ChatFormatting.GREEN));
        }
        if (stack.has(ModDataComponents.LAVA_PROOF.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.lava_proof").withStyle(ChatFormatting.GOLD));
        }
        if (stack.has(ModDataComponents.OVERCLOCK.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.overclock").withStyle(ChatFormatting.RED));
        }
        if (stack.has(ModDataComponents.BREEZE_CHARGE.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.breeze_charge").withStyle(ChatFormatting.DARK_AQUA));
        }
        if (stack.has(ModDataComponents.AUTO_SMELT.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.auto_smelt").withStyle(ChatFormatting.YELLOW));
        }
        if (stack.has(ModDataComponents.REINFORCED.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.reinforced").withStyle(ChatFormatting.BLUE));
        }
        if (stack.has(ModDataComponents.AMETHYST_SHIELD.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.amethyst_shield").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        if (stack.has(ModDataComponents.PHOTOSYNTHESIS.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.photosynthesis").withStyle(ChatFormatting.DARK_GREEN));
        }
        if (stack.has(ModDataComponents.MAGNETIC.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.magnetic").withStyle(ChatFormatting.DARK_PURPLE));
        }

        // 2. Item descriptions for upgrade plates and station
        if (stack.is(ModItems.HARDENING_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.hardening_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.DEPTH_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.depth_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_hammers_excavators").withStyle(ChatFormatting.DARK_AQUA));
        } else if (stack.is(ModItems.ECHO_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.echo_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.LAVA_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.lava_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.OVERCLOCK_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.overclock_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.BREEZE_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.breeze_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.AUTO_SMELT_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.auto_smelt_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.REINFORCED_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.reinforced_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.AMETHYST_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.amethyst_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.MOSS_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.moss_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.MAGNETIC_PLATE.get())) {
            event.getToolTip().add(Component.translatable("item.justtools.magnetic_plate.desc").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.justtools.compatible_all").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.is(ModItems.HARDENING_STATION.get())) {
            event.getToolTip().add(Component.translatable("block.justtools.hardening_station.desc").withStyle(ChatFormatting.GRAY));
        }

        // 3. Tool type descriptions
        if (stack.getItem() instanceof HammerItem) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.hammer_desc").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.getItem() instanceof ExcavatorItem) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.excavator_desc").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.getItem() instanceof PaxelItem) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.paxel_desc").withStyle(ChatFormatting.DARK_GRAY));
        } else if (stack.getItem() instanceof net.minecraft.world.item.ArmorItem) {
            if (stack.has(ModDataComponents.DEPTH_UPGRADE.get())) {
                event.getToolTip().add(Component.translatable("tooltip.justtools.armor_depth").withStyle(ChatFormatting.DARK_GRAY));
            }
            if (stack.has(ModDataComponents.LAVA_PROOF.get())) {
                event.getToolTip().add(Component.translatable("tooltip.justtools.armor_lava").withStyle(ChatFormatting.DARK_GRAY));
            }
            if (stack.has(ModDataComponents.BREEZE_CHARGE.get())) {
                event.getToolTip().add(Component.translatable("tooltip.justtools.armor_breeze").withStyle(ChatFormatting.DARK_GRAY));
            }
            if (stack.has(ModDataComponents.OVERCLOCK.get())) {
                event.getToolTip().add(Component.translatable("tooltip.justtools.armor_overclock").withStyle(ChatFormatting.DARK_GRAY));
            }
            if (stack.has(ModDataComponents.AUTO_SMELT.get())) {
                event.getToolTip().add(Component.translatable("tooltip.justtools.armor_auto_smelt").withStyle(ChatFormatting.DARK_GRAY));
            }
            if (stack.has(ModDataComponents.MAGNETIC.get())) {
                event.getToolTip().add(Component.translatable("tooltip.justtools.armor_magnetic").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}
