package ch.baramex.trakersmod.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class ServerC implements IServerC, INBTSerializable<NBTTagCompound> {
	
	private int antenne;
	
	public ServerC(int antenne) {
		this.antenne = antenne;
	}

	@Override
	public int getServerAntenne() {
		return antenne;
	}
	
	@Override
	public void setServerAntenne(int antenne) {
		this.antenne = antenne;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("an", this.antenne);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.antenne = nbt.getInteger("an");
	}
}
