package com.hyygybs.voidmachines.common.registration;

import com.hyygybs.voidmachines.VoidMachines;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class VMCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VoidMachines.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.voidmachines.main"))
            .withTabsBefore(CreativeModeTabs.REDSTONE_BLOCKS)
            .icon(() -> VMItems.VOID_ENERGY_GENERATOR.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(VMItems.RED_VOID_MATTER.get());
                output.accept(VMItems.BLUE_VOID_MATTER.get());
                output.accept(VMItems.YELLOW_VOID_MATTER.get());
                output.accept(VMItems.GREEN_VOID_MATTER.get());
                output.accept(VMItems.VIOLET_VOID_MATTER.get());
                output.accept(VMItems.BLACK_VOID_MATTER.get());
                output.accept(VMItems.WHITE_VOID_MATTER.get());
                output.accept(VMItems.COLORFUL_VOID_MATTER.get());
                output.accept(VMItems.SPEED_UPGRADE.get());
                output.accept(VMItems.VOID_ENERGY_GENERATOR.get());
                output.accept(VMItems.VOID_ORE_GENERATOR.get());
                output.accept(VMItems.VOID_NATURE_GENERATOR.get());
                output.accept(VMItems.VOID_MOB_GENERATOR.get());
            })
            .build());

    private VMCreativeTabs() {
    }
}
