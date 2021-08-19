package ch.baramex.trackersmod.gui;

import ch.baramex.trackersmod.container.IIntContainer;
import ch.baramex.trackersmod.network.messages;
import ch.baramex.trackersmod.network.packetSyncInt;
import ch.baramex.trackersmod.tileentity.TileEntityServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ServerGui extends Container implements IIntContainer {
	
	private TileEntityServer te;

    public ServerGui(IInventory playerInventory, TileEntityServer te) {
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
				if(te.energy.getEnergyStored() != te.getClientInt(player.getName(), "energy")) {
					messages.instance.sendTo(new packetSyncInt(te.energy.getEnergyStored(), "energy"), player);
					te.setClientInt(te.energy.getEnergyStored(), player.getName(), "energy");
				}
				if(te.antenne != te.getClientInt(player.getName(), "antenne")) {
					messages.instance.sendTo(new packetSyncInt(te.antenne, "antenne"), player);
					te.setClientInt(te.antenne, player.getName(), "antenne");
				}
				if(te.nbMachine != te.getClientInt(player.getName(), "nbMachine")) {
					messages.instance.sendTo(new packetSyncInt(te.nbMachine, "nbMachine"), player);
					te.setClientInt(te.nbMachine, player.getName(), "nbMachine");
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
	
	@Override
	public void syncInt(int inte, String name, String param) {
		te.setClientInt(inte, name, param);
	}
}