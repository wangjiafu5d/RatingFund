package com.ratingfund.app.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ratingfund.app.db.RatingFundDB;
import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.model.FundFactory;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

public class PraseResponse {
	public synchronized static boolean handleFundAResponse(RatingFundDB ratingFundDB,String response){
//		Log.d("TAG", "PraseStart");
		
			Pattern p = Pattern.compile("((?=\\w+)[\\d]{6}(?==\"))([\\S]+(?=\"))");
			Matcher m = p.matcher(response);

//			Log.d("TAG", "Praseing");
			String str="";
//			try {
//				final String CREATE_FUNDA = "create table IF NOT EXISTS FundA ("
//						+ "id integer primary key autoincrement,"
//						+ "fund_code text,"
//						+ "fund_name text,"
//						+ "closing text,"
//						+ "current text)";
//				final String DELETE_SEQUENCE = "DELETE FROM sqlite_sequence WHERE name = 'FundA'";
//				ratingFundDB.db.delete("FundA", null, null);
//				ratingFundDB.db.execSQL(DELETE_SEQUENCE);
//				ratingFundDB.db.execSQL(CREATE_FUNDA);
//			} catch (Exception e) {
//				
//			}
			while(m.find()){
				FundA fundA = (FundA) new FundFactory().createFund("FundA");
				str = m.group(1)+","+m.group(2).substring(2,m.group(2).length()-3);
//				Log.d("TAG4", str);
				String[] temp = str.split(",");
				
//				BigDecimal one = new BigDecimal(1);
//				BigDecimal b_closing = new BigDecimal(temp[3]);
//				BigDecimal b_current = new BigDecimal(temp[4]);
//				BigDecimal rising = b_current.divide(b_closing).subtract(one);
//				
//				NumberFormat percent = NumberFormat.getPercentInstance();
//				percent.setMaximumFractionDigits(2);
				
			if (temp.length > 15) {
				fundA.setFund(temp);
			}
//				Log.d("TAG", "FundSetFinish");
//				Log.d("TAG", ""+(ratingFundDB==null));
//				ContentValues values = new ContentValues();
//				values.put("fund_name",fundA.getFund_name() );
//				ratingFundDB.db.update("FundA", values, "fund_code=?",new String[]{fundA.getFund_code()});
				ratingFundDB.savedFundA(fundA);
//				Log.d("TAG", "DBSaveFinish");
			
		}
		
		
			return true;
	}
	public synchronized static boolean handleFundBResponse(RatingFundDB ratingFundDB,String response){
		
			Pattern p = Pattern.compile("((?=\\w+)[\\d]{6}(?==\"))([\\S]+(?=\"))");
			Matcher m = p.matcher(response);

			String str="";
			while(m.find()){
				FundB fundB = (FundB) new FundFactory().createFund("FundB");
				str = m.group(1)+","+m.group(2).substring(2,m.group(2).length()-3);
//				Log.d("TAG", str);
				String[] temp = str.split(",");
				fundB.setFund(temp);
//				Log.d("TAG", "FundSetFinish");
//				Log.d("TAG", ""+(ratingFundDB==null));
				ratingFundDB.savedFundB(fundB);
//				Log.d("TAG", "DBSaveFinish");
			
		}
			return true;
	}
	public synchronized static boolean handleFundJjjz(RatingFundDB ratingFundDB,String response,String category){
	
			Pattern p = Pattern.compile("([\\d]{6}(?==\"))([\\S]+(?=\"))");
			Matcher m = p.matcher(response);

//			Log.d("TAG", "Praseing");
			String str="";
			while(m.find()){
				Fund fund = new Fund();
				str = m.group(1)+","+m.group(2).substring(2,m.group(2).length()-3);
				String[] temp = str.split(",");
				fund.setFund_code(temp[0]);
//				fund.setFund_name(temp[1]);
				fund.setJjjz(temp[2]);
				ratingFundDB.savedFundJjjz(fund,category);
			
		}
			return true;
	}
	public synchronized static boolean handleIndex(RatingFundDB ratingFundDB,String response,String table){
//		Log.d("hsi", response);
		Pattern p = Pattern.compile("([\\d]{6}(?==\"))(.*(?=\"))");
		Matcher m = p.matcher(response);
		String str = "";
		while (m.find()) {
			FundA fundA = (FundA) new FundFactory().createFund("FundA");
			str = m.group(1) + "," + m.group(2).substring(2, m.group(2).length() - 3);
			String[] temp = str.split(",");
//			Log.d("hsii", str);
			fundA.setIndex(temp[0]);
			fundA.setIndex_name(temp[1]);

			BigDecimal close = new BigDecimal(temp[3]);
			BigDecimal current = new BigDecimal(temp[4]);
			fundA.setIndex_rising(current.divide(close, 4, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE)
					.divide(new BigDecimal(0.01), 2, BigDecimal.ROUND_HALF_UP).toString());
//			Log.d("hsii", fundA.getIndex_rising());
			ratingFundDB.saveIndex(fundA, table);
			
		}
		Pattern p2 = Pattern.compile("(I(?==\"))(.*(?=\"))");
		Matcher m2 = p2.matcher(response);
		String str2 = "";
		while (m2.find()) {
			FundA fundA = (FundA) new FundFactory().createFund("FundA");
			str2 = m2.group(1) + "," + m2.group(2).substring(2, m2.group(2).length() - 3);
			String[] temp = str2.split(",");
//			Log.d("hsi", str2);
			fundA.setIndex(temp[1]);
			fundA.setIndex_name(temp[2]);
			fundA.setIndex_rising(temp[9]);
			ratingFundDB.saveIndex(fundA, table);
		}
		return true;
			
	}
}
