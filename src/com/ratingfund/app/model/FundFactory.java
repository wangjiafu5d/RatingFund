package com.ratingfund.app.model;

public class FundFactory {
	
	
	public Fund createFund(String type){
		switch (type) {
		case "FundA":
			return new FundA();
		case "FundB":
			return new FundB();
		case "FundM":
			return new FundM();
		case "FundNI":
			return new FundNI();
		case "FundHB":
			return new FundHB();
			

		default:
			return null;
		}
	}
}
