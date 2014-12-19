package com.lashou.MBusiness.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.calendartest.R;
import com.lashou.MBusiness.bean.DayAttendance;

/**
 * 类名 BaseSwipeRefreshFragment.java</br> 创建日期 2014-11-10 10:26:31</br>
 * 
 * @author billchen</br> Email chenbiao@lashou-inc.com</br> 更新时间 2014-11-10
 *         10:26:55</br> 最后更新者 BillChen</br>
 * 
 *         说明 下拉刷新界面的基类
 */
public class CalendarMonthView extends LinearLayout implements
		OnItemClickListener {
	private GridView month;
	private CalendarMonthViewAdapter gridViewAdapter;
	private Context context;
	private OnTouchListener onMonthTouchListener;
	private OnDaySelectListener onDaySelectListener;

	public CalendarMonthView(Context context) {
		super(context);
		this.context = context;

	}

	public CalendarMonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init(List<DayAttendance> data) {

		gridViewAdapter = new CalendarMonthViewAdapter(context, data,
				R.layout.calendar_day);

		View view = LayoutInflater.from(context).inflate(
				R.layout.calendar_month, this, true);

		month = (GridView) findViewById(R.id.calendar_month);

		month.setAdapter(gridViewAdapter);
		Log.v("com.calendar", "setOnItemClickListener ！");
		month.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.v("com.calendar", "GridView been clicked ！");
				if (!gridViewAdapter.getItem(position).isThisMonth())
					return;

				for (int i = 0; i < gridViewAdapter.getCount(); i++) {
					gridViewAdapter.getItem(i).setSelectFlag(false);

				}
				gridViewAdapter.getItem(position).setSelectFlag(true);
				gridViewAdapter.notifyDataSetChanged();
				if (onDaySelectListener != null) {
					onDaySelectListener.onDaySelect(gridViewAdapter
							.getItem(position));
				}
			}

		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	public OnTouchListener getOnMonthTouchListener() {
		Log.v("com.calendar", "Grid View OnTouchListener !!!");
		return onMonthTouchListener;
	}

	public void setOnMonthTouchListener(OnTouchListener onMonthTouchListener) {
		this.onMonthTouchListener = onMonthTouchListener;
		month.setOnTouchListener(onMonthTouchListener);
	}

	/**
	 * 当日历控件上的莫一天被选中的时候
	 * 
	 * @author chenbiao
	 *
	 */
	public interface OnDaySelectListener {
		public void onDaySelect(DayAttendance dayAttendance);
	}

	public OnDaySelectListener getOnDaySelectListener() {

		return onDaySelectListener;
	}

	public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
		this.onDaySelectListener = onDaySelectListener;
	}
}