package com.aiTrans.vo;

public class MoData {

	
	private String cn;//云翻译模块名称
	private String cv;//云翻译模块版本
	private String cd;//发布时间
	private String cen;//云翻译模块是否生效（默认为生效）
	private String cr;//云翻译模块排名
	private String op;//本项云翻译模块需要执行的操作
/**
	（增加、更新、删除）；值分为：
	81:增加
	82:更新
	83:删除
	10:更新云翻译模块配置文件(目前暂仅修改排名**/
	//2.8版本 新增字段
	private String ch;//md5值
	private String cl;//云翻译模块发布日志
	private String ct;//云翻译模块文件类型(值：ZIP或DLL(默认DLL，如果该字段不存在，则自动误别个DLL))
	private String cs;//模块文件大小（字节）
	private String cp;//模块名称(本地存储名称)
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
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	@Override
	public String toString() {
		return "MoData [cn=" + cn + ", cv=" + cv + ", cd=" + cd + ", cen=" + cen + ", cr=" + cr + ", ct=" + ", op="
				+ op + "]";
	}
	
	
	
}
