package com.ratingfund.app.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.model.FundHB;
import com.ratingfund.app.model.FundM;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.model.FundNI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RatingFundDB {
	public static final String DB_NAME = "rating_fund";
	
	public static final int VERSION = 1;
	
	private static RatingFundDB ratingFundDB;
	
	public SQLiteDatabase db;
	
	private RatingFundDB(Context context){
		RatingFundOpenHelper dbHelper = new RatingFundOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
		
	}
	
	public synchronized static RatingFundDB getInstacnce(Context context){
		if (ratingFundDB==null) {
			ratingFundDB = new RatingFundDB(context);
		}
		return ratingFundDB;
	}
	
	public void savedFundA(FundA fund){
//		Log.d("TAG4", "saveStart");
		
		if(fund!=null){
//			Log.d("TAG", "valuesPutStart");
			
			ContentValues values = new ContentValues();
			values.put("fund_code", fund.getFund_code());
			values.put("fund_name", fund.getFund_name());
			values.put("closing", fund.getClosing());
			values.put("rising", fund.getRising());
			values.put("bprice", fund.getBprice());
			values.put("sprice", fund.getSprice());
			values.put("current", fund.getCurrent());
			values.put("tradeMoney", fund.getTradeMoney());
			values.put("discount", getDiscount(fund));
			values.put("corrected_intrest", getCorrectedInterest(fund));
//			Log.d("TAG5", "put"+fund.getTradeMoney());
			db.update("FundA", values, "fund_code=?", new String[]{fund.getFund_code()});
//			db.insert("FundA", null, values);
//			long result = db.insert("FundA", null, values);
//			Log.d("TAG4", "re " + result);
		}
	}
	public void savedFundB(FundB fund){

		if(fund!=null){
			ContentValues values = new ContentValues();
			values.put("fund_b_code", fund.getFund_code());
			values.put("fund_b_name", fund.getFund_name());
			values.put("b_rising", fund.getRising());
			values.put("b_bprice", fund.getBprice());
			values.put("b_sprice", fund.getSprice());
			values.put("b_current", fund.getCurrent());
			values.put("b_tradeMoney", fund.getTradeMoney());
			values.put("b_price_leverage", getPriceLeverage(fund));
			values.put("b_discount", getFundBDiscount(fund));
			
			values.put("m_rising", getFundMRising(fund));
			values.put("m_tradeMoney", getFundMTradeMoney(fund));
			values.put("m_sprice_discount", getFundMPriceDiscount(fund,"sell"));
			values.put("m_bprice_discount", getFundMPriceDiscount(fund,"buy"));
			values.put("m_current", getFundMCurrent(fund));
			
			db.update("FundA", values, "fund_b_code=?", new String[]{fund.getFund_code()});
		}
	}
	public List<FundA> loadFundA(){
		List<FundA> list = new ArrayList<FundA>();
		Cursor cursor = db.query("FundA", null, null, null, null, null, null);
		List<String> filterList = getFilterList();
//		Log.d("TAG1", "load fund a"+cursor.moveToFirst());
		if(cursor.moveToFirst()){
			do {
				FundA fund = new FundA();
				if (filteFund(cursor, filterList,"FundA")) {
					continue ;
				}
//				fund.setId(cursor.getInt(cursor.getColumnIndex("id")));
				fund.setFund_code(cursor.getString(cursor.getColumnIndex("fund_code")));
				fund.setFund_name(cursor.getString(cursor.getColumnIndex("fund_name")));
//				fund.setClosing(cursor.getString(cursor.getColumnIndex("closing")));
				fund.setRising(cursor.getString(cursor.getColumnIndex("rising")));
				fund.setCurrent(cursor.getString(cursor.getColumnIndex("current")));
				fund.setTradeMoney(cursor.getString(cursor.getColumnIndex("tradeMoney")));
//				fund.setJjjz(cursor.getString(cursor.getColumnIndex("jjjz")));
				fund.setDiscount(cursor.getString(cursor.getColumnIndex("discount")));
				fund.setIndex(cursor.getString(cursor.getColumnIndex("hy_index")));
				fund.setIndex_name(cursor.getString(cursor.getColumnIndex("index_name")));
				fund.setIndex_rising(cursor.getString(cursor.getColumnIndex("index_rising")));
				fund.setCorrectedIntrest(cursor.getString(cursor.getColumnIndex("corrected_intrest")));
				fund.setIntrestRule(cursor.getString(cursor.getColumnIndex("intrest_rule")));
//				Log.d("TAG10", "load "+fund.getIndex_rising());
//				if (cursor.getString(cursor.getColumnIndex("intrest_rule")).equals("+3.0%")) {
					list.add(fund);
//				}
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	public List<FundB> loadFundB(){
//		Log.d("TAG1", "load fund b");
		List<FundB> list = new ArrayList<FundB>();
		Cursor cursor = db.query("FundA", null, null, null, null, null, null);
		List<String> filterList = getFilterList();
		if(cursor.moveToFirst()){
			do {
				if (filteFund(cursor, filterList,"FundB")) {
					continue ;
				}
				FundB fund = new FundB();
				fund.setFund_code(cursor.getString(cursor.getColumnIndex("fund_b_code")));
				fund.setFund_name(cursor.getString(cursor.getColumnIndex("fund_b_name")));
				fund.setRising(cursor.getString(cursor.getColumnIndex("b_rising")));
				fund.setCurrent(cursor.getString(cursor.getColumnIndex("b_current")));
				fund.setTradeMoney(cursor.getString(cursor.getColumnIndex("b_tradeMoney")));
				fund.setDiscount(cursor.getString(cursor.getColumnIndex("b_discount")));
				fund.setPriceLeverage(cursor.getString(cursor.getColumnIndex("b_price_leverage")));
				fund.setIntrestRule(cursor.getString(cursor.getColumnIndex("intrest_rule")));
				fund.setIndex(cursor.getString(cursor.getColumnIndex("hy_index")));
				fund.setIndex_name(cursor.getString(cursor.getColumnIndex("index_name")));
				fund.setIndex_rising(cursor.getString(cursor.getColumnIndex("index_rising")));
				list.add(fund);
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	
	public List<FundM> loadFundM(){
		List<FundM> list = new ArrayList<FundM>();
		Cursor cursor = db.query("FundA", null, null, null, null, null, null);
		List<String> filterList = getFilterList();
		if(cursor.moveToFirst()){
			do {
				if (filteFund(cursor, filterList,"FundM")) {
					continue ;
				}
				FundM fund = new FundM();
				fund.setFund_code(cursor.getString(cursor.getColumnIndex("fund_m_code")));
				fund.setFund_name(cursor.getString(cursor.getColumnIndex("fund_m_name")));
				fund.setRising(cursor.getString(cursor.getColumnIndex("m_rising")));
				fund.setTradeMoney(cursor.getString(cursor.getColumnIndex("m_tradeMoney")));
				fund.setSpriceDiscount(cursor.getString(cursor.getColumnIndex("m_sprice_discount")));
				fund.setBpriceDiscount(cursor.getString(cursor.getColumnIndex("m_bprice_discount")));
				
				
				list.add(fund);
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	public List<FundA> loadFundZX(){
		List<FundA> list = new ArrayList<FundA>();
		Cursor cursor = db.query("Zixuan", null, null, null, null, null, null);
		
		if(cursor.moveToFirst()){
			do {
				FundA fund = new FundA();
				String code = cursor.getString(cursor.getColumnIndex("fund_code"));
				String category = cursor.getString(cursor.getColumnIndex("fund_category"));
				fund.setFund_code(code);
				Cursor cursor2 = db.query("FundA", null, "fund_code=? or fund_b_code=? or fund_m_code=?",
						new String[]{code,code,code}, null, null, null);
				if (cursor2.moveToFirst()) {
					switch (category) {
					case "a":
						fund.setFund_name(cursor2.getString(cursor2.getColumnIndex("fund_name")));
						fund.setCurrent(cursor2.getString(cursor2.getColumnIndex("current")));
						fund.setRising(cursor2.getString(cursor2.getColumnIndex("rising")));
						fund.setTradeMoney(cursor2.getString(cursor2.getColumnIndex("tradeMoney")));
						fund.setDiscount(cursor2.getString(cursor2.getColumnIndex("discount")));
						break;
					case "b":
						fund.setFund_name(cursor2.getString(cursor2.getColumnIndex("fund_b_name")));
						fund.setCurrent(cursor2.getString(cursor2.getColumnIndex("b_current")));
						fund.setRising(cursor2.getString(cursor2.getColumnIndex("b_rising")));
						fund.setTradeMoney(cursor2.getString(cursor2.getColumnIndex("b_tradeMoney")));
						fund.setDiscount(cursor2.getString(cursor2.getColumnIndex("b_discount")));
						break;
					case "m":
						fund.setFund_name(cursor2.getString(cursor2.getColumnIndex("fund_m_name")));
						fund.setCurrent(cursor2.getString(cursor2.getColumnIndex("m_current")));
						fund.setRising(cursor2.getString(cursor2.getColumnIndex("m_rising")));
						fund.setTradeMoney(cursor2.getString(cursor2.getColumnIndex("m_tradeMoney")));
						fund.setDiscount(cursor2.getString(cursor2.getColumnIndex("m_bprice_discount")));
						break;	
				}
				}
				list.add(fund);
			} while (cursor.moveToNext());
		}
		
		return list;
	}
	
	public void savedFundJjjz(Fund fund,String category){
		ContentValues values = new ContentValues();
		if (category.equals("a")) {
			values.put("jjjz", fund.getJjjz());
			db.update("FundA", values, "fund_code=?", new String[] { fund.getFund_code() });
		}
		if (category.equals("b")) {
			values.put("b_jjjz", fund.getJjjz());
			db.update("FundA", values, "fund_b_code=?", new String[] { fund.getFund_code() });
		}
		if (category.equals("m")) {
			values.put("m_jjjz", fund.getJjjz());
//			values.put("fund_m_name", fund.getFund_name());
			db.update("FundA", values, "fund_m_code=?", new String[] { fund.getFund_code() });
		}
	}
	public void saveIndex(Fund fund,String table){
		ContentValues values = new ContentValues();
		values.put("index_name", fund.getIndex_name());
		values.put("index_rising", fund.getIndex_rising());
		 db.update(table, values, "hy_index=?",new String[]{fund.getIndex()});
		
	}
	private String getDiscount(FundA fund){
		BigDecimal cPrice = new BigDecimal(fund.getCurrent());
		if (cPrice.compareTo(BigDecimal.ZERO)==0) {
			return "0";
		}
		Cursor cursor = db.query("FundA", null, "fund_code=?", new String[]{fund.getFund_code()}, null, null, null);
		BigDecimal jjjz = null;
		if(cursor.moveToFirst()){
			jjjz = new BigDecimal(cursor.getString(cursor.getColumnIndex("jjjz")));
		}
		if(jjjz!=null&&jjjz.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal discount = jjjz.subtract(cPrice).
				divide(jjjz,4,BigDecimal.ROUND_HALF_UP).
				divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP);
				return discount.toString();
		}else {
			return "0";
		}
		
	}
	private String getFundBDiscount(FundB fund){
		BigDecimal cPrice = new BigDecimal(fund.getCurrent());
		if (cPrice.compareTo(BigDecimal.ZERO)==0) {
			return "0";
		}
		Cursor cursor = db.query("FundA", null, "fund_b_code=?", new String[]{fund.getFund_code()}, null, null, null);
		BigDecimal b_jjjz = null;
		String ratioString = "";
		String m_jjjz = "";
		String index_rising = "";
		if(cursor.moveToFirst()){
			ratioString = cursor.getString(cursor.getColumnIndex("ratio"));
			b_jjjz = new BigDecimal(cursor.getString(cursor.getColumnIndex("b_jjjz")));
			m_jjjz = cursor.getString(cursor.getColumnIndex("m_jjjz"));
			index_rising = cursor.getString(cursor.getColumnIndex("index_rising"));
		}
		if(b_jjjz!=null&&b_jjjz.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal ratio = new BigDecimal(ratioString.substring(2, 3));
			BigDecimal indexRising = new BigDecimal(index_rising);
			BigDecimal mJjjz = new BigDecimal(m_jjjz);
			BigDecimal mJjjzRising = indexRising.multiply(new BigDecimal(0.95)).multiply(mJjjz).
					divide(BigDecimal.TEN).divide(ratio);
			BigDecimal discount = b_jjjz.add(mJjjzRising)
				.subtract(cPrice).
				divide(b_jjjz.add(mJjjzRising),4,BigDecimal.ROUND_HALF_UP).
				divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP);
				return discount.toString();
		}else {
			return "0";
		}
		
	}
	private String getCorrectedInterest(FundA fund){
		BigDecimal cPrice = new BigDecimal(fund.getCurrent());
		if (cPrice.compareTo(BigDecimal.ZERO)==0) {
			return "0";
		}
		Cursor cursor = db.query("FundA", null, "fund_code=?", new String[]{fund.getFund_code()}, null, null, null);
		BigDecimal jjjz = null;
		BigDecimal intrest = null;
		if(cursor.moveToFirst()&&cursor.getString(cursor.getColumnIndex("intrest_term")).equals("永续")){
			jjjz = new BigDecimal(cursor.getString(cursor.getColumnIndex("jjjz")));
			intrest = new BigDecimal(cursor.getString(cursor.getColumnIndex("intrest")));
		}
		if(jjjz!=null&&jjjz.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal corrected_intrest = intrest.
				divide(cPrice.subtract(jjjz).add(BigDecimal.ONE),3,BigDecimal.ROUND_HALF_UP);
				return corrected_intrest.toString();
		}else {
			return "0";
		}
	}
	private String getPriceLeverage(FundB fund){
		try {
			BigDecimal cPrice = new BigDecimal(fund.getCurrent());
			if (cPrice.compareTo(BigDecimal.ZERO)==0) {
				return "0";
			}
			Cursor cursor = db.query("FundA", null, "fund_b_code=?", new String[]{fund.getFund_code()}, null, null, null);
			String ratioString = "";
			String m_jjjz = "";
			String index_rising = "";
			if(cursor.moveToFirst()){
				ratioString = cursor.getString(cursor.getColumnIndex("ratio"));
				m_jjjz = cursor.getString(cursor.getColumnIndex("m_jjjz"));
				index_rising = cursor.getString(cursor.getColumnIndex("index_rising"));
			}
			BigDecimal indexRising = new BigDecimal(index_rising);
			BigDecimal ratio = new BigDecimal(ratioString.substring(2, 3));
			BigDecimal mJjjz = new BigDecimal(m_jjjz);
			BigDecimal mJjjzRising = indexRising.divide(BigDecimal.valueOf(100)).multiply(new BigDecimal(0.95)).add(BigDecimal.ONE);
			BigDecimal leverage = mJjjz.multiply(mJjjzRising).
					multiply(BigDecimal.TEN).
					divide(cPrice,2,BigDecimal.ROUND_HALF_UP).
					divide(ratio,2,BigDecimal.ROUND_HALF_UP);
			return leverage.toString();
		} catch (Exception e) {
			return "0";
		}
		
	}
	private String getFundMRising(Fund fund){
		try {		
			Cursor cursor = db.query("FundA", null, "fund_b_code=?", new String[]{fund.getFund_code()}, null, null, null);
			String index_rising = "";
			if(cursor.moveToFirst()){
				index_rising = cursor.getString(cursor.getColumnIndex("index_rising"));
			}
			BigDecimal indexRising = new BigDecimal(index_rising);			
			BigDecimal mJjjzRising = indexRising.multiply(new BigDecimal(0.95)).
					divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP);			
			return mJjjzRising.toString();
		} catch (Exception e) {
			return "0";
		}
	}
	private String getFundMTradeMoney(Fund fund){
		try {		
			Cursor cursor = db.query("FundA", null, "fund_b_code=?", new String[]{fund.getFund_code()}, null, null, null);
			
			String tradeMoney ="";
			String bTradeMoney ="";
			if(cursor.moveToFirst()){
				tradeMoney = cursor.getString(cursor.getColumnIndex("tradeMoney"));
				bTradeMoney = cursor.getString(cursor.getColumnIndex("b_tradeMoney"));
			}
			BigDecimal mTradeMoney = (new BigDecimal(tradeMoney)).add(new BigDecimal(bTradeMoney));		
			return mTradeMoney.toString();
		} catch (Exception e) {
			return "0";
		}
	}
	private String getFundMPriceDiscount(Fund fund,String category){
		try {		
			Cursor cursor = db.query("FundA", null, "fund_b_code=?", new String[]{fund.getFund_code()}, null, null, null);
			
			String ratioString = "";
			String m_jjjz = "";
			String index_rising = "";
			String a_sprice = "";
			String b_sprice = "";
			if(cursor.moveToFirst()){
				ratioString = cursor.getString(cursor.getColumnIndex("ratio"));
				m_jjjz = cursor.getString(cursor.getColumnIndex("m_jjjz"));
				index_rising = cursor.getString(cursor.getColumnIndex("index_rising"));
				if (category.equals("sell")) {
					a_sprice = cursor.getString(cursor.getColumnIndex("sprice"));
					b_sprice = cursor.getString(cursor.getColumnIndex("b_sprice"));
				}
				if (category.equals("buy")) {
					a_sprice = cursor.getString(cursor.getColumnIndex("bprice"));
					b_sprice = cursor.getString(cursor.getColumnIndex("b_bprice"));
				}
			}
			BigDecimal indexRising = new BigDecimal(index_rising);
			BigDecimal aRatio = new BigDecimal(ratioString.substring(0, 1));
			BigDecimal bRatio = new BigDecimal(ratioString.substring(2, 3));
			BigDecimal mJjjz = new BigDecimal(m_jjjz);
			BigDecimal aSPrice = new BigDecimal(a_sprice);
			BigDecimal bSPrice = new BigDecimal(b_sprice);
			BigDecimal mSPrice = aSPrice.multiply(aRatio).add(bSPrice.multiply(bRatio)).divide(BigDecimal.TEN);
			BigDecimal mJjjzRising = indexRising.divide(BigDecimal.valueOf(100)).multiply(new BigDecimal(0.95)).add(BigDecimal.ONE);
			BigDecimal m_sPriceDiscount = mJjjz.multiply(mJjjzRising).
					divide(mSPrice,6,BigDecimal.ROUND_HALF_UP).
					subtract(BigDecimal.ONE).
					multiply(BigDecimal.valueOf(100)).
					divide(BigDecimal.ONE,3,BigDecimal.ROUND_HALF_UP);
					
			return m_sPriceDiscount.toString();
		} catch (Exception e) {
			return "0";
		}
	}
	
	private String getFundMCurrent(Fund fund){
		try {		
			Cursor cursor = db.query("FundA", null, "fund_b_code=?", new String[]{fund.getFund_code()}, null, null, null);
			String ratioString = "";
			String a_current = "";
			String b_current = "";
			if(cursor.moveToFirst()){
				ratioString = cursor.getString(cursor.getColumnIndex("ratio"));
				a_current = cursor.getString(cursor.getColumnIndex("current"));
				b_current = cursor.getString(cursor.getColumnIndex("b_current"));
				
			}
			
			BigDecimal aRatio = new BigDecimal(ratioString.substring(0, 1));
			BigDecimal bRatio = new BigDecimal(ratioString.substring(2, 3));
		
			BigDecimal aCurrent = new BigDecimal(a_current);
			BigDecimal bCurrent = new BigDecimal(b_current);
			BigDecimal m_current = aCurrent.multiply(aRatio).
					add(bCurrent.multiply(bRatio)).
					divide(BigDecimal.TEN,4,BigDecimal.ROUND_HALF_UP);
				
			return m_current.toString();
		} catch (Exception e) {
			return "0";
		}
	}
	
	private boolean filteFund(Cursor cursor,List<String> filterList,String category){
		if (filterList.size() == 9) {
			
			String intrestTerm = cursor.getString(cursor.getColumnIndex("intrest_term"));
			switch (intrestTerm) {
			case "永续":
				if(filterList.get(8).equals("其他")){
					return true;
				}
				break;
			default:
				if(filterList.get(8).equals("永续")){
					return true;
				}
				break;
					
			}
			String intrestRule = cursor.getString(cursor.getColumnIndex("intrest_rule"));
			String money ="";
			if (category.equals("FundA")) {
				 money = cursor.getString(cursor.getColumnIndex("tradeMoney"));
			}
			if (category.equals("FundB")) {
				 money = cursor.getString(cursor.getColumnIndex("b_tradeMoney"));
			}
			if (category.equals("FundM")) {
				 money = cursor.getString(cursor.getColumnIndex("m_tradeMoney"));
			}
			boolean moneyFliter = Double.valueOf(money) >= Double.valueOf(filterList.get(7)) ? true : false;
			if (moneyFliter) {
				switch (intrestRule) {
				case "+3.0%":
					if ("true".equals(filterList.get(0))) {
						return false;
					}
					break;
				case "+3.2%":
					if ("true".equals(filterList.get(1))) {
						return false;
					}
					break;
				case "+3.5%":
					if ("true".equals(filterList.get(2))) {
						return false;
					}
					break;
				case "+4.0%":
					if ("true".equals(filterList.get(3))) {
						return false;
					}
					break;
				case "+4.5%":
					if ("true".equals(filterList.get(4))) {
						return false;
					}
					break;
				case "+5.0%":
					if ("true".equals(filterList.get(5))) {
						return false;
					}
					break;
				default:
					if ("true".equals(filterList.get(6))) {
						return false;
					}
					break;
				}

			}
		}
		return true;
	}
	private List<String> getFilterList(){
		List<String> filterList = new ArrayList<String>();
		Cursor filterCousor = db.query("Filter", null, null, null, null, null, null);
		if(filterCousor.moveToFirst()){
			do{
				filterList.add(filterCousor.getString(filterCousor.getColumnIndex("rule_value")));
			}while(filterCousor.moveToNext());
		}
		return filterList;
	}
}
