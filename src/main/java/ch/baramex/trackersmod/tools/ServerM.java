package ch.baramex.trackersmod.tools;

import ch.baramex.trackersmod.capabilities.ServerC;

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
