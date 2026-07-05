package com.hyygybs.voidmachines.common.menu;

import com.hyygybs.voidmachines.common.blockentity.VoidEnergyGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.registration.VMMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class VoidEnergyGeneratorMenu extends AbstractMachineMenu {
    public VoidEnergyGeneratorMenu(int containerId, Inventory inventory, VoidEnergyGeneratorBlockEntity blockEntity) {
        super(VMMenuTypes.VOID_ENERGY_GENERATOR.get(), containerId, inventory, blockEntity, 0, 1, 0, 0, 152, 64, 140);
    }

    public VoidEnergyGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory, buffer.readBlockPos());
    }

    private VoidEnergyGeneratorMenu(int containerId, Inventory inventory, BlockPos blockPos) {
        super(VMMenuTypes.VOID_ENERGY_GENERATOR.get(), containerId, inventory, blockPos, 0, 1, 0, 0, 152, 64, 140);
    }
}
