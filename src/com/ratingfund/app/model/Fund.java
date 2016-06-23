package com.ratingfund.app.model;

public class Fund {
	private String hqTime;
	private int id;
	private String fund_code;
	private String fund_name;
	private String rising;
	private String jjjz;
	private String discount;
	private String index;
	private String index_name;
	private String index_rising;
	private String intrestRule;
	private String current;
	private String sprice;
	private String bprice;
	private String category;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSprice() {
		return sprice;
	}
	public void setSprice(String sprice) {
		this.sprice = sprice;
	}
	public String getBprice() {
		return bprice;
	}
	public void setBprice(String bprice) {
		this.bprice = bprice;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getIntrestRule() {
		return intrestRule;
	}
	public void setIntrestRule(String intrestRule) {
		this.intrestRule = intrestRule;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex_name() {
		return index_name;
	}
	public void setIndex_name(String index_name) {
		this.index_name = index_name;
	}
	public String getIndex_rising() {
		return index_rising;
	}
	public void setIndex_rising(String index_rising) {
		this.index_rising = index_rising;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	
	public String getRising() {
		return rising;
	}
	public void setRising(String rising) {
		this.rising = rising;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFund_code() {
		return fund_code;
	}
	public void setFund_code(String fund_code) {
		this.fund_code = fund_code;
	}
	public String getFund_name() {
		return fund_name;
	}
	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}
	public String getJjjz() {
		return jjjz;
	}
	public void setJjjz(String jjjz) {
		this.jjjz = jjjz;
	}
}
