package com.hyygybs.voidmachines.data.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class VMLootTableProvider extends LootTableProvider {
    public VMLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(VMBlockLootProvider::new, LootContextParamSets.BLOCK)));
    }
}
