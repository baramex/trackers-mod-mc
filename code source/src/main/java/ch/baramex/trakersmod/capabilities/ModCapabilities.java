package ch.baramex.trakersmod.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
	
	@CapabilityInject(IServerC.class)
	public static final Capability<IServerC> CAPABILITY_SERVER = null;
	
	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IServerC.class, new CapabilityServer(), ServerC.class);
	}
	
	public static class CapabilityServer implements IStorage<IServerC> {
		
		@Override
		public NBTBase writeNBT(Capability<IServerC> capability, IServerC instance, EnumFacing side) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("an", instance.getServerAntenne());
			return nbt;
		}

		@Override
		public void readNBT(Capability<IServerC> capability, IServerC instance, EnumFacing side, NBTBase nbt) {
			
		}
	}
}
