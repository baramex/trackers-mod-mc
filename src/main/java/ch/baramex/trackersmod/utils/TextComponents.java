package ch.baramex.trackersmod.utils;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class TextComponents {
	
	public static final ITextComponent noPlayer = new TextComponentTranslation("message." + Reference.MODID + ".gui.noplayer");
	public static final ITextComponent antenna = new TextComponentTranslation("message." + Reference.MODID + ".gui.antenna");
	public static final ITextComponent trackThePlayer = new TextComponentTranslation("message." + Reference.MODID + ".gui.tracktheplayer");
	public static final ITextComponent coordinate = new TextComponentTranslation("message." + Reference.MODID + ".gui.coordinate");
	
	public static Style styleLink(String link, String text) {
		Style style = new Style();
		style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
		style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(text)));
		style.setColor(TextFormatting.BLUE);
		
		return style;
	}
	
	public static ITextComponent getTileName(String tileName) {
		return new TextComponentTranslation("tile." + tileName + ".name");
	}
}
