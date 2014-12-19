package com.lashou.MBusiness.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.calendartest.R;

/**
 * 类名 BaseSwipeRefreshFragment.java</br> 创建日期 2014-11-10 10:26:31</br>
 * 
 * @author billchen</br> Email chenbiao@lashou-inc.com</br> 更新时间 2014-11-10
 *         10:26:55</br> 最后更新者 BillChen</br>
 * 
 *         说明 下拉刷新界面的基类
 */
public abstract class CalendarDayView extends LinearLayout {

	public CalendarDayView(Context context) {
		super(context);

	}

	public CalendarDayView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.calendar_day,
				this, true);
	}

}