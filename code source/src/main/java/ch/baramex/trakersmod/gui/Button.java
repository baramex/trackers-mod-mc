package ch.baramex.trakersmod.gui;

import ch.baramex.trakersmod.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class Button extends GuiButton {

	public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/button/buttonbasic.png"));
		GlStateManager.color(1, 1, 1, 1);
		boolean mouseDragged = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		this.mouseDragged(mc, mouseX, mouseY);
		int i3 = this.getHoverState(mouseDragged);
		if(i3 == 1) {
			this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width, this.height);
			this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 0, this.width / 2, this.height);
		}
		else {
			this.drawTexturedModalRect(this.x, this.y, 0, 10, this.width, this.height);
			this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 10, this.width / 2, this.height);
		}
		
		int textColor = 0xffffff;
		
		this.drawString(mc.fontRenderer, this.displayString, this.x+2, this.y+1, textColor);
	}
}
