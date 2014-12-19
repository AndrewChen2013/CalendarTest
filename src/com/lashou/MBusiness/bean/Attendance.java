package com.lashou.MBusiness.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class Attendance extends Entity {

	public static final String TYPE = "ATTENDANCE";

	public static final String NODE_MONTH = "month";
	public static final String NODE_ATTENDANCE = "attendance";
	public static final String NODE_DAYS = "days";
	public static final String NODE_DAY = "day";
	public static final String NODE_DATE = "date";
	public static final String NODE_HOLIDAY = "holiday";
	public static final String NODE_SIGN_IN = "sign_in";
	public static final String NODE_SIGN_OUT = "sign_out";
	public static final String NODE_DAY_TYPE = "day_type";

	/*
	 * 含年的月份2014-08
	 */
	private int month;
	private int year;
	private List<DayAttendance> dayAttendance;

	/*
	 * 
	 */
	public Attendance() {
		super();
		dayAttendance = new ArrayList<DayAttendance>();
	}

	public List<DayAttendance> getDayAttendance() {
		return dayAttendance;
	}

	public void setDayAttendance(List<DayAttendance> dayAttendance) {
		this.dayAttendance = dayAttendance;
	}

	public static Attendance parse(InputStream inputStream) {

		return null;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
