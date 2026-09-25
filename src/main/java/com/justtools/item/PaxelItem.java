package com.justtools.item;

import com.justtools.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class PaxelItem extends DiggerItem {
    public PaxelItem(Tier tier, Properties properties) {
        super(tier, ModTags.Blocks.MINEABLE_WITH_PAXEL, properties);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (state.is(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                || state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_HOE)) {
            return !state.is(this.getTier().getIncorrectBlocksForDrops());
        }
        return super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(ModTags.Blocks.MINEABLE_WITH_PAXEL)
                || state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_HOE)) {
            return this.getTier().getSpeed();
        }
        return 1.0F;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        // Extinguish Campfire (like shovel)
        if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
            level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
            CampfireBlock.dowse(player, level, pos, state);
            BlockState unlitState = state.setValue(CampfireBlock.LIT, false);
            level.setBlock(pos, unlitState, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, unlitState));
            if (player != null) {
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Tilling when sneaking (hoe priority)
        if (player != null && player.isShiftKeyDown()) {
            BlockState hoeModified = state.getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
            if (hoeModified != null) {
                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, hoeModified, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, hoeModified));
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        // Standard Right-click: Axe strip -> Scrape -> Wax off -> Shovel flatten -> Hoe till
        ItemAbility[] abilitiesToTry = new ItemAbility[]{
                ItemAbilities.AXE_STRIP,
                ItemAbilities.AXE_SCRAPE,
                ItemAbilities.AXE_WAX_OFF,
                ItemAbilities.SHOVEL_FLATTEN,
                ItemAbilities.HOE_TILL
        };

        for (ItemAbility ability : abilitiesToTry) {
            BlockState modified = state.getToolModifiedState(context, ability, false);
            if (modified != null) {
                if (ability == ItemAbilities.AXE_STRIP) {
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (ability == ItemAbilities.AXE_SCRAPE) {
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (ability == ItemAbilities.AXE_WAX_OFF) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (ability == ItemAbilities.SHOVEL_FLATTEN) {
                    level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (ability == ItemAbilities.HOE_TILL) {
                    level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                level.setBlock(pos, modified, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, modified));
                if (player != null) {
                    stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return super.useOn(context);
    }
}
