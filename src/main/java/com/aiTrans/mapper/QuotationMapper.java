package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.QuotationFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface QuotationMapper extends BaseMapper{

	public void editToTrans(QuotationFormMap quotationFormMap);
	
	public List<TranslatorFormMap> queryByTransId(String id);
	
	public List<QuotationFormMap> queryByDomLanLev(QuotationFormMap quotationFormMap);
	
	public int insertPrice(QuotationFormMap quotationFormMap);
	
	public int delPrice(String [] ids);
	
	public int updatePrice(QuotationFormMap quotationFormMap);
	
	public int insert(QuotationFormMap quotationFormMap);
	
	public int update(QuotationFormMap quotationFormMap);
	
	
	
}
