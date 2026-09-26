package com.justtools.item;

import com.justtools.init.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModCrossbowItem extends CrossbowItem {
    private final Supplier<Ingredient> repairIngredient;
    private final float damageBonus;
    private final int baseChargeTime;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public ModCrossbowItem(int durability, float damageBonus, int baseChargeTime, Supplier<Ingredient> repairIngredient, Properties properties) {
        super(properties.durability(durability));
        this.repairIngredient = repairIngredient;
        this.damageBonus = damageBonus;
        this.baseChargeTime = baseChargeTime;
    }

    public float getDamageBonus() {
        return this.damageBonus;
    }

    public int getBaseChargeTime() {
        return this.baseChargeTime;
    }

    public int getCustomChargeDuration(ItemStack stack, LivingEntity entity) {
        float baseSeconds = this.baseChargeTime / 20.0F;
        float modified = EnchantmentHelper.modifyCrossbowChargingTime(stack, entity, baseSeconds);
        int ticks = net.minecraft.util.Mth.floor(modified * 20.0F);
        if (stack.has(ModDataComponents.OVERCLOCK.get())) {
            ticks = (int) Math.max(5, ticks * 0.7F); // 30% faster reload with Redstone Turbine
        }
        return Math.max(ticks, 1);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.STRING)
                || repair.is(Items.TRIPWIRE_HOOK)
                || repair.is(Items.IRON_INGOT)
                || (this.repairIngredient != null && this.repairIngredient.get().test(repair))
                || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        amount = ToolDamageHandler.handleDamage(stack, amount, entity);
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        return super.use(level, player, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int timeLeft) {
        if (!level.isClientSide) {
            int duration = getCustomChargeDuration(stack, entity);
            float progress = (float) (this.getUseDuration(stack, entity) - timeLeft) / (float) duration;
            if (progress < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (progress >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_START.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            if (progress >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_MIDDLE.value(), SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int charge = this.getUseDuration(stack, entity) - timeLeft;
        float progress = (float) charge / (float) getCustomChargeDuration(stack, entity);
        if (progress >= 1.0F && !isCharged(stack) && tryLoadProjectiles(entity, stack)) {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_END.value(),
                    entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    private static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack weapon) {
        ItemStack ammo = shooter.getProjectile(weapon);
        List<ItemStack> list = draw(weapon, ammo, shooter);
        if (!list.isEmpty()) {
            weapon.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
            return true;
        }
        return false;
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack weapon, ItemStack ammo) {
        arrow.setBaseDamage(arrow.getBaseDamage() + this.damageBonus);

        // Thermal Core: Flame Bolts
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
