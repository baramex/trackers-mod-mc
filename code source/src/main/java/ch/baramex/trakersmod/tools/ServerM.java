package ch.baramex.trakersmod.tools;

import ch.baramex.trakersmod.capabilities.ServerC;

public class ServerM extends ServerC {
	
	public ServerM(int antenne) {
		super(antenne);
	}
	
	public int getAntenne() {
		return this.getServerAntenne();
	}
	
	public void setAntenne(int antenne) {
		this.setServerAntenne(antenne);
	}
}
