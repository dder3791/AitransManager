package com.aiTrans.vo;

import java.util.List;

public class CloudData {
	private String sn;
	private String ip;
	private String un;	
	private List<MoData> mo;
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	
	public List<MoData> getMo() {
		return mo;
	}
	public void setMo(List<MoData> mo) {
		this.mo = mo;
	}
	@Override
	public String toString() {
		return "CloudData [sn=" + sn + ", ip=" + ip + ", un=" + un + ", mo=" + mo + "]";
	}
	
	
}
