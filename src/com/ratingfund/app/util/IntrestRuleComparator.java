package com.ratingfund.app.util;

import java.util.Comparator;

import com.ratingfund.app.model.Fund;

public class IntrestRuleComparator implements Comparator<Fund>{

	@Override
	public int compare(Fund f1, Fund f2) {
		
		return f1.getIntrestRule().compareTo(f2.getIntrestRule());
	}

}
