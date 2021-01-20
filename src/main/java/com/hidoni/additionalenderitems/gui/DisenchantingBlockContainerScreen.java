package com.hidoni.additionalenderitems.gui;

import com.hidoni.additionalenderitems.AdditionalEnderItems;
import com.hidoni.additionalenderitems.containers.DisenchantingBlockContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DisenchantingBlockContainerScreen extends ContainerScreen<DisenchantingBlockContainer>
{
    private final ResourceLocation GUI = new ResourceLocation(AdditionalEnderItems.MOD_ID, "textures/gui/disenchanting_table.png");

    public DisenchantingBlockContainerScreen(DisenchantingBlockContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);
        if ((this.container.getSlot(0).getHasStack() && this.container.getSlot(1).getHasStack()) && this.container.getSlot(2).getHasStack() && !this.container.getSlot(3).getHasStack())
        {
            this.blit(matrixStack, relX + 92, relY + 31, this.xSize, 0, 28, 21);
        }
        if (this.container.requiredLevel != 0)
        {
            if (this.minecraft.player.experienceLevel >= this.container.requiredLevel || this.minecraft.player.isCreative())
            {
                int xOffset = this.container.requiredLevel == 5 ? 16 : 0;
                this.blit(matrixStack, relX + 131, relY + 53, 7 + xOffset, 176, 16, 11);
            }
            else
            {
                int xOffset = this.container.requiredLevel == 5 ? 16 : 0;
                this.blit(matrixStack, relX + 132, relY + 54, 8 + xOffset, 193, 14, 9);
            }
        }
    }
}
