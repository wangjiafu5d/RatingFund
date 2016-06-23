package com.ratingfund.app.util;

import java.util.Comparator;

import com.ratingfund.app.model.Fund;

public class RisingComparator implements Comparator<Fund>{

	@Override
	public int compare(Fund f1, Fund f2) {
		if(Double.parseDouble(f1.getRising())-Double.parseDouble(f2.getRising())>0){
			return 1;
		}else if (Double.parseDouble(f1.getRising())-Double.parseDouble(f2.getRising())<0) {
			return -1;
		}
		return 0;
	}

}
