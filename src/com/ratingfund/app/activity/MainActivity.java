package com.ratingfund.app.activity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.ratingfund.app.R;
import com.ratingfund.app.adapter.ListAdapter;
import com.ratingfund.app.adapter.ListAdapterB;
import com.ratingfund.app.adapter.ListAdapterM;
import com.ratingfund.app.adapter.ListAdapterZ;
import com.ratingfund.app.db.RatingFundDB;

import com.ratingfund.app.fragment.FragmentB;
import com.ratingfund.app.fragment.FragmentLunTan;
import com.ratingfund.app.fragment.FragmentM;
import com.ratingfund.app.fragment.FragmentZiXuan;
import com.ratingfund.app.model.Fund;
import com.ratingfund.app.model.FundA;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.model.FundM;
import com.ratingfund.app.service.UpdateService;
import com.ratingfund.app.service.UpdateService.RefreshBinder;
import com.ratingfund.app.util.CPriceComparator;
import com.ratingfund.app.util.CodeComparator;
import com.ratingfund.app.util.CorrectedIntrestComparator;
import com.ratingfund.app.util.DiscountComparator;

import com.ratingfund.app.util.IndexComparator;
import com.ratingfund.app.util.IndexRisingComparator;
import com.ratingfund.app.util.IntrestRuleComparator;

import com.ratingfund.app.util.RisingComparator;
import com.ratingfund.app.util.TradeMoneyComparator;
import com.ratingfund.app.view.CHScrollView;
import com.ratingfund.app.view.ScrollViewListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;

import android.os.Bundle;
import android.os.IBinder;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity implements OnClickListener,ScrollViewListener{
	
	public ListView mListView;
	public ListView mListViewB;
	public ListView mListViewM;
	public ListView mListViewZ;
	public RatingFundDB ratingFundDB;
	
	private ListAdapter adapter;
	public ListAdapterB adapterB;
	public ListAdapterM adapterM;
	public ListAdapterZ adapterZ;
	public int positionA;
	public HorizontalScrollView mTouchView;
	public HorizontalScrollView mTouchViewB;
	// 装入所有的HScrollView
	public List<CHScrollView> mHScrollViews = new ArrayList<CHScrollView>();
	public List<CHScrollView> mHScrollViewsB = new ArrayList<CHScrollView>();
	public ImageView leftOk;
	public ImageView rightOk;
	public ImageView leftNo;
	public ImageView rightNo;
	public ImageView leftOkB,rightOkB,leftNoB,rightNoB;
	public List<FundA> datas = new ArrayList<FundA>();
	public List<FundB> datasB = new ArrayList<FundB>();
	public List<FundM> datasM = new ArrayList<FundM>();
	public List<FundA> datasZ = new ArrayList<FundA>();
	
	private Fragment fragmentA;
	private FragmentB fragmentB;
	private FragmentM fragmentM;
	private Fragment fragmentMain;
	private FragmentZiXuan fragmentZiXuan;
	private Fragment fragmentLunTan;
	
	private WebView webView;
	private TextView filter_text,title,refresh_button,search_text;
	private TextView sh_text,sz_text,cy_text;
	
	private int fragmentIsShow = 1;
	private int childFragmentIsShow = 1;
	private boolean sortFlag = false;
	private int buttonId = 1;
	public int b_buttonId = 1;
	public int m_buttonId = 1;
	public int z_buttonId = 1;
	public long lastClickTime = 0;
	
	private UpdateReceiver updateReceiver;
	
	RadioGroup sortRadioGroup;
	RadioButton fund_a_cprice_button ;
	RadioButton fund_a_code_button;
	RadioButton fund_a_rising_button;
	RadioButton fund_a_tradeMoney_button;
	RadioButton fund_a_checkedButton;
	RadioButton fund_a_discount_button;
	RadioButton fund_a_corrected_intrest_button;
	RadioButton fund_a_intrest_rule_button;
	RadioButton fund_a_index_button;
	RadioButton fund_a_index_rising_button;
	
	List<RadioButton> fund_a_radioButton_list = new ArrayList<RadioButton>();	
	public List<RadioButton> fund_b_radioButton_list = new ArrayList<RadioButton>();
	
	RadioButton hangqing;
	RadioButton zixuan;	
	RadioButton luntan;
	
	FrameLayout main_title_bar;
	
	private UpdateService.RefreshBinder refreshBinder;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			refreshBinder = (RefreshBinder) service;			
		}
	};
	
	public static List<String> displayList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		if(pref.getBoolean("statement", true)){
				showWarningDialog();
		}
