package com.justtools.event;

import com.justtools.JustTools;
import com.justtools.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
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
    }

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
            if (player.isEyeInFluid(FluidTags.WATER) && !player.hasEffect(MobEffects.DIG_SPEED)) {
                speed *= 5.0F;
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

        // Amethyst Shield: prevent tool from breaking at 1 durability
        if (tool.has(ModDataComponents.AMETHYST_SHIELD.get())) {
            if (tool.getDamageValue() >= tool.getMaxDamage() - 1) {
                event.setCanceled(true);
                player.displayClientMessage(Component.translatable("message.justtools.break_prevented").withStyle(ChatFormatting.LIGHT_PURPLE), true);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.2F);
                return;
            }
        }

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

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (!left.isEmpty()) {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(left.getItem());
            if (key.getNamespace().equals(JustTools.MODID)) {
                // Prevent "Too Expensive!" lock in anvil by capping level cost below 40
                if (event.getCost() >= 40) {
                    event.setCost(39);
                }
            }
        }
    }
}
