package com.ratingfund.app.util;

import java.util.Comparator;

import com.ratingfund.app.model.Fund;

public class CodeComparator implements Comparator<Fund>{

	@Override
	public int compare(Fund f1, Fund f2) {
		
		return f1.getFund_code().compareTo(f2.getFund_code());
		
		
	}

	

}
