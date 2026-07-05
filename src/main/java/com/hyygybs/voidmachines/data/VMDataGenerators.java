package com.hyygybs.voidmachines.data;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.data.provider.VMBlockLootProvider;
import com.hyygybs.voidmachines.data.provider.VMBlockStateProvider;
import com.hyygybs.voidmachines.data.provider.VMBlockTagsProvider;
import com.hyygybs.voidmachines.data.provider.VMItemModelProvider;
import com.hyygybs.voidmachines.data.provider.VMLanguageProvider;
import com.hyygybs.voidmachines.data.provider.VMLootTableProvider;
import com.hyygybs.voidmachines.data.provider.VMRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VoidMachines.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class VMDataGenerators {
    private VMDataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        VMBlockTagsProvider blockTagsProvider = new VMBlockTagsProvider(output, event.getLookupProvider(), existingFileHelper);

        generator.addProvider(event.includeServer(), new VMRecipeProvider(output));
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new VMLootTableProvider(output));
        generator.addProvider(event.includeClient(), new VMLanguageProvider(output));
        generator.addProvider(event.includeClient(), new VMBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new VMItemModelProvider(output, existingFileHelper));
    }
}
