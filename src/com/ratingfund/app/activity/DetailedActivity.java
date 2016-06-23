package com.ratingfund.app.activity;

import com.ratingfund.app.R;
import com.ratingfund.app.db.RatingFundDB;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;

import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailedActivity extends Activity implements OnClickListener{
	String a_code ;
	String b_code ;
	String m_code ;
	Button buttonA;
	Button buttonB; 
	Button buttonM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.detail));
		actionBar.setDisplayShowHomeEnabled(false);
		setContentView(R.layout.detailed_layout);
		
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String intent_fund_code = intent.getStringExtra("fund_code");
		try {
			TextView fund_a_code = (TextView) findViewById(R.id.fund_a_code);		
			TextView fund_a_price = (TextView) findViewById(R.id.fund_a_price);
			TextView fund_a_rising = (TextView) findViewById(R.id.fund_a_rising);
			TextView fund_a_trade_money = (TextView) findViewById(R.id.fund_a_trade_money);
			TextView fund_a_discount = (TextView) findViewById(R.id.fund_a_discount);
			
			TextView fund_a_sprice = (TextView) findViewById(R.id.fund_a_sprice);		
			TextView fund_a_bprice = (TextView) findViewById(R.id.fund_a_bprice);
			TextView fund_a_corrected_intrest = (TextView) findViewById(R.id.fund_a_corrected_intrest);
			TextView fund_a_intrest_rule = (TextView) findViewById(R.id.fund_a_intrest_rule);
			TextView fund_a_intrest_term = (TextView) findViewById(R.id.fund_a_intrest_term);
			
			TextView jjjz = (TextView) findViewById(R.id.jjjz);
			TextView index = (TextView) findViewById(R.id.index);
			TextView index_name = (TextView) findViewById(R.id.index_name);
			TextView index_rising = (TextView) findViewById(R.id.index_rising);
			
			TextView fund_b_code = (TextView) findViewById(R.id.fund_b_code);		
			TextView fund_b_price = (TextView) findViewById(R.id.fund_b_price);
			TextView fund_b_rising = (TextView) findViewById(R.id.fund_b_rising);
			TextView fund_b_trade_money = (TextView) findViewById(R.id.fund_b_trade_money);
			TextView fund_b_discount = (TextView) findViewById(R.id.fund_b_discount);
			
			TextView fund_b_jjjz = (TextView) findViewById(R.id.fund_b_jjjz);
			TextView fund_b_sprice = (TextView) findViewById(R.id.fund_b_sprice);
			TextView fund_b_bprice = (TextView) findViewById(R.id.fund_b_bprice);
			TextView fund_b_price_leverage = (TextView) findViewById(R.id.fund_b_price_leverage);
			
			TextView fund_m_code = (TextView) findViewById(R.id.fund_m_code);		
			TextView fund_m_price = (TextView) findViewById(R.id.fund_m_price);
			TextView fund_m_rising = (TextView) findViewById(R.id.fund_m_rising);
			TextView fund_m_trade_money = (TextView) findViewById(R.id.fund_m_trade_money);
			TextView fund_m_jjjz = (TextView) findViewById(R.id.fund_m_jjjz);
			
			TextView fund_m_sprice_discount = (TextView) findViewById(R.id.fund_m_sprice_discount);
			TextView fund_m_bprice_discount = (TextView) findViewById(R.id.fund_m_bprice_discount);
			TextView fund_m_purchase = (TextView) findViewById(R.id.fund_m_purchase);
			TextView fund_m_redeem = (TextView) findViewById(R.id.fund_m_redeem);
			
			
			Cursor cursor = RatingFundDB.getInstacnce(getApplicationContext()).db.query("FundA", null,
					"fund_code=? or fund_b_code=? or fund_m_code=?", 
					new String[] { intent_fund_code, intent_fund_code, intent_fund_code },null, null, null, null);
			
			
			if(cursor.moveToFirst()){
				a_code = cursor.getString(cursor.getColumnIndex("fund_code"));			
				fund_a_code.setText(a_code+"\r\n"+cursor.getString(cursor.getColumnIndex("fund_name")));
				
				SpannableString discount = new SpannableString(cursor.getString(cursor.getColumnIndex("discount"))+"%");			
				discount.setSpan(new UnderlineSpan(), 0, discount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);			
				fund_a_discount.setText(discount);
				
				fund_a_price.setText(""+cursor.getString(cursor.getColumnIndex("current")));
				fund_a_rising.setText(cursor.getString(cursor.getColumnIndex("rising"))+"%");
				if(cursor.getString(cursor.getColumnIndex("rising")).contains("-")){
					fund_a_rising.setTextColor(Color.GREEN);
				}else {
					fund_a_rising.setTextColor(Color.RED);
				}
				fund_a_trade_money.setText(cursor.getString(cursor.getColumnIndex("tradeMoney")));
				
				fund_a_sprice.setText(cursor.getString(cursor.getColumnIndex("sprice")));
				fund_a_bprice.setText(cursor.getString(cursor.getColumnIndex("bprice")));
				fund_a_corrected_intrest.setText(cursor.getString(cursor.getColumnIndex("corrected_intrest")));
				fund_a_intrest_rule.setText(cursor.getString(cursor.getColumnIndex("intrest_rule")));
				fund_a_intrest_term.setText(cursor.getString(cursor.getColumnIndex("intrest_term")));
				
				jjjz.setText(cursor.getString(cursor.getColumnIndex("jjjz")));
				index.setText(cursor.getString(cursor.getColumnIndex("hy_index")));
				index_name.setText(cursor.getString(cursor.getColumnIndex("index_name")));
				index_rising.setText(cursor.getString(cursor.getColumnIndex("index_rising"))+"%");
				if(cursor.getString(cursor.getColumnIndex("index_rising")).contains("-")){
					index_rising.setTextColor(Color.GREEN);
				}else {
					index_rising.setTextColor(Color.RED);
				}
				
				
				
				b_code = cursor.getString(cursor.getColumnIndex("fund_b_code"));
				fund_b_code.setText(b_code
						+"\r\n"+cursor.getString(cursor.getColumnIndex("fund_b_name")));
				fund_b_price.setText(cursor.getString(cursor.getColumnIndex("b_current")));
				fund_b_rising.setText(cursor.getString(cursor.getColumnIndex("b_rising"))+"%");
				if(cursor.getString(cursor.getColumnIndex("b_rising")).contains("-")){
					fund_b_rising.setTextColor(Color.GREEN);
				}else {
					fund_b_rising.setTextColor(Color.RED);
				}
				fund_b_trade_money.setText(cursor.getString(cursor.getColumnIndex("b_tradeMoney")));
				fund_b_discount.setText(cursor.getString(cursor.getColumnIndex("b_discount"))+"%");
				
				fund_b_jjjz.setText(cursor.getString(cursor.getColumnIndex("b_jjjz")));
				fund_b_sprice.setText(cursor.getString(cursor.getColumnIndex("b_sprice")));
				fund_b_bprice.setText(cursor.getString(cursor.getColumnIndex("b_bprice")));
				fund_b_price_leverage.setText(cursor.getString(cursor.getColumnIndex("b_price_leverage")));
				
				
				
				
				m_code = cursor.getString(cursor.getColumnIndex("fund_m_code"));
				fund_m_code.setText(m_code
						+"\r\n"+cursor.getString(cursor.getColumnIndex("fund_m_name")));
				fund_m_price.setText(cursor.getString(cursor.getColumnIndex("m_current")));
				fund_m_rising.setText(cursor.getString(cursor.getColumnIndex("m_rising"))+"%");
				if(cursor.getString(cursor.getColumnIndex("m_rising")).contains("-")){
					fund_m_rising.setTextColor(Color.GREEN);
				}else {
					fund_m_rising.setTextColor(Color.RED);
				}
				fund_m_trade_money.setText(cursor.getString(cursor.getColumnIndex("m_tradeMoney")));
				fund_m_jjjz.setText(cursor.getString(cursor.getColumnIndex("m_jjjz")));
				
				
				fund_m_sprice_discount.setText(cursor.getString(cursor.getColumnIndex("m_sprice_discount"))+"%");
				fund_m_bprice_discount.setText(cursor.getString(cursor.getColumnIndex("m_bprice_discount"))+"%");
				fund_m_purchase.setText(cursor.getString(cursor.getColumnIndex("purchase_cost")));
				fund_m_redeem.setText(cursor.getString(cursor.getColumnIndex("redeem_cost")));
				
				buttonHandle();
				
			}
			
		} catch (Exception e) {			
			finish();
		}
		
		
	}
	
	private void buttonHandle(){
		buttonA = (Button) findViewById(R.id.add_fund_a);
		buttonB = (Button) findViewById(R.id.add_fund_b);
		buttonM = (Button) findViewById(R.id.add_fund_m);
		buttonA.setOnClickListener(this);
		buttonB.setOnClickListener(this);
		buttonM.setOnClickListener(this);
		
		Cursor cursor = RatingFundDB.getInstacnce(getApplicationContext()).db.
				query("Zixuan", null, "fund_code=? or fund_code=? or fund_code=?", new String[]{a_code,b_code,m_code}, null, null, null, null);
		if(cursor.moveToFirst()){
			do {
				String category = cursor.getString(cursor.getColumnIndex("fund_category"));
				if(category.equals("a")){
					buttonA.setText(getString(R.string.deletefrom_self_choose));
					buttonA.setBackgroundColor(Color.GRAY);
				}else if(category.equals("b")){
					buttonB.setText(getString(R.string.deletefrom_self_choose));
					buttonB.setBackgroundColor(Color.GRAY);
				}
				else if(category.equals("m")){
					buttonM.setText(getString(R.string.deletefrom_self_choose));
					buttonM.setBackgroundColor(Color.GRAY);
				}
				
				
			} while (cursor.moveToNext());
			
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_fund_a:
			ClickHandle(buttonA, a_code, "a");
			break;

		case R.id.add_fund_b:
			ClickHandle(buttonB, b_code, "b");
			break;
		case R.id.add_fund_m:
			ClickHandle(buttonM, m_code, "m");
			break;

		default:
			break;
		}
		Intent i = new Intent("BroadcastAction");
		sendBroadcast(i);
	}
	
	private void ClickHandle(Button button,String code,String category){
		if(button.getText().equals(getString(R.string.addto_self_choose))){
			ContentValues values = new ContentValues();
			values.put("fund_code", code);
			values.put("fund_category", category);
			RatingFundDB.getInstacnce(getApplicationContext()).db.insert("Zixuan", null, values);
			button.setText(getString(R.string.deletefrom_self_choose));
			button.setBackgroundColor(Color.GRAY);
		}else {
			RatingFundDB.getInstacnce(getApplicationContext()).db.delete("Zixuan", "fund_code=?", new String[]{code});
			button.setText(getString(R.string.addto_self_choose));
			button.setBackgroundResource(R.drawable.button_bg);
		}
	}
	
	
}
