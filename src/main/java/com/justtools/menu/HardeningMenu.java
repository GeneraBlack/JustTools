package com.justtools.menu;

import com.justtools.init.ModBlocks;
import com.justtools.init.ModItems;
import com.justtools.init.ModMenuTypes;
import com.justtools.menu.slot.HardeningResultSlot;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HardeningMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT_TOOL = 0;
    public static final int INPUT_SLOT_PLATE = 1;
    public static final int RESULT_SLOT = 2;
    public static final int INV_SLOT_START = 3;
    public static final int INV_SLOT_END = 30;
    public static final int USE_ROW_SLOT_START = 30;
    public static final int USE_ROW_SLOT_END = 39;

    private final Container inputSlots = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            HardeningMenu.this.slotsChanged(this);
        }
    };
    private final ResultContainer resultSlots = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Player player;

    public HardeningMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public HardeningMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.HARDENING_MENU.get(), containerId);
        this.access = access;
        this.player = playerInventory.player;

        // Tool Input Slot
        this.addSlot(new Slot(this.inputSlots, INPUT_SLOT_TOOL, 27, 47));

        // Plate Input Slot
        this.addSlot(new Slot(this.inputSlots, INPUT_SLOT_PLATE, 76, 47) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.HARDENING_PLATE.get());
            }
        });

        // Result Slot
        this.addSlot(new HardeningResultSlot(this.player, this.inputSlots, this.resultSlots, 0, 134, 47, access));

        // Player Inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.inputSlots) {
            this.createResult();
        }
    }

    private void createResult() {
        ItemStack toolStack = this.inputSlots.getItem(INPUT_SLOT_TOOL);
        ItemStack plateStack = this.inputSlots.getItem(INPUT_SLOT_PLATE);

        if (!toolStack.isEmpty() && !plateStack.isEmpty() && plateStack.is(ModItems.HARDENING_PLATE.get())) {
            Item hardenedItem = ModItems.getHardenedVariant(toolStack.getItem());
            if (hardenedItem != null) {
                ItemStack result = new ItemStack(hardenedItem);

                if (toolStack.has(DataComponents.ENCHANTMENTS)) {
                    result.set(DataComponents.ENCHANTMENTS, toolStack.get(DataComponents.ENCHANTMENTS));
                }
                if (toolStack.has(DataComponents.CUSTOM_NAME)) {
                    result.set(DataComponents.CUSTOM_NAME, toolStack.get(DataComponents.CUSTOM_NAME));
                }
                if (toolStack.has(DataComponents.REPAIR_COST)) {
                    result.set(DataComponents.REPAIR_COST, toolStack.get(DataComponents.REPAIR_COST));
                }

                this.resultSlots.setItem(0, result);
                this.broadcastChanges();
                return;
            }
        }

        this.resultSlots.setItem(0, ItemStack.EMPTY);
        this.broadcastChanges();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.inputSlots));
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.HARDENING_STATION.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (slotIndex == RESULT_SLOT) {
                if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (slotIndex == INPUT_SLOT_TOOL || slotIndex == INPUT_SLOT_PLATE) {
                if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= INV_SLOT_START && slotIndex < USE_ROW_SLOT_END) {
                if (itemstack1.is(ModItems.HARDENING_PLATE.get())) {
                    if (!this.moveItemStackTo(itemstack1, INPUT_SLOT_PLATE, INPUT_SLOT_PLATE + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ModItems.getHardenedVariant(itemstack1.getItem()) != null) {
                    if (!this.moveItemStackTo(itemstack1, INPUT_SLOT_TOOL, INPUT_SLOT_TOOL + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex < INV_SLOT_END) {
                    if (!this.moveItemStackTo(itemstack1, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
