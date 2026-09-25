package com.justtools.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class CustomDurabilityTier implements Tier {
    private final Tier parent;
    private final int uses;

    public CustomDurabilityTier(Tier parent, int uses) {
        this.parent = parent;
        this.uses = uses;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return parent.getSpeed();
    }

    @Override
    public float getAttackDamageBonus() {
        return parent.getAttackDamageBonus();
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return parent.getIncorrectBlocksForDrops();
    }

    @Override
    public int getEnchantmentValue() {
        return parent.getEnchantmentValue();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return parent.getRepairIngredient();
    }
}
