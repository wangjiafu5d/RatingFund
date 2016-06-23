package com.ratingfund.app.adapter;

import java.math.BigDecimal;
import java.util.List;

import com.ratingfund.app.R;
import com.ratingfund.app.activity.MainActivity;
import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
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



public class ListAdapter extends BaseAdapter {

	private List<FundA> fundA;
	private LayoutInflater inflater;
	private List<CHScrollView> mHScrollViews;
	private ListView mListView;
	private Context context;
	private HorizontalScrollView mTouchView;

	public ListAdapter(Context context, List<CHScrollView> mHScrollViews,
			ListView mListView, List<FundA> fundA,HorizontalScrollView mTouchView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mHScrollViews = mHScrollViews;
		this.mListView = mListView;
		this.fundA = fundA;
		this.mTouchView = mTouchView;
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
			convertView = inflater.inflate(R.layout.list_item, null);
			// 第一次初始化的时候装进来
			CHScrollView chScrollView = (CHScrollView) convertView.findViewById(R.id.item_scroll);
			Log.d("TAG","sz"+ mHScrollViews.size());
//			CHScrollView scrollView = mHScrollViews.get(0);
//			int scrollX = scrollView.getScrollX();
//			chScrollView.scrollTo(scrollX, 0);
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
		
		final FundA fundA = getItem(position);
		holder.item_title.setText(fundA.getFund_name()+"\r\n"+fundA.getFund_code());
		holder.item_data1.setText(fundA.getCurrent());
		holder.item_data2.setText(fundA.getRising()+"%");
		holder.item_data3.setText(fundA.getTradeMoney());
		holder.item_data4.setText(fundA.getDiscount()+"%");
		holder.item_data5.setText(fundA.getCorrectedIntrest()+"%");
		holder.item_data6.setText(fundA.getIntrestRule());
		holder.item_data7.setText(fundA.getIndex_name()+"\r\n"+fundA.getIndex());
		holder.item_data8.setText(fundA.getIndex_rising()+"%");
		
		if (!TextUtils.isEmpty(fundA.getRising()) && fundA.getRising().substring(0, 1).equals("-")) {
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
			
		
		
//		Log.d("TAG4", "item rising "+fundA.getRising());
//		holder.item_title.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "xxx" + book.getName(), 1).show();
//			}
//		});
//		holder.item_data1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Toast.makeText(context, "xxx" + book.getName(), 1).show();
//			}
//		});
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(context, "xxx" + book.getName(), Toast.LENGTH_SHORT).show();
//				
//			}
//		});
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

	public void addHViews(final CHScrollView hScrollView) {
		if (!mHScrollViews.isEmpty()) {
//			int size = mHScrollViews.size();
			CHScrollView scrollView = mHScrollViews.get(0);
			final int scrollX = scrollView.getScrollX();
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				mListView.post(new Runnable() {
					@Override
					public void run() {
//						Log.d("TAGX", "x"+scrollX);
						// 当listView刷新完成之后，把该条移动到最终位置
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViews.add(hScrollView);
		hScrollView.setScrollViewListener(new ScrollViewListener() {
			
			@Override
			public void onScrollChanged(HorizontalScrollView scrollView, int l, int t, int oldl, int oldt) {
				for (CHScrollView temp_scrollView : mHScrollViews) {
					// 防止重复滑动
//					Log.d("TAG", ""+mHScrollViews.size());
					if (scrollView!= temp_scrollView)
						temp_scrollView.smoothScrollTo(l, t);
				}
				
			}
		});
		
	}
	

}
