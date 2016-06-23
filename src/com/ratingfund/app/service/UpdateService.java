package com.ratingfund.app.service;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import com.ratingfund.app.R;
import com.ratingfund.app.db.RatingFundDB;
import com.ratingfund.app.model.FundB;
import com.ratingfund.app.util.HttpCallbackListener;
import com.ratingfund.app.util.HttpUtil;
import com.ratingfund.app.util.PraseResponse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat.MediaItem.Flags;
import android.util.Log;



public class UpdateService extends Service{
	boolean updateJjjz = true;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		boolean flag = pref.getBoolean("InitDB", true);
		if(flag){
			InitDateBase();
		
			SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
			editor.putBoolean("InitDB", false);
			editor.commit();
//			
//			updateIndex();
//			updateHq();
		}
		if(updateJjjz){
			updateJjjz("a.txt");
			updateJjjz("b.txt");
			updateJjjz("m.txt");
			
			
		}
//		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//		long date = System.currentTimeMillis();  
//		String str_date= format.format(date).substring(11,13);
//		Integer hours = Integer.parseInt(str_date);
//		if(hours>8&&hours<17){			
//			updateIndex();
//			updateHq();
//		}
		updateIndex();
		updateHq();
		updateJjjz = false;
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int time = pref.getInt("refreshTime", 5000);
		long triggerAtTime = System.currentTimeMillis()+time;
		
		Intent i = new Intent("BroadcastAction");
		
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.set(AlarmManager.RTC, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
		
	}
	public void updateHq(){

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getResources().getAssets().open("a.txt")));
			String temp;
			StringBuilder str = new StringBuilder();
			str.append(getString(R.string.sina_interface));
			while ((temp=br.readLine())!=null) {
				if(temp.substring(0, 1).equals("1")){
					str.append("sz").append(temp).append(",");
				}else {
					str.append("sh").append(temp).append(",");
				}
			}
			br.close();
//			
			HttpUtil.sendHttpRequest(str.toString(), new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					
					PraseResponse.handleFundAResponse(RatingFundDB.getInstacnce(getApplicationContext()), response);
//					
				}
				
				@Override
				public void onError(Exception e) {
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("b.txt")));
			String temp;
			StringBuilder str = new StringBuilder();
			str.append("http://hq.sinajs.cn/list=");
			while ((temp=br.readLine())!=null) {
				if(temp.substring(0, 1).equals("1")){
					str.append("sz").append(temp).append(",");
				}else {
					str.append("sh").append(temp).append(",");
				}
			}
			br.close();

			HttpUtil.sendHttpRequest(str.toString(), new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
//					
					 PraseResponse.handleFundBResponse(RatingFundDB.getInstacnce(getApplicationContext()), response);
//				
				}
				
				@Override
				public void onError(Exception e) {
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public synchronized void updateJjjz(final String file) {
//		Log.d("TAG5", "updateJjjz");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getResources().getAssets().open(file)));
			String temp;
			StringBuilder str = new StringBuilder();
			str.append(getString(R.string.sina_interface));
			while ((temp=br.readLine())!=null) {
				str.append("f_").append(temp).append(",");
			}
			br.close();
//			Log.d("TAG5", str.toString());
			HttpUtil.sendHttpRequest(str.toString(), new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					String category = file.substring(0, 1);
					PraseResponse.handleFundJjjz(RatingFundDB.getInstacnce(getApplicationContext()), response, category);
				}
				
				@Override
				public void onError(Exception e) {
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	}
	public void updateIndex(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getResources().getAssets().open("index.txt")));
			String temp;
			StringBuilder str = new StringBuilder();
			str.append(getString(R.string.sina_interface));
			while ((temp=br.readLine())!=null) {
				if(temp.substring(0, 1).equals("3")){
					str.append("sz").append(temp).append(",");
				}else if(temp.substring(0, 1).equals("0")){
					str.append("sh").append(temp).append(",");
				}else if(temp.substring(0, 1).equals("H")){
					str.append("rt_hk").append(temp).append(",");
				}
			}
			br.close();
//			
			HttpUtil.sendHttpRequest(str.toString(), new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					
					PraseResponse.handleIndex(RatingFundDB.getInstacnce(getApplicationContext()), response,"FundA");
//					
				}
				
				@Override
				public void onError(Exception e) {
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	public void InitDateBase(){
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(getResources().getAssets().open("table.txt"), "GBK"));
			String temp;
			String v[] = new String[11];
			while ((temp = br.readLine()) != null) {
				v = temp.split("\t");
				ContentValues values = new ContentValues();
				values.put("fund_code", v[0]);
				values.put("hy_index", v[1]);
				values.put("intrest", v[2]);
				values.put("intrest_rule", v[3]);
				values.put("intrest_term", v[4]);
				values.put("fund_b_code", v[5]);
				values.put("fund_m_code", v[6]);
				values.put("ratio", v[7]);
				values.put("purchase_cost", v[8]);
				values.put("redeem_cost", v[9]);
				values.put("fund_m_name", v[10]);
				RatingFundDB.getInstacnce(getApplicationContext()).db.insert("FundA", null, values);
			}
			br.close();
			
			BufferedReader br2 = new BufferedReader(
					new InputStreamReader(getResources().getAssets().open("filter.txt"), "GBK"));
			String temp2;
			String v2[] = new String[10];
			while ((temp2 = br2.readLine()) != null) {
				v2 = temp2.split("\t");
				ContentValues values = new ContentValues();
				values.put("rule", v2[0]);
				values.put("rule_value", v2[1]);
				RatingFundDB.getInstacnce(getApplicationContext()).db.insert("Filter", null, values);
			}
			br2.close();
//			br = new BufferedReader(new InputStreamReader(getResources().getAssets().open("index.txt")));
//			Integer i = Integer.valueOf(1);
//			while ((temp=br.readLine())!=null) {
//				ContentValues values = new ContentValues();
//				values.put("index", temp);
//				RatingFundDB.getInstacnce(getApplicationContext()).db.update("FundA", values, "id=?", new String[]{i.toString()});
//				i++;
//			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
