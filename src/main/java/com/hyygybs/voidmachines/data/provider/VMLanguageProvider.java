package com.hyygybs.voidmachines.data.provider;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.registration.VMBlocks;
import com.hyygybs.voidmachines.common.registration.VMItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class VMLanguageProvider extends LanguageProvider {
    public VMLanguageProvider(PackOutput output) {
        super(output, VoidMachines.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.voidmachines.main", "VoidMachines");
        addItem(VMItems.RED_VOID_MATTER, "红色虚空物质");
        addItem(VMItems.BLUE_VOID_MATTER, "蓝色虚空物质");
        addItem(VMItems.YELLOW_VOID_MATTER, "黄色虚空物质");
        addItem(VMItems.GREEN_VOID_MATTER, "绿色虚空物质");
        addItem(VMItems.VIOLET_VOID_MATTER, "紫色虚空物质");
        addItem(VMItems.BLACK_VOID_MATTER, "黑色虚空物质");
        addItem(VMItems.WHITE_VOID_MATTER, "白色虚空物质");
        addItem(VMItems.COLORFUL_VOID_MATTER, "彩色虚空物质");
        addItem(VMItems.SPEED_UPGRADE, "加速升级");
        addBlock(VMBlocks.VOID_ENERGY_GENERATOR, "虚空能量生成器");
        addBlock(VMBlocks.VOID_ORE_GENERATOR, "虚空矿石资源生成器");
        addBlock(VMBlocks.VOID_NATURE_GENERATOR, "虚空之物资源生成器");
        addBlock(VMBlocks.VOID_MOB_GENERATOR, "虚空生物资源生成器");
        add("gui.voidmachines.energy", "FE: %s / %s");
        add("gui.voidmachines.speed", "倍率: %sx");
        add("gui.voidmachines.upgrade", "升级");
        add("gui.voidmachines.upgrade_count", "升级: %s / %s");
        add("gui.voidmachines.output", "输出");
        add("gui.voidmachines.output_storage", "内部仓储");
        add("gui.voidmachines.extract", "取出");
        add("gui.voidmachines.cycle", "周期: %s / %s");
        add("gui.voidmachines.passive_generation", "持续生成虚空能量");
        add("gui.voidmachines.energy_buffer", "自动向相邻方块输出 FE");
        add("gui.voidmachines.progress_info", "持续生成中，当前倍率: %sx");
        add("message.voidmachines.machine_status", "FE: %s / %s，产物: %s");
        add("message.voidmachines.machine_status_energy_only", "FE: %s / %s");
        add("message.voidmachines.empty", "空");
        add("message.voidmachines.extracted", "已取出 %s");
    }
}
