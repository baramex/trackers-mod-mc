package ch.baramex.trackersmod.utils;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import scala.swing.TextComponent;

public class ItemToolTip extends Item {
	
	List<String> tooltip;
	
	public ItemToolTip(List<String> tt) {
		tooltip = tt;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tt, ITooltipFlag flagIn) {
		tt.addAll(tooltip);
		
		super.addInformation(stack, worldIn, tt, flagIn);
	}
}
