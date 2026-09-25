package com.justtools.event;

import com.justtools.JustTools;
import com.justtools.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = JustTools.MODID)
public class JustToolsEvents {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(ModDataComponents.DEPTH_UPGRADE.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.depth_upgrade").withStyle(ChatFormatting.AQUA));
        }
        if (stack.has(ModDataComponents.AUTO_REPAIR.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.auto_repair").withStyle(ChatFormatting.GREEN));
        }
        if (stack.has(ModDataComponents.LAVA_PROOF.get())) {
            event.getToolTip().add(Component.translatable("tooltip.justtools.lava_proof").withStyle(ChatFormatting.GOLD));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || player.level().getGameTime() % 100 != 0) {
            return;
        }

        // Passive auto-repair for any item in inventory with AUTO_REPAIR (1 durability per 5s)
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.has(ModDataComponents.AUTO_REPAIR.get()) && stack.isDamaged()) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }

        // XP-Mending for held item with AUTO_REPAIR
        ItemStack mainHand = player.getMainHandItem();
        if (!mainHand.isEmpty() && mainHand.has(ModDataComponents.AUTO_REPAIR.get()) && mainHand.isDamaged()) {
            int repairAmount = event.getOrb().getValue() * 2;
            int currentDamage = mainHand.getDamageValue();
            int actualRepair = Math.min(repairAmount, currentDamage);
            mainHand.setDamageValue(currentDamage - actualRepair);
        }
    }
}
