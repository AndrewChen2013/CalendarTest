package com.lashou.MBusiness.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AttendanceList extends Entity {
	private MonthComparator comparator = new MonthComparator();
	private List<Attendance> list;

	public AttendanceList() {
		list = new ArrayList<Attendance>();
	}

	public List<Attendance> getList() {
		Collections.sort(this.list, comparator);
		return this.list;
	}

	public void setList(List<Attendance> list) {
		this.list = list;
	}

	public void add(Attendance attendance) {
		this.list.add(attendance);
		Collections.sort(this.list, comparator);
	}

	/**
	 * 比较类
	 * 
	 * @author chenbiao
	 *
	 */
	public class MonthComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			Attendance attendance0 = (Attendance) arg0;
			Attendance attendance1 = (Attendance) arg1;
			if ((attendance0.getYear() == attendance1.getYear())
					&& (attendance0.getMonth() == attendance1.getMonth())) {
				return 0;
			}
			if (attendance0.getYear() != attendance1.getYear()) {
				return attendance0.getYear() - attendance1.getYear();
			}

			return attendance0.getMonth() - attendance1.getMonth();

		}
	}
}
