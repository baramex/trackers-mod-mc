package ch.baramex.trakersmod.gui;

import java.util.ArrayList;
import java.util.List;

import ch.baramex.trakersmod.container.IEntityPlayerContainer;
import ch.baramex.trakersmod.container.IIntContainer;
import ch.baramex.trakersmod.container.IIsGoodContainer;
import ch.baramex.trakersmod.container.IStringContainer;
import ch.baramex.trakersmod.network.messages;
import ch.baramex.trakersmod.network.packetSyncGood;
import ch.baramex.trakersmod.network.packetSyncInt;
import ch.baramex.trakersmod.network.packetSyncPlayerEntities;
import ch.baramex.trakersmod.network.packetSyncString;
import ch.baramex.trakersmod.tileentity.TileEntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TrackerGui extends Container implements IIsGoodContainer, IIntContainer, IEntityPlayerContainer, IStringContainer {
	
	private TileEntityTracker te;

    public TrackerGui(IInventory playerInventory, TileEntityTracker te) {
        this.te = te;
        addPlayerSlots(playerInventory);
    }

	private void addPlayerSlots(IInventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 118;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 118;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for(IContainerListener listener : listeners) {
			if(listener instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) listener;
				if(te.isGood != te.getClientGood(player.getName())) {
					messages.instance.sendTo(new packetSyncGood(te.isGood), player);
					te.setClientGood(te.isGood, player.getName());
				}
				if(te.serverG.getAntenne() != te.getClientAntenne(player.getName())) {
					messages.instance.sendTo(new packetSyncInt(te.serverG.getAntenne(), "antenne"), player);
					te.setClientAntenne(te.serverG.getAntenne(), player.getName());
				}
				if(te.result != te.getClientResult(player.getName())) {
					messages.instance.sendTo(new packetSyncString(te.result), player);
					te.setClientResult(te.result, player.getName());
				}
				List<String> playersName = new ArrayList<String>();
				for(EntityPlayer pl : te.getWorld().playerEntities) {
					playersName.add(pl.getName());
				}
				if(playersName != te.getClientPlayerEntities(player.getName())) {
					messages.instance.sendTo(new packetSyncPlayerEntities(playersName), player);
					te.setClientPlayerEntities(playersName, player.getName());
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}

	@Override
	public void syncGood(String isGood, String name) {
		te.setClientGood(isGood, name);
	}

	@Override
	public void syncInt(int inte, String name, String param) {
		if(param.equals("antenne")) {
			te.setClientAntenne(inte, name);
		}
	}

	@Override
	public void syncEntityPlayer(List<String> players, String name) {
		te.setClientPlayerEntities(players, name);
	}

	@Override
	public void syncResult(String result, String name) {
		te.setClientResult(result, name);
	}
}