//		InitDateBase();
//		getActionBar().setDisplayShowHomeEnabled(false);
//		InitDateBase();
		ratingFundDB = RatingFundDB.getInstacnce(this);
		initListView();
//		search = (SearchView) findViewById(R.id.search_view);
//		search.setOnQueryTextListener(new OnQueryTextListener() {
//			
//			@Override
//			public boolean onQueryTextSubmit(String query) {
//				
//				return false;
//			}
//			
//			@Override
//			public boolean onQueryTextChange(String newText) {
//				Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
		updateReceiver = new UpdateReceiver();
		IntentFilter filter = new IntentFilter();
        filter.addAction("BroadcastAction");
        registerReceiver(updateReceiver, filter);        
     
        
		Intent i = new Intent(this, UpdateService.class);
		startService(i);
		bindService(i, connection, BIND_AUTO_CREATE);
	}
	@Override
	protected void onStart() {
//		Intent i = new Intent(this, UpdateService.class);
//		startService(i);		
		super.onStart();
	}
	
	@Override
	protected void onStop() {
//		Intent i = new Intent(this, UpdateService.class);
//		stopService(i);
		super.onStop();
	}
	@Override
	public void onBackPressed() {
		
		if (isFastDoubleClick()) {
			super.onBackPressed();
		} else {
			if (fragmentLunTan != null && fragmentIsShow == 3) {
				webView = (WebView) findViewById(R.id.web_view);
				String url = webView.getUrl();
				if (!webView.canGoBack() || url.equals(getString(R.string.url)) || url.equals(getString(R.string.url1))
						|| url.equals(getString(R.string.url2)) || url.equals(getString(R.string.url3))) {
					showFinishDialog();
				} else {
					webView.goBack();
				}
			} else {
				showFinishDialog();
			}
		}
		
	}
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
	    MenuInflater inflater = getMenuInflater();  
	    inflater.inflate(R.menu.main, menu);  
	    return super.onCreateOptionsMenu(menu);  
	}  
	@Override
	protected void onDestroy() {
		
		unregisterReceiver(updateReceiver);		
		unbindService(connection);
		Intent stopIntent = new Intent(this,UpdateService.class);
		stopService(stopIntent);
		super.onDestroy();
	}
	
	private void initListView() {
		leftOk = (ImageView) findViewById(R.id.iv_left_ok);
		leftNo = (ImageView) findViewById(R.id.iv_left_no);
		rightOk = (ImageView) findViewById(R.id.iv_right_ok);
		rightNo = (ImageView) findViewById(R.id.iv_right_no);
		main_title_bar = (FrameLayout) findViewById(R.id.main_title_bar);
		
		
		CHScrollView headerScroll = (CHScrollView) findViewById(R.id.item_scroll_title);
		headerScroll.setScrollViewListener(this);
		mHScrollViews.add(headerScroll);
		
		
		
		
		
		title = (TextView) findViewById(R.id.title);
		search_text = (TextView) findViewById(R.id.search_text);
		filter_text = (TextView) findViewById(R.id.filter_text);
		refresh_button = (TextView) findViewById(R.id.refresh_button);
		sh_text = (TextView) findViewById(R.id.sh_text);
		sz_text = (TextView) findViewById(R.id.sz_text);
		cy_text = (TextView) findViewById(R.id.cy_text);
		
		
		sortRadioGroup = (RadioGroup) findViewById(R.id.sort_radio_group_a);
		fund_a_code_button = (RadioButton) findViewById(R.id.fund_a_code_button);
		fund_a_cprice_button = (RadioButton) findViewById(R.id.fund_a_cprice_button);
		fund_a_rising_button = (RadioButton) findViewById(R.id.fund_a_rising_button);
		fund_a_tradeMoney_button = (RadioButton) findViewById(R.id.fund_a_trademoney_button);
		fund_a_discount_button = (RadioButton) findViewById(R.id.fund_a_discount_button);
		fund_a_corrected_intrest_button = (RadioButton) findViewById(R.id.fund_a_corrected_interst_button);
		fund_a_intrest_rule_button = (RadioButton) findViewById(R.id.fund_a_interest_rule_button);
		fund_a_index_button = (RadioButton) findViewById(R.id.fund_a_index_button);
		fund_a_index_rising_button = (RadioButton) findViewById(R.id.fund_a_index_rising_button);
		fund_a_radioButton_list.add(fund_a_code_button);
		fund_a_radioButton_list.add(fund_a_cprice_button);
		fund_a_radioButton_list.add(fund_a_rising_button);
		fund_a_radioButton_list.add(fund_a_tradeMoney_button);
		fund_a_radioButton_list.add(fund_a_discount_button);
		fund_a_radioButton_list.add(fund_a_corrected_intrest_button);
		fund_a_radioButton_list.add(fund_a_intrest_rule_button);
		fund_a_radioButton_list.add(fund_a_index_button);
		fund_a_radioButton_list.add(fund_a_index_rising_button);
		for (RadioButton button : fund_a_radioButton_list) {
			button.setOnClickListener(this);
		}
		fund_a_code_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					for (RadioButton button : fund_a_radioButton_list) {
						if(button!=null&&button!=fund_a_code_button){
							button.setHovered(false);
						}
					}
					sortRadioGroup.clearCheck();
//					fund_a_code_button.setChecked(true);
//					fund_a_code_button.setPressed(true);
				}
			}
		});
		sortRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				fund_a_checkedButton = (RadioButton) findViewById(checkedId);
				if(fund_a_checkedButton!=null&&fund_a_checkedButton.isChecked()){
					for (RadioButton button : fund_a_radioButton_list) {
						if(button!=null&&button!=fund_a_checkedButton){
							button.setHovered(false);
							button.setChecked(false);
						}
					}
				}
				
			}
		});
		
		
		RadioButton fundA = (RadioButton) findViewById(R.id.fund_a);
		RadioButton fundB = (RadioButton) findViewById(R.id.fund_b);
		RadioButton fundM = (RadioButton) findViewById(R.id.fund_m);
		hangqing = (RadioButton) findViewById(R.id.hangqing);
		zixuan = (RadioButton) findViewById(R.id.zixuan);	
		luntan = (RadioButton) findViewById(R.id.luntan);
