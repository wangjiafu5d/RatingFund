package com.ratingfund.app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.adapter.ListAdapterM;
import com.ratingfund.app.adapter.ListAdapterZ;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.util.BpriceDiscountComparator;
import com.ratingfund.app.util.CPriceComparator;
import com.ratingfund.app.util.CodeComparator;
import com.ratingfund.app.util.DiscountComparator;
import com.ratingfund.app.util.RisingComparator;
import com.ratingfund.app.util.SpriceDiscountComparator;
import com.ratingfund.app.util.TradeMoneyComparator;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentZiXuan extends Fragment implements OnClickListener {
	View view;
	public ListView listView;
	public ListAdapterZ adapterZ;
	MainActivity main;
	boolean sortFlag = false;
	int buttonId = 1;
	private List<RadioButton> radioButton_list;
	public RadioGroup sortRadioGroup;	
	public RadioButton code_button;	
	public RadioButton price_button ;
	public RadioButton rising_button;
	public RadioButton tradeMoney_button;
	public RadioButton discount_button;
	public RadioButton fund_z_checkedButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.fragment_zixuan, container, false);
		radioButton_list = new ArrayList<RadioButton>();
		
		listView = (ListView) view.findViewById(R.id.self_choose_list);	
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FundA fund = main.datasZ.get(position);
				Intent intent = new Intent("com.ratingfund.app.activity.DetailedActivity.ACTION_START");			
				intent.putExtra("fund_code", fund.getFund_code());
				startActivity(intent);
				
			}
		});

		main = (MainActivity) getActivity();
		main.mListViewZ = listView;
		adapterZ = new ListAdapterZ(main,main.datasZ);
		main.adapterZ = adapterZ;
		listView.setAdapter(adapterZ);

		sortRadioGroup = (RadioGroup) view.findViewById(R.id.sort_radio_group_z);
		code_button = (RadioButton) view.findViewById(R.id.fund_z_code_button);		
		price_button = (RadioButton)  view.findViewById(R.id.fund_z_current_button);
		rising_button = (RadioButton)  view.findViewById(R.id.fund_z_rising_button);
		tradeMoney_button = (RadioButton)  view.findViewById(R.id.fund_z_trademoney_button);
		discount_button = (RadioButton)  view.findViewById(R.id.fund_z_discount_button);
		discount_button.setChecked(false);
		
		radioButton_list.add(code_button);
		radioButton_list.add(price_button);
		radioButton_list.add(rising_button);
		radioButton_list.add(tradeMoney_button);
		radioButton_list.add(discount_button);
		for (RadioButton radioButton : radioButton_list) {
			radioButton.setOnClickListener(this);
		}
		sortRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				fund_z_checkedButton = (RadioButton) view.findViewById(checkedId);
				if (fund_z_checkedButton != null && fund_z_checkedButton.isChecked()) {
					for (RadioButton button : radioButton_list) {
						if (button != null && button != fund_z_checkedButton) {
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
		case R.id.fund_z_code_button:
			sortFundACode();
			sortFlag = !sortFlag;
			break;		
		case R.id.fund_z_current_button:
			sortFundACPrice();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_z_rising_button:
			sorFundARising();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_z_trademoney_button:
			sortFundATradeMoney();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_z_discount_button:
			sortFundADiscount();
			sortFlag = !sortFlag;			
			break;
		}
		main.z_buttonId = buttonId;
		adapterZ.notifyDataSetChanged();
		
	}
	public void sortFundACode(){		
		if(buttonId!=1){
			sortFlag = true;
		}
		Collections.sort(main.datasZ, new CodeComparator());
		setSortArrow(code_button);
		buttonId = 1;
	}
	public void sortFundACPrice(){
		if(buttonId!=2){
			sortFlag = true;
		}
		Collections.sort(main.datasZ, new CPriceComparator());
		setSortArrow(price_button);
		buttonId = 2;
	}
	public void sorFundARising(){
		if(buttonId!=3){
			sortFlag = true;
		}
		Collections.sort(main.datasZ, new RisingComparator());
		setSortArrow(rising_button);
		buttonId = 3;
	}
	public void sortFundATradeMoney(){
		if(buttonId!=4){
			sortFlag = true;
		}
		Collections.sort(main.datasZ, new TradeMoneyComparator());
		setSortArrow(tradeMoney_button);
		buttonId = 4;
	}
	public void sortFundADiscount(){
		if(buttonId!=5){
			sortFlag = true;
		}
		Collections.sort(main.datasZ, new DiscountComparator());
		setSortArrow(discount_button);
		buttonId = 5;
	}
	
	public void setSortArrow(RadioButton radioButton){
		if(sortFlag){
			Collections.reverse(main.datasZ);
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
