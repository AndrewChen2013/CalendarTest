package com.lashou.MBusiness.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.example.calendartest.R;
import com.lashou.MBusiness.bean.Attendance;
import com.lashou.MBusiness.bean.AttendanceList;
import com.lashou.MBusiness.bean.DayAttendance;
import com.lashou.MBusiness.bean.Entity;
import com.lashou.MBusiness.bean.PageList;
import com.lashou.MBusiness.widget.CalendarMonthView.OnDaySelectListener;

/**
 * 类名 BaseSwipeRefreshFragment.java</br> 创建日期 2014-11-10 10:26:31</br>
 * 
 * @author billchen</br> Email chenbiao@lashou-inc.com</br> 更新时间 2014-11-10
 *         10:26:55</br> 最后更新者 BillChen</br>
 * 
 *         说明 下拉刷新界面的基类
 */
@SuppressLint("NewApi")
public class CalendarView<Data extends Entity, Result extends PageList<Data>>
		extends LinearLayout implements OnItemClickListener, OnGestureListener,
		OnTouchListener {
	private DataLoader loader;
	private ViewFlipper calendarMonths;
	private GestureDetector gestureDetector;
	private Date currentDate;
	private Context context;
	private Calendar calendar = Calendar.getInstance();// 获取日历实例
	private OnDaySelectListener onDaySelectListener;
	private TextView yearTV;
	private TextView monthTV;

	private AttendanceList list = new AttendanceList();

	public CalendarView(Context context) {
		super(context);
		init(context);
		this.context = context;
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		this.context = context;
	}

	private void init(Context context) {
		currentDate = new Date();
		gestureDetector = new GestureDetector(context, this);

		calendar.setTime(currentDate);
		calendar.set(Calendar.MONTH, 10);
		currentDate = calendar.getTime();

		View view = LayoutInflater.from(context).inflate(
				R.layout.calendar_view, this, true);
		calendarMonths = (ViewFlipper) findViewById(R.id.calendar_view_flipper);
		yearTV = (TextView) findViewById(R.id.calendar_year);
		monthTV = (TextView) findViewById(R.id.calendar_month);

	}

	public void show() {
		Attendance data = loadData(currentDate);
		list.add(data);
		// data.setMonth("201401");
		// /list.add(data);

		CalendarMonthView cv = new CalendarMonthView(context);
		Log.v("com.calendar", "cv.init(data.getDayAttendance()) ！");
		cv.init(data.getDayAttendance());
		cv.setOnMonthTouchListener(this);
		cv.setOnDaySelectListener(onDaySelectListener);
		
		cv.setOverScrollMode(View.OVER_SCROLL_NEVER) ;
		calendarMonths.addView(cv, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}

	/*
	 * 加载数据,并组装入新的数据
	 */
	private Attendance loadData(Date date) {
		Attendance ald = null;
		if (loader != null) {
			ald = loader.loadDate(currentDate);
		}
		Attendance alc = initOneMonth(currentDate);
		return mergeAttendance(ald, alc);

	}

	/**
	 * 
	 * @param ald
	 *            有出勤数据的,可能为 null
	 * @param alc
	 *            只有日历数据的
	 * @return
	 */
	private Attendance mergeAttendance(Attendance ald, Attendance alc) {
		if (ald == null)
			return alc;

		for (int i = 0; i < ald.getDayAttendance().size(); i++) {
			for (int j = 0; j < alc.getDayAttendance().size(); j++) {
				if ((ald.getDayAttendance().get(i).getDate() == alc
						.getDayAttendance().get(j).getDate())
						&& alc.getDayAttendance().get(j).isThisMonth()) {
					alc.getDayAttendance()
							.get(j)
							.setDayType(
									ald.getDayAttendance().get(i).getDayType());
					alc.getDayAttendance()
							.get(j)
							.setHoliday(
									ald.getDayAttendance().get(i).getHoliday());
					alc.getDayAttendance()
							.get(j)
							.setSignIn(
									ald.getDayAttendance().get(i).getSignIn());
					alc.getDayAttendance()
							.get(j)
							.setSignOut(
									ald.getDayAttendance().get(i).getSignOut());
					alc.getDayAttendance()
							.get(j)
							.setSelectFlag(
									ald.getDayAttendance().get(i)
											.isSelectFlag());
				}
			}
		}
		return alc;
	}

	/**
	 * 初始化一个月的日历数据
	 * 
	 * @param date
	 * @return
	 */
	private Attendance initOneMonth(Date date) {
		Attendance attendance = new Attendance();

		List<DayAttendance> list = new ArrayList<DayAttendance>();
		calendar.setTime(currentDate);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		attendance.setMonth(month);

		attendance.setYear(year);

		calendar.set(Calendar.DATE, 1);
		int endDayofNextMonth = 31;

		if (calendar.get(Calendar.MONTH) % 2 == 1) {
			endDayofNextMonth = 30;
		} else {
			endDayofNextMonth = 31;
		}

		// 添加上一月的空白数据
		for (int i = (countNeedHowMuchEmpety(calendar) - 1); i >= 0; i--) {
			DayAttendance day = new DayAttendance();
			day.setDate(endDayofNextMonth - i);
			day.setThisMonth(false);
			day.setSelectFlag(false);
			list.add(day);
		}
		// 添加本月的数据
		for (int i = 1; i <= (30 + (calendar.get(Calendar.MONTH) % 2)); i++) {
			DayAttendance day = new DayAttendance();
			day.setDate(i);
			day.setThisMonth(true);
			day.setSelectFlag(false);

			list.add(day);
		}
		// 添加下一月的空白数据

		int count = (7 - (list.size() % 7));
		if ((list.size() % 7) == 0) {
			count = 0;
		}

		for (int i = 1; i <= count; i++) {

			DayAttendance day = new DayAttendance();
			day.setDate(i);
			day.setThisMonth(false);
			day.setSelectFlag(false);
			list.add(day);
		}
		attendance.setDayAttendance(list);
		return attendance;
	}

	/**
	 * 获取当前月第一天在第一个礼拜的第几天，得出第一天是星期几
	 * 
	 * @param cal
	 * @return
	 */
	private int countNeedHowMuchEmpety(Calendar cal) {

		cal.setTime(currentDate);
		cal.set(Calendar.DATE, 1);
		int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK);

		return firstDayInWeek - 1;
	}

	/*
	 * 加载相对当前未来的时间
	 */
	private void onLoadBeforeMonth() {
		calendar.setTime(currentDate);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		int nextYear = year;
		int nextMonth;
		if (month == 0) {
			nextMonth = 11;
			nextYear = year - 1;
		} else {
			nextMonth = month - 1;
			nextYear = year;
		}

		calendar.set(nextYear, nextMonth, 1);

		// 更新当前时间
		currentDate = calendar.getTime();

		Attendance a = loadData(currentDate);
		monthTV.setText(a.getMonth() + "");
		yearTV.setText(a.getYear() + "");

		CalendarMonthView cv = new CalendarMonthView(context);
		cv.init(a.getDayAttendance());
		cv.setOnMonthTouchListener(this);
		cv.setOnDaySelectListener(onDaySelectListener);
		calendarMonths.addView(cv, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

	}

	/*
	 * 加载相对当前已过的下一个月的数据
	 */
	private void onLoadAfterMonth() {
		calendar.setTime(currentDate);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		int nextYear = year;
		int nextMonth;
		if (month == 11) {
			nextMonth = 0;
			nextYear = year + 1;
		} else {
			nextMonth = month + 1;
			nextYear = year;
		}

		calendar.set(nextYear, nextMonth, 1);

		// 更新当前时间
		currentDate = calendar.getTime();
		Attendance a = loadData(currentDate);
		CalendarMonthView cv = new CalendarMonthView(context);
		cv.init(a.getDayAttendance());
		cv.setOnMonthTouchListener(this);
		cv.setOnDaySelectListener(onDaySelectListener);
		monthTV.setText(a.getMonth() + "");
		yearTV.setText(a.getYear() + "");
		calendarMonths.addView(cv, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	/*
	 * 数据加载器
	 */
	public interface DataLoader {
		public Attendance loadDate(Date date);

	}

	public DataLoader getLoader() {
		return loader;
	}

	public void setLoader(DataLoader loader) {
		this.loader = loader;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		if (arg0 == null)
			return false;
		Log.v("com.calendar",
				" is coming onFling!!!  " + (arg0.getY() - arg1.getY()));
		// 对手指滑动的距离进行了计算，如果滑动距离大于120像素，就做切换动作，否则不做任何切换动作。
		// 从左向右滑动
		if (arg0.getY() - arg1.getY() > 120) {
			Log.v("com.calendar", " from down to up  onFling!!!");
			onLoadAfterMonth();
			// 添加动画
			this.calendarMonths.setInAnimation(AnimationUtils.loadAnimation(
					context, R.anim.push_left_in));
			this.calendarMonths.setOutAnimation(AnimationUtils.loadAnimation(
					context, R.anim.push_left_out));
			this.calendarMonths.showNext();
			return true;
		}// 从右向左滑动
		else if (arg0.getY() - arg1.getY() < -120) {
			Log.v("com.calendar", " from up to down  onFling!!!");
			onLoadBeforeMonth();
			this.calendarMonths.setInAnimation(AnimationUtils.loadAnimation(
					context, R.anim.push_right_in));
			this.calendarMonths.setOutAnimation(AnimationUtils.loadAnimation(
					context, R.anim.push_right_out));
			this.calendarMonths.showPrevious();
			return true;
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		gestureDetector.onTouchEvent(event);
		return false;

	}

	public OnDaySelectListener getOnDaySelectListener() {
		return onDaySelectListener;
	}

	public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
		this.onDaySelectListener = onDaySelectListener;
	}

}