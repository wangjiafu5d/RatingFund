package com.ratingfund.app.util;

import java.util.Comparator;

import com.ratingfund.app.model.Fund;

public class DiscountComparator implements Comparator<Fund>{

	@Override
	public int compare(Fund f1, Fund f2) {
		
//		return Double.parseDouble(f1.getDiscount())-Double.parseDouble(f2.getDiscount())>0?1:-1;
		
		if(Double.parseDouble(f1.getDiscount())-Double.parseDouble(f2.getDiscount())>0){
			return 1;
		}else if (Double.parseDouble(f1.getDiscount())-Double.parseDouble(f2.getDiscount())<0) {
			return -1;
		}
		return 0;
	}

}
