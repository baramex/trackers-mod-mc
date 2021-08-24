package ch.baramex.trackersmod.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ch.baramex.trackersmod.capabilities.ModCapabilities;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.init.ModItems;
import ch.baramex.trackersmod.tools.ServerM;
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

public class TileEntityTracker extends TileEntity implements ITickable {
	
	public static final int ANTENNE_MIN = 1;
	public static final int ANTENNE_MAX = 3;
	public static final int ENERGY_CONSUME = 50000;
	public static final int MIN_BLOCK = 400;
	public static final int MAX_BLOCK = 500;
	public static final int MIN_PRECISION_BLOCK = 100;
	public static final int MAX_PRECISION_BLOCK = 150;
	
	public ServerM serverG = new ServerM(0);
	public String isGood = "error, requires server restart";
	public boolean requestTrack;
	public EntityPlayer player = null;
	public String playerName = null;
	public int work = 0;
	public int work1 = 0;
	public String result = "";
	
	public Map<String, String> clientIsGood = new HashMap<String, String>();
	public Map<String, Integer> clientAntenne = new HashMap<String, Integer>();
	public Map<String, List<String>> clientPlayerEntities = new HashMap<String, List<String>>();
	public Map<String, String> clientResult = new HashMap<String, String>();
	
	public String getClientResult(String name) {
		if(this.clientResult.get(name) == null) {
			return "error, requires server restart";
		}
		else {
			return this.clientResult.get(name);
		}
	}
	
	public void setClientResult(String pl, String name) {
		if(this.clientResult.containsKey(name)) {
			this.clientResult.replace(name, pl);
		}
		else {
			this.clientResult.put(name, pl);
		}
	}
	
	public List<String> getClientPlayerEntities(String name) {
		if(this.clientPlayerEntities.get(name) == null) {
			List<String> l = new ArrayList<String>();
			l.add("error, requires server restart");
			return l;
		}
		else {
			return this.clientPlayerEntities.get(name);
		}
	}
	
	public void setClientPlayerEntities(List<String> pl, String name) {
		if(this.clientPlayerEntities.containsKey(name)) {
			this.clientPlayerEntities.replace(name, pl);
		}
		else {
			this.clientPlayerEntities.put(name, pl);
		}
	}
	
	public int getClientAntenne(String name) {
		if(this.clientAntenne.get(name) == null) {
			return -1;
		}
		else {
			return this.clientAntenne.get(name);
		}
	}
	
	public void setClientAntenne(int antenne, String name) {
		if(this.clientAntenne.containsKey(name)) {
			this.clientAntenne.replace(name, antenne);
		}
		else {
			this.clientAntenne.put(name, antenne);
		}
	}
	
	public String getClientGood(String name) {
		if(this.clientIsGood.get(name) == null) {
			return "error, requires server restart";
		}
		else {
			return this.clientIsGood.get(name);
		}
	}
	
