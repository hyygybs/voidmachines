package com.hyygybs.voidmachines.common.util;

import com.hyygybs.voidmachines.common.registration.VMItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class VoidMatterHelper {
    private VoidMatterHelper() {
    }

    public static List<Item> getBaseVoidMatterItems() {
        return VMItems.getBaseVoidMatterItems();
    }

    public static ItemStack createRandomBaseVoidMatter(RandomSource random) {
        List<Item> items = getBaseVoidMatterItems();
        return new ItemStack(items.get(random.nextInt(items.size())));
    }
}
