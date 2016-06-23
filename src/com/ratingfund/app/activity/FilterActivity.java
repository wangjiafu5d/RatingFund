package com.ratingfund.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.db.RatingFundDB;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.graphics.Color;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class FilterActivity extends Activity implements OnClickListener{
	String time;
	String term;
	EditText edit;
	Spinner timeSpinner,termSpinner;
	Button button;
	CheckBox intrest3_0;
	CheckBox intrest3_2;
	CheckBox intrest3_5;
	CheckBox intrest4_0;
	CheckBox intrest4_5;
	CheckBox intrest5_0;
	CheckBox intrestOther;
	List<String> filterList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.filter));
		actionBar.setDisplayShowHomeEnabled(false);
		setContentView(R.layout.filter_layout);
		super.onCreate(savedInstanceState);
		
		edit = (EditText) findViewById(R.id.filte_trade_money);
		termSpinner = (Spinner) findViewById(R.id.term_set);
		timeSpinner = (Spinner) findViewById(R.id.refresh_time);
		SharedPreferences share = getSharedPreferences("data", MODE_PRIVATE);		
		switch (share.getInt("refreshTime", 5000)) {
		case 1000:
			timeSpinner.setSelection(0, true);
			break;
		case 2000:
			timeSpinner.setSelection(1, true);
			break;
		case 5000:
			timeSpinner.setSelection(2, true);
			break;
		case 10000:
			timeSpinner.setSelection(3, true);
			break;
		case 30000:
			timeSpinner.setSelection(4, true);
			break;
		case 60000:
			timeSpinner.setSelection(5, true);
			break;

		default:
			break;
		}
		
		button = (Button) findViewById(R.id.save_filter);
		button.setOnClickListener(this);
		
		intrest3_0 = (CheckBox) findViewById(R.id.intrest3_0);
		intrest3_2 = (CheckBox) findViewById(R.id.intrest3_2);
		intrest3_5 = (CheckBox) findViewById(R.id.intrest3_5);
		intrest4_0 = (CheckBox) findViewById(R.id.intrest4_0);
		intrest4_5 = (CheckBox) findViewById(R.id.intrest4_5);
		intrest5_0 = (CheckBox) findViewById(R.id.intrest5_0);
		intrestOther = (CheckBox) findViewById(R.id.others_intrest);
		intrest3_0.setOnClickListener(this);
		intrest3_2.setOnClickListener(this);
		intrest3_5.setOnClickListener(this);
		intrest4_0.setOnClickListener(this);
		intrest4_5.setOnClickListener(this);
		intrest5_0.setOnClickListener(this);
		intrestOther.setOnClickListener(this);
		
		Cursor filterCousor = RatingFundDB.getInstacnce(this).db.query("Filter", null, null, null, null, null, null);
		if(filterCousor.moveToFirst()){
			do{
				filterList.add(filterCousor.getString(filterCousor.getColumnIndex("rule_value")));
			}while(filterCousor.moveToNext());
		}
		edit.setText(filterList.get(7));
		if(filterList.get(8).equals("ÓÀÐø")){
			termSpinner.setSelection(1, true);
		}else if(filterList.get(8).equals("ÆäËû")){
			termSpinner.setSelection(2, true);
		}
		intrest3_0.setChecked(filterList.get(0).equals("true"));
		intrest3_2.setChecked(filterList.get(1).equals("true"));
		intrest3_5.setChecked(filterList.get(2).equals("true"));
		intrest4_0.setChecked(filterList.get(3).equals("true"));
		intrest4_5.setChecked(filterList.get(4).equals("true"));
		intrest5_0.setChecked(filterList.get(5).equals("true"));
		intrestOther.setChecked(filterList.get(6).equals("true"));
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_filter:
			time = (String) timeSpinner.getSelectedItem();
			int cycle = (Integer.valueOf(time.substring(0, time.length()-1))*1000);
			SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
			editor.putInt("refreshTime",cycle);
			editor.commit();
			
			filterList.set(7, edit.getText().toString());
			term = (String) termSpinner.getSelectedItem();
			filterList.set(8, term);
			int i = 1;
			for (String temp : filterList) {
				ContentValues values = new ContentValues();
				values.put("rule_value", temp);
				RatingFundDB.getInstacnce(getApplicationContext()).db.update("Filter", values, "id=?",
						new String[] { Integer.toString(i) });
				i++;
			}
			button.setBackground(null);
			button.setBackgroundColor(Color.GRAY);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							button.setBackgroundResource(R.drawable.button_bg);
							
						}
					});
				}
			}).start();
			break;
		case R.id.intrest3_0:
			if(intrest3_0.isChecked()){
				filterList.set(0, "true");
			}else {
				filterList.set(0, "false");
			}
			break;
		case R.id.intrest3_2:
			if(intrest3_2.isChecked()){
				filterList.set(1, "true");
			}else {
				filterList.set(1, "false");
			}

			break;
		case R.id.intrest3_5:
			if(intrest3_5.isChecked()){
				filterList.set(2, "true");
			}else {
				filterList.set(2, "false");
			}

			break;
		case R.id.intrest4_0:
			if(intrest4_0.isChecked()){
				filterList.set(3, "true");
			}else {
				filterList.set(3, "false");
			}

			break;
		case R.id.intrest4_5:
			if(intrest4_5.isChecked()){
				filterList.set(4, "true");
			}else {
				filterList.set(4, "false");
			}

			break;
		case R.id.intrest5_0:
			if(intrest5_0.isChecked()){
				filterList.set(5, "true");
			}else {
				filterList.set(5, "false");
			}

			break;
		case R.id.others_intrest:
			if(intrestOther.isChecked()){
				filterList.set(6, "true");
			}else {
				filterList.set(6, "false");
			}

			break;
		default:
			break;
		}
		
		
	}
}
