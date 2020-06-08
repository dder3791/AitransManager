package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ClientSoftInfoFormMap;
import com.aiTrans.entity.ClientUploadDataFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface ClientUploadDataMapper extends BaseMapper{
	public List<ClientUploadDataFormMap> findClientUploadData();
	public int insertClientUploadData(ClientUploadDataFormMap params);
	public ClientSoftInfoFormMap findClientUploadInfo(ClientSoftInfoFormMap params);

}
