package com.ratingfund.app.util;

import java.util.Comparator;

import com.ratingfund.app.model.FundB;

public class PriceLeverageComparator implements Comparator<FundB>{

	@Override
	public int compare(FundB f1, FundB f2) {
		if(Double.valueOf(f1.getPriceLeverage())>Double.valueOf(f2.getPriceLeverage())){
			return 1;
		}else if(Double.valueOf(f1.getPriceLeverage())<Double.valueOf(f2.getPriceLeverage())){
			return -1;
		}
		return 0;
	}

}
