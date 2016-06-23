package com.ratingfund.app.fragment;

import java.util.Collections;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.adapter.ListAdapterB;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.util.CPriceComparator;
import com.ratingfund.app.util.CodeComparator;
import com.ratingfund.app.util.CorrectedIntrestComparator;
import com.ratingfund.app.util.DiscountComparator;
import com.ratingfund.app.util.IndexComparator;
import com.ratingfund.app.util.IndexRisingComparator;
import com.ratingfund.app.util.IntrestRuleComparator;
import com.ratingfund.app.util.PriceLeverageComparator;
import com.ratingfund.app.util.RisingComparator;
import com.ratingfund.app.util.TradeMoneyComparator;
import com.ratingfund.app.view.CHScrollView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentB extends Fragment implements OnClickListener{
	View view;
	public ListView mListViewB;
	public ListAdapterB adapterB;
	MainActivity main;
	boolean sortFlag = false;
	int buttonId = 1;
	public RadioGroup b_sortRadioGroup;
	public RadioButton fund_b_checkedButton;
	public RadioButton fund_b_code_button;
	public RadioButton fund_b_cprice_button;
	public RadioButton fund_b_rising_button ;
	public RadioButton fund_b_tradeMoney_button;
	public RadioButton fund_b_discount_button;
	public RadioButton fund_b_price_leverage_button;
	public RadioButton fund_b_intrest_rule_button;
	public RadioButton fund_b_index_button;
	public RadioButton fund_b_index_rising_button;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.fragment_b, container, false);
		mListViewB = (ListView) view.findViewById(R.id.scroll_list_b);
		mListViewB.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FundA fundA = main.datasB.get(position);
				Intent intent = new Intent("com.ratingfund.app.activity.DetailedActivity.ACTION_START");			
				intent.putExtra("fund_code", fundA.getFund_code());
				startActivity(intent);
				
			}
		});
//		Log.d("TAG1", ""+(getActivity()==null));
//		Log.d("TAG1", ""+(mListViewB==null));
		main = (MainActivity) getActivity();
		main.leftOkB = (ImageView) view.findViewById(R.id.iv_left_ok_b);
		main.leftNoB = (ImageView) view.findViewById(R.id.iv_left_no_b);
		main.rightOkB = (ImageView) view.findViewById(R.id.iv_right_ok_b);
		main.rightNoB = (ImageView) view.findViewById(R.id.iv_right_no_b);
		
		CHScrollView headerScrollB = (CHScrollView) view.findViewById(R.id.item_scroll_title_b);
		headerScrollB.setScrollViewListener(main);
		main.mHScrollViewsB.add(headerScrollB);
		adapterB = new ListAdapterB(main, main.mHScrollViewsB, mListViewB,main.datasB);
		main.adapterB = adapterB;
		mListViewB.setAdapter(adapterB);