//		fundA.setChecked(true);
//		hangqing.setChecked(true);
		fundA.setOnClickListener(this);
		fundB.setOnClickListener(this);
		fundM.setOnClickListener(this);
		hangqing.setOnClickListener(this);
		zixuan.setOnClickListener(this);
		luntan.setOnClickListener(this);
		filter_text.setOnClickListener(this);
		search_text.setOnClickListener(this);
		refresh_button.setOnClickListener(this);
		
		
		mListView = (ListView) findViewById(R.id.scroll_list_a);

		adapter = new ListAdapter(this, mHScrollViews, mListView,
				datas,mTouchView);
		
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				FundA fundA = datas.get(arg2);
//				Log.d("TAG", fundA.getFund_name());
				Intent intent = new Intent("com.ratingfund.app.activity.DetailedActivity.ACTION_START");
//				Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
				intent.putExtra("fund_code", fundA.getFund_code());
				startActivity(intent);
				
			}
		});
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					getDisplayList(view,datas);
					// Log.d("TAG", ""+position);
					// scrolledX = mListView.getScrollX();
					// scrolledY = mListView.getScrollY();

				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				for(int i = 0;i<visibleItemCount;i++){
					LinearLayout item = (LinearLayout) mListView.getChildAt(i);
					CHScrollView temp = (CHScrollView) item.findViewById(R.id.item_scroll);
					if(!mHScrollViews.contains(temp)){
						mHScrollViews.add(temp);
						temp.setScrollViewListener(MainActivity.this);
						temp.scrollTo(mHScrollViews.get(0).getScrollX(), 0);
					}
				}


			}
		});
//		refreshListView();
//		setDatas();
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fund_a:
			showFragment(1);
			childFragmentIsShow = 1;
			try {
				refreshListView();
			} catch (Exception e) {
			}
			break;
		case R.id.fund_b:
			showFragment(2);
			childFragmentIsShow = 2;
			try {
				refreshListView();
			} catch (Exception e) {
			}
			break;
		case R.id.fund_m:
			showFragment(3);
			childFragmentIsShow = 3;
			try {
				refreshListView();
			} catch (Exception e) {
			}
			break;
		case R.id.hangqing:
			main_title_bar.setVisibility(View.VISIBLE);
//			search.setVisibility(View.VISIBLE);
			title.setText(R.string.hangqing);
			showFragment2(1);
			fragmentIsShow = 1;
			break;
		case R.id.zixuan:
			main_title_bar.setVisibility(View.VISIBLE);
			title.setText(R.string.zixuan);
			showFragment2(2);
			fragmentIsShow = 2;
			try {
				refreshListView();
			} catch (Exception e) {
			}
			break;
		case R.id.luntan:
//			search.setVisibility(View.GONE);
			showFragment2(3);
			fragmentIsShow = 3;
			main_title_bar.setVisibility(View.GONE);
			break;
		case R.id.filter_text:
			Intent filter = new Intent(this, FilterActivity.class);
			
			startActivity(filter);
			break;
		case R.id.search_text:
			Intent search = new Intent(this, SearchActivity.class);
			
			startActivity(search);
			break;
		case R.id.refresh_button:			
			refreshBinder.startRefresh();
			break;
