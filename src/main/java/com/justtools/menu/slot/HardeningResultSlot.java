package com.justtools.menu.slot;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HardeningResultSlot extends Slot {
    private final Container inputSlots;
    private final Player player;
    private final ContainerLevelAccess access;

    public HardeningResultSlot(Player player, Container inputSlots, Container resultSlots, int slotIndex, int x, int y, ContainerLevelAccess access) {
        super(resultSlots, slotIndex, x, y);
        this.player = player;
        this.inputSlots = inputSlots;
        this.access = access;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.inputSlots.removeItem(0, 1);
        this.inputSlots.removeItem(1, 1);

        this.access.execute((level, pos) -> {
            level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
        });

        super.onTake(player, stack);
    }
}
