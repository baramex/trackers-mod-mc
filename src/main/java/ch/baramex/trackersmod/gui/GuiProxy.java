package ch.baramex.trackersmod.gui;

import javax.annotation.Nullable;

import ch.baramex.trackersmod.tileentity.TileEntityServer;
import ch.baramex.trackersmod.tileentity.TileEntityTracker;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntityTracker te = (TileEntityTracker) world.getTileEntity(pos);
	        if (te instanceof TileEntityTracker) {
	            return new TrackerGui(player.inventory, te);
	        }
		}
		if(ID == 1) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntityServer te = (TileEntityServer) world.getTileEntity(pos);
	        if (te instanceof TileEntityServer) {
	            return new ServerGui(player.inventory, te);
	        }
		}
		if(ID == 2) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntityTrackerAdv te = (TileEntityTrackerAdv) world.getTileEntity(pos);
	        if (te instanceof TileEntityTrackerAdv) {
	            return new TrackerAdvGui(player.inventory, te);
	        }
		}
        return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntityTracker te = (TileEntityTracker) world.getTileEntity(pos);
	        if (te instanceof TileEntityTracker) {
	        	return new TrackerGuiMachine(te, new TrackerGui(player.inventory, te));
	        }
		}
		if(ID == 1) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntityServer te = (TileEntityServer) world.getTileEntity(pos);
	        if (te instanceof TileEntityServer) {
        	return new ServerGuiMachine(te, new ServerGui(player.inventory, te));
	        }
		}
		if(ID == 2) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntityTrackerAdv te = (TileEntityTrackerAdv) world.getTileEntity(pos);
	        if (te instanceof TileEntityTrackerAdv) {
        	return new TrackerAdvGuiMachine(te, new TrackerAdvGui(player.inventory, te));
	        }
		}
        return null;
	}
}
