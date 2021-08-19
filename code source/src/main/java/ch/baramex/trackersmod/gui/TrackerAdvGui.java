package ch.baramex.trackersmod.gui;

import java.util.ArrayList;
import java.util.List;

import ch.baramex.trackersmod.container.IEntityPlayerContainer;
import ch.baramex.trackersmod.container.IIntContainer;
import ch.baramex.trackersmod.container.IIsGoodContainer;
import ch.baramex.trackersmod.container.IItemStackContainer;
import ch.baramex.trackersmod.container.IStringContainer;
import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.network.packetSyncGood;
import ch.baramex.trackersmod.network.packetSyncInt;
import ch.baramex.trackersmod.network.packetSyncItemStack;
import ch.baramex.trackersmod.network.packetSyncPlayerEntities;
import ch.baramex.trackersmod.network.packetSyncString;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TrackerAdvGui extends Container implements IIsGoodContainer, IIntContainer, IEntityPlayerContainer, IStringContainer, IItemStackContainer {
	
	private TileEntityTrackerAdv te;

    public TrackerAdvGui(IInventory playerInventory, TileEntityTrackerAdv te) {
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
				if(te.result != "" && te.playerItemStack != ItemStack.EMPTY) {
					if(te.playerItemStack != te.getClientItemStack(player.getName())) {
						messages.instance.sendTo(new packetSyncItemStack(te.playerItemStack), player);
						te.setClientItemStack(te.playerItemStack, player.getName());
					}
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
	public void syncPlayerItem(ItemStack item, String name) {
		te.setClientItemStack(item, name);
	}

	@Override
	public void syncResult(String result, String name) {
		te.setClientResult(result, name);
	}
}