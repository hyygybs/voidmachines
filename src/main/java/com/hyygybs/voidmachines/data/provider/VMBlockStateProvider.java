package com.hyygybs.voidmachines.data.provider;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.registration.VMBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class VMBlockStateProvider extends BlockStateProvider {
    public VMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VoidMachines.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMachine(VMBlocks.VOID_ENERGY_GENERATOR.get());
        registerMachine(VMBlocks.VOID_ORE_GENERATOR.get());
        registerMachine(VMBlocks.VOID_NATURE_GENERATOR.get());
        registerMachine(VMBlocks.VOID_MOB_GENERATOR.get());
    }

    private void registerMachine(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile model = models().cubeAll(name, modLoc("block/" + name));

        simpleBlock(block, model);
    }
}
