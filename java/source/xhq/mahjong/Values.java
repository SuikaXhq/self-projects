package xhq.mahjong;

public enum Values {
	DAN_YAO(1),
	PIN_HU(1),
	I_PE_KO(1),
	DUI_DUI(2),
	CHI_DUI(2),
	I_TSU(2),
	CHIN_I_TSU(6),
	SAN_AN_KOU(2),
	CYAN_DAI(3),
	LYAN_PE_KO(3),
	SU_AN_KOU(true),
	LYU_I_SO(true),
	KYU_LEN(true);
	private final int points;
	private final boolean isYaKuMan;
	
	public int getPoints() {
		return points;
	}

	public boolean isYaKuMan() {
		return isYaKuMan;
	}

	Values(int points) {
		this.points = points;
		isYaKuMan = false;
	}
	
	Values(boolean b) {
		points = 0;
		isYaKuMan = b;
	}
}
