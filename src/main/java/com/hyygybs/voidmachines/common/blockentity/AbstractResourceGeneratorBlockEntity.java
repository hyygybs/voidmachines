package com.hyygybs.voidmachines.common.blockentity;

import com.hyygybs.voidmachines.common.config.VMConfig;
import com.hyygybs.voidmachines.common.util.VoidMatterHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class AbstractResourceGeneratorBlockEntity extends AbstractMachineBlockEntity {
    public static final int INTERNAL_STORAGE_SLOTS = 32;

    protected AbstractResourceGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyCapacity) {
        super(type, pos, state, energyCapacity, Integer.MAX_VALUE, 0, INTERNAL_STORAGE_SLOTS);
    }

    @Override
    protected void tickServer(ServerLevel level) {
        int effectiveInterval = getEffectiveOperationInterval();
        if (workTicks % effectiveInterval != 0) {
            if (workTicks % 10L == 0L) {
                autoExportOutput(level);
            }
            return;
        }

        if (energyStorage.getEnergyStored() < getOperationCost()) {
            return;
        }

        List<Item> candidates = getCandidateItems(level);
        if (candidates.isEmpty()) {
            return;
        }

        RandomSource random = level.getRandom();
        ItemStack result = createOutput(level, random, candidates);
        if (!canOutput(result)) {
            return;
        }

        energyStorage.extractEnergy(getOperationCost(), false);
        insertOutput(result);
        autoExportOutput(level);
    }

    @Override
    public int getProgress() {
        return (int) (workTicks % getEffectiveOperationInterval());
    }

    @Override
    public int getMaxProgress() {
        return getEffectiveOperationInterval();
    }

    protected int getOutputCount(Item item) {
        return 1;
    }

    protected ItemStack createOutput(ServerLevel level, RandomSource random, List<Item> candidates) {
        if (random.nextDouble() < VMConfig.getResourceMachineBaseVoidMatterChance()) {
            return VoidMatterHelper.createRandomBaseVoidMatter(random);
        }

        Item item = candidates.get(random.nextInt(candidates.size()));
        return new ItemStack(item, getOutputCount(item));
    }

    protected int getEffectiveOperationInterval() {
        return Math.max(1, Mth.ceil(getOperationInterval() / getSpeedMultiplier()));
    }

    @Override
    public abstract AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player);

    protected abstract int getOperationCost();

    protected abstract int getOperationInterval();

    protected abstract List<Item> getCandidateItems(ServerLevel level);
}
