package com.hyygybs.voidmachines.common.registration;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.blockentity.VoidEnergyGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.blockentity.VoidMobResourceGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.blockentity.VoidNatureResourceGeneratorBlockEntity;
import com.hyygybs.voidmachines.common.blockentity.VoidOreResourceGeneratorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class VMBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VoidMachines.MODID);

    public static final RegistryObject<BlockEntityType<VoidEnergyGeneratorBlockEntity>> VOID_ENERGY_GENERATOR = BLOCK_ENTITIES.register(
            "void_energy_generator",
            () -> BlockEntityType.Builder.of(VoidEnergyGeneratorBlockEntity::new, VMBlocks.VOID_ENERGY_GENERATOR.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<VoidOreResourceGeneratorBlockEntity>> VOID_ORE_GENERATOR = BLOCK_ENTITIES.register(
            "void_ore_generator",
            () -> BlockEntityType.Builder.of(VoidOreResourceGeneratorBlockEntity::new, VMBlocks.VOID_ORE_GENERATOR.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<VoidNatureResourceGeneratorBlockEntity>> VOID_NATURE_GENERATOR = BLOCK_ENTITIES.register(
            "void_nature_generator",
            () -> BlockEntityType.Builder.of(VoidNatureResourceGeneratorBlockEntity::new, VMBlocks.VOID_NATURE_GENERATOR.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<VoidMobResourceGeneratorBlockEntity>> VOID_MOB_GENERATOR = BLOCK_ENTITIES.register(
            "void_mob_generator",
            () -> BlockEntityType.Builder.of(VoidMobResourceGeneratorBlockEntity::new, VMBlocks.VOID_MOB_GENERATOR.get()).build(null)
    );

    private VMBlockEntities() {
    }
}
