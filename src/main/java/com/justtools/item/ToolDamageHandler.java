package com.justtools.item;

import com.justtools.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ToolDamageHandler {

    public static <T extends LivingEntity> int handleDamage(ItemStack stack, int amount, T entity) {
        if (stack.has(ModDataComponents.AMETHYST_SHIELD.get())) {
            // Shock absorption: reduce wrong block / entity attack penalty to 1
            if (amount > 1) {
                amount = 1;
            }
            int current = stack.getDamageValue();
            int max = stack.getMaxDamage();
            // Break prevention: stop right at 1 durability
            if (current + amount >= max) {
                amount = Math.max(0, max - 1 - current);
                if (entity instanceof Player player && !player.level().isClientSide) {
                    player.displayClientMessage(Component.translatable("message.justtools.break_prevented").withStyle(ChatFormatting.LIGHT_PURPLE), true);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.2F);
                }
            }
        }

        if (stack.has(ModDataComponents.REINFORCED.get())) {
            // 50% chance of zero durability loss
            if (entity.getRandom().nextBoolean()) {
                return 0;
            }
        }

        return amount;
    }
}
