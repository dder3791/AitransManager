package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AdvertiseListFormMap;
import com.aiTrans.entity.AdvertisementFormMap;
import com.aiTrans.entity.PicAdvertiseFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface AdvertisementMapper extends BaseMapper{
	
	/**
	 * 分页查询客户Index
	 * @param evaluateClientFormMap
	 * @return
	 */
	public List<AdvertisementFormMap> findAdvertiIndexPage(AdvertisementFormMap advertisementFormMap);
	
	
	/**
	 * 分页查询客户广告
	 * @param evaluateClientFormMap
	 * @return
	 */
	public List<PicAdvertiseFormMap> findPicadvertisePage(PicAdvertiseFormMap advertisementFormMap);
	
	
	/**
	 * 分页展示广告列表
	 * @param advertiseListFormMap
	 * @return
	 */
	public List<AdvertiseListFormMap> findAdverListFormMapPage(AdvertiseListFormMap advertiseListFormMap);
	

}
