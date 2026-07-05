package com.hyygybs.voidmachines.common.blockentity;

import com.hyygybs.voidmachines.common.registration.VMItems;
import com.hyygybs.voidmachines.common.util.SimpleEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements MenuProvider {
    public static final int MAX_SPEED_UPGRADES = 8;

    protected final SimpleEnergyStorage energyStorage;
    protected final ItemStackHandler outputHandler;
    protected final ItemStackHandler upgradeHandler;

    private final LazyOptional<IEnergyStorage> energyCapability;
    private final LazyOptional<IItemHandler> itemCapability;

    protected long workTicks;

    protected AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyCapacity, int maxReceive, int maxExtract, int outputSlots) {
        super(type, pos, state);
        this.energyStorage = new SimpleEnergyStorage(energyCapacity, maxReceive, maxExtract, this::markDirtyAndNotify);
        this.outputHandler = new ItemStackHandler(outputSlots) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirtyAndNotify();
            }
        };
        this.upgradeHandler = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return isSpeedUpgrade(stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                return MAX_SPEED_UPGRADES;
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirtyAndNotify();
            }
        };
        this.energyCapability = LazyOptional.of(() -> this.energyStorage);
        this.itemCapability = outputSlots > 0 ? LazyOptional.of(() -> this.outputHandler) : LazyOptional.empty();
    }

    public void serverTick() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        workTicks++;
        tickServer(serverLevel);
    }

    protected abstract void tickServer(ServerLevel level);

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public abstract AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player);

    public Component createStatusMessage() {
        if (outputHandler.getSlots() <= 0) {
            return Component.translatable(
                    "message.voidmachines.machine_status_energy_only",
                    energyStorage.getEnergyStored(),
                    energyStorage.getMaxEnergyStored()
            );
        }

        ItemStack output = getOutputPreview();
        Component outputText = output.isEmpty() ? Component.translatable("message.voidmachines.empty") : output.getHoverName();
        return Component.translatable(
                "message.voidmachines.machine_status",
                energyStorage.getEnergyStored(),
                energyStorage.getMaxEnergyStored(),
                outputText
        );
    }

    public ItemStack extractOutputForPlayer() {
        if (outputHandler.getSlots() <= 0) {
            return ItemStack.EMPTY;
        }
        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            ItemStack stack = outputHandler.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                return outputHandler.extractItem(slot, stack.getCount(), false);
            }
        }
        return ItemStack.EMPTY;
    }

    public void dropContents() {
        if (level == null || level.isClientSide() || outputHandler.getSlots() <= 0) {
            return;
        }

        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            ItemStack stack = outputHandler.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack.copy());
            }
        }

        ItemStack upgradeStack = upgradeHandler.getStackInSlot(0);
        if (!upgradeStack.isEmpty()) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), upgradeStack.copy());
        }
    }

    public int getComparatorOutput() {
        if (outputHandler.getSlots() <= 0) {
            return Mth.clamp(energyStorage.getEnergyStored() * 15 / Math.max(1, energyStorage.getMaxEnergyStored()), 0, 15);
        }
        int storedItems = 0;
        int capacity = 0;
        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            ItemStack stack = outputHandler.getStackInSlot(slot);
            storedItems += stack.getCount();
            capacity += Math.min(outputHandler.getSlotLimit(slot), stack.isEmpty() ? 64 : stack.getMaxStackSize());
        }
        if (storedItems <= 0 || capacity <= 0) {
            return 0;
        }
        return Math.max(1, storedItems * 15 / capacity);
    }

    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    public int getProgress() {
        return 0;
    }

    public int getMaxProgress() {
        return 0;
    }

    public int getSpeedUpgradeCount() {
        return upgradeHandler.getStackInSlot(0).getCount();
    }

    public float getSpeedBoostFactor() {
        return Math.min(4.0F, getSpeedUpgradeCount() * 0.5F);
    }

    public float getSpeedMultiplier() {
        return 1.0F + getSpeedBoostFactor();
    }

    public int getOutputSlots() {
        return outputHandler.getSlots();
    }

    public ItemStack getOutputPreview() {
        if (outputHandler.getSlots() <= 0) {
            return ItemStack.EMPTY;
        }
        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            ItemStack stack = outputHandler.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public IItemHandler getOutputHandler() {
        return outputHandler;
    }

    public ItemStack getUpgradePreview() {
        return upgradeHandler.getStackInSlot(0);
    }

    public IItemHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public boolean isSpeedUpgrade(ItemStack stack) {
        return !stack.isEmpty() && stack.is(VMItems.SPEED_UPGRADE.get());
    }

    protected void autoExportOutput(ServerLevel level) {
        if (outputHandler.getSlots() <= 0) {
            return;
        }

        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            ItemStack stack = outputHandler.getStackInSlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            for (Direction direction : Direction.values()) {
                if (stack.isEmpty()) {
                    break;
                }

                BlockEntity targetBlockEntity = level.getBlockEntity(worldPosition.relative(direction));
                if (targetBlockEntity == null) {
                    continue;
                }

                IItemHandler targetHandler = targetBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite()).orElse(null);
                if (targetHandler == null) {
                    continue;
                }

                stack = tryInsertIntoHandler(targetHandler, stack);
            }

            outputHandler.setStackInSlot(slot, stack);
        }
    }

    protected boolean canOutput(ItemStack stack) {
        if (outputHandler.getSlots() <= 0 || stack.isEmpty()) {
            return false;
        }

        int remaining = stack.getCount();
        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            ItemStack existing = outputHandler.getStackInSlot(slot);
            int slotLimit = Math.min(outputHandler.getSlotLimit(slot), stack.getMaxStackSize());
            if (existing.isEmpty()) {
                remaining -= slotLimit;
            } else if (ItemStack.isSameItemSameTags(existing, stack)) {
                remaining -= Math.max(0, slotLimit - existing.getCount());
            }

            if (remaining <= 0) {
                return true;
            }
        }
        return false;
    }

    protected void insertOutput(ItemStack stack) {
        if (stack.isEmpty() || outputHandler.getSlots() <= 0) {
            return;
        }

        ItemStack remaining = stack.copy();
        for (int slot = 0; slot < outputHandler.getSlots(); slot++) {
            if (remaining.isEmpty()) {
                break;
            }

            ItemStack existing = outputHandler.getStackInSlot(slot);
            int slotLimit = Math.min(outputHandler.getSlotLimit(slot), remaining.getMaxStackSize());
            if (existing.isEmpty()) {
                ItemStack inserted = remaining.copy();
                inserted.setCount(Math.min(slotLimit, remaining.getCount()));
                outputHandler.setStackInSlot(slot, inserted);
                remaining.shrink(inserted.getCount());
            } else if (ItemStack.isSameItemSameTags(existing, remaining) && existing.getCount() < slotLimit) {
                int transfer = Math.min(slotLimit - existing.getCount(), remaining.getCount());
                ItemStack merged = existing.copy();
                merged.grow(transfer);
                outputHandler.setStackInSlot(slot, merged);
                remaining.shrink(transfer);
            }
        }
    }

    private ItemStack tryInsertIntoHandler(IItemHandler handler, ItemStack stack) {
        ItemStack remaining = stack.copy();
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            remaining = handler.insertItem(slot, remaining, false);
            if (remaining.isEmpty()) {
                return ItemStack.EMPTY;
            }
        }
        return remaining;
    }

    protected void markDirtyAndNotify() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("energy", energyStorage.getEnergyStored());
        tag.putLong("workTicks", workTicks);
        if (outputHandler.getSlots() > 0) {
            tag.put("inventory", outputHandler.serializeNBT());
        }
        tag.put("upgrades", upgradeHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energyStorage.setStoredEnergy(tag.getInt("energy"));
        workTicks = tag.getLong("workTicks");
        if (outputHandler.getSlots() > 0 && tag.contains("inventory")) {
            outputHandler.deserializeNBT(tag.getCompound("inventory"));
        }
        if (tag.contains("upgrades")) {
            upgradeHandler.deserializeNBT(tag.getCompound("upgrades"));
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCapability.invalidate();
        itemCapability.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (capability == ForgeCapabilities.ENERGY) {
            return energyCapability.cast();
        }
        if (capability == ForgeCapabilities.ITEM_HANDLER && outputHandler.getSlots() > 0) {
            return itemCapability.cast();
        }
        return super.getCapability(capability, side);
    }
}
