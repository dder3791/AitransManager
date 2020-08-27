package com.aiTrans.vo;

public class ClientCheckData {
	private String un;	//用户名称(软件)
	private String uc;	//软件码
	private String sn;	//客户端软件名称(PAT 6.0等)
	private String at;	//认证时间
	private String wp;	//计算机系统平台(Windows 10等)
	private String ip;	//客户端软件IP	
	private String ll;	//(软件用户)授权版本（级别）
	private String lc;	//(软件用户)授权码
	private String lt;	//(软件用户)授权起始时间
	private String tp;	//翻译语言对
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public String getUc() {
		return uc;
	}
	public void setUc(String uc) {
		this.uc = uc;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAt() {
		return at;
	}
	public void setAt(String at) {
		this.at = at;
	}
	public String getWp() {
		return wp;
	}
	public void setWp(String wp) {
		this.wp = wp;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}	
	public String getLl() {
		return ll;
	}
	public void setLl(String ll) {
		this.ll = ll;
	}
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	public String getLt() {
		return lt;
	}
	public void setLt(String lt) {
		this.lt = lt;
	}
	public String getTp() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	@Override
	public String toString() {
		return "ClientCheckData [un=" + un + ", uc=" + uc + ", sn=" + sn + ", at=" + at + ", wp=" + wp + ", ip=" + ip
				+ ", ll=" + ll + ", lc=" + lc + ", lt=" + lt + ", tp=" + tp + "]";
	}
	
	
}
