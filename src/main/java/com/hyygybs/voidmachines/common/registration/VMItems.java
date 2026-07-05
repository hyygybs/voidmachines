package com.hyygybs.voidmachines.common.registration;

import com.hyygybs.voidmachines.VoidMachines;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public final class VMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VoidMachines.MODID);

    public static final RegistryObject<Item> RED_VOID_MATTER = registerItem("red_void_matter");
    public static final RegistryObject<Item> BLUE_VOID_MATTER = registerItem("blue_void_matter");
    public static final RegistryObject<Item> YELLOW_VOID_MATTER = registerItem("yellow_void_matter");
    public static final RegistryObject<Item> GREEN_VOID_MATTER = registerItem("green_void_matter");
    public static final RegistryObject<Item> VIOLET_VOID_MATTER = registerItem("violet_void_matter");
    public static final RegistryObject<Item> BLACK_VOID_MATTER = registerItem("black_void_matter");
    public static final RegistryObject<Item> WHITE_VOID_MATTER = registerItem("white_void_matter");
    public static final RegistryObject<Item> COLORFUL_VOID_MATTER = registerItem("colorful_void_matter");
    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new Item(new Item.Properties().stacksTo(8)));
    public static final RegistryObject<Item> VOID_ENERGY_GENERATOR = registerBlockItem("void_energy_generator", VMBlocks.VOID_ENERGY_GENERATOR);
    public static final RegistryObject<Item> VOID_ORE_GENERATOR = registerBlockItem("void_ore_generator", VMBlocks.VOID_ORE_GENERATOR);
    public static final RegistryObject<Item> VOID_NATURE_GENERATOR = registerBlockItem("void_nature_generator", VMBlocks.VOID_NATURE_GENERATOR);
    public static final RegistryObject<Item> VOID_MOB_GENERATOR = registerBlockItem("void_mob_generator", VMBlocks.VOID_MOB_GENERATOR);

    private VMItems() {
    }

    public static List<Item> getBaseVoidMatterItems() {
        return List.of(
                RED_VOID_MATTER.get(),
                BLUE_VOID_MATTER.get(),
                YELLOW_VOID_MATTER.get(),
                GREEN_VOID_MATTER.get(),
                VIOLET_VOID_MATTER.get(),
                BLACK_VOID_MATTER.get(),
                WHITE_VOID_MATTER.get()
        );
    }

    private static RegistryObject<Item> registerItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> registerBlockItem(String name, RegistryObject<? extends net.minecraft.world.level.block.Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
