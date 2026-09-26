package com.justtools.item;

import com.justtools.init.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModBowItem extends BowItem {
    private final Supplier<Ingredient> repairIngredient;
    private final float arrowDamageBonus;
    private final float drawSpeedMultiplier;

    public ModBowItem(int durability, float arrowDamageBonus, float drawSpeedMultiplier, Supplier<Ingredient> repairIngredient, Properties properties) {
        super(properties.durability(durability));
        this.repairIngredient = repairIngredient;
        this.arrowDamageBonus = arrowDamageBonus;
        this.drawSpeedMultiplier = drawSpeedMultiplier;
    }

    public float getArrowDamageBonus() {
        return this.arrowDamageBonus;
    }

    public float getDrawSpeedMultiplier() {
        return this.drawSpeedMultiplier;
    }

    public float getDrawTime(ItemStack stack) {
        float time = 20.0F / Math.max(0.1F, this.drawSpeedMultiplier);
        if (stack.has(ModDataComponents.OVERCLOCK.get())) {
            time /= 1.35F; // 35% faster draw with Redstone Turbine
        }
        return Math.max(5.0F, time);
    }

    public float getCustomPowerForTime(int charge, ItemStack stack) {
        float f = (float) charge / getDrawTime(stack);
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.STRING)
                || repair.is(Items.STICK)
                || (this.repairIngredient != null && this.repairIngredient.get().test(repair))
                || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        amount = ToolDamageHandler.handleDamage(stack, amount, entity);
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            if (!itemstack.isEmpty()) {
                int i = this.getUseDuration(stack, entity) - timeLeft;
                float f = getCustomPowerForTime(i, stack);
                if (!((double) f < 0.1D)) {
                    List<ItemStack> list = draw(stack, itemstack, player);
                    if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                        this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, null);
                    }

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.ARROW_SHOOT,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                    );
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack weapon, ItemStack ammo) {
        arrow.setBaseDamage(arrow.getBaseDamage() + this.arrowDamageBonus);

        // Thermal Core: Flame Arrows
        if (weapon.has(ModDataComponents.AUTO_SMELT.get())) {
            arrow.igniteForSeconds(100);
        }

        // Wind Core: Gale Velocity
        if (weapon.has(ModDataComponents.BREEZE_CHARGE.get())) {
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(1.35));
        }

        return super.customArrow(arrow, weapon, ammo);
    }
}
