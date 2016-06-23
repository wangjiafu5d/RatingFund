package com.ratingfund.app.model;


import java.math.BigDecimal;
import java.text.NumberFormat;

import android.util.Log;

public class FundA extends Fund{
	private String closing;
	private String tradeAmount;
	private String tradeMoney;
	private String bAmount1;
	private String bPrice1;
	private String sAmount1;
	private String sPrice1;
	private String correctedIntrest;
	
	public void setFund(String[] temp){
		this.setFund_code(temp[0]);
		this.setFund_name(temp[1]);
		closing = temp[3];
		this.setCurrent(temp[4]);
		this.setBprice(temp[7]);
		this.setSprice(temp[8]);
		
//		BigDecimal hundreds = new BigDecimal(100);
		BigDecimal b_closing = new BigDecimal(temp[3]);
		BigDecimal b_current = new BigDecimal(temp[4]);
		BigDecimal b_rising;
		if (b_closing.compareTo(BigDecimal.ZERO) != 0) {
		b_rising = b_current.divide(b_closing,4,BigDecimal.ROUND_HALF_UP).
				subtract(BigDecimal.ONE).
				divide(new BigDecimal(0.01), 2, BigDecimal.ROUND_HALF_UP);
		}else {
			b_rising = BigDecimal.ZERO;
		}
//		b_rising = b_rising.multiply(hundreds);
//		NumberFormat percent = NumberFormat.getPercentInstance();
//		percent.setMaximumFractionDigits(2);
		this.setRising(b_rising.toString());
//		Log.d("TAG4", ""+percent.format(b_rising.doubleValue()));
		
//		tradeAmount = ""+System.currentTimeMillis();
//		Log.d("TAG", temp[0]+" "+ temp[1]+" "+temp[3]+" "+temp[4]+" ");
		BigDecimal b_tradeMoney = new BigDecimal(temp[10]);
		tradeMoney = b_tradeMoney.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP).toString();
		
	}
	public String getCorrectedIntrest() {
		return correctedIntrest;
	}
	public void setCorrectedIntrest(String correctedIntrest) {
		this.correctedIntrest = correctedIntrest;
	}
	
	public String getClosing() {
		return closing;
	}
	public void setClosing(String closing) {
		this.closing = closing;
	}
	
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getTradeMoney() {
		return tradeMoney;
	}
	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	public String getbAmount1() {
		return bAmount1;
	}
	public void setbAmount1(String bAmount1) {
		this.bAmount1 = bAmount1;
	}
	public String getbPrice1() {
		return bPrice1;
	}
	public void setbPrice1(String bPrice1) {
		this.bPrice1 = bPrice1;
	}
	public String getsAmount1() {
		return sAmount1;
	}
	public void setsAmount1(String sAmount1) {
		this.sAmount1 = sAmount1;
	}
	public String getsPrice1() {
		return sPrice1;
	}
	public void setsPrice1(String sPrice1) {
		this.sPrice1 = sPrice1;
	}



}