//			if (search_text.getText() == "搜索") {
//				filter.setVisibility(View.GONE);
//				title.setVisibility(View.GONE);
//				search_text.setText("关闭");
//				search.setVisibility(View.VISIBLE);
//			} else {
//				search.setVisibility(View.GONE);
//				filter.setVisibility(View.VISIBLE);
//				title.setVisibility(View.VISIBLE);
//				search_text.setText("搜索");
//				
//			}
		case R.id.fund_a_code_button:
//			sortRadioGroup.clearCheck();
			sortFundACode();
			sortFlag = !sortFlag;
			break;
		case R.id.fund_a_cprice_button:
			sortFundACPrice();
			sortFlag = !sortFlag;
			break;
		case R.id.fund_a_rising_button:
			sorFundARising();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_a_trademoney_button:
			sortFundATradeMoney();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_a_discount_button:
			sortFundADiscount();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_a_corrected_interst_button:
			sortFundACorrectedIntrest();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_a_interest_rule_button:
			sortFundAIntrestRule();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_a_index_button:
			sortFundAIndex();
			sortFlag = !sortFlag;			
			break;
		case R.id.fund_a_index_rising_button:
			sortFundAIndexRising();
			sortFlag = !sortFlag;			
			break;
		default:
			break;
		}
//		getDisplayList(mListView, datas);
		adapter.notifyDataSetChanged();
	}
	
//	public void onScrollChanged(int l, int t, int oldl, int oldt) {
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int width = dm.widthPixels;
//		if (l == 0) {
//			leftOk.setVisibility(View.GONE);
//			leftNo.setVisibility(View.VISIBLE);
//		} else {
//			leftOk.setVisibility(View.VISIBLE);
//			leftNo.setVisibility(View.GONE);
//		}
//		if (l == width) {
//			rightOk.setVisibility(View.GONE);
//			rightNo.setVisibility(View.VISIBLE);
//		} else {
//			rightOk.setVisibility(View.VISIBLE);
//			rightNo.setVisibility(View.GONE);
//		}
//		for (CHScrollView scrollView : mHScrollViews) {
//			// 防止重复滑动
////			Log.d("TAG", ""+mHScrollViews.size());
//			if (mTouchView != scrollView)
//				scrollView.smoothScrollTo(l, t);
//		}
//	}
//	public void onScrollChangedB(int l, int t, int oldl, int oldt) {
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int width = dm.widthPixels;
//		if (l == 0) {
//			leftOkB.setVisibility(View.GONE);
//			leftNoB.setVisibility(View.VISIBLE);
//		} else {
//			leftOkB.setVisibility(View.VISIBLE);
//			leftNoB.setVisibility(View.GONE);
//		}
//		if (l == width) {
//			rightOkB.setVisibility(View.GONE);
//			rightNoB.setVisibility(View.VISIBLE);
//		} else {
//			rightOkB.setVisibility(View.VISIBLE);
//			rightNoB.setVisibility(View.GONE);
//		}
//		for (CHScrollView scrollView : mHScrollViews) {
//			// 防止重复滑动
////			Log.d("TAG", ""+mHScrollViews.size());
//			if (mTouchViewB != scrollView)
//				scrollView.smoothScrollTo(l, t);
//		}
//	}
//	public void setDatas(){
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("a.txt")));
//			String temp;
//			StringBuilder str = new StringBuilder();
//			str.append("http://hq.sinajs.cn/list=");
//			while ((temp=br.readLine())!=null) {
////				FundA tempFund = new FundA();
////				tempFund.setFund_code(temp);
////				ratingFundDB.savedFundA(tempFund);
//				if(temp.substring(0, 1).equals("1")){
//					str.append("sz").append(temp).append(",");
//				}else {
//					str.append("sh").append(temp).append(",");
//				}
//			}
//			br.close();
////			Log.d("TAG", str.toString());
////			dialog = new ProgressDialog(this);
////			dialog.setCancelable(true);
////			dialog.setMessage("刷新中...");
////			dialog.show();
//			HttpUtil.sendHttpRequest(str.toString(), new HttpCallbackListener() {
//				
//				@Override
//				public void onFinish(String response) {
////					Log.d("TAG", response);
//					
//					boolean result = PraseResponse.handleFundAResponse(ratingFundDB, response);
//					Log.d("TAG", "Prase "+result);
//					if(result&&!threadBreakFlag){
//						runOnUiThread(new Runnable() {
//							
//							@Override
//							public void run() {
////								dialog.dismiss();
//								datas.clear();
//								for (FundA fund : ratingFundDB.loadFundA()) {
//									datas.add(fund);
//								}
////								datas = ratingFundDB.loadFundA();
//								if(datas.size()>0){
////									Log.d("TAG", "load datas "+datas.size());
////									Log.d("TAG", "load datas "+datas.get(0).getFund_name());
//									
//									adapter.notifyDataSetChanged();
//									Toast.makeText(getApplication(), "刷新完成", Toast.LENGTH_SHORT).show();
////									Log.d("TAG", "notifyFinish ");
////									mListView.setSelection(0);
////									mListView.scrollTo(scrolledX, scrolledY); 
//									}
//								
//							}
//						});
//					
//					}
//				}
//				
//				@Override
//				public void onError(Exception e) {
//				}
//			});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	public void setDatasB(){
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("b.txt")));
//			String temp;
//			StringBuilder str = new StringBuilder();
//			str.append("http://hq.sinajs.cn/list=");
//			while ((temp=br.readLine())!=null) {
//				if(temp.substring(0, 1).equals("1")){
//					str.append("sz").append(temp).append(",");
//				}else {
//					str.append("sh").append(temp).append(",");
//				}
//			}
//			br.close();
////			Log.d("TAG", str.toString());
////			dialog = new ProgressDialog(this);
////			dialog.setCancelable(false);
////			dialog.setMessage("刷新中...");
////			dialog.show();
//			HttpUtil.sendHttpRequest(str.toString(), new HttpCallbackListener() {
//				
//				@Override
//				public void onFinish(String response) {
////					Log.d("TAG", response);
//					boolean result = PraseResponse.handleFundBResponse(ratingFundDB, response);
////					Log.d("TAG", "Prase "+result);
//					if(result&&!threadBreakFlag){
//						runOnUiThread(new Runnable() {
//							
//							@Override
//							public void run() {
////								dialog.dismiss();
//								datasB.clear();
//								for (FundB fund : ratingFundDB.loadFundB()) {
//									datasB.add(fund);
////									Log.d("TAG1", ""+fund.getFund_name());
//								}
////								Log.d("TAG1", ""+datasB.size());
////								datas = ratingFundDB.loadFundA();
//								if(datasB.size()>0){
////									Log.d("TAG", "load datas "+datas.size());
////									Log.d("TAG", "load datas "+datas.get(0).getFund_name());
////									adapterB = fragmentB.adapterB;
//									adapterB.notifyDataSetChanged();
////									Toast.makeText(getApplication(), "刷新完成", Toast.LENGTH_SHORT).show();
////									Log.d("TAG", "notifyFinish ");
////									mListView.setSelection(0);
////									mListView.scrollTo(scrolledX, scrolledY); 
//									}
//								
//							}
//						});
//					
//					}
//				}
//				
//				@Override
//				public void onError(Exception e) {
//				}
//			});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public void showFragment(int index){
		FragmentManager parentFm = getFragmentManager();
		FragmentTransaction parentFt = parentFm.beginTransaction();
		fragmentMain = parentFm.findFragmentById(R.id.fragment_main);
