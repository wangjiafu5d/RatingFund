package com.ratingfund.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.adapter.SearchListAdapter;
import com.ratingfund.app.db.RatingFundDB;
import com.ratingfund.app.model.Fund;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class SearchActivity extends Activity {
	SearchView searchView ;
	List<Fund> list;
	SearchListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.search));
		actionBar.setDisplayShowHomeEnabled(false);
		setContentView(R.layout.search_layout);
		super.onCreate(savedInstanceState);
		
		
		list = new ArrayList<Fund>();
		ListView listView = (ListView) findViewById(R.id.search_list);
		adapter = new SearchListAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Fund fund = list.get(position);
				Intent intent = new Intent("com.ratingfund.app.activity.DetailedActivity.ACTION_START");			
				intent.putExtra("fund_code", fund.getFund_code());
				startActivity(intent);
				
			}
		});
		
		searchView = (SearchView) findViewById(R.id.search_view);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
			
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				list.clear();
				Cursor cursor = RatingFundDB.getInstacnce(getApplicationContext()).db.query("FundA", null, 
						"fund_code like ? or fund_name like ?",new String[]{"%"+newText+"%","%"+newText+"%"}, null, null, null);
				if (cursor.moveToFirst()) {
					do {
						Fund fund = new Fund();
						fund.setFund_code(cursor.getString(cursor.getColumnIndex("fund_code")));
						fund.setFund_name(cursor.getString(cursor.getColumnIndex("fund_name")));
						fund.setCurrent(cursor.getString(cursor.getColumnIndex("current")));
						fund.setRising(cursor.getString(cursor.getColumnIndex("rising")));						
						list.add(fund);
					} while (cursor.moveToNext());
				}
				Cursor b_cursor = RatingFundDB.getInstacnce(getApplicationContext()).db.query("FundA", null, 
						"fund_b_code like ? or fund_b_name like ?",new String[]{"%"+newText+"%","%"+newText+"%"}, null, null, null);
				if (b_cursor.moveToFirst()) {
					do {
						Fund fund = new Fund();
						fund.setFund_code(b_cursor.getString(b_cursor.getColumnIndex("fund_b_code")));
						fund.setFund_name(b_cursor.getString(b_cursor.getColumnIndex("fund_b_name")));
						fund.setCurrent(b_cursor.getString(b_cursor.getColumnIndex("b_current")));
						fund.setRising(b_cursor.getString(b_cursor.getColumnIndex("b_rising")));						
						list.add(fund);
					} while (b_cursor.moveToNext());
				}
				
				Cursor m_cursor = RatingFundDB.getInstacnce(getApplicationContext()).db.query("FundA", null, 
						"fund_m_code like ? or fund_m_name like ?",new String[]{"%"+newText+"%","%"+newText+"%"}, null, null, null);
				if (m_cursor.moveToFirst()) {
					do {
						Fund fund = new Fund();
						fund.setFund_code(m_cursor.getString(m_cursor.getColumnIndex("fund_m_code")));
						fund.setFund_name(m_cursor.getString(m_cursor.getColumnIndex("fund_m_name")));
						fund.setCurrent(m_cursor.getString(m_cursor.getColumnIndex("m_current")));
						fund.setRising(m_cursor.getString(m_cursor.getColumnIndex("m_rising")));						
						list.add(fund);
					} while (m_cursor.moveToNext());
				}
				adapter.notifyDataSetChanged();
				return false;
			}
		});
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return super.onOptionsItemSelected(item);
	}
	
}
