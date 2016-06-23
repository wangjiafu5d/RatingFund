package com.ratingfund.app.adapter;

import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.model.FundM;
import com.ratingfund.app.view.CHScrollView;
import com.ratingfund.app.view.ScrollViewListener;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class ListAdapterM extends BaseAdapter {

	private List<FundM> fundM;
	private LayoutInflater inflater;	

	

	public ListAdapterM(Context context, List<FundM> fundM) {
		
		this.inflater = LayoutInflater.from(context);		
		this.fundM = fundM;
	}

	@Override
	public int getCount() {
		return fundM.size();
	}

	@Override
	public FundM getItem(int position) {
		return fundM.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public synchronized View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_m, null);
			holder = new ViewHolder();			
			holder.item_data1 = (TextView) convertView
					.findViewById(R.id.item_data1);
			holder.item_data2 = (TextView) convertView
					.findViewById(R.id.item_data2);
			holder.item_data3 = (TextView) convertView
					.findViewById(R.id.item_data3);
			holder.item_data4 = (TextView) convertView
					.findViewById(R.id.item_data4);
			holder.item_data5 = (TextView) convertView
					.findViewById(R.id.item_data5);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final FundM fundM = getItem(position);
		holder.item_data1.setText(fundM.getFund_name()+"\r\n"+fundM.getFund_code());		
		holder.item_data2.setText(fundM.getRising()+"%");
		holder.item_data3.setText(fundM.getTradeMoney());
		holder.item_data4.setText(fundM.getSpriceDiscount()+"%");
		holder.item_data5.setText(fundM.getBpriceDiscount()+"%");
		
		if (!TextUtils.isEmpty(fundM.getRising()) && fundM.getRising().substring(0, 1).equals("-")) {
			int green = Color.parseColor("#308014");
			
			holder.item_data1.setTextColor(green);
			holder.item_data2.setTextColor(green);
			holder.item_data3.setTextColor(green);
			holder.item_data4.setTextColor(green);
			holder.item_data5.setTextColor(green);
			
		} else {
			
			holder.item_data1.setTextColor(Color.RED);
			holder.item_data2.setTextColor(Color.RED);
			holder.item_data3.setTextColor(Color.RED);
			holder.item_data4.setTextColor(Color.RED);
			holder.item_data5.setTextColor(Color.RED);
			
		}

		return convertView;
	}

	private class ViewHolder {
		
		private TextView item_data1;
		private TextView item_data2;
		private TextView item_data3;
		private TextView item_data4;
		private TextView item_data5;
		
	}


}
