package com.hyygybs.voidmachines;

import com.hyygybs.voidmachines.common.config.VMConfig;
import com.hyygybs.voidmachines.common.registration.VMBlockEntities;
import com.hyygybs.voidmachines.common.registration.VMBlocks;
import com.hyygybs.voidmachines.common.registration.VMCreativeTabs;
import com.hyygybs.voidmachines.common.registration.VMItems;
import com.hyygybs.voidmachines.common.registration.VMMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(VoidMachines.MODID)
public class VoidMachines {
    public static final String MODID = "voidmachines";
    public static final Logger LOGGER = LogUtils.getLogger();

    public VoidMachines(FMLJavaModLoadingContext context) {
        var modEventBus = context.getModEventBus();

        VMBlocks.BLOCKS.register(modEventBus);
        VMItems.ITEMS.register(modEventBus);
        VMBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        VMMenuTypes.MENUS.register(modEventBus);
        VMCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        context.registerConfig(ModConfig.Type.COMMON, VMConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
