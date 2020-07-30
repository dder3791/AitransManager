package com.aiTrans.vo;

public class MoData {
/**
 * "mo": [{
   "cn": "google",
   "cv": "1.0.0",
   "cd": "2020.02.28",
   "cen": 1,
   "cr": 1
 */
	
	private String cn;
	private String cv;
	private String cd;
	private String cen;
	private String cr;
	//private Integer ct;
	private String op;
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCen() {
		return cen;
	}
	public void setCen(String cen) {
		this.cen = cen;
	}
	public String getCr() {
		return cr;
	}
	public void setCr(String cr) {
		this.cr = cr;
	}
	
	/*public Integer getCt() {
		return ct;
	}
	public void setCt(Integer ct) {
		this.ct = ct;
	}*/
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	@Override
	public String toString() {
		return "MoData [cn=" + cn + ", cv=" + cv + ", cd=" + cd + ", cen=" + cen + ", cr=" + cr + ", ct=" + ", op="
				+ op + "]";
	}
	
	
	
}
