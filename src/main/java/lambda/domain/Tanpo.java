package lambda.domain;

public class Tanpo {
	private String shoban;
	private String shumoku;
	private String meisai;
	private String tanpo;
	
	
	public Tanpo(String shoban, String shumoku, String meisai, String tanpo) {
		super();
		this.shoban = shoban;
		this.shumoku = shumoku;
		this.meisai = meisai;
		this.tanpo = tanpo;
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
	
}
