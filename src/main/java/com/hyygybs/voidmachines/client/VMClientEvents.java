package com.hyygybs.voidmachines.client;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.client.screen.VoidEnergyGeneratorScreen;
import com.hyygybs.voidmachines.client.screen.VoidResourceGeneratorScreen;
import com.hyygybs.voidmachines.common.registration.VMMenuTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = VoidMachines.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class VMClientEvents {
    private VMClientEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(VMMenuTypes.VOID_ENERGY_GENERATOR.get(), VoidEnergyGeneratorScreen::new);
            MenuScreens.register(VMMenuTypes.VOID_RESOURCE_GENERATOR.get(), VoidResourceGeneratorScreen::new);
        });
    }
}
