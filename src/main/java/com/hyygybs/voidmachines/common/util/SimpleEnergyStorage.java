package com.hyygybs.voidmachines.common.util;

import net.minecraft.util.Mth;
import net.minecraftforge.energy.EnergyStorage;

public class SimpleEnergyStorage extends EnergyStorage {
    private final Runnable onChange;

    public SimpleEnergyStorage(int capacity, int maxReceive, int maxExtract, Runnable onChange) {
        super(capacity, maxReceive, maxExtract);
        this.onChange = onChange;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (received > 0 && !simulate) {
            onChange.run();
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (extracted > 0 && !simulate) {
            onChange.run();
        }
        return extracted;
    }

    public void setStoredEnergy(int energy) {
        this.energy = Mth.clamp(energy, 0, capacity);
        onChange.run();
    }
}
