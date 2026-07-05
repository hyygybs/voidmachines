package com.hyygybs.voidmachines.common.blockentity;

import com.hyygybs.voidmachines.common.config.VMConfig;
import com.hyygybs.voidmachines.common.menu.VoidEnergyGeneratorMenu;
import com.hyygybs.voidmachines.common.registration.VMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class VoidEnergyGeneratorBlockEntity extends AbstractMachineBlockEntity {
    private static final int PASSIVE_GENERATION_CYCLE = 20;

    public VoidEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(VMBlockEntities.VOID_ENERGY_GENERATOR.get(), pos, state, VMConfig.getEnergyGeneratorCapacity(), 0, Integer.MAX_VALUE, 0);
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new VoidEnergyGeneratorMenu(containerId, inventory, this);
    }

    @Override
    protected void tickServer(ServerLevel level) {
        int generated = getScaledGenerationRate();
        if (generated > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
            energyStorage.setStoredEnergy(Math.min(energyStorage.getMaxEnergyStored(), energyStorage.getEnergyStored() + generated));
        }
        pushEnergyToNeighbors(level);
    }

    @Override
    public int getProgress() {
        return (int) (workTicks % PASSIVE_GENERATION_CYCLE);
    }

    @Override
    public int getMaxProgress() {
        return PASSIVE_GENERATION_CYCLE;
    }

    private void pushEnergyToNeighbors(ServerLevel level) {
        int budget = Math.min(getScaledTransferRate(), energyStorage.getEnergyStored());
        if (budget <= 0) {
            return;
        }

        for (Direction direction : Direction.values()) {
            if (budget <= 0) {
                break;
            }

            BlockEntity blockEntity = level.getBlockEntity(worldPosition.relative(direction));
            if (blockEntity == null) {
                continue;
            }

            IEnergyStorage target = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).orElse(null);
            if (target == null || !target.canReceive()) {
                continue;
            }

            int offered = Math.min(budget, energyStorage.getEnergyStored());
            int accepted = target.receiveEnergy(offered, false);
            if (accepted > 0) {
                energyStorage.extractEnergy(accepted, false);
                budget -= accepted;
            }
        }
    }

    private int getScaledGenerationRate() {
        return Math.max(1, net.minecraft.util.Mth.floor(VMConfig.getEnergyGeneratorFePerTick() * getSpeedMultiplier()));
    }

    private int getScaledTransferRate() {
        return Math.max(1, net.minecraft.util.Mth.floor(VMConfig.getEnergyGeneratorTransferPerTick() * getSpeedMultiplier()));
    }
}
