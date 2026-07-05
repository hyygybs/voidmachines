package com.hyygybs.voidmachines.common.config;

import com.hyygybs.voidmachines.VoidMachines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = VoidMachines.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class VMConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final int DEFAULT_ENERGY_GENERATOR_CAPACITY = 1_000_000_000;
    private static final int DEFAULT_ENERGY_GENERATOR_FE_PER_TICK = 80;
    private static final int DEFAULT_ENERGY_GENERATOR_TRANSFER = 2_000;
    private static final int DEFAULT_RESOURCE_MACHINE_CAPACITY = 2_000_000;
    private static final double DEFAULT_RESOURCE_MACHINE_BASE_VOID_MATTER_CHANCE = 0.02D;
    private static final double DEFAULT_WORLD_BASE_VOID_MATTER_CHANCE = 0.001D;
    private static final int DEFAULT_ORE_COST = 40_000;
    private static final int DEFAULT_ORE_INTERVAL = 400;
    private static final int DEFAULT_NATURE_COST = 30_000;
    private static final int DEFAULT_NATURE_INTERVAL = 400;
    private static final int DEFAULT_MOB_COST = 120_000;
    private static final int DEFAULT_MOB_INTERVAL = 400;

    private static final ForgeConfigSpec.IntValue ENERGY_GENERATOR_CAPACITY = BUILDER
            .comment("Internal FE capacity for the void energy generator.")
            .defineInRange("energyGenerator.capacity", DEFAULT_ENERGY_GENERATOR_CAPACITY, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue ENERGY_GENERATOR_FE_PER_TICK = BUILDER
            .comment("Passive FE generated every tick.")
            .defineInRange("energyGenerator.fePerTick", DEFAULT_ENERGY_GENERATOR_FE_PER_TICK, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue ENERGY_GENERATOR_TRANSFER = BUILDER
            .comment("Max FE pushed to adjacent machines each tick.")
            .defineInRange("energyGenerator.transferPerTick", DEFAULT_ENERGY_GENERATOR_TRANSFER, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue RESOURCE_MACHINE_CAPACITY = BUILDER
            .comment("Internal FE capacity for all resource generators.")
            .defineInRange("resourceMachines.capacity", DEFAULT_RESOURCE_MACHINE_CAPACITY, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.DoubleValue RESOURCE_MACHINE_BASE_VOID_MATTER_CHANCE = BUILDER
            .comment("Chance for any void resource generator to output a base void matter item instead of its normal result. Uses a 0.0 to 1.0 range.")
            .defineInRange("resourceMachines.baseVoidMatterChance", DEFAULT_RESOURCE_MACHINE_BASE_VOID_MATTER_CHANCE, 0.0D, 1.0D);
    private static final ForgeConfigSpec.DoubleValue WORLD_BASE_VOID_MATTER_CHANCE = BUILDER
            .comment("Chance for block breaking, mob kills, and fishing to drop a base void matter item. Uses a 0.0 to 1.0 range.")
            .defineInRange("worldDrops.baseVoidMatterChance", DEFAULT_WORLD_BASE_VOID_MATTER_CHANCE, 0.0D, 1.0D);

    private static final ForgeConfigSpec.IntValue ORE_COST = BUILDER
            .comment("FE consumed per ore-resource generation.")
            .defineInRange("oreGenerator.cost", DEFAULT_ORE_COST, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue ORE_INTERVAL = BUILDER
            .comment("Ticks between ore-resource generation attempts.")
            .defineInRange("oreGenerator.interval", DEFAULT_ORE_INTERVAL, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue NATURE_COST = BUILDER
            .comment("FE consumed per plant-resource generation.")
            .defineInRange("natureGenerator.cost", DEFAULT_NATURE_COST, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue NATURE_INTERVAL = BUILDER
            .comment("Ticks between plant-resource generation attempts.")
            .defineInRange("natureGenerator.interval", DEFAULT_NATURE_INTERVAL, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MOB_COST = BUILDER
            .comment("FE consumed per mob-loot generation.")
            .defineInRange("mobGenerator.cost", DEFAULT_MOB_COST, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue MOB_INTERVAL = BUILDER
            .comment("Ticks between mob-loot generation attempts.")
            .defineInRange("mobGenerator.interval", DEFAULT_MOB_INTERVAL, 1, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_WHITELIST = stringList("oreGenerator.whitelist");
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_BLACKLIST = stringList("oreGenerator.blacklist");
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> NATURE_WHITELIST = stringList("natureGenerator.whitelist");
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> NATURE_BLACKLIST = stringList("natureGenerator.blacklist");
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> MOB_WHITELIST = stringList("mobGenerator.whitelist");
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> MOB_BLACKLIST = stringList("mobGenerator.blacklist");

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int energyGeneratorCapacity = DEFAULT_ENERGY_GENERATOR_CAPACITY;
    public static int energyGeneratorFePerTick = DEFAULT_ENERGY_GENERATOR_FE_PER_TICK;
    public static int energyGeneratorTransferPerTick = DEFAULT_ENERGY_GENERATOR_TRANSFER;
    public static int resourceMachineCapacity = DEFAULT_RESOURCE_MACHINE_CAPACITY;
    public static double resourceMachineBaseVoidMatterChance = DEFAULT_RESOURCE_MACHINE_BASE_VOID_MATTER_CHANCE;
    public static double worldBaseVoidMatterChance = DEFAULT_WORLD_BASE_VOID_MATTER_CHANCE;
    public static int oreGeneratorCost = DEFAULT_ORE_COST;
    public static int oreGeneratorInterval = DEFAULT_ORE_INTERVAL;
    public static int natureGeneratorCost = DEFAULT_NATURE_COST;
    public static int natureGeneratorInterval = DEFAULT_NATURE_INTERVAL;
    public static int mobGeneratorCost = DEFAULT_MOB_COST;
    public static int mobGeneratorInterval = DEFAULT_MOB_INTERVAL;
    private static Set<ResourceLocation> oreWhitelist = Set.of();
    private static Set<ResourceLocation> oreBlacklist = Set.of();
    private static Set<ResourceLocation> natureWhitelist = Set.of();
    private static Set<ResourceLocation> natureBlacklist = Set.of();
    private static Set<ResourceLocation> mobWhitelist = Set.of();
    private static Set<ResourceLocation> mobBlacklist = Set.of();

    private VMConfig() {
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }
        bake();
    }

    private static void bake() {
        energyGeneratorCapacity = getEnergyGeneratorCapacity();
        energyGeneratorFePerTick = getEnergyGeneratorFePerTick();
        energyGeneratorTransferPerTick = getEnergyGeneratorTransferPerTick();
        resourceMachineCapacity = getResourceMachineCapacity();
        resourceMachineBaseVoidMatterChance = getResourceMachineBaseVoidMatterChance();
        worldBaseVoidMatterChance = getWorldBaseVoidMatterChance();
        oreGeneratorCost = getOreGeneratorCost();
        oreGeneratorInterval = getOreGeneratorInterval();
        natureGeneratorCost = getNatureGeneratorCost();
        natureGeneratorInterval = getNatureGeneratorInterval();
        mobGeneratorCost = getMobGeneratorCost();
        mobGeneratorInterval = getMobGeneratorInterval();
        oreWhitelist = parseLocations(ORE_WHITELIST.get());
        oreBlacklist = parseLocations(ORE_BLACKLIST.get());
        natureWhitelist = parseLocations(NATURE_WHITELIST.get());
        natureBlacklist = parseLocations(NATURE_BLACKLIST.get());
        mobWhitelist = parseLocations(MOB_WHITELIST.get());
        mobBlacklist = parseLocations(MOB_BLACKLIST.get());
    }

    public static int getEnergyGeneratorCapacity() {
        return getIntValue(ENERGY_GENERATOR_CAPACITY, DEFAULT_ENERGY_GENERATOR_CAPACITY);
    }

    public static int getEnergyGeneratorFePerTick() {
        return getIntValue(ENERGY_GENERATOR_FE_PER_TICK, DEFAULT_ENERGY_GENERATOR_FE_PER_TICK);
    }

    public static int getEnergyGeneratorTransferPerTick() {
        return getIntValue(ENERGY_GENERATOR_TRANSFER, DEFAULT_ENERGY_GENERATOR_TRANSFER);
    }

    public static int getResourceMachineCapacity() {
        return getIntValue(RESOURCE_MACHINE_CAPACITY, DEFAULT_RESOURCE_MACHINE_CAPACITY);
    }

    public static double getResourceMachineBaseVoidMatterChance() {
        return getDoubleValue(RESOURCE_MACHINE_BASE_VOID_MATTER_CHANCE, DEFAULT_RESOURCE_MACHINE_BASE_VOID_MATTER_CHANCE);
    }

    public static double getWorldBaseVoidMatterChance() {
        return getDoubleValue(WORLD_BASE_VOID_MATTER_CHANCE, DEFAULT_WORLD_BASE_VOID_MATTER_CHANCE);
    }

    public static int getOreGeneratorCost() {
        return getIntValue(ORE_COST, DEFAULT_ORE_COST);
    }

    public static int getOreGeneratorInterval() {
        return getIntValue(ORE_INTERVAL, DEFAULT_ORE_INTERVAL);
    }

    public static int getNatureGeneratorCost() {
        return getIntValue(NATURE_COST, DEFAULT_NATURE_COST);
    }

    public static int getNatureGeneratorInterval() {
        return getIntValue(NATURE_INTERVAL, DEFAULT_NATURE_INTERVAL);
    }

    public static int getMobGeneratorCost() {
        return getIntValue(MOB_COST, DEFAULT_MOB_COST);
    }

    public static int getMobGeneratorInterval() {
        return getIntValue(MOB_INTERVAL, DEFAULT_MOB_INTERVAL);
    }

    public static boolean isItemEnabled(FilterGroup group, Item item) {
        ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(item);
        if (key == null) {
            return false;
        }

        Set<ResourceLocation> whitelist = getWhitelist(group);
        Set<ResourceLocation> blacklist = getBlacklist(group);
        if (!whitelist.isEmpty() && !whitelist.contains(key)) {
            return false;
        }
        return !blacklist.contains(key);
    }

    private static ForgeConfigSpec.ConfigValue<List<? extends String>> stringList(String path) {
        return BUILDER
                .comment("Optional list of item ids. When whitelist is non-empty, only listed items are allowed.")
                .defineListAllowEmpty(path, List.of(), value -> value instanceof String string && isValidResourceLocation(string));
    }

    private static boolean isValidResourceLocation(String value) {
        try {
            ResourceLocation.parse(value);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static Set<ResourceLocation> parseLocations(List<? extends String> values) {
        Set<ResourceLocation> locations = new LinkedHashSet<>();
        for (String value : values) {
            try {
                locations.add(ResourceLocation.parse(value));
            } catch (Exception ignored) {
                // Ignore invalid entries so one bad config line does not break startup.
            }
        }
        return Set.copyOf(locations);
    }

    private static int getIntValue(ForgeConfigSpec.IntValue value, int fallback) {
        try {
            return value.get();
        } catch (IllegalStateException ignored) {
            return fallback;
        }
    }

    private static double getDoubleValue(ForgeConfigSpec.DoubleValue value, double fallback) {
        try {
            return value.get();
        } catch (IllegalStateException ignored) {
            return fallback;
        }
    }

    private static Set<ResourceLocation> getWhitelist(FilterGroup group) {
        return switch (group) {
            case ORE -> oreWhitelist;
            case NATURE -> natureWhitelist;
            case MOB -> mobWhitelist;
        };
    }

    private static Set<ResourceLocation> getBlacklist(FilterGroup group) {
        return switch (group) {
            case ORE -> oreBlacklist;
            case NATURE -> natureBlacklist;
            case MOB -> mobBlacklist;
        };
    }

    public enum FilterGroup {
        ORE,
        NATURE,
        MOB
    }
}
