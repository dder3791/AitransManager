package com.aiTrans.entity;

import com.aiTrans.annotation.TableSeg;
import com.aiTrans.util.FormMap;



/**
 * Customer客户实体表
 */
@TableSeg(tableName = "customerD", id="id")
public class CustomerFormMap extends FormMap<String,Object>{

	private static final long serialVersionUID = 1L;

}
