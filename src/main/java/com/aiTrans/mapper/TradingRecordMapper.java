package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.TradingRecordFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface TradingRecordMapper extends BaseMapper{
   
	public List<TradingRecordFormMap> findTradingRecordflowPage(TradingRecordFormMap tradingRecordFormMap);

}
