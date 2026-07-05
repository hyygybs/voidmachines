package com.hyygybs.voidmachines.data.provider;

import com.hyygybs.voidmachines.common.registration.VMBlocks;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class VMBlockLootProvider extends net.minecraft.data.loot.BlockLootSubProvider {
    protected VMBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(VMBlocks.VOID_ENERGY_GENERATOR.get());
        dropSelf(VMBlocks.VOID_ORE_GENERATOR.get());
        dropSelf(VMBlocks.VOID_NATURE_GENERATOR.get());
        dropSelf(VMBlocks.VOID_MOB_GENERATOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return java.util.List.of(
                VMBlocks.VOID_ENERGY_GENERATOR.get(),
                VMBlocks.VOID_ORE_GENERATOR.get(),
                VMBlocks.VOID_NATURE_GENERATOR.get(),
                VMBlocks.VOID_MOB_GENERATOR.get()
        );
    }
}
