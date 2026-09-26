package com.justtools.event;

import com.justtools.JustTools;
import com.justtools.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = JustTools.MODID)
public class JustToolsEvents {

    public static boolean hasArmorWith(Player player, DataComponentType<?> component) {
        for (ItemStack armor : player.getArmorSlots()) {
            if (!armor.isEmpty() && armor.has(component)) {
                return true;
            }
        }
        return false;
    }

    public static int countArmorWith(Player player, DataComponentType<?> component) {
        int count = 0;
        for (ItemStack armor : player.getArmorSlots()) {
            if (!armor.isEmpty() && armor.has(component)) {
                count++;
            }
        }
        return count;
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
            // Inventory tools
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (!stack.isEmpty() && stack.has(ModDataComponents.AUTO_REPAIR.get()) && stack.isDamaged()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                }
            }
            // Equipped armor
            for (ItemStack armor : player.getArmorSlots()) {
                if (!armor.isEmpty() && armor.has(ModDataComponents.AUTO_REPAIR.get()) && armor.isDamaged()) {
                    armor.setDamageValue(armor.getDamageValue() - 1);
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
                // Inventory tools
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);
                    if (!stack.isEmpty() && stack.has(ModDataComponents.PHOTOSYNTHESIS.get()) && stack.isDamaged()) {
                        stack.setDamageValue(stack.getDamageValue() - 1);
                    }
                }
                // Equipped armor
                for (ItemStack armor : player.getArmorSlots()) {
                    if (!armor.isEmpty() && armor.has(ModDataComponents.PHOTOSYNTHESIS.get()) && armor.isDamaged()) {
                        armor.setDamageValue(armor.getDamageValue() - 1);
                    }
                }
                // Daytime regeneration from Photosynthesis armor
                if (inSunlight && hasArmorWith(player, ModDataComponents.PHOTOSYNTHESIS.get())) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 0, false, false, false));
                }
            }
        }

        // 3. Armor Plate: Lava Proof (clears fire, gives Fire Resistance in lava)
        if (hasArmorWith(player, ModDataComponents.LAVA_PROOF.get())) {
            if (player.isOnFire()) {
                player.clearFire();
            }
            if (player.isInLava()) {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, false, false, false));
            }
        }

        // 4. Armor Plate: Overclock (Kinetic Boost: +15% movement speed)
        if (gameTime % 20 == 0 && hasArmorWith(player, ModDataComponents.OVERCLOCK.get())) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, false, false, false));
        }

        // 5. Armor Plate: Auto-Smelt (Flame Barrier: freeze immunity from Powder Snow)
        if (hasArmorWith(player, ModDataComponents.AUTO_SMELT.get())) {
            if (player.getTicksFrozen() > 0) {
                player.setTicksFrozen(0);
            }
        }

        // 6. Armor Plate: Magnetic Vacuum (Item Magnet Aura within 8 blocks)
        if (gameTime % 10 == 0 && hasArmorWith(player, ModDataComponents.MAGNETIC.get())) {
            AABB magnetBox = player.getBoundingBox().inflate(8.0);
            for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, magnetBox)) {
                if (item.isAlive() && !item.hasPickUpDelay()) {
                    Vec3 motion = player.position().add(0, 0.5, 0).subtract(item.position()).normalize().scale(0.35);
                    item.setDeltaMovement(motion);
                }
            }
        }

        // 7. Armor Plate: Amethyst Shield (Armor shatter protection safeguard)
        for (ItemStack armor : player.getArmorSlots()) {
            if (!armor.isEmpty() && armor.has(ModDataComponents.AMETHYST_SHIELD.get())) {
                if (armor.getDamageValue() >= armor.getMaxDamage()) {
                    armor.setDamageValue(armor.getMaxDamage() - 1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            // Wind Core Armor: immune to fall damage
            if (hasArmorWith(player, ModDataComponents.BREEZE_CHARGE.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            // Lava Proof Armor: immune to all fire and lava damage
            if (event.getSource().is(DamageTypeTags.IS_FIRE) && hasArmorWith(player, ModDataComponents.LAVA_PROOF.get())) {
                event.setCanceled(true);
                return;
            }

            // Heavy Plating (Depth Plate on Armor): 12% damage reduction per piece installed
            int depthPlates = countArmorWith(player, ModDataComponents.DEPTH_UPGRADE.get());
            if (depthPlates > 0) {
                float factor = Math.max(0.5F, 1.0F - (0.12F * depthPlates));
                event.setAmount(event.getAmount() * factor);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamagePost(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Player player) {
            // Flame Barrier (Auto-Smelt on Armor): set attacker on fire for 4 seconds
            if (hasArmorWith(player, ModDataComponents.AUTO_SMELT.get())) {
                Entity attacker = event.getSource().getEntity();
                if (attacker != null) {
                    attacker.igniteForSeconds(4.0F);
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
            java.util.Iterator<ItemEntity> it = event.getDrops().iterator();
            while (it.hasNext()) {
                ItemEntity itemEntity = it.next();
                ItemStack drop = itemEntity.getItem();
                if (player.getInventory().add(drop)) {
                    it.remove();
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

        int orbValue = event.getOrb().getValue();
        int repairAvailable = orbValue * 2;

        // 1. XP-Mending for held item with AUTO_REPAIR
        ItemStack mainHand = player.getMainHandItem();
        if (!mainHand.isEmpty() && mainHand.has(ModDataComponents.AUTO_REPAIR.get()) && mainHand.isDamaged()) {
            int currentDamage = mainHand.getDamageValue();
            int actualRepair = Math.min(repairAvailable, currentDamage);
            mainHand.setDamageValue(currentDamage - actualRepair);
            repairAvailable -= actualRepair;
        }

        // 2. XP-Mending for offhand item with AUTO_REPAIR
        if (repairAvailable > 0) {
            ItemStack offHand = player.getOffhandItem();
            if (!offHand.isEmpty() && offHand.has(ModDataComponents.AUTO_REPAIR.get()) && offHand.isDamaged()) {
                int currentDamage = offHand.getDamageValue();
                int actualRepair = Math.min(repairAvailable, currentDamage);
                offHand.setDamageValue(currentDamage - actualRepair);
                repairAvailable -= actualRepair;
            }
        }

        // 3. XP-Mending for equipped armor with AUTO_REPAIR
        if (repairAvailable > 0) {
            for (ItemStack armor : player.getArmorSlots()) {
                if (!armor.isEmpty() && armor.has(ModDataComponents.AUTO_REPAIR.get()) && armor.isDamaged()) {
                    int currentDamage = armor.getDamageValue();
                    int actualRepair = Math.min(repairAvailable, currentDamage);
                    armor.setDamageValue(currentDamage - actualRepair);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) return;
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            boolean hasMagnetic = (!mainHand.isEmpty() && mainHand.has(ModDataComponents.MAGNETIC.get()))
                    || (!offHand.isEmpty() && offHand.has(ModDataComponents.MAGNETIC.get()));
            if (hasMagnetic) {
                Level level = player.level();
                for (ItemEntity itemEntity : event.getDrops()) {
                    ItemStack drop = itemEntity.getItem();
                    if (player.getInventory().add(drop)) {
                        itemEntity.discard();
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 1.5F);
                    }
                }
            }
        }
    }
}
