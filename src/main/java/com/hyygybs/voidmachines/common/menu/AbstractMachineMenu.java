package com.hyygybs.voidmachines.common.menu;

import com.hyygybs.voidmachines.common.blockentity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public abstract class AbstractMachineMenu extends AbstractContainerMenu {
    protected static final int DATA_COUNT = 6;

    private final BlockPos blockPos;
    protected final Level level;
    protected final ContainerData data;
    protected final AbstractMachineBlockEntity blockEntity;
    protected final int visibleOutputSlots;
    protected final int machineSlotCount;

    protected AbstractMachineMenu(MenuType<?> menuType, int containerId, Inventory inventory, AbstractMachineBlockEntity blockEntity,
                                  int visibleOutputSlots, int outputColumns, int outputStartX, int outputStartY,
                                  int upgradeX, int upgradeY, int playerInventoryStartY) {
        this(menuType, containerId, inventory, blockEntity, createData(blockEntity), visibleOutputSlots, outputColumns, outputStartX, outputStartY, upgradeX, upgradeY, playerInventoryStartY);
    }

    protected AbstractMachineMenu(MenuType<?> menuType, int containerId, Inventory inventory, BlockPos blockPos,
                                  int visibleOutputSlots, int outputColumns, int outputStartX, int outputStartY,
                                  int upgradeX, int upgradeY, int playerInventoryStartY) {
        this(menuType, containerId, inventory, getBlockEntity(inventory, blockPos), new SimpleContainerData(DATA_COUNT), visibleOutputSlots, outputColumns, outputStartX, outputStartY, upgradeX, upgradeY, playerInventoryStartY);
    }

    private AbstractMachineMenu(MenuType<?> menuType, int containerId, Inventory inventory, AbstractMachineBlockEntity blockEntity, ContainerData data,
                                int visibleOutputSlots, int outputColumns, int outputStartX, int outputStartY,
                                int upgradeX, int upgradeY, int playerInventoryStartY) {
        super(menuType, containerId);
        this.blockEntity = blockEntity;
        this.data = data;
        this.level = inventory.player.level();
        this.blockPos = blockEntity.getBlockPos();
        this.visibleOutputSlots = Math.min(visibleOutputSlots, blockEntity.getOutputSlots());
        this.machineSlotCount = this.visibleOutputSlots + 1;

        for (int slotIndex = 0; slotIndex < this.visibleOutputSlots; slotIndex++) {
            int x = outputStartX + (slotIndex % outputColumns) * 18;
            int y = outputStartY + (slotIndex / outputColumns) * 18;
            this.addSlot(new SlotItemHandler(blockEntity.getOutputHandler(), slotIndex, x, y) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        }

        this.addSlot(new SlotItemHandler(blockEntity.getUpgradeHandler(), 0, upgradeX, upgradeY) {
            @Override
            public int getMaxStackSize() {
                return AbstractMachineMenu.this.blockEntity.getUpgradeHandler().getSlotLimit(0);
            }
        });

        addPlayerInventory(inventory, playerInventoryStartY);
        addDataSlots(data);
    }

    protected static AbstractMachineBlockEntity getBlockEntity(Inventory inventory, BlockPos pos) {
        Level level = inventory.player.level();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AbstractMachineBlockEntity machineBlockEntity) {
            return machineBlockEntity;
        }
        throw new IllegalStateException("Expected machine block entity at " + pos);
    }

    protected static ContainerData createData(AbstractMachineBlockEntity blockEntity) {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> blockEntity.getEnergyStored();
                    case 1 -> blockEntity.getMaxEnergyStored();
                    case 2 -> blockEntity.getProgress();
                    case 3 -> blockEntity.getMaxProgress();
                    case 4 -> blockEntity.getSpeedUpgradeCount();
                    case 5 -> Math.round(blockEntity.getSpeedMultiplier() * 100.0F);
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int getCount() {
                return DATA_COUNT;
            }
        };
    }

    private void addPlayerInventory(Inventory inventory, int startY) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, startY + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column, 8 + column * 18, startY + 58));
        }
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == 0 && visibleOutputSlots > 0) {
            ItemStack extracted = blockEntity.extractOutputForPlayer();
            if (!extracted.isEmpty()) {
                if (!player.getInventory().add(extracted)) {
                    player.drop(extracted, false);
                }
                return true;
            }
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index < machineSlotCount) {
            if (!this.moveItemStackTo(stack, machineSlotCount, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (blockEntity.isSpeedUpgrade(stack)) {
                if (!this.moveItemStackTo(stack, visibleOutputSlots, visibleOutputSlots + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        if (!(level.getBlockEntity(blockPos) instanceof AbstractMachineBlockEntity)) {
            return false;
        }
        return player.distanceToSqr(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D) <= 64.0D;
    }

    public int getEnergyStored() {
        return data.get(0);
    }

    public int getMaxEnergyStored() {
        return data.get(1);
    }

    public int getProgress() {
        return data.get(2);
    }

    public int getMaxProgress() {
        return data.get(3);
    }

    public int getSpeedUpgradeCount() {
        return data.get(4);
    }

    public float getSpeedMultiplier() {
        return data.get(5) / 100.0F;
    }

    public boolean hasOutputSlot() {
        return visibleOutputSlots > 0;
    }
}
