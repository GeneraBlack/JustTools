package com.justtools.menu;

import com.justtools.init.ModArmorMaterials;
import com.justtools.init.ModBlocks;
import com.justtools.init.ModDataComponents;
import com.justtools.init.ModItems;
import com.justtools.init.ModMenuTypes;
import com.justtools.init.ModTiers;
import com.justtools.item.ExcavatorItem;
import com.justtools.item.HammerItem;
import com.justtools.menu.slot.HardeningResultSlot;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

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
    private int materialCost = 1;

    public HardeningMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public HardeningMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.HARDENING_MENU.get(), containerId);
        this.access = access;
        this.player = playerInventory.player;

        // Tool Input Slot
        this.addSlot(new Slot(this.inputSlots, INPUT_SLOT_TOOL, 27, 47));

        // Plate / Repair Material Input Slot (accepts plates, repair ingots/materials, or duplicate tools)
        this.addSlot(new Slot(this.inputSlots, INPUT_SLOT_PLATE, 76, 47) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        });

        // Result Slot
        this.addSlot(new HardeningResultSlot(this, this.player, this.inputSlots, this.resultSlots, 0, 134, 47, access));

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

    public int getMaterialCost() {
        return this.materialCost;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.inputSlots) {
            this.createResult();
        }
    }

    private void createResult() {
        this.materialCost = 1;
        ItemStack toolStack = this.inputSlots.getItem(INPUT_SLOT_TOOL);
        ItemStack plateStack = this.inputSlots.getItem(INPUT_SLOT_PLATE);

        if (!toolStack.isEmpty() && !plateStack.isEmpty()) {
            // Case 1: Hardening Plate -> upgrade base tool to hardened variant (fully repaired & resets anvil penalty)
            if (plateStack.is(ModItems.HARDENING_PLATE.get())) {
                Item hardenedItem = ModItems.getHardenedVariant(toolStack.getItem());
                if (hardenedItem != null) {
                    ItemStack result = new ItemStack(hardenedItem);
                    result.applyComponents(toolStack.getComponents());
                    result.setDamageValue(0);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 2: Hardening Plate -> fully repair an already-hardened or non-upgradeable damaged tool
            if (plateStack.is(ModItems.HARDENING_PLATE.get()) && toolStack.isDamaged()) {
                ItemStack result = toolStack.copy();
                result.setDamageValue(0);
                result.remove(DataComponents.REPAIR_COST);
                this.materialCost = 1;
                this.resultSlots.setItem(0, result);
                this.broadcastChanges();
                return;
            }

            // Case 3: Depth Plate (3x3x2 tunnel mining for tools / Heavy Plating for armor)
            if (plateStack.is(ModItems.DEPTH_PLATE.get())) {
                if (((toolStack.getItem() instanceof HammerItem || toolStack.getItem() instanceof ExcavatorItem)
                        || toolStack.getItem() instanceof ArmorItem)
                        && !toolStack.has(ModDataComponents.DEPTH_UPGRADE.get())) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.DEPTH_UPGRADE.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 4: Echo Plate (Auto-Repair & XP Mending) -> applies to any tool
            if (plateStack.is(ModItems.ECHO_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.AUTO_REPAIR.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.AUTO_REPAIR.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 5: Lava Plate (Obsidian Sealing / Fireproof) -> applies to any tool
            if (plateStack.is(ModItems.LAVA_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.LAVA_PROOF.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
                    result.set(ModDataComponents.LAVA_PROOF.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 6: Overclock Plate (+35% speed & streak haste) -> applies to any tool
            if (plateStack.is(ModItems.OVERCLOCK_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.OVERCLOCK.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.OVERCLOCK.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 7: Breeze Plate (Water & air speed penalty removal) -> applies to any tool
            if (plateStack.is(ModItems.BREEZE_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.BREEZE_CHARGE.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.BREEZE_CHARGE.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 8: Auto-Smelt Plate (Direct smelting of mined drops) -> applies to any tool
            if (plateStack.is(ModItems.AUTO_SMELT_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.AUTO_SMELT.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.AUTO_SMELT.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 9: Reinforced Frame Plate (50% zero-damage chance) -> applies to any tool
            if (plateStack.is(ModItems.REINFORCED_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.REINFORCED.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.REINFORCED.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 10: Amethyst Shield Plate (Break prevention at 1 dur & shock absorption) -> applies to any tool
            if (plateStack.is(ModItems.AMETHYST_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.AMETHYST_SHIELD.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.AMETHYST_SHIELD.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 11: Photosynthesis Plate (Continuous sunlight repair) -> applies to any tool
            if (plateStack.is(ModItems.MOSS_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.PHOTOSYNTHESIS.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.PHOTOSYNTHESIS.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 12: Magnetic Vacuum Plate (Pulls all drops directly into inventory) -> applies to any tool
            if (plateStack.is(ModItems.MAGNETIC_PLATE.get())) {
                if (!toolStack.has(ModDataComponents.MAGNETIC.get()) && toolStack.isDamageableItem()) {
                    ItemStack result = toolStack.copy();
                    result.set(ModDataComponents.MAGNETIC.get(), true);
                    result.remove(DataComponents.REPAIR_COST);
                    this.materialCost = 1;
                    this.resultSlots.setItem(0, result);
                    this.broadcastChanges();
                    return;
                }
            }

            // Case 13: Raw Material Repair (Ingots, Diamonds, Netherite Scraps, Planks, Cobblestone, etc.)
            // Restores 50% max durability per material, 0 XP, resets anvil penalty!
            if (toolStack.isDamaged() && isRepairMaterial(toolStack, plateStack)) {
                int currentDamage = toolStack.getDamageValue();
                int maxDamage = toolStack.getMaxDamage();
                int repairPerItem = Math.max(1, maxDamage / 2);
                int needed = (int) Math.ceil((double) currentDamage / repairPerItem);
                int toConsume = Math.min(needed, plateStack.getCount());
                int repairAmount = toConsume * repairPerItem;
                int newDamage = Math.max(0, currentDamage - repairAmount);

                ItemStack result = toolStack.copy();
                result.setDamageValue(newDamage);
                result.remove(DataComponents.REPAIR_COST);
                this.materialCost = toConsume;
                this.resultSlots.setItem(0, result);
                this.broadcastChanges();
                return;
            }

            // Case 14: Same Tool Repair (Combining two tools of the same type)
            if (toolStack.isDamaged() && plateStack.is(toolStack.getItem()) && plateStack.isDamageableItem()) {
                int currentDamage = toolStack.getDamageValue();
                int otherDurability = plateStack.getMaxDamage() - plateStack.getDamageValue();
                int bonus = (int) (toolStack.getMaxDamage() * 0.12);
                int newDamage = Math.max(0, currentDamage - otherDurability - bonus);

                ItemStack result = toolStack.copy();
                result.setDamageValue(newDamage);
                result.remove(DataComponents.REPAIR_COST);

                // Combine enchantments
                ItemEnchantments ench0 = toolStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                ItemEnchantments ench1 = plateStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
                ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ench0);
                for (var entry : ench1.entrySet()) {
                    mutable.upgrade(entry.getKey(), entry.getIntValue());
                }
                EnchantmentHelper.setEnchantments(result, mutable.toImmutable());

                // Preserve/merge special upgrades
                if (plateStack.has(ModDataComponents.DEPTH_UPGRADE.get())) {
                    result.set(ModDataComponents.DEPTH_UPGRADE.get(), true);
                }
                if (plateStack.has(ModDataComponents.AUTO_REPAIR.get())) {
                    result.set(ModDataComponents.AUTO_REPAIR.get(), true);
                }
                if (plateStack.has(ModDataComponents.LAVA_PROOF.get())) {
                    result.set(ModDataComponents.LAVA_PROOF.get(), true);
                    result.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
                }
                if (plateStack.has(ModDataComponents.OVERCLOCK.get())) {
                    result.set(ModDataComponents.OVERCLOCK.get(), true);
                }
                if (plateStack.has(ModDataComponents.BREEZE_CHARGE.get())) {
                    result.set(ModDataComponents.BREEZE_CHARGE.get(), true);
                }
                if (plateStack.has(ModDataComponents.AUTO_SMELT.get())) {
                    result.set(ModDataComponents.AUTO_SMELT.get(), true);
                }
                if (plateStack.has(ModDataComponents.REINFORCED.get())) {
                    result.set(ModDataComponents.REINFORCED.get(), true);
                }
                if (plateStack.has(ModDataComponents.AMETHYST_SHIELD.get())) {
                    result.set(ModDataComponents.AMETHYST_SHIELD.get(), true);
                }
                if (plateStack.has(ModDataComponents.PHOTOSYNTHESIS.get())) {
                    result.set(ModDataComponents.PHOTOSYNTHESIS.get(), true);
                }
                if (plateStack.has(ModDataComponents.MAGNETIC.get())) {
                    result.set(ModDataComponents.MAGNETIC.get(), true);
                }

                this.materialCost = 1;
                this.resultSlots.setItem(0, result);
                this.broadcastChanges();
                return;
            }
        }

        this.materialCost = 1;
        this.resultSlots.setItem(0, ItemStack.EMPTY);
        this.broadcastChanges();
    }

    public static boolean isRepairMaterial(ItemStack tool, ItemStack material) {
        if (tool.isEmpty() || material.isEmpty()) {
            return false;
        }

        // Vanilla tool tier check
        if (tool.getItem().isValidRepairItem(tool, material)) {
            return true;
        }

        // Netherite Scrap support for Netherite tools and armor
        if (tool.getItem() instanceof TieredItem tiered) {
            Tier tier = tiered.getTier();
            if ((tier == Tiers.NETHERITE || tier == ModTiers.HARDENED_NETHERITE) && material.is(Items.NETHERITE_SCRAP)) {
                return true;
            }
        }
        if (tool.getItem() instanceof ArmorItem armor) {
            var mat = armor.getMaterial();
            if ((mat == ArmorMaterials.NETHERITE || mat == ModArmorMaterials.HARDENED_NETHERITE) && material.is(Items.NETHERITE_SCRAP)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAnyRepairMaterial(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.is(Items.COPPER_INGOT)
                || stack.is(Items.IRON_INGOT)
                || stack.is(Items.GOLD_INGOT)
                || stack.is(Items.DIAMOND)
                || stack.is(Items.NETHERITE_INGOT)
                || stack.is(Items.NETHERITE_SCRAP)
                || stack.is(Items.LEATHER)
                || stack.is(ItemTags.PLANKS)
                || stack.is(ItemTags.STONE_TOOL_MATERIALS)
                || stack.is(Items.COBBLED_DEEPSLATE)
                || stack.is(ModItems.HARDENING_PLATE.get());
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
                boolean isUpgradePlate = itemstack1.is(ModItems.HARDENING_PLATE.get())
                        || itemstack1.is(ModItems.DEPTH_PLATE.get())
                        || itemstack1.is(ModItems.ECHO_PLATE.get())
                        || itemstack1.is(ModItems.LAVA_PLATE.get())
                        || itemstack1.is(ModItems.OVERCLOCK_PLATE.get())
                        || itemstack1.is(ModItems.BREEZE_PLATE.get())
                        || itemstack1.is(ModItems.AUTO_SMELT_PLATE.get())
                        || itemstack1.is(ModItems.REINFORCED_PLATE.get())
                        || itemstack1.is(ModItems.AMETHYST_PLATE.get())
                        || itemstack1.is(ModItems.MOSS_PLATE.get())
                        || itemstack1.is(ModItems.MAGNETIC_PLATE.get());

                ItemStack toolInSlot0 = this.inputSlots.getItem(INPUT_SLOT_TOOL);
                boolean isTool = itemstack1.isDamageableItem();

                boolean movedToStation = false;
                if (isUpgradePlate) {
                    movedToStation = this.moveItemStackTo(itemstack1, INPUT_SLOT_PLATE, INPUT_SLOT_PLATE + 1, false);
                } else if (!toolInSlot0.isEmpty() && (isRepairMaterial(toolInSlot0, itemstack1) || itemstack1.is(toolInSlot0.getItem()))) {
                    movedToStation = this.moveItemStackTo(itemstack1, INPUT_SLOT_PLATE, INPUT_SLOT_PLATE + 1, false);
                } else if (isTool) {
                    movedToStation = this.moveItemStackTo(itemstack1, INPUT_SLOT_TOOL, INPUT_SLOT_TOOL + 1, false);
                    if (!movedToStation && !toolInSlot0.isEmpty() && itemstack1.is(toolInSlot0.getItem())) {
                        movedToStation = this.moveItemStackTo(itemstack1, INPUT_SLOT_PLATE, INPUT_SLOT_PLATE + 1, false);
                    }
                } else if (isAnyRepairMaterial(itemstack1)) {
                    movedToStation = this.moveItemStackTo(itemstack1, INPUT_SLOT_PLATE, INPUT_SLOT_PLATE + 1, false);
                }

                if (!movedToStation) {
                    if (slotIndex < INV_SLOT_END) {
                        if (!this.moveItemStackTo(itemstack1, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
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
