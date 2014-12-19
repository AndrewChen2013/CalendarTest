package com.lashou.MBusiness.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
 
/**
 * 通知信息实体类
 * 
 * @author billchen
 * @version 1.0
 * @created 2014-12-8 11:01:50
 */
public class Notice implements Serializable {

	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "lashou";
	public final static String NODE_NOTICE_TYPE = "noticeType";

	public final static String TYPE_WORK_LIST = "workList";
	public final static String TYPE_WORK = "work";
	public final static String TYPE_APPROVE = "approve";

	private String noticeType;
	private Entity entity;

	public static Notice parse(InputStream inputStream) {
		return null;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	private static Entity getDetial(String result, String noticeType) {

		return null;

	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
