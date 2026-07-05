package com.hyygybs.voidmachines.data.provider;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.registration.VMItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class VMItemModelProvider extends ItemModelProvider {
    public VMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VoidMachines.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generatedItem(VMItems.RED_VOID_MATTER);
        generatedItem(VMItems.BLUE_VOID_MATTER);
        generatedItem(VMItems.YELLOW_VOID_MATTER);
        generatedItem(VMItems.GREEN_VOID_MATTER);
        generatedItem(VMItems.VIOLET_VOID_MATTER);
        generatedItem(VMItems.BLACK_VOID_MATTER);
        generatedItem(VMItems.WHITE_VOID_MATTER);
        generatedItem(VMItems.COLORFUL_VOID_MATTER);
        withExistingParent(VMItems.SPEED_UPGRADE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + VMItems.SPEED_UPGRADE.getId().getPath()));
        withExistingParent(VMItems.VOID_ENERGY_GENERATOR.getId().getPath(), modLoc("block/" + VMItems.VOID_ENERGY_GENERATOR.getId().getPath()));
        withExistingParent(VMItems.VOID_ORE_GENERATOR.getId().getPath(), modLoc("block/" + VMItems.VOID_ORE_GENERATOR.getId().getPath()));
        withExistingParent(VMItems.VOID_NATURE_GENERATOR.getId().getPath(), modLoc("block/" + VMItems.VOID_NATURE_GENERATOR.getId().getPath()));
        withExistingParent(VMItems.VOID_MOB_GENERATOR.getId().getPath(), modLoc("block/" + VMItems.VOID_MOB_GENERATOR.getId().getPath()));
    }

    private void generatedItem(net.minecraftforge.registries.RegistryObject<net.minecraft.world.item.Item> item) {
        withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + item.getId().getPath()));
    }
}
