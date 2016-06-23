package com.ratingfund.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView{
	int flag = 0;
	float StartX,StartY,ScollX,ScollY;
	long t1,td;
	public MyListView(Context context) {
		super(context);

	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}
	
//	public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//		
//	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			Log.d("VIEW", "L_DOWN");
//			break;
//		case MotionEvent.ACTION_MOVE:
//			Log.d("VIEW", "L_MOVE");
//			break;
//		case MotionEvent.ACTION_UP:
//			Log.d("VIEW", "L_UP");
//			break;
//		default:
//			break;
//		}
		return super.onTouchEvent(ev);
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//总是调用listview的touch事件处理
		
		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			onTouchEvent(ev);
			t1 = System.currentTimeMillis();
			StartX = ev.getX();
			StartY = ev.getY();
			return false;
		}
		if(ev.getAction()==MotionEvent.ACTION_MOVE){
			ScollX = ev.getX()-StartX;
			ScollY = ev.getY()-StartY;
			//判断是横滑还是竖滑，竖滑的话拦截move事件和up事件（不拦截会由于listview和scrollview同时执行滑动卡顿）
			if(Math.abs(ScollX)<Math.abs(ScollY)){
				onTouchEvent(ev);
				flag = 1;
				return true;
			}
			return false;
		}
		if(ev.getAction()==MotionEvent.ACTION_UP){
			td = System.currentTimeMillis()-t1;
			if(td<100){
				onTouchEvent(ev);
				}
			if(flag==1){
				flag=0;
				return true;
			}
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
	

}
