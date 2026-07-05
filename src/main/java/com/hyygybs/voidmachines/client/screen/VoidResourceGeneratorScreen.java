package com.hyygybs.voidmachines.client.screen;

import com.hyygybs.voidmachines.VoidMachines;
import com.hyygybs.voidmachines.common.menu.VoidResourceGeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class VoidResourceGeneratorScreen extends AbstractMachineScreen<VoidResourceGeneratorMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(VoidMachines.MODID, "textures/gui/void_resource_generator_gui.png");
    private static final int PROGRESS_BAR_X = 26;
    private static final int PROGRESS_BAR_Y = 21;

    public VoidResourceGeneratorScreen(VoidResourceGeneratorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, TEXTURE, 222);
        this.inventoryLabelY = 129;
    }

    @Override
    protected void renderMachineWidgets(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        renderProgressBar(graphics, leftPos + PROGRESS_BAR_X, topPos + PROGRESS_BAR_Y, menu.getProgress(), menu.getMaxProgress());
    }

    @Override
    protected void renderCustomTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
        if (isHoveringRegion(mouseX, mouseY, ENERGY_BAR_X, ENERGY_BAR_Y, ENERGY_BAR_WIDTH, ENERGY_BAR_HEIGHT)) {
            graphics.renderTooltip(this.font, Component.translatable("gui.voidmachines.energy", menu.getEnergyStored(), menu.getMaxEnergyStored()), mouseX, mouseY);
        } else if (isHoveringRegion(mouseX, mouseY, PROGRESS_BAR_X, PROGRESS_BAR_Y, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT)) {
            graphics.renderTooltip(this.font, Component.translatable("gui.voidmachines.cycle", menu.getProgress(), menu.getMaxProgress()), mouseX, mouseY);
        }
    }
}
