package com.lashou.MBusiness.bean;

import java.io.Serializable;

/**
 * 实体基类：实现序列化
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public abstract class Base implements Serializable {

	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "lashou";
	
	/**
	 * 交互的通信的密钥
	 * 每次通信都将key存储在本地，下次通信的时候去取出通讯key
	 */
	public String key;
	
	protected Notice notice;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
