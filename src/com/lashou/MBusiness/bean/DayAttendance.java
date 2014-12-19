package com.lashou.MBusiness.bean;

public class DayAttendance {
	int date;
	String holiday;
	String dayType;
	String signIn;
	String signOut;
	boolean isThisMonth;
	boolean selectFlag;
	

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getSignIn() {
		return signIn;
	}

	public void setSignIn(String signIn) {
		this.signIn = signIn;
	}

	public String getSignOut() {
		return signOut;
	}

	public void setSignOut(String signOut) {
		this.signOut = signOut;
	}

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

	public boolean isThisMonth() {
		return isThisMonth;
	}

	public void setThisMonth(boolean isThisMonth) {
		this.isThisMonth = isThisMonth;
	}

	public boolean isSelectFlag() {
		return selectFlag;
	}

	public void setSelectFlag(boolean selectFlag) {
		this.selectFlag = selectFlag;
	}
}
