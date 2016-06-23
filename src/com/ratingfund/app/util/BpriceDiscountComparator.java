package com.ratingfund.app.util;

import java.util.Comparator;

import com.ratingfund.app.model.FundM;

public class BpriceDiscountComparator implements Comparator<FundM>{

	@Override
	public int compare(FundM f1, FundM f2) {
		if(Double.parseDouble(f1.getBpriceDiscount())-Double.parseDouble(f2.getBpriceDiscount())>0){
			return 1;
		}else if (Double.parseDouble(f1.getBpriceDiscount())-Double.parseDouble(f2.getBpriceDiscount())<0) {
			return -1;
		}
		return 0;
	}

}
