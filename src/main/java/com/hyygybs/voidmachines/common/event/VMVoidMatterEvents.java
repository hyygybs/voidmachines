package com.hyygybs.voidmachines.common.event;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.config.VMConfig;
import com.hyygybs.voidmachines.common.util.VoidMatterHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VoidMachines.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class VMVoidMatterEvents {
    private VMVoidMatterEvents() {
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level) || event.isCanceled()) {
            return;
        }

        Player player = event.getPlayer();
        if (player == null || player.isCreative()) {
            return;
        }

        if (level.random.nextDouble() < VMConfig.getWorldBaseVoidMatterChance()) {
            Block.popResource(level, event.getPos(), VoidMatterHelper.createRandomBaseVoidMatter(level.random));
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel level) || event.isCanceled() || !event.isRecentlyHit()) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof Player)) {
            return;
        }

        if (level.random.nextDouble() < VMConfig.getWorldBaseVoidMatterChance()) {
            BlockPos pos = event.getEntity().blockPosition();
            event.getDrops().add(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, VoidMatterHelper.createRandomBaseVoidMatter(level.random)));
        }
    }

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel level) || event.isCanceled()) {
            return;
        }

        if (level.random.nextDouble() < VMConfig.getWorldBaseVoidMatterChance()) {
            event.getDrops().add(VoidMatterHelper.createRandomBaseVoidMatter(level.random));
        }
    }
}
