package ch.baramex.trackersmod.tileentity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ch.baramex.trackersmod.SoundsHandler;
import ch.baramex.trackersmod.capabilities.ModCapabilities;
import ch.baramex.trackersmod.config.Config;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.tools.EnergyM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityServer extends TileEntity implements ITickable {
	
	public EnergyM energy = new EnergyM(Config.MAX_STORED_ENERGY_SERVER, Config.MAX_ENERGY_RECEIVE_PER_TICK_SERVER);
	public int antenne;
	public int nbMachine;
	
	private int updateTime = 0;
	private int sound = 0;
	private long beforeTime = new Date().getTime();
	
	private Map<String, Integer> clientInt = new HashMap<String, Integer>();
	
	public int getClientInt(String param) {
		if(clientInt.get(param) != null) {
			return this.clientInt.get(param);
		}
		else {
			return -1;
		}
	}
	
	public void setClientInt(int inte, String param) {
		if(this.clientInt.containsKey(param)) {
			this.clientInt.replace(param, inte);
		}
		else {
			this.clientInt.put(param, inte);
		}
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			updateTime--;
			
			if(updateTime <= 0) {
				updateTime = 100;
				
				antenne = getAntenne();
				int nbMachine1 = 0;
				for(int a = 0; a < 6; a++) {
					if(world.getTileEntity(getPosByFacing(a)) != null) {
						if(world.getTileEntity(getPosByFacing(a)).hasCapability(ModCapabilities.CAPABILITY_SERVER, null)) {
							nbMachine1++;
							world.getTileEntity(getPosByFacing(a)).getCapability(ModCapabilities.CAPABILITY_SERVER, null).setServerAntenne(antenne);
						}
					}
				}
				nbMachine = nbMachine1;
			}
			
			if(sound <= 0) {
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundsHandler.BEEP, SoundCategory.BLOCKS, 0.8f, 0.8f);
			}
			
			sound += new Date().getTime() - beforeTime;
			
			if(sound >= SoundsHandler.BEEP_DURATION) {
				sound = 0;
			}
			
			beforeTime = new Date().getTime();
		
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
		else if(face == 4) {
			b = pos.up();
		}
		else if(face == 5) {
			b = pos.down();
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
