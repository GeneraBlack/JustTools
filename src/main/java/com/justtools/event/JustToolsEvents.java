package com.justtools.event;

import com.justtools.JustTools;
import com.justtools.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = JustTools.MODID)
public class JustToolsEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            return;
        }
        Level level = player.level();
        long gameTime = level.getGameTime();

        // 1. Passive auto-repair (every 100 ticks = 5s)
        if (gameTime % 100 == 0) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (!stack.isEmpty() && stack.has(ModDataComponents.AUTO_REPAIR.get()) && stack.isDamaged()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                }
            }
        }

        // 2. Photosynthesis: repairs in sunlight or on moss/grass (every 60 ticks = 3s)
        if (gameTime % 60 == 0) {
            BlockPos pos = player.blockPosition();
            boolean inSunlight = level.isDay() && level.canSeeSky(pos);
            BlockState belowState = level.getBlockState(pos.below());
            boolean onNature = belowState.is(BlockTags.DIRT) || belowState.is(Blocks.MOSS_BLOCK);

            if (inSunlight || onNature) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    if (!stack.isEmpty() && stack.has(ModDataComponents.PHOTOSYNTHESIS.get()) && stack.isDamaged()) {
                        stack.setDamageValue(stack.getDamageValue() - 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.isEmpty()) return;

        // Overclock: +35% mining speed
        if (mainHand.has(ModDataComponents.OVERCLOCK.get())) {
            event.setNewSpeed(event.getNewSpeed() * 1.35F);
        }

        // Breeze: ignore underwater & airborne speed penalties
        if (mainHand.has(ModDataComponents.BREEZE_CHARGE.get())) {
            float speed = event.getNewSpeed();
            // Only compensate if the player doesn't have Aqua Affinity on their helmet.
            // Check by comparing to the item's base speed — if underwater penalty was applied,
            // the speed will be ~5x lower than expected.
            if (player.isEyeInFluid(FluidTags.WATER)) {
                // Check helmet for aqua affinity via data component
                ItemStack helmet = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
                boolean hasAquaAffinity = false;
                if (!helmet.isEmpty()) {
                    var enchantments = helmet.getOrDefault(net.minecraft.core.component.DataComponents.ENCHANTMENTS,
                            net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY);
                    var registry = player.level().registryAccess()
                            .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
                    var aquaHolder = registry.get(net.minecraft.world.item.enchantment.Enchantments.AQUA_AFFINITY);
                    if (aquaHolder.isPresent()) {
                        hasAquaAffinity = enchantments.getLevel(aquaHolder.get()) > 0;
                    }
                }
                if (!hasAquaAffinity) {
                    speed *= 5.0F;
                }
            }
            if (!player.onGround()) {
                speed *= 5.0F;
            }
            event.setNewSpeed(speed);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty()) return;

        // Overclock: 2-second Haste boost on continuous mining streaks
        if (tool.has(ModDataComponents.OVERCLOCK.get())) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, 0, false, false, false));
        }
    }

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (event.getLevel().isClientSide()) return;
        ItemStack tool = event.getTool();
        if (tool.isEmpty()) return;
        ServerLevel level = event.getLevel();
        Entity breaker = event.getBreaker();

        // 1. Auto-Smelt: directly smelt drops
        if (tool.has(ModDataComponents.AUTO_SMELT.get())) {
            for (ItemEntity itemEntity : event.getDrops()) {
                ItemStack original = itemEntity.getItem();
                var recipeHolder = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(original), level);
                if (recipeHolder.isPresent()) {
                    ItemStack result = recipeHolder.get().value().assemble(new SingleRecipeInput(original), level.registryAccess());
                    if (!result.isEmpty()) {
                        result.setCount(result.getCount() * original.getCount());
                        itemEntity.setItem(result);
                        level.sendParticles(ParticleTypes.FLAME, itemEntity.getX(), itemEntity.getY() + 0.2, itemEntity.getZ(), 3, 0.1, 0.1, 0.1, 0.02);
                    }
                }
            }
        }

        // 2. Magnetic: pull drops directly into player's inventory
        if (tool.has(ModDataComponents.MAGNETIC.get()) && breaker instanceof Player player) {
            for (ItemEntity itemEntity : event.getDrops()) {
                ItemStack drop = itemEntity.getItem();
                if (player.getInventory().add(drop)) {
                    itemEntity.discard();
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 1.5F);
                } else {
                    itemEntity.setPos(player.getX(), player.getY() + 0.2, player.getZ());
                }
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