//		FragmentManager childFm = fragmentMain.getChildFragmentManager();
//		FragmentTransaction childFt = childFm.beginTransaction();
		fragmentA = parentFm.findFragmentById(R.id.fragment_list_a);
//		Log.d("TAG2", "fragmentA" +(fragmentA==null));
		
		hideFragment(parentFt);
		
		switch (index) {
	    case 1:
	      // 如果fragment1已经存在则将其显示出来
	      if (fragmentA != null){
	        parentFt.show(fragmentA);
	      }
	      // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
	      break;
	    case 2:
	  
	      if (fragmentB != null){
	        parentFt.show(fragmentB);
	      }
	      else {
	        fragmentB = new FragmentB();	       
			parentFt.add(R.id.fragment_layout, fragmentB);
//			ListView mListViewS = (ListView) fragmentB.getActivity().findViewById(R.id.scroll_list_b);
//			Log.d("TAG2", "mListViewS "+(mListViewS==null));
	      }
	      break;
	    case 3:
	  	  
		      if (fragmentM != null){
		        parentFt.show(fragmentM);
		      }
		      else {
		        fragmentM = new FragmentM();	       
				parentFt.add(R.id.fragment_layout, fragmentM);
//				ListView mListViewS = (ListView) fragmentB.getActivity().findViewById(R.id.scroll_list_b);
//				Log.d("TAG2", "mListViewS "+(mListViewS==null));
		      }
		      break;
		}
		parentFt.commit();
	     
		
		
	}
	public void showFragment2(int index){
		FragmentManager parentFm = getFragmentManager();
		fragmentMain = parentFm.findFragmentById(R.id.fragment_main);
		FragmentTransaction parentFt = parentFm.beginTransaction();
		hideFragment2(parentFt);
		
		switch (index) {
	    case 1:
	      // 如果fragment1已经存在则将其显示出来
	      if (fragmentMain != null){
	        parentFt.show(fragmentMain);
	      }
	      // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add

	      break;
	    case 2:
	      if (fragmentZiXuan != null){
	        parentFt.show(fragmentZiXuan);
	      }
	      else {
	    	fragmentZiXuan = new FragmentZiXuan();
	        parentFt.add(R.id.fragment_main_layout, fragmentZiXuan);
	      }
	      break;
	    case 3:
		      if (fragmentLunTan != null){
		        parentFt.show(fragmentLunTan);
		      }
		      else {
		    	fragmentLunTan = new FragmentLunTan();
		        parentFt.add(R.id.fragment_main_layout, fragmentLunTan);
		      }
	      break;
		}
		parentFt.commit();
	}
	
	public void hideFragment(FragmentTransaction ft){
		if (fragmentA != null) {
			ft.hide(fragmentA);
//			Log.d("TAG2", "fragmentA");
		}
		if (fragmentB != null) {
			ft.hide(fragmentB);
//			Log.d("TAG2", "fragmentB");
		}
		if (fragmentM != null) {
			ft.hide(fragmentM);
//			Log.d("TAG2", "fragmentB");
		}
	}
	public void hideFragment2(FragmentTransaction ft){
		if (fragmentMain != null) {
			ft.hide(fragmentMain);
		}
		if (fragmentZiXuan != null) {
			ft.hide(fragmentZiXuan);
//			Log.d("TAG2", "ZiXuan");
		}
		if (fragmentLunTan != null) {
			ft.hide(fragmentLunTan);
		}
	}
	public class UpdateReceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent) {
		
			try {
				refreshListView();
			} catch (Exception e) {
			}
			
			Cursor cursor = ratingFundDB.db.query("FundA", null, "hy_index=? or ? or ?", new String[]{"000001","399001","399006"}, null, null, null);
			if(cursor.moveToFirst()){
				do {
					String rising = "0";
					if(cursor.getString(cursor.getColumnIndex("hy_index")).equals("000001")){
						rising = cursor.getString(cursor.getColumnIndex("index_rising"));
						if(rising!=null&&Double.parseDouble(rising)<0){
							sh_text.setTextColor(Color.GREEN);
						}else {
							sh_text.setTextColor(Color.RED);
						}
						sh_text.setText("上证："+rising+"%");
					}else  if(cursor.getString(cursor.getColumnIndex("hy_index")).equals("399001")){
						rising = cursor.getString(cursor.getColumnIndex("index_rising"));
						if(rising!=null&&Double.parseDouble(rising)<0){
							sz_text.setTextColor(Color.GREEN);
						}else {
							sz_text.setTextColor(Color.RED);
						}
						sz_text.setText("深圳："+rising+"%");
					}else  if(cursor.getString(cursor.getColumnIndex("hy_index")).equals("399006")){
						rising = cursor.getString(cursor.getColumnIndex("index_rising"));
						if(rising!=null&&Double.parseDouble(rising)<0){
							cy_text.setTextColor(Color.GREEN);
						}else {
							cy_text.setTextColor(Color.RED);
						}
						cy_text.setText("创业："+rising+"%");
					}
				} while (cursor.moveToNext());
			}
			
			
//			setDatas();
//			Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(context, UpdateService.class);
			context.startService(i);
			
		}
	}
	
		
	@Override
	public void onScrollChanged(HorizontalScrollView scrollView, int l, int t, int oldl, int oldt) {
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int width = dm.widthPixels;
		if (mHScrollViews.contains(scrollView)) {
			for (CHScrollView temp_scrollView : mHScrollViews) {
				// 防止重复滑动
				// Log.d("TAG", ""+mHScrollViews.size());
				if (scrollView != temp_scrollView)
					temp_scrollView.smoothScrollTo(l, t);
			}
		}
		if(mHScrollViewsB.contains(scrollView)){
			for (CHScrollView temp_scrollView : mHScrollViewsB) {
				if (scrollView != temp_scrollView)
					temp_scrollView.smoothScrollTo(l, t);
			}
		}
		
	}
	
	public void refreshListView(){
//		Log.d("TAG6", "childFragmentIsShow "+childFragmentIsShow);		
		if (childFragmentIsShow == 1) {
			datas.clear();
			for (FundA fund : ratingFundDB.loadFundA()) {
				datas.add(fund);
//				Log.d("TAG6", "datas.size "+datas.size());	
			}

			if (datas.size() > 0) {
				switch (buttonId) {
				case 1:
					sortFundACode();
					setSortArrow2(fund_a_code_button);
					break;
				case 2:
					sortFundACPrice();
					setSortArrow2(fund_a_cprice_button);
					break;
				case 3:
					sorFundARising();
					setSortArrow2(fund_a_rising_button);
					break;
				case 4:
					sortFundATradeMoney();
					setSortArrow2(fund_a_tradeMoney_button);
					break;
				case 5:
					sortFundADiscount();
					;
					setSortArrow2(fund_a_discount_button);
					break;
				case 6:
					sortFundACorrectedIntrest();
					setSortArrow2(fund_a_corrected_intrest_button);
					break;
				case 7:
					sortFundAIntrestRule();
					setSortArrow2(fund_a_intrest_rule_button);
					break;
				case 8:
					sortFundAIndex();
					setSortArrow2(fund_a_index_button);
					break;
				case 9:
					sortFundAIndexRising();
					setSortArrow2(fund_a_index_rising_button);
					break;
				default:
					break;
				}
				
				Collections.reverse(datas);
				adapter.notifyDataSetChanged();
//				mHScrollViews.remove(mHScrollViews.get(mHScrollViews.size()-1));
				
			}
		}
			if (childFragmentIsShow == 2) {
				datasB.clear();
				for (FundB fund : ratingFundDB.loadFundB()) {
					datasB.add(fund);					
				}

				if (datasB.size() > 0) {
					switch (b_buttonId) {					
					case 1:
						fragmentB.sortFundACode();
						fragmentB.setSortArrow2(fragmentB.fund_b_code_button);						
						break;
					case 2:
						fragmentB.sortFundACPrice();
						fragmentB.setSortArrow2(fragmentB.fund_b_cprice_button);
						break;
					case 3:
						fragmentB.sorFundARising();
						fragmentB.setSortArrow2(fragmentB.fund_b_rising_button);
						break;
					case 4:
						fragmentB.sortFundATradeMoney();
						fragmentB.setSortArrow2(fragmentB.fund_b_tradeMoney_button);
						break;
					case 5:
						fragmentB.sortFundADiscount();
						fragmentB.setSortArrow2(fragmentB.fund_b_discount_button);
						break;
					case 6:
						fragmentB.sortPriceLeverage();
						fragmentB.setSortArrow2(fragmentB.fund_b_price_leverage_button);
						break;
					case 7:
						fragmentB.sortFundAIntrestRule();
						fragmentB.setSortArrow2(fragmentB.fund_b_intrest_rule_button);
						break;
					case 8:
						fragmentB.sortFundAIndex();
						fragmentB.setSortArrow2(fragmentB.fund_b_index_button);
						break;
					case 9:
						fragmentB.sortFundAIndexRising();
						fragmentB.setSortArrow2(fragmentB.fund_b_index_rising_button);
						break;
					
					}
					
					Collections.reverse(datasB);
//					SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//					editor.putBoolean("allowedAdd", false);
//					editor.commit();
					adapterB.notifyDataSetChanged();
					
				}
			}
			if (childFragmentIsShow == 3) {
//				Log.d("TAG", "分级套利");
				datasM.clear();
				for (FundM fund : ratingFundDB.loadFundM()) {
					datasM.add(fund);					
				}
//				Log.d("TAG", "loadeEnd"+datasM.size());
				if (datasM.size() > 0) {
					switch (m_buttonId) {					
					case 1:
						fragmentM.sortFundACode();
						fragmentM.setSortArrow2(fragmentM.fund_m_code_button);						
						break;					
					case 2:
						fragmentM.sorFundARising();
						fragmentM.setSortArrow2(fragmentM.fund_m_rising_button);
						break;
					case 3:
						fragmentM.sortFundATradeMoney();
						fragmentM.setSortArrow2(fragmentM.fund_m_tradeMoney_button);
						break;
					case 4:
						fragmentM.sortSpriceDiscount();
						fragmentM.setSortArrow2(fragmentM.fund_m_sprice_discount_button);
						break;
					case 5:
						fragmentM.sortBpriceDiscount();
						fragmentM.setSortArrow2(fragmentM.fund_m_bprice_discount_button);
						break;
					
					}
//					Log.d("TAG", "End");
					Collections.reverse(datasM);
					adapterM.notifyDataSetChanged();
//					Log.d("TAG", "End");
					
				}
			}
			if (fragmentIsShow == 2) {
				
				datasZ.clear();
				for (FundA fund : ratingFundDB.loadFundZX()) {
					
					datasZ.add(fund);					
				}

				if (datasZ.size() > 0) {
					switch (z_buttonId) {					
					case 1:
						fragmentZiXuan.sortFundACode();
						fragmentZiXuan.setSortArrow2(fragmentZiXuan.code_button);						
						break;					
					case 2:
						fragmentZiXuan.sortFundACPrice();
						fragmentZiXuan.setSortArrow2(fragmentZiXuan.price_button);
						break;
					case 3:
						fragmentZiXuan.sorFundARising();
						fragmentZiXuan.setSortArrow2(fragmentZiXuan.rising_button);
						break;
					case 4:
						fragmentZiXuan.sortFundATradeMoney();
						fragmentZiXuan.setSortArrow2(fragmentZiXuan.tradeMoney_button);
						break;
					case 5:
						fragmentZiXuan.sortFundADiscount();
						fragmentZiXuan.setSortArrow2(fragmentZiXuan.discount_button);
						break;
					
					}
					
					Collections.reverse(datasZ);
					adapterZ.notifyDataSetChanged();
					
				}
			}
	}
	public void sortFundACode(){
		if(buttonId!=1){
			sortFlag = true;
		}
		Collections.sort(datas, new CodeComparator());
		setSortArrow(fund_a_code_button);
		buttonId = 1;
	}
	public void sortFundACPrice(){
		if(buttonId!=2){
			sortFlag = true;
		}
		Collections.sort(datas, new CPriceComparator());
		setSortArrow(fund_a_cprice_button);
		buttonId = 2;
	}
	public void sorFundARising(){
		if(buttonId!=3){
			sortFlag = true;
		}
		Collections.sort(datas, new RisingComparator());
		setSortArrow(fund_a_rising_button);
		buttonId = 3;
	}
	public void sortFundATradeMoney(){
		if(buttonId!=4){
			sortFlag = true;
		}
		Collections.sort(datas, new TradeMoneyComparator());
		setSortArrow(fund_a_tradeMoney_button);
		buttonId = 4;
	}
	private void sortFundADiscount(){
		if(buttonId!=5){
			sortFlag = true;
		}
		Collections.sort(datas, new DiscountComparator());
		setSortArrow(fund_a_discount_button);
		buttonId = 5;
	}
	private void sortFundACorrectedIntrest(){
		if(buttonId!=6){
			sortFlag = true;
		}
		Collections.sort(datas, new CorrectedIntrestComparator());
		setSortArrow(fund_a_corrected_intrest_button);
		buttonId = 6;
	}
	private void sortFundAIntrestRule(){
		if(buttonId!=7){
			sortFlag = true;
		}
		Collections.sort(datas, new IntrestRuleComparator());
		setSortArrow(fund_a_intrest_rule_button);
		buttonId = 7;
	}
	private void sortFundAIndex(){
		if(buttonId!=8){
			sortFlag = true;
		}
		Collections.sort(datas, new IndexComparator());
		setSortArrow(fund_a_index_button);
		buttonId = 8;
	}
	private void sortFundAIndexRising(){
		if(buttonId!=9){
			sortFlag = true;
		}
		Collections.sort(datas, new IndexRisingComparator());
		setSortArrow(fund_a_index_rising_button);
		buttonId = 9;
	}
	public void setSortArrow(RadioButton radioButton){
		if(sortFlag){
			Collections.reverse(datas);
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
	
	public boolean isFastDoubleClick() {
		        long time = System.currentTimeMillis();
		        long timeD = time - lastClickTime;
		        if (timeD >= 0 && timeD <= 500) {
			        return true;
		    } else {
			        lastClickTime = time;
			        return false;
		        }
	}
	public void showFinishDialog(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示");
		dialog.setMessage("确认退出程序?");
//		dialog.setCancelable(false);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
	public void showWarningDialog(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("郑重声明：");
		dialog.setMessage(getString(R.string.statement));
//		dialog.setCancelable(false);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
				editor.putBoolean("statement", false);
				editor.commit();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialog.show();
	}
	public void getDisplayList(AbsListView view,List<? extends Fund> srcList){
		if (srcList.size() > 0) {
			displayList.clear();
			List<? extends Fund> tempList = new ArrayList<Fund>();
			try {
				tempList = srcList.subList(view.getFirstVisiblePosition(), view.getLastVisiblePosition() + 2);
			} catch (Exception e) {
				
			}
			
			for (Fund fund : tempList) {
				displayList.add(fund.getFund_code());
			}
			for (String str : displayList) {
//				Log.d("TAG", str);
			}
//			Log.d("TAG6", "displayList.size()" + displayList.size());
		}
	}
	
}
