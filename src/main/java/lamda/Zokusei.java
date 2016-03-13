package lamda;

public class Zokusei {
	private String shoban;
	private String shumoku;
	private String meisai;
	private String tanpo;
	private String zokusei;
	private String value;
	public Zokusei(String shoban, String shumoku, String meisai, String tanpo, String zokusei, String value) {
		super();
		this.shoban = shoban;
		this.shumoku = shumoku;
		this.meisai = meisai;
		this.tanpo = tanpo;
		this.zokusei = zokusei;
		this.value = value;
	}
	public String getShoban() {
		return shoban;
	}
	public void setShoban(String shoban) {
		this.shoban = shoban;
	}
	public String getShumoku() {
		return shumoku;
	}
	public void setShumoku(String shumoku) {
		this.shumoku = shumoku;
	}
	public String getMeisai() {
		return meisai;
	}
	public void setMeisai(String meisai) {
		this.meisai = meisai;
	}
	public String getTanpo() {
		return tanpo;
	}
	public void setTanpo(String tanpo) {
		this.tanpo = tanpo;
	}
	public String getZokusei() {
		return zokusei;
	}
	public void setZokusei(String zokusei) {
		this.zokusei = zokusei;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
