package com.hyygybs.voidmachines.common.blockentity;

import com.hyygybs.voidmachines.common.config.VMConfig;
import com.hyygybs.voidmachines.common.menu.VoidResourceGeneratorMenu;
import com.hyygybs.voidmachines.common.registration.VMBlockEntities;
import com.hyygybs.voidmachines.common.util.GeneratorContentCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class VoidMobResourceGeneratorBlockEntity extends AbstractResourceGeneratorBlockEntity {
    public VoidMobResourceGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(VMBlockEntities.VOID_MOB_GENERATOR.get(), pos, state, VMConfig.getResourceMachineCapacity());
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new VoidResourceGeneratorMenu(containerId, inventory, this);
    }

    @Override
    protected int getOperationCost() {
        return VMConfig.getMobGeneratorCost();
    }

    @Override
    protected int getOperationInterval() {
        return VMConfig.getMobGeneratorInterval();
    }

    @Override
    protected List<Item> getCandidateItems(ServerLevel level) {
        return GeneratorContentCache.getMobLootItems(level);
    }
}
