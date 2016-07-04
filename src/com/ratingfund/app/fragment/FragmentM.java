package com.ratingfund.app.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.adapter.ListAdapterB;
import com.ratingfund.app.adapter.ListAdapterM;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundM;
import com.ratingfund.app.util.BpriceDiscountComparator;
import com.ratingfund.app.util.CPriceComparator;
import com.ratingfund.app.util.CodeComparator;
import com.ratingfund.app.util.CorrectedIntrestComparator;
import com.ratingfund.app.util.DiscountComparator;
import com.ratingfund.app.util.IndexComparator;
import com.ratingfund.app.util.IndexRisingComparator;
import com.ratingfund.app.util.IntrestRuleComparator;
import com.ratingfund.app.util.PriceLeverageComparator;
import com.ratingfund.app.util.RisingComparator;
import com.ratingfund.app.util.SpriceDiscountComparator;
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

public class FragmentM extends Fragment implements OnClickListener{
	View view;
	public ListView listView;
	public ListAdapterM adapterM;
	MainActivity main;
	boolean sortFlag = false;
	int buttonId = 1;
	private List<RadioButton> fund_m_radioButton_list;
	public RadioGroup m_sortRadioGroup;	
	public RadioButton fund_m_code_button;	
	public RadioButton fund_m_rising_button ;
	public RadioButton fund_m_tradeMoney_button;
	public RadioButton fund_m_sprice_discount_button;
	public RadioButton fund_m_bprice_discount_button;
	public RadioButton fund_m_checkedButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.fragment_m, container, false);
		fund_m_radioButton_list = new ArrayList<RadioButton>();
		
		listView = (ListView) view.findViewById(R.id.scroll_list_m);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FundM fundM = main.datasM.get(position);
				Intent intent = new Intent("com.ratingfund.app.activity.DetailedActivity.ACTION_START");			
				intent.putExtra("fund_code", fundM.getFund_code());
				startActivity(intent);
				
			}
		});

		main = (MainActivity) getActivity();
		main.mListViewM = listView;
		
		adapterM = new ListAdapterM(main,main.datasM);
		main.adapterM = adapterM;
		listView.setAdapter(adapterM);

		m_sortRadioGroup = (RadioGroup) view.findViewById(R.id.sort_radio_group_m);
		fund_m_code_button = (RadioButton) view.findViewById(R.id.fund_m_code_button);		
		fund_m_rising_button = (RadioButton)  view.findViewById(R.id.fund_m_rising_button);
		fund_m_tradeMoney_button = (RadioButton)  view.findViewById(R.id.fund_m_trademoney_button);
		fund_m_sprice_discount_button = (RadioButton)  view.findViewById(R.id.fund_m_sprice_discount_button);
		fund_m_bprice_discount_button = (RadioButton)  view.findViewById(R.id.fund_m_bprice_discount_button);
		fund_m_bprice_discount_button.setChecked(false);
		
		fund_m_radioButton_list.add(fund_m_code_button);
		fund_m_radioButton_list.add(fund_m_rising_button);
		fund_m_radioButton_list.add(fund_m_tradeMoney_button);
		fund_m_radioButton_list.add(fund_m_sprice_discount_button);
		fund_m_radioButton_list.add(fund_m_bprice_discount_button);
		for (RadioButton radioButton : fund_m_radioButton_list) {
			radioButton.setOnClickListener(this);
		}
		m_sortRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				fund_m_checkedButton = (RadioButton) view.findViewById(checkedId);
				if (fund_m_checkedButton != null && fund_m_checkedButton.isChecked()) {
					for (RadioButton button : fund_m_radioButton_list) {
						if (button != null && button != fund_m_checkedButton) {
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
		case R.id.fund_m_code_button:
//			sortRadioGroup.clearCheck();
			sortFundACode();
			sortFlag = !sortFlag;
			break;		
		case R.id.fund_m_rising_button:
			sorFundARising();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_m_trademoney_button:
			sortFundATradeMoney();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_m_sprice_discount_button:
			sortSpriceDiscount();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_m_bprice_discount_button:
			sortBpriceDiscount();
			sortFlag = !sortFlag;			
			break;
		}
		main.m_buttonId = buttonId;
		adapterM.notifyDataSetChanged();
		
	}
	public void sortFundACode(){
		
		if(buttonId!=1){
			sortFlag = true;
		}
		Collections.sort(main.datasM, new CodeComparator());
		setSortArrow(fund_m_code_button);
		buttonId = 1;
	}
	public void sorFundARising(){
		if(buttonId!=2){
			sortFlag = true;
		}
		Collections.sort(main.datasM, new RisingComparator());
		setSortArrow(fund_m_rising_button);
		buttonId = 2;
	}
	public void sortFundATradeMoney(){
		if(buttonId!=3){
			sortFlag = true;
		}
		Collections.sort(main.datasM, new TradeMoneyComparator());
		setSortArrow(fund_m_tradeMoney_button);
		buttonId = 3;
	}
	public void sortSpriceDiscount(){
		if(buttonId!=4){
			sortFlag = true;
		}
		Collections.sort(main.datasM, new SpriceDiscountComparator());
		setSortArrow(fund_m_sprice_discount_button);
		buttonId = 4;
	}
	public void sortBpriceDiscount(){
		if(buttonId!=5){
			sortFlag = true;
		}
		Collections.sort(main.datasM, new BpriceDiscountComparator());
		setSortArrow(fund_m_bprice_discount_button);
		buttonId = 5;
	}
	
	public void setSortArrow(RadioButton radioButton){
		if(sortFlag){
			Collections.reverse(main.datasM);
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
