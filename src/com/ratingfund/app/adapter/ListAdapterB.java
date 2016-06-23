package com.ratingfund.app.adapter;

import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.view.CHScrollView;
import com.ratingfund.app.view.ScrollViewListener;

import android.content.Context;
import android.content.SharedPreferences;
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



public class ListAdapterB extends BaseAdapter {

	private List<FundB> fundB;
	private LayoutInflater inflater;
	private List<CHScrollView> mHScrollViewsB;
	private ListView mListView;
	private Context context;
	private MainActivity activity;

	public ListAdapterB(Context context, List<CHScrollView> mHScrollViewsB,
			ListView mListViewB, List<FundB> fundB) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mHScrollViewsB = mHScrollViewsB;
		this.mListView = mListViewB;
		this.fundB = fundB;
	}

	@Override
	public int getCount() {
		return fundB.size();
	}

	@Override
	public FundB getItem(int position) {
		return fundB.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public synchronized View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			// 第一次初始化的时候装进来
			CHScrollView chScrollView = (CHScrollView) convertView.findViewById(R.id.item_scroll);
			Log.d("TAG","sz"+ mHScrollViewsB.size());
//			SharedPreferences pref = context.getSharedPreferences("data", 0);
//			boolean flag = pref.getBoolean("allowedAdd", true);
//			if (flag) {
				addHViews(chScrollView);
//			} else {
//				SharedPreferences.Editor editor = context.getSharedPreferences("data", 0).edit();
//				editor.putBoolean("allowedAdd", true);
//				editor.commit();
//			}			
			holder = new ViewHolder();
			holder.item_title = (TextView) convertView
					.findViewById(R.id.item_title);
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
			holder.item_data6 = (TextView) convertView
					.findViewById(R.id.item_data6);
			holder.item_data7 = (TextView) convertView
					.findViewById(R.id.item_data7);
			holder.item_data8 = (TextView) convertView
					.findViewById(R.id.item_data8);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final FundB fundB = getItem(position);
		holder.item_title.setText(fundB.getFund_name()+"\r\n"+fundB.getFund_code());
		holder.item_data1.setText(fundB.getCurrent());
		holder.item_data2.setText(fundB.getRising()+"%");
		holder.item_data3.setText(fundB.getTradeMoney());
		holder.item_data4.setText(fundB.getDiscount()+"%");
		holder.item_data5.setText(fundB.getPriceLeverage());
		holder.item_data6.setText(fundB.getIntrestRule());
		holder.item_data7.setText(fundB.getIndex_name()+"\r\n"+fundB.getIndex());
		holder.item_data8.setText(fundB.getIndex_rising()+"%");
		
		if (!TextUtils.isEmpty(fundB.getRising()) && fundB.getRising().substring(0, 1).equals("-")) {
			int green = Color.parseColor("#308014");
			holder.item_title.setTextColor(green);
			holder.item_data1.setTextColor(green);
			holder.item_data2.setTextColor(green);
			holder.item_data3.setTextColor(green);
			holder.item_data4.setTextColor(green);
			holder.item_data5.setTextColor(green);
			holder.item_data6.setTextColor(green);
			holder.item_data7.setTextColor(green);
			holder.item_data8.setTextColor(green);
		} else {
			holder.item_title.setTextColor(Color.RED);
			holder.item_data1.setTextColor(Color.RED);
			holder.item_data2.setTextColor(Color.RED);
			holder.item_data3.setTextColor(Color.RED);
			holder.item_data4.setTextColor(Color.RED);
			holder.item_data5.setTextColor(Color.RED);
			holder.item_data6.setTextColor(Color.RED);
			holder.item_data7.setTextColor(Color.RED);
			holder.item_data8.setTextColor(Color.RED);
		}

		return convertView;
	}

	private class ViewHolder {
		private TextView item_title;
		private TextView item_data1;
		private TextView item_data2;
		private TextView item_data3;
		private TextView item_data4;
		private TextView item_data5;
		private TextView item_data6;
		private TextView item_data7;
		private TextView item_data8;
	}

	public void addHViews(final CHScrollView hScrollViewB) {
		if (!mHScrollViewsB.isEmpty()) {
			CHScrollView scrollView = mHScrollViewsB.get(0);
			final int scrollX = scrollView.getScrollX();
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				mListView.post(new Runnable() {
					@Override
					public void run() {
						// 当listView刷新完成之后，把该条移动到最终位置
						hScrollViewB.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViewsB.add(hScrollViewB);
		hScrollViewB.setScrollViewListener(new ScrollViewListener() {
			
			@Override
			public void onScrollChanged(HorizontalScrollView scrollView, int l, int t, int oldl, int oldt) {
				for (CHScrollView temp_scrollView : mHScrollViewsB) {
					// 防止重复滑动
//					Log.d("TAG", ""+mHScrollViews.size());
					if (scrollView!= temp_scrollView)
						temp_scrollView.smoothScrollTo(l, t);
				}
				
			}
		});
		
	}

}