//		main.setDatasB();
		b_sortRadioGroup = (RadioGroup) view.findViewById(R.id.sort_radio_group_b);
		fund_b_code_button = (RadioButton) view.findViewById(R.id.fund_b_code_button);
		fund_b_cprice_button = (RadioButton) view.findViewById(R.id.fund_b_cprice_button);
		fund_b_rising_button = (RadioButton)  view.findViewById(R.id.fund_b_rising_button);
		fund_b_tradeMoney_button = (RadioButton)  view.findViewById(R.id.fund_b_trademoney_button);
		fund_b_discount_button = (RadioButton)  view.findViewById(R.id.fund_b_discount_button);
		fund_b_price_leverage_button = (RadioButton)  view.findViewById(R.id.fund_b_price_leverage_button);
		fund_b_intrest_rule_button = (RadioButton)  view.findViewById(R.id.fund_b_interest_rule_button);
		fund_b_index_button = (RadioButton)  view.findViewById(R.id.fund_b_index_button);
		fund_b_index_rising_button = (RadioButton)  view.findViewById(R.id.fund_b_index_rising_button);
		
		main.fund_b_radioButton_list.add(fund_b_code_button);
		main.fund_b_radioButton_list.add(fund_b_cprice_button);
		main.fund_b_radioButton_list.add(fund_b_rising_button);
		main.fund_b_radioButton_list.add(fund_b_tradeMoney_button);
		main.fund_b_radioButton_list.add(fund_b_discount_button);
		main.fund_b_radioButton_list.add(fund_b_price_leverage_button);
		main.fund_b_radioButton_list.add(fund_b_intrest_rule_button);
		main.fund_b_radioButton_list.add(fund_b_index_button);
		main.fund_b_radioButton_list.add(fund_b_index_rising_button);
		for (RadioButton button : main.fund_b_radioButton_list) {
			button.setOnClickListener(this);
		}
		fund_b_code_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					for (RadioButton button : main.fund_b_radioButton_list) {
						if (button != null && button != fund_b_code_button) {
							button.setHovered(false);
						}
					}
					b_sortRadioGroup.clearCheck();
				}
			}
		});
		b_sortRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				fund_b_checkedButton = (RadioButton) view.findViewById(checkedId);
				if (fund_b_checkedButton != null && fund_b_checkedButton.isChecked()) {
					for (RadioButton button : main.fund_b_radioButton_list) {
						if (button != null && button != fund_b_checkedButton) {
							button.setHovered(false);
							button.setChecked(false);
						}
					}
				}

			}
		});
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fund_b_code_button:
//			sortRadioGroup.clearCheck();
			sortFundACode();
			sortFlag = !sortFlag;
			break;
		case R.id.fund_b_cprice_button:
			sortFundACPrice();
			sortFlag = !sortFlag;
			break;
		case R.id.fund_b_rising_button:
			sorFundARising();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_b_trademoney_button:
			sortFundATradeMoney();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_b_discount_button:
			sortFundADiscount();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_b_price_leverage_button:
			sortPriceLeverage();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_b_interest_rule_button:
			sortFundAIntrestRule();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_b_index_button:
			sortFundAIndex();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_b_index_rising_button:
			sortFundAIndexRising();
			sortFlag = !sortFlag;			
			break;
			
		default:
			break;
		}
		main.b_buttonId = buttonId;
		adapterB.notifyDataSetChanged();
		
	}
	public void sortFundACode(){
		
		if(buttonId!=1){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new CodeComparator());
		setSortArrow(fund_b_code_button);
		buttonId = 1;
	}
	public void sortFundACPrice(){
		if(buttonId!=2){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new CPriceComparator());
		setSortArrow(fund_b_cprice_button);
		buttonId = 2;
	}
	public void sorFundARising(){
		if(buttonId!=3){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new RisingComparator());
		setSortArrow(fund_b_rising_button);
		buttonId = 3;
	}
	public void sortFundATradeMoney(){
		if(buttonId!=4){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new TradeMoneyComparator());
		setSortArrow(fund_b_tradeMoney_button);
		buttonId = 4;
	}
	public void sortFundADiscount(){
		if(buttonId!=5){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new DiscountComparator());
		setSortArrow(fund_b_discount_button);
		buttonId = 5;
	}
	public void sortPriceLeverage(){
		if(buttonId!=6){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new PriceLeverageComparator());
		setSortArrow(fund_b_price_leverage_button);
		buttonId = 6;
	}
	public void sortFundAIntrestRule(){
		if(buttonId!=7){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new IntrestRuleComparator());
		setSortArrow(fund_b_intrest_rule_button);
		buttonId = 7;
	}
	public void sortFundAIndex(){
		if(buttonId!=8){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new IndexComparator());
		setSortArrow(fund_b_index_button);
		buttonId = 8;
	}
	public void sortFundAIndexRising(){
		if(buttonId!=9){
			sortFlag = true;
		}
		Collections.sort(main.datasB, new IndexRisingComparator());
		setSortArrow(fund_b_index_rising_button);
		buttonId = 9;
	}
	public void setSortArrow(RadioButton radioButton){
		if(sortFlag){
			Collections.reverse(main.datasB);
			radioButton.setHovered(false);
		}else {
			radioButton.setHovered(true);
		}
	}
	public void setSortArrow2(RadioButton radioButton){
		if(!sortFlag){
			radioButton.setHovered(false);
		}else {
			radioButton.setHovered(true);
		}
	}
	
}
