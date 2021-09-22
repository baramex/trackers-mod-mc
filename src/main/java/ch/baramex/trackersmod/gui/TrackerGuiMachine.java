package ch.baramex.trackersmod.gui;

import java.awt.Color;
import java.util.List;

import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.network.packetSyncRequestTrack;
import ch.baramex.trackersmod.tileentity.TileEntityTracker;
import ch.baramex.trackersmod.utils.Reference;
import ch.baramex.trackersmod.utils.Status;
import ch.baramex.trackersmod.utils.Status.TrackStatus;
import ch.baramex.trackersmod.utils.TextComponents;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class TrackerGuiMachine extends GuiContainer {

	public static final int WIDTH = 181;
    public static final int HEIGHT = 200;
    
    private TileEntityTracker tile;
    private int playerSelectedId = 0;
    private String result = "";
    
    private static final ResourceLocation background = new ResourceLocation(Reference.MODID, "textures/gui/machines/tracker.png");
    
    public TrackerGuiMachine(TileEntityTracker tileEntity, TrackerGui container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
        
        tile = tileEntity;
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	if(button.id == 0) {
    		if(playerSelectedId+1<tile.getClientPlayerEntities().size()) {
    			playerSelectedId++;
    		}
    	}
    	if(button.id == 1) {
    		if(playerSelectedId-1>=0) {
    			playerSelectedId--;
    		}
    	}
    	if(button.id == 2) {
    		if(tile.getClientPlayerEntities().size() > 0) {
	    		if(tile.getClientPlayerEntities().get(playerSelectedId) != null && tile.getClientGood() == TrackStatus.READY) {
	    			result = "";
	    			messages.instance.sendToServer(new packetSyncRequestTrack(true, tile.getPos(), tile.getClientPlayerEntities().get(playerSelectedId)));
	    		}
    		}
    	}
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	result = tile.getClientResult();
    	List<String> playersName = tile.getClientPlayerEntities();
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        if(playersName.size() > 0) {
	        if(playerSelectedId >= playersName.size()) {
	        	playerSelectedId = playersName.size()-1;
	        }
	        else if(playerSelectedId < 0) {
	        	playerSelectedId = 0;
	        }
        }
        
        String name = playersName.size() > 0 ? playersName.get(playerSelectedId) : TextComponents.noPlayer.getFormattedText();
        
        if(buttonList.size() == 0) {
        	buttonList.add(new GuiButton(0, guiLeft+10, guiTop+40, 20, 20, ">"));
    		buttonList.add(new GuiButton(1, guiLeft+10, guiTop+40, 20, 20, "<"));
    		buttonList.add(new GuiButton(2, guiLeft+xSize/2-50, guiTop+65, 100, 20, TextComponents.trackThePlayer.getFormattedText()));
        }
        else {
        	buttonList.set(0, new GuiButton(0, guiLeft+xSize/2+fontRenderer.getStringWidth(name)/2+5, guiTop+40, 20, 20, ">"));
    		buttonList.set(1, new GuiButton(1, guiLeft+xSize/2-fontRenderer.getStringWidth(name)/2-20-5, guiTop+40, 20, 20, "<"));
        }
        
        fontRenderer.drawString(name, guiLeft+xSize/2-fontRenderer.getStringWidth(name)/2, guiTop+45, Color.BLACK.getRGB());
        
        fontRenderer.drawString(TextComponents.getTileName("tracker").getFormattedText(), 5 + guiLeft, 5 + guiTop, Color.BLACK.getRGB());
    	int color = Color.RED.getRGB();
    	if(tile.getClientGood() == Status.TrackStatus.READY || tile.getClientGood() == Status.TrackStatus.TRACKING) {
    		color = Color.GREEN.getRGB();
    	}
    	drawString(fontRenderer, tile.getClientGood().getTextComponent().getFormattedText(), guiLeft+3, guiTop+20, color);
    	
    	if(result != "") {
    		drawCenteredString(fontRenderer, TextComponents.coordinate.getFormattedText(), guiLeft+xSize/2, guiTop+90, Color.GREEN.getRGB());
    		drawCenteredString(fontRenderer, result, guiLeft+xSize/2, guiTop+100, Color.GREEN.getRGB());
    	}
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	renderHoveredToolTip(mouseX, mouseY);
    }
}
