package ch.baramex.trakersmod.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ch.baramex.trakersmod.capabilities.ModCapabilities;
import ch.baramex.trakersmod.init.ModBlocks;
import ch.baramex.trakersmod.tools.EnergyM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityServer extends TileEntity implements ITickable {
	
	public EnergyM energy = new EnergyM(1000000, 100000);
	public int antenne;
	public int nbMachine;
	
	public Map<String, Map<String, Integer>> clientInt = new HashMap<String, Map<String, Integer>>();
	
	public int getClientInt(String name, String param) {
		if(clientInt.get(param) != null) {
			if(this.clientInt.get(param).get(name) == null) {
				return -1;
			}
			else {
				return this.clientInt.get(param).get(name);
			}
		}
		else {
			return -1;
		}
	}
	
	public void setClientInt(int inte, String name, String param) {
		if(this.clientInt.containsKey(param)) {
			if(this.clientInt.get(param).containsKey(name)) {
				this.clientInt.get(param).replace(name, inte);
			}
			else {
				this.clientInt.get(param).put(name, inte);
			}
		}
		else {
			this.clientInt.put(param, new HashMap<String, Integer>());
			this.clientInt.get(param).put(name, inte);
		}
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			antenne = getAntenne();
			int nbMachine1 = 0;
			for(int a = 0; a < 4; a++) {
				if(world.getTileEntity(getPosByFacing(a)) != null) {
					if(world.getTileEntity(getPosByFacing(a)).hasCapability(ModCapabilities.CAPABILITY_SERVER, null)) {
						nbMachine1++;
						world.getTileEntity(getPosByFacing(a)).getCapability(ModCapabilities.CAPABILITY_SERVER, null).setServerAntenne(antenne);
					}
				}
			}
			nbMachine = nbMachine1;
			markDirty();
		}
	}
	
	public int getAntenne() {
		int a = 0;
		int x = pos.getX()-9;
		int y = 0;
		int z = pos.getZ()-9;
		for(int b = 0; b < 19; b++) {
			for(int c = 0; c < 19; c++) {
				for(int d = 0; d < 257; d++) {
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == ModBlocks.antenne) {
						a++;
					}
					y++;
				}
				y = 0;
				z++;
			}
			z = pos.getZ()-9;
			x++;
		}
		return a;
	}
	
	public BlockPos getPosByFacing(int face) {
		BlockPos b = null;
		if(face == 0) {
			b = pos.north();
		}
		else if(face == 1) {
			b = pos.south();
		}
		else if(face == 2) {
			b = pos.east();
		}
		else if(face == 3) {
			b = pos.west();
		}
		else {
			b = pos.north();
		}
		return b;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energy);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.energy.setEnergy(compound.getInteger("en"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("en", this.energy.getEnergyStored());
		return super.writeToNBT(compound);
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

}
