package com.hyygybs.voidmachines.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hyygybs.voidmachines.common.config.VMConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public final class GeneratorContentCache {
    private static final long CACHE_TTL = 20L * 60L;
    private static final WeakHashMap<MinecraftServer, CacheBucket> CACHE = new WeakHashMap<>();

    private static final List<TagKey<Item>> ORE_TAGS = List.of(
            forgeTag("ores"),
            forgeTag("ingots"),
            forgeTag("gems"),
            forgeTag("dusts"),
            forgeTag("nuggets"),
            forgeTag("raw_materials"),
            forgeTag("coals")
    );

    private static final List<TagKey<Item>> NATURE_TAGS = List.of(
            forgeTag("logs"),
            forgeTag("leaves"),
            minecraftTag("flowers"),
            minecraftTag("tall_flowers"),
            forgeTag("crops"),
            forgeTag("fruits"),
            minecraftTag("seeds"),
            minecraftTag("saplings"),
            forgeTag("mushrooms")
    );

    private GeneratorContentCache() {
    }

    public static List<Item> getOreItems(ServerLevel level) {
        return getCached(level, CacheKind.ORE, () -> collectTagItems(level, ORE_TAGS, VMConfig.FilterGroup.ORE));
    }

    public static List<Item> getNatureItems(ServerLevel level) {
        return getCached(level, CacheKind.NATURE, () -> collectTagItems(level, NATURE_TAGS, VMConfig.FilterGroup.NATURE));
    }

    public static List<Item> getMobLootItems(ServerLevel level) {
        return getCached(level, CacheKind.MOB, () -> collectMobLootItems(level.getServer()));
    }

    private static List<Item> getCached(ServerLevel level, CacheKind kind, Supplier<List<Item>> supplier) {
        CacheBucket bucket = CACHE.computeIfAbsent(level.getServer(), ignored -> new CacheBucket());
        CachedList cached = bucket.entries.get(kind);
        long now = level.getGameTime();
        if (cached == null || cached.expireGameTime <= now) {
            cached = new CachedList(List.copyOf(supplier.get()), now + CACHE_TTL);
            bucket.entries.put(kind, cached);
        }
        return cached.items;
    }

    private static List<Item> collectTagItems(ServerLevel level, List<TagKey<Item>> tags, VMConfig.FilterGroup filterGroup) {
        Set<Item> items = new LinkedHashSet<>();
        var itemRegistry = level.registryAccess().registryOrThrow(Registries.ITEM);
        for (TagKey<Item> tag : tags) {
            itemRegistry.getTag(tag).ifPresent(named -> named.forEach(holder -> {
                Item item = holder.value();
                if (item != null && item != net.minecraft.world.item.Items.AIR && VMConfig.isItemEnabled(filterGroup, item)) {
                    items.add(item);
                }
            }));
        }
        return new ArrayList<>(items);
    }

    private static List<Item> collectMobLootItems(MinecraftServer server) {
        Set<Item> items = new LinkedHashSet<>();
        Map<ResourceLocation, Resource> resources = server.getResourceManager().listResources(
                "loot_tables/entities",
                path -> path.getPath().endsWith(".json")
        );
        Map<ResourceLocation, JsonElement> tableMap = new HashMap<>();

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            try (InputStreamReader reader = new InputStreamReader(entry.getValue().open(), StandardCharsets.UTF_8)) {
                JsonElement json = JsonParser.parseReader(reader);
                tableMap.put(toLootTableId(entry.getKey()), json);
            } catch (Exception ignored) {
                // Ignore malformed or dynamic loot tables so one bad datapack does not break the machine.
            }
        }

        for (ResourceLocation lootTableId : tableMap.keySet()) {
            collectItemEntries(tableMap, lootTableId, items, new HashSet<>());
        }

        return new ArrayList<>(items);
    }

    private static void collectItemEntries(Map<ResourceLocation, JsonElement> tableMap, ResourceLocation lootTableId, Set<Item> items, Set<ResourceLocation> visited) {
        if (!visited.add(lootTableId)) {
            return;
        }
        JsonElement element = tableMap.get(lootTableId);
        if (element != null) {
            collectItemEntries(element, items, tableMap, visited);
        }
    }

    private static void collectItemEntries(JsonElement element, Set<Item> items, Map<ResourceLocation, JsonElement> tableMap, Set<ResourceLocation> visited) {
        if (element == null || element.isJsonNull()) {
            return;
        }

        if (element.isJsonArray()) {
            for (JsonElement child : element.getAsJsonArray()) {
                collectItemEntries(child, items, tableMap, visited);
            }
            return;
        }

        if (!element.isJsonObject()) {
            return;
        }

        JsonObject object = element.getAsJsonObject();
        if (object.has("type") && object.has("name")) {
            String type = object.get("type").getAsString();
            if ("minecraft:item".equals(type)) {
                Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(object.get("name").getAsString()));
                if (item != null && item != net.minecraft.world.item.Items.AIR && VMConfig.isItemEnabled(VMConfig.FilterGroup.MOB, item)) {
                    items.add(item);
                }
            } else if ("minecraft:loot_table".equals(type)) {
                try {
                    collectItemEntries(tableMap, ResourceLocation.parse(object.get("name").getAsString()), items, visited);
                } catch (Exception ignored) {
                    // Ignore broken loot-table references.
                }
            }
        }

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            collectItemEntries(entry.getValue(), items, tableMap, visited);
        }
    }

    private static ResourceLocation toLootTableId(ResourceLocation resourcePath) {
        String path = resourcePath.getPath();
        String trimmed = path.substring("loot_tables/".length(), path.length() - ".json".length());
        return ResourceLocation.fromNamespaceAndPath(resourcePath.getNamespace(), trimmed);
    }

    private static TagKey<Item> forgeTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
    }

    private static TagKey<Item> minecraftTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", path));
    }

    private enum CacheKind {
        ORE,
        NATURE,
        MOB
    }

    private record CachedList(List<Item> items, long expireGameTime) {
    }

    private static final class CacheBucket {
        private final java.util.EnumMap<CacheKind, CachedList> entries = new java.util.EnumMap<>(CacheKind.class);
    }
}
