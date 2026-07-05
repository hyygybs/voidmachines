package com.hyygybs.voidmachines.common.menu;

import com.hyygybs.voidmachines.common.blockentity.AbstractResourceGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.registration.VMMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class VoidResourceGeneratorMenu extends AbstractMachineMenu {
    public VoidResourceGeneratorMenu(int containerId, Inventory inventory, AbstractResourceGeneratorBlockEntity blockEntity) {
        super(VMMenuTypes.VOID_RESOURCE_GENERATOR.get(), containerId, inventory, blockEntity, 32, 8, 26, 54, 152, 21, 140);
    }

    public VoidResourceGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory, buffer.readBlockPos());
    }

    private VoidResourceGeneratorMenu(int containerId, Inventory inventory, BlockPos blockPos) {
        super(VMMenuTypes.VOID_RESOURCE_GENERATOR.get(), containerId, inventory, blockPos, 32, 8, 26, 54, 152, 21, 140);
    }
}
