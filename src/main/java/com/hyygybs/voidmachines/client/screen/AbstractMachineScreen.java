package com.hyygybs.voidmachines.client.screen;

import com.hyygybs.voidmachines.common.menu.AbstractMachineMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractMachineScreen<T extends AbstractMachineMenu> extends AbstractContainerScreen<T> {
    protected static final int TEXTURE_SIZE = 256;
    protected static final int ENERGY_BAR_X = 8;
    protected static final int ENERGY_BAR_Y = 21;
    protected static final int ENERGY_BAR_WIDTH = 12;
    protected static final int ENERGY_BAR_HEIGHT = 103;
    protected static final int PROGRESS_BAR_WIDTH = 120;
    protected static final int PROGRESS_BAR_HEIGHT = 16;

    private final ResourceLocation backgroundTexture;

    protected AbstractMachineScreen(T menu, Inventory inventory, Component title, ResourceLocation backgroundTexture, int imageHeight) {
        super(menu, inventory, title);
        this.backgroundTexture = backgroundTexture;
        this.imageWidth = 176;
        this.imageHeight = imageHeight;
        this.titleLabelY = 6;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        graphics.blit(backgroundTexture, x, y, 0, 0, imageWidth, imageHeight, TEXTURE_SIZE, TEXTURE_SIZE);
        renderEnergyBar(graphics, x + ENERGY_BAR_X, y + ENERGY_BAR_Y);
        renderMachineWidgets(graphics, partialTick, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);
        renderExtraLabels(graphics, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderCustomTooltips(graphics, mouseX, mouseY);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    protected void renderEnergyBar(GuiGraphics graphics, int x, int y) {
        int maxEnergy = Math.max(1, menu.getMaxEnergyStored());
        int filled = Mth.clamp(menu.getEnergyStored() * ENERGY_BAR_HEIGHT / maxEnergy, 0, ENERGY_BAR_HEIGHT);
        if (filled > 0) {
            int drawY = y + ENERGY_BAR_HEIGHT - filled;
            int sourceV = ENERGY_BAR_HEIGHT - filled;
            graphics.blit(backgroundTexture, x, drawY, imageWidth, sourceV, ENERGY_BAR_WIDTH, filled, TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }

    protected void renderProgressBar(GuiGraphics graphics, int x, int y, int progress, int maxProgress) {
        if (maxProgress <= 0) {
            return;
        }
        int filled = Mth.clamp(progress * PROGRESS_BAR_WIDTH / maxProgress, 0, PROGRESS_BAR_WIDTH);
        if (filled > 0) {
            graphics.blit(backgroundTexture, x, y, 0, imageHeight, filled, PROGRESS_BAR_HEIGHT, TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }

    protected boolean isHoveringRegion(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= leftPos + x && mouseX < leftPos + x + width && mouseY >= topPos + y && mouseY < topPos + y + height;
    }

    protected void renderCustomTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
    }

    protected abstract void renderMachineWidgets(GuiGraphics graphics, float partialTick, int mouseX, int mouseY);

    protected void renderExtraLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }
}
