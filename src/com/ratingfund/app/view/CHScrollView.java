package com.ratingfund.app.view;



import com.ratingfund.app.activity.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class CHScrollView extends HorizontalScrollView {
	
	private ScrollViewListener scrollViewListener = null; 
	MainActivity activity;
//	boolean flag = false;
	public CHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		activity = (MainActivity) context;
		
	}

	
	public CHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MainActivity) context;
		
	}

	public CHScrollView(Context context) {
		super(context);
		activity = (MainActivity) context;
		
	}
//	public long lastClickTime = 0;
//	public boolean isFastDoubleClick() {
//		        long time = System.currentTimeMillis();
//		        long timeD = time - lastClickTime;
//		        if (timeD >= 0 && timeD <= 500) {
//			        return true;
//		    } else {
//			        lastClickTime = time;
//			        return false;
//		        }
//	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//进行触摸赋值
		
		activity.mTouchView = this;
//		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//		        if (isFastDoubleClick()) {
//			        return false;
//		        }
//	    }
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			
//			break;
//		case MotionEvent.ACTION_MOVE:
//			flag = true ;
//			break;
//		case MotionEvent.ACTION_UP:
//			getParent().requestDisallowInterceptTouchEvent(flag);
//			return flag;
//		case MotionEvent.ACTION_CANCEL:  
//			getParent().requestDisallowInterceptTouchEvent(false);  
//		default:
//			break;
//		}
//		if(ev.getAction() == MotionEvent.ACTION_MOVE){
//			  //do
//			  return super.onTouchEvent(ev);
//			}
		
//		if(ev.getAction()==MotionEvent.ACTION_DOWN){
//			Log.d("VIEW", "S_DOWN");
//			
//		}
//		if(ev.getAction()==MotionEvent.ACTION_MOVE){
//			Log.d("VIEW", "S_MOVE");
//			
//		}
//		if(ev.getAction()==MotionEvent.ACTION_UP){
//			Log.d("VIEW", "S_UP");
//			
//			
//		}
		return super.onTouchEvent(ev);
		
		
	}
	public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
        this.scrollViewListener = scrollViewListener;  
    }  
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//当当前的CHSCrollView被触摸时，滑动其它
		
		if(scrollViewListener != null&&activity.mTouchView == this) {
//			Log.d("TAG4", "TOUCH");
			 scrollViewListener.onScrollChanged(this, l, t, oldl, oldt); 
			 
			 
			if (activity.mHScrollViews.contains(this)) {
				if (l == 0) {
					activity.leftOk.setVisibility(View.GONE);
					activity.leftNo.setVisibility(View.VISIBLE);
				} else {
					activity.leftOk.setVisibility(View.VISIBLE);
					activity.leftNo.setVisibility(View.GONE);
				}
				if (l == this.getChildAt(0).getWidth() - this.getWidth()) {
					activity.rightOk.setVisibility(View.GONE);
					activity.rightNo.setVisibility(View.VISIBLE);
				} else {
					activity.rightOk.setVisibility(View.VISIBLE);
					activity.rightNo.setVisibility(View.GONE);
				}
			}
			if (activity.mHScrollViewsB.contains(this)) {
				if (l == 0) {
					activity.leftOkB.setVisibility(View.GONE);
					activity.leftNoB.setVisibility(View.VISIBLE);
				} else {
					activity.leftOkB.setVisibility(View.VISIBLE);
					activity.leftNoB.setVisibility(View.GONE);
				}
				if (l == this.getChildAt(0).getWidth() - this.getWidth()) {
					activity.rightOkB.setVisibility(View.GONE);
					activity.rightNoB.setVisibility(View.VISIBLE);
				} else {
					activity.rightOkB.setVisibility(View.VISIBLE);
					activity.rightNoB.setVisibility(View.GONE);
				}
			}
		} else {
			// Log.d("TAG4", "TOUCH2");
			super.onScrollChanged(l, t, oldl, oldt);
		}
		
	}
	
}
