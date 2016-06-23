package com.ratingfund.app.adapter;

import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.model.FundA;
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



public class ListAdapterZ extends BaseAdapter {

	private List<FundA> fundA;
	private LayoutInflater inflater;	

	

	public ListAdapterZ(Context context, List<FundA> fundA) {
		
		this.inflater = LayoutInflater.from(context);		
		this.fundA = fundA;
	}

	@Override
	public int getCount() {
		return fundA.size();
	}

	@Override
	public FundA getItem(int position) {
		return fundA.get(position);
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
		
		final FundA fundA = getItem(position);
		holder.item_data1.setText(fundA.getFund_name()+"\r\n"+fundA.getFund_code());		
		holder.item_data2.setText(fundA.getCurrent());
		holder.item_data3.setText(fundA.getRising()+"%");
		holder.item_data4.setText(fundA.getTradeMoney());
		holder.item_data5.setText(fundA.getDiscount()+"%");
		
		if (!TextUtils.isEmpty(fundA.getRising()) && fundA.getRising().substring(0, 1).equals("-")) {
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
