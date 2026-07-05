package com.hyygybs.voidmachines.common.registration;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.block.MachineBlock;
import com.hyygybs.voidmachines.common.blockentity.VoidEnergyGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.blockentity.VoidMobResourceGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.blockentity.VoidNatureResourceGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.blockentity.VoidOreResourceGeneratorBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class VMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, VoidMachines.MODID);

    public static final RegistryObject<Block> VOID_ENERGY_GENERATOR = BLOCKS.register(
            "void_energy_generator",
            () -> new MachineBlock(false, () -> VMBlockEntities.VOID_ENERGY_GENERATOR.get(), VoidEnergyGeneratorBlockEntity::new)
    );

    public static final RegistryObject<Block> VOID_ORE_GENERATOR = BLOCKS.register(
            "void_ore_generator",
            () -> new MachineBlock(false, () -> VMBlockEntities.VOID_ORE_GENERATOR.get(), VoidOreResourceGeneratorBlockEntity::new)
    );

    public static final RegistryObject<Block> VOID_NATURE_GENERATOR = BLOCKS.register(
            "void_nature_generator",
            () -> new MachineBlock(false, () -> VMBlockEntities.VOID_NATURE_GENERATOR.get(), VoidNatureResourceGeneratorBlockEntity::new)
    );

    public static final RegistryObject<Block> VOID_MOB_GENERATOR = BLOCKS.register(
            "void_mob_generator",
            () -> new MachineBlock(false, () -> VMBlockEntities.VOID_MOB_GENERATOR.get(), VoidMobResourceGeneratorBlockEntity::new)
    );

    private VMBlocks() {
    }
}
