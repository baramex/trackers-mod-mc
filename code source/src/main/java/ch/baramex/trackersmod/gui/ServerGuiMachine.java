package ch.baramex.trackersmod.gui;

import java.awt.Color;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.tileentity.TileEntityServer;
import ch.baramex.trackersmod.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ServerGuiMachine extends GuiContainer {

	public static final int WIDTH = 181;
    public static final int HEIGHT = 200;
    
    private TileEntityServer tile;
    private String clientName = Minecraft.getMinecraft().player.getName();
    
    private static final ResourceLocation background = new ResourceLocation(Reference.MODID, "textures/gui/machines/server.png");
    
    public ServerGuiMachine(TileEntityServer tileEntity, ServerGui container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
        
        tile = tileEntity;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        drawEnergyBar(tile.getClientInt(clientName, "energy"));
        
        fontRenderer.drawString("Server", 5 + guiLeft, 5 + guiTop, Color.BLACK.getRGB());
        
        fontRenderer.drawString(tile.getClientInt(clientName, "antenne") + " antenna(s)" , 90 + guiLeft, 40 + guiTop, tile.getClientInt(clientName, "antenne") <= 0 ? Color.RED.getRGB() : tile.getClientInt(clientName, "antenne") <= 2 ? Color.ORANGE.getRGB() : Color.BLACK.getRGB());
        fontRenderer.drawString(tile.getClientInt(clientName, "nbMachine") + " machine(s)" , 90 + guiLeft, 70 + guiTop, tile.getClientInt(clientName, "nbMachine") <= 0 ? Color.RED.getRGB() : Color.BLACK.getRGB());
        
        GlStateManager.enableLight(0);
        RenderItem renderItem = mc.getRenderItem();
		renderItem.renderItemIntoGUI(new ItemStack(ModBlocks.antenne), guiLeft + 70, guiTop + 35);
		renderItem.renderItemIntoGUI(new ItemStack(ModBlocks.tracker), guiLeft + 70, guiTop + 65);
		GlStateManager.disableLight(0);
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	renderHoveredToolTip(mouseX, mouseY);
    	
    	if(mouseX > guiLeft+xSize-21 && mouseX < guiLeft+xSize-9 && mouseY > guiTop+9 && mouseY < guiTop+100+11) {
    		drawHoveringText(tile.getClientInt(clientName, "energy") + " RF", mouseX, mouseY);
    	}
    }
	
	private void drawEnergyBar(int energy) {
		if(energy != -1) {
			for(int x = 0; x < 100; x++) {
				drawHorizontalLine(guiLeft+xSize-20, guiLeft+xSize-10, guiTop+10+x, (x % 2 == 0 ? new Color(110, 25, 25).getRGB() : new Color(48, 1, 1).getRGB()));
			}
			for(int x = 0; x < 100*energy/1000000; x++) {
				drawHorizontalLine(guiLeft+xSize-20, guiLeft+xSize-10, guiTop+100+9-x, (x % 2 == 1 ? new Color(184, 53, 53).getRGB() : new Color(92, 16, 16).getRGB()));
			}
			drawVerticalLine(guiLeft+xSize-21, guiTop+8, guiTop+101+8+2, Color.BLACK.getRGB());
			drawVerticalLine(guiLeft+xSize-9, guiTop+8, guiTop+101+8+2, Color.BLACK.getRGB());
			drawHorizontalLine(guiLeft+xSize-20, guiLeft+xSize-10, guiTop+9, Color.BLACK.getRGB());
			drawHorizontalLine(guiLeft+xSize-20, guiLeft+xSize-10, guiTop+100+10, Color.BLACK.getRGB());
		}
	}
}
