package com.lashou.MBusiness.bean;

import java.util.List;

/**
 * 类名 PageList.java</br>
 * 创建日期 2014-11-10 10:29:23</br>
 * @author bilchen</br>
 * Email chenbiao@lashou-inc.com</br>
 * 更新时间 2014-11-10 10:29:43</br>
 * 最后更新者 billchen</br>
 * 
 * 说明 类的描述
 */
public interface PageList<T> {

	public int getPageSize();
	public int getCount();
	public List<T> getList();
	public Notice getNotice();
}
