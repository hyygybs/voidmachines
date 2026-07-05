package com.hyygybs.voidmachines.data.provider;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.registration.VMBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class VMBlockTagsProvider extends BlockTagsProvider {
    public VMBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VoidMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(VMBlocks.VOID_ENERGY_GENERATOR.get())
                .add(VMBlocks.VOID_ORE_GENERATOR.get())
                .add(VMBlocks.VOID_NATURE_GENERATOR.get())
                .add(VMBlocks.VOID_MOB_GENERATOR.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(VMBlocks.VOID_ENERGY_GENERATOR.get())
                .add(VMBlocks.VOID_ORE_GENERATOR.get())
                .add(VMBlocks.VOID_NATURE_GENERATOR.get())
                .add(VMBlocks.VOID_MOB_GENERATOR.get());
    }
}
