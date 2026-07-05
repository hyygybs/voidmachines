package com.hyygybs.voidmachines.common.registration;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.menu.VoidEnergyGeneratorMenu;
import com.hyygybs.voidmachines.common.menu.VoidResourceGeneratorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class VMMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, VoidMachines.MODID);

    public static final RegistryObject<MenuType<VoidEnergyGeneratorMenu>> VOID_ENERGY_GENERATOR = MENUS.register(
            "void_energy_generator",
            () -> IForgeMenuType.create(VoidEnergyGeneratorMenu::new)
    );

    public static final RegistryObject<MenuType<VoidResourceGeneratorMenu>> VOID_RESOURCE_GENERATOR = MENUS.register(
            "void_resource_generator",
            () -> IForgeMenuType.create(VoidResourceGeneratorMenu::new)
    );

    private VMMenuTypes() {
    }
}
