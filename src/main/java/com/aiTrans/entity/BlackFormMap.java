package com.aiTrans.entity;

import com.aiTrans.annotation.TableSeg;
import com.aiTrans.util.FormMap;



/**
 * 黑名单实体表
 */
@TableSeg(tableName = "blackD", id="id")
public class BlackFormMap extends FormMap<String,Object>{

	private static final long serialVersionUID = 1L;

}
