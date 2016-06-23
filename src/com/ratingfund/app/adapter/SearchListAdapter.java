package com.ratingfund.app.adapter;

import java.util.List;
import com.ratingfund.app.R;
import com.ratingfund.app.model.Fund;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter{
	private List<Fund> fund;
	private LayoutInflater inflater;
	public SearchListAdapter(Context context,List<Fund> fund) {
			this.inflater = LayoutInflater.from(context);
			this.fund = fund;
	}
	@Override
	public int getCount() {
		return fund.size();
	}

	@Override
	public Fund getItem(int position) {
		return fund.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_search, null);
			holder = new ViewHolder();
			holder.search_code = (TextView) convertView
					.findViewById(R.id.search_code);
			holder.search_name = (TextView) convertView
					.findViewById(R.id.search_name);
			holder.search_current = (TextView) convertView
					.findViewById(R.id.search_current);
			holder.search_rising = (TextView) convertView
					.findViewById(R.id.search_rising);			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Fund fund = getItem(position);
		holder.search_code.setText(fund.getFund_code());
		holder.search_name.setText(fund.getFund_name());
		holder.search_current.setText(fund.getCurrent());
		holder.search_rising.setText(fund.getRising()+"%");
		
		
		if (!TextUtils.isEmpty(fund.getRising()) && fund.getRising().substring(0, 1).equals("-")) {
			int green = Color.parseColor("#308014");
			holder.search_code.setTextColor(green);
			holder.search_name.setTextColor(green);
			holder.search_current.setTextColor(green);
			holder.search_rising.setTextColor(green);
		} else {
			holder.search_code.setTextColor(Color.RED);
			holder.search_name.setTextColor(Color.RED);
			holder.search_current.setTextColor(Color.RED);
			holder.search_rising.setTextColor(Color.RED);
		}
			
		return convertView;
	}

	private class ViewHolder {
		private TextView search_code;
		private TextView search_name;
		private TextView search_current;
		private TextView search_rising;
		
	}
	
	
}