	public void setClientGood(String isGood, String name) {
		if(this.clientIsGood.containsKey(name)) {
			this.clientIsGood.replace(name, isGood);
		}
		else {
			this.clientIsGood.put(name, isGood);
		}
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			if(work1 == 0) {
				if(result != "" ) {
					result = "";
				}
			}
			else {
				work1--;
			}
			if(work == 0) {
				if(getTileEntityByCapability(CapabilityEnergy.ENERGY) != null) {
					if(serverG.getAntenne() != 0 && getTileEntityByCapability(CapabilityEnergy.ENERGY).getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() != 0) {
						if(serverG.getAntenne() >= ANTENNE_MIN) {
							if(getTileEntityByCapability(CapabilityEnergy.ENERGY).getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() >= ENERGY_CONSUME) {
								isGood = "Ready for tracking";
							}
							else {
								isGood = "Not enough energy";
							}
						}
						else {
							isGood = "Not enough antenna";
						}
					}
					else {
						isGood = "Server not found/Server is empty";
					}
				}
				else {
					isGood = "Server not found/Server is empty";
				}
			}
			else {
				work--;
			}
			
			if(requestTrack && isGood == "Ready for tracking") {
				result = "";
				if(player == null && playerName != null) {
					player = world.getPlayerEntityByName(playerName);
					playerName = null;
					work = 20;
					requestTrack = false;
					// 1 / 4
					getTileEntityByCapability(CapabilityEnergy.ENERGY).getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(ENERGY_CONSUME/4, false);
					if(player != null) {
						int distance = 0;
						if(player.posX > pos.getX()) {
							distance = (int)player.posX - pos.getX();
						}
						else {
							distance = pos.getX() - (int)player.posX;
						}
						if(player.posZ > pos.getZ()) {
							distance += (int)player.posZ - pos.getZ();
						}
						else {
							distance += pos.getZ() - (int)player.posZ;
						}
						int nbAntenne = serverG.getAntenne();
						if(nbAntenne > ANTENNE_MAX) {
							nbAntenne = ANTENNE_MAX;
						}
						int blockMax = MIN_BLOCK+((nbAntenne-ANTENNE_MIN)*((MAX_BLOCK-MIN_BLOCK)/(ANTENNE_MAX-ANTENNE_MIN)));
						if(distance <= blockMax) {
							// 2 / 4
							getTileEntityByCapability(CapabilityEnergy.ENERGY).getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(ENERGY_CONSUME/4, false);
							if(!player.inventory.hasItemStack(new ItemStack(ModItems.anti_tracker))) {
								int x = (int) player.posX-9;
				    			int y = (int) 0;
				    			int z = (int) player.posZ-9;
				    			
				    			boolean isBrouiller = false;
				    			
								for(int a = 0; a < 19; a++) {
									for(int b = 0; b < 19; b++) {
										for(int c = 0; c < 257; c++) {
											if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == ModBlocks.brouiller) {
												isBrouiller = true;
												break;
											}
											y++;
										}
										y = 0;
										z++;
									}
									z = (int) player.posZ-9;
									x++;
								}
								
								if(!isBrouiller) {
									// 3 / 4
									getTileEntityByCapability(CapabilityEnergy.ENERGY).getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(ENERGY_CONSUME/4, false);
									boolean isBlock = false;
									
									int y1 = (int)player.posY+2;
									for(int a = 0;a < 257-(int)player.posY; a++) {
										if(world.getBlockState(new BlockPos(Math.floor(player.posX), y1, Math.floor(player.posZ))).getBlock() != Blocks.AIR) {
											isBlock = true;
											break;
										}
										y1++;
									}
									
									if(!isBlock) {
										// 4 / 4
										getTileEntityByCapability(CapabilityEnergy.ENERGY).getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(ENERGY_CONSUME/4, false);
										isGood = "Tracking...";
							        	int pre = MAX_PRECISION_BLOCK-((nbAntenne-ANTENNE_MIN)*((MAX_PRECISION_BLOCK-MIN_PRECISION_BLOCK)/(ANTENNE_MAX-ANTENNE_MIN)));
							        	int ran = new Random().nextInt(pre);
							        	int ran1 = new Random().nextInt(pre-ran);
							        	int x1 = (int) player.posX-pre+ran;
							        	int z1 = (int) player.posZ-(pre-ran)+ran1;
							        	result = "x: " + x1 + " z: " + z1;
										work = 10;
										work1 = 500;
									}
									else {
										isGood = "Tracking failed";
									}
								}
								else {
									isGood = "Tracking failed";
								}
							}
							else {
								isGood = "Tracking failed";
							}
						}
						else {
							isGood = player.getName() + " is too far";
						}
					}
					else {
						isGood = "Player not found";
					}
				}
			}
			if(player != null) {
				player = null;
			}
			markDirty();
		}
	}
	
	public TileEntity getTileEntityByCapability(Capability<?> cap) {
		TileEntity te = null;
		for(int a = 0; a < 4; a++) {
			if(world.getTileEntity(getPosByFacing(a)) != null) {
				if(world.getTileEntity(getPosByFacing(a)).hasCapability(cap, null)) {
					te = world.getTileEntity(getPosByFacing(a));
				}
			}
		}
		return te;
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
		if(capability == ModCapabilities.CAPABILITY_SERVER) {
			return ModCapabilities.CAPABILITY_SERVER.cast(serverG);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == ModCapabilities.CAPABILITY_SERVER) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return super.writeToNBT(compound);
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}
}
