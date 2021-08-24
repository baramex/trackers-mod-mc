package ch.baramex.trackersmod.gui;

import java.awt.Color;
import java.util.List;
import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.network.packetSyncRequestTrack;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import ch.baramex.trackersmod.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.ResourceLocation;

public class TrackerAdvGuiMachine extends GuiContainer {

	public static final int WIDTH = 181;
    public static final int HEIGHT = 200;
    
    private TileEntityTrackerAdv tile;
    private int playerSelectedId = 0;
    private String result = "";
    private String clientName = Minecraft.getMinecraft().player.getName();
    
    private static final ResourceLocation background = new ResourceLocation(Reference.MODID, "textures/gui/machines/tracker.png");
    
    public TrackerAdvGuiMachine(TileEntityTrackerAdv tileEntity, TrackerAdvGui container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
        
        tile = tileEntity;
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	if(button.id == 0) {
    		if(playerSelectedId+1<tile.getClientPlayerEntities(clientName).size()) {
    			playerSelectedId++;
    		}
    	}
    	if(button.id == 1) {
    		if(playerSelectedId-1>=0) {
    			playerSelectedId--;
    		}
    	}
    	if(button.id == 2) {
    		if(tile.getClientPlayerEntities(clientName).get(playerSelectedId) != null && tile.getClientGood(clientName).equals("Ready for tracking")) {
    			result = "";
    			messages.instance.sendToServer(new packetSyncRequestTrack(true, tile.getPos(), tile.getClientPlayerEntities(clientName).get(playerSelectedId)));
    		}
    	}
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	result = tile.getClientResult(clientName);
    	List<String> playersName = tile.getClientPlayerEntities(clientName);
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        if(playerSelectedId >= playersName.size()) {
        	playerSelectedId = playersName.size()-1;
        }
        else if(playerSelectedId < 0) {
        	playerSelectedId = 0;
        }
        
        if(buttonList.size() == 0) {
        	buttonList.add(new GuiButton(0, guiLeft+10, guiTop+40, 20, 20, ">"));
    		buttonList.add(new GuiButton(1, guiLeft+10, guiTop+40, 20, 20, "<"));
    		buttonList.add(new GuiButton(2, guiLeft+xSize/2-50, guiTop+65, 100, 20, "Track the player"));
        }
        else {
        	buttonList.set(0, new GuiButton(0, guiLeft+fontRenderer.getWordWrappedHeight(playersName.get(playerSelectedId), playersName.get(playerSelectedId).length())/2+xSize/2-5, guiTop+40, 20, 20, ">"));
    		buttonList.set(1, new GuiButton(1, guiLeft+xSize/2-20-fontRenderer.getWordWrappedHeight(playersName.get(playerSelectedId), playersName.get(playerSelectedId).length())/2+5, guiTop+40, 20, 20, "<"));
        }
        
        fontRenderer.drawString(playersName.get(playerSelectedId), guiLeft+xSize/2-(fontRenderer.getWordWrappedHeight(playersName.get(playerSelectedId), playersName.get(playerSelectedId).length())/2)+12, guiTop+45, Color.BLACK.getRGB());
        
        fontRenderer.drawString("Tracker advanced", 5 + guiLeft, 5 + guiTop, Color.BLACK.getRGB());
    	int color = Color.RED.getRGB();
    	if(tile.getClientGood(clientName).equals("Ready for tracking") || tile.getClientGood(clientName).equals("Tracking...")) {
    		color = Color.GREEN.getRGB();
    	}
    	drawString(fontRenderer, tile.getClientGood(clientName), guiLeft+3, guiTop+20, color);
    	
    	if(result != "" && tile.getClientItemStack(clientName) != null) {
    		drawCenteredString(fontRenderer, "coordinate", guiLeft+xSize/2-40, guiTop+90, Color.GREEN.getRGB());
    		drawCenteredString(fontRenderer, result, guiLeft+xSize/2-40, guiTop+100, Color.GREEN.getRGB());
    		
    		GlStateManager.enableLight(0);
    		RenderItem renderItem = mc.getRenderItem();
			renderItem.renderItemIntoGUI(tile.getClientItemStack(clientName), guiLeft + xSize/2+40, guiTop + 90);
			drawCenteredString(fontRenderer, "x" + tile.getClientItemStack(clientName).getCount(), guiLeft+xSize/2+67, guiTop+94, Color.GREEN.getRGB());
			GlStateManager.disableLight(0);
    	}
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	renderHoveredToolTip(mouseX, mouseY);
    }
}
