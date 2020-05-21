package com.aiTrans.entity;

import com.aiTrans.annotation.TableSeg;
import com.aiTrans.util.FormMap;



/**
 * user实体表
 */
//2017年4月27日10:40:48 去掉ly_
@TableSeg(tableName = "userD", id="id")
//@TableSeg(tableName = "ly_user", id="id")
public class UserFormMap extends FormMap<String,Object>{

	/**
	 *@descript
	 *@author aiTrans
	 *@date 2015年3月29日
	 *@version 1.0
	 */
	private static final long serialVersionUID = 1L;

}
