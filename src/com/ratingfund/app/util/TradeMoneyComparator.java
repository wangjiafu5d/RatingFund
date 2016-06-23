package com.ratingfund.app.util;

import java.util.Comparator;
import com.ratingfund.app.model.FundA;

public class TradeMoneyComparator implements Comparator<FundA>{

	@Override
	public int compare(FundA f1, FundA f2) {
		if(Double.parseDouble(f1.getTradeMoney())-Double.parseDouble(f2.getTradeMoney())>0){
			return 1;
		}else if (Double.parseDouble(f1.getTradeMoney())-Double.parseDouble(f2.getTradeMoney())<0) {
			return -1;
		}
		return 0;
	}

}
