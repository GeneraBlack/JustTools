package com.justtools.item;

import com.justtools.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ExcavatorItem extends ShovelItem {
    private static final ThreadLocal<Boolean> IS_BREAKING_AREA = ThreadLocal.withInitial(() -> false);

    public ExcavatorItem(Tier tier, int durability, Properties properties) {
        super(new CustomDurabilityTier(tier, durability), properties);
    }

    public ExcavatorItem(Tier tier, int durability) {
        this(tier, durability, new Properties());
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, java.util.function.Consumer<net.minecraft.world.item.Item> onBroken) {
        amount = ToolDamageHandler.handleDamage(stack, amount, entity);
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean result = true;
        if (!IS_BREAKING_AREA.get()) {
            result = super.mineBlock(stack, level, state, pos, entityLiving);
        }

        if (!level.isClientSide && !IS_BREAKING_AREA.get() && entityLiving instanceof ServerPlayer player) {
            mineArea(level, player, stack, pos);
        }

        return result;
    }

    private void mineArea(Level level, ServerPlayer player, ItemStack stack, BlockPos origin) {
        Direction dir = getHitDirection(player, origin);
        Direction.Axis axis = dir.getAxis();
        int maxDepth = stack.has(ModDataComponents.DEPTH_UPGRADE.get()) ? 2 : 1;

        for (int depth = 0; depth < maxDepth; depth++) {
            BlockPos sliceOrigin = origin.relative(dir.getOpposite(), depth);

            for (int u = -1; u <= 1; u++) {
                for (int v = -1; v <= 1; v++) {
                    if (depth == 0 && u == 0 && v == 0) {
                        continue; // Center primary block already broken
                    }

                    BlockPos targetPos;
                    if (axis == Direction.Axis.Y) {
                        targetPos = sliceOrigin.offset(u, 0, v);
                    } else if (axis == Direction.Axis.Z) {
                        targetPos = sliceOrigin.offset(u, v, 0);
                    } else {
                        targetPos = sliceOrigin.offset(0, v, u);
                    }

                    BlockState targetState = level.getBlockState(targetPos);
                    if (targetState.isAir() || targetState.getDestroySpeed(level, targetPos) < 0) {
                        continue;
                    }

                    if (!targetState.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                        continue;
                    }

                    if (!this.isCorrectToolForDrops(stack, targetState)) {
                        continue;
                    }

                    IS_BREAKING_AREA.set(true);
                    try {
                        if (player.gameMode.destroyBlock(targetPos)) {
                            if (stack.isEmpty()) {
                                return;
                            }
                        }
                    } finally {
                        IS_BREAKING_AREA.remove();
                    }
                }
            }
        }
    }

    public static Direction getHitDirection(Player player, BlockPos origin) {
        HitResult hit = player.pick(20.0D, 0.0F, false);
        if (hit instanceof BlockHitResult blockHit && blockHit.getBlockPos().equals(origin)) {
            return blockHit.getDirection();
        }
        float pitch = player.getXRot();
        if (pitch > 45.0F) return Direction.DOWN;
        if (pitch < -45.0F) return Direction.UP;
        return player.getDirection().getOpposite();
    }
}
