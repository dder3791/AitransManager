package com.aiTrans.entity;

import com.aiTrans.annotation.TableSeg;
import com.aiTrans.util.FormMap;

/**
 *平台交易记录
 * @author vampire
 *时间：2017 10 19 下午 13：08
 */
@TableSeg(tableName = "tradingRecordD", id="id")
public class TradingRecordFormMap extends FormMap<String,Object>{
  
	private static final long serialVersionUID = 1L;
}
