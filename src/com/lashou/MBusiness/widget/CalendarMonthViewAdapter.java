package com.lashou.MBusiness.widget;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calendartest.R;
import com.lashou.MBusiness.bean.DayAttendance;

public class CalendarMonthViewAdapter extends BaseAdapter {
	private static List<DayAttendance> dayList;// 数据集合
	// private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	private Context context;// 运行上下文

	private CalendarMonthViewAdapter() {
	}

	public CalendarMonthViewAdapter(Context context, List<DayAttendance> data,
			int resource) {
		// TODO Auto-generated constructor stub
		this.context = context;
		// this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.itemViewResource = resource;

		this.dayList = data;
	}

	@Override
	public int getCount() {
		return dayList.size();
	}

	@Override
	public DayAttendance getItem(int position) {
		return dayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GrideViewHolder holder;
		if (convertView == null) {
			holder = new GrideViewHolder();
			// convertView = listContainer.inflate(this.itemViewResource, null);

			convertView = parent.inflate(parent.getContext(),
					this.itemViewResource, null);
			holder.date = (TextView) convertView
					.findViewById(R.id.tv_calendar_day);
			holder.holiday = (TextView) convertView
					.findViewById(R.id.tv_calendar_holiday);
			holder.sign = (ImageView) convertView
					.findViewById(R.id.tv_calendar_sign);
			holder.background = (LinearLayout) convertView
					.findViewById(R.id.tv_calendar_background);
			convertView.setTag(holder);
		} else {
			holder = (GrideViewHolder) convertView.getTag();
		}
		holder.date.setText(getItem(position).getDate() + "");
		holder.holiday.setText(getItem(position).getHoliday());
		if (getItem(position).isThisMonth()) {
			if ((getItem(position).getSignIn() == null
					|| getItem(position).getSignIn().isEmpty()
					|| getItem(position).getSignOut().isEmpty() || getItem(
					position).getSignOut() == null)
					&& (getItem(position).getHoliday() == null || getItem(
							position).getHoliday().isEmpty())) {
				holder.sign.setBackgroundResource(R.drawable.overlay_error);
			} else {
				holder.sign.setBackgroundResource(R.drawable.overlay_ok);
			}
			convertView.setClickable(true);
			holder.background.setBackgroundColor(convertView.getResources()
					.getColor(R.color.white));
		} else {
			convertView.setClickable(false);
			holder.sign.setBackgroundResource(R.drawable.overlay_empty);
			holder.background.setBackgroundColor(convertView.getResources()
					.getColor(R.color.gray));
		}
		if (getItem(position).isSelectFlag()&&getItem(position).isThisMonth()) {
			holder.background.setBackgroundColor(convertView.getResources()
					.getColor(R.color.yellow));
		}

		convertView.setClickable(false);
		return convertView;
	}

	static class GrideViewHolder {

		TextView date;
		TextView holiday;
		ImageView sign;
		LinearLayout background;
	}
}