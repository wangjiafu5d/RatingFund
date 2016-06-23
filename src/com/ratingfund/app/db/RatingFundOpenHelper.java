package com.ratingfund.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RatingFundOpenHelper extends SQLiteOpenHelper{
	public static final String CREATE_FUNDA = "create table FundA ("
			+ "id integer primary key autoincrement,"
			+ "fund_code text,"
			+ "fund_name text,"
			+ "closing text,"
			+ "rising text,"
			+ "sprice text,"
			+ "bprice text,"
			+ "current text,"
			+ "tradeMoney text,"
			+ "jjjz text,"
			+ "discount text,"
			+ "intrest text,"
			+ "intrest_rule text,"
			+ "intrest_term text,"
			+ "corrected_intrest text,"
			+ "ratio text,"
			+ "hy_index text,"
			+ "index_name text,"
			+ "index_rising text,"
			+ "fund_b_code text,"
			+ "fund_b_name text,"
			+ "b_rising text,"
			+ "b_sprice text,"
			+ "b_bprice text,"
			+ "b_current text,"
			+ "b_tradeMoney text,"
			+ "b_price_leverage text,"
			+ "b_jjjz text,"
			+ "b_discount text,"
			+ "fund_m_code text,"
			+ "fund_m_name text,"
			+ "m_rising text,"
			+ "m_sprice text,"
			+ "m_bprice text,"
			+ "m_current text,"
			+ "m_tradeMoney text,"
			+ "m_jjjz text,"
			+ "m_sprice_discount text,"
			+ "m_bprice_discount text,"
			+ "purchase_cost text,"
			+ "redeem_cost text)";
//	public static final String CREATE_FUNDB = "create table FundB ("
//			+ "id integer primary key autoincrement,"
//			+ "fund_code text,"
//			+ "fund_name text,"
//			+ "closing text,"
//			+ "rising text,"
//			+ "current text"
//			+ "tradeMoney text,"
//			+ "jjjz text,"
//			+ "discount text)";
//	public static final String CREATE_FUNDM = "create table FundM ("
//			+ "id integer primary key autoincrement,"
//			+ "fund_code text,"
//			+ "fund_name text)";
//	public static final String CREATE_FUNDNI = "create table FundNI ("
//			+ "id integer primary key autoincrement,"
//			+ "fund_code text,"
//			+ "fund_name text)";
//	public static final String CREATE_FUNDHB = "create table FundHB ("
//			+ "id integer primary key autoincrement,"
//			+ "fund_code text,"
//			+ "fund_name text)";
	public static final String CREATE_FILTER = "create table Filter ("
			+ "id integer primary key autoincrement,"
			+ "rule text,"
			+ "rule_value text)";
	public static final String CREATE_SELFCHOOSE = "create table Zixuan ("
			+ "id integer primary key autoincrement,"
			+ "fund_code text,"
			+ "fund_category text)";
	public RatingFundOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_FUNDA);
//		db.execSQL(CREATE_FUNDB);
//		db.execSQL(CREATE_FUNDM);
//		db.execSQL(CREATE_FUNDNI);
//		db.execSQL(CREATE_FUNDHB);
		db.execSQL(CREATE_FILTER);
		db.execSQL(CREATE_SELFCHOOSE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
	}

}
