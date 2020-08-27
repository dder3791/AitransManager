package com.aiTrans.mapper;

import java.util.List;
import java.util.Map;

import com.aiTrans.entity.ClientCloudFormMap;
import com.aiTrans.entity.ClientCloudLogFormMap;
import com.aiTrans.entity.ClientCloudVersionFormMap;
import com.aiTrans.entity.ClientSoftInfoFormMap;
import com.aiTrans.entity.ClientUploadDataFormMap;
import com.aiTrans.mapper.base.BaseMapper;
import com.aiTrans.vo.CloudVersion;

public interface ClientUploadDataMapper extends BaseMapper{
	/**
	 * 客户端云翻译数据查询
	 * @return
	 */
	public List<ClientUploadDataFormMap> findClientUploadData();
	/**
	 * 保存客户端云翻译数据
	 * @return
	 */
	public int insertClientUploadData(ClientUploadDataFormMap params);
	/**
	 * 返回客户端软件信息（最新）
	 * @return
	 */
	public ClientSoftInfoFormMap findClientUploadInfo(ClientSoftInfoFormMap params);
	/**
	 * 返回云翻译模块信息（按客户端软件ID）
	 * @return
	 */
	public List<ClientCloudFormMap> findCloudInfo(Integer params);
	/**
	 * @param
	 * ClientSoftInfoFormMap params
	 * softName 客户端软件名称
	 * softIp  客户端软件IP
	 * userName 用户
	 * 返回客户端软件信息
	 * @return ClientSoftInfoFormMap
	 */
	public ClientSoftInfoFormMap findClientInfo(ClientSoftInfoFormMap params);
	/**
	 * 返回云翻译模块信息
	 * @param params
	 * @return
	 */
	public ClientCloudFormMap findCloudSingle(ClientCloudFormMap params);
	/**
	 * 保存云翻译模块信息
	 * @param params
	 * @return
	 */
	public int insertCloudData(ClientCloudFormMap params);
	/**
	 * 返回云翻译模块信息（最新版本）
	 * @param params
	 * @return
	 */
	public ClientCloudFormMap findCloudNew(ClientCloudFormMap params);
	/**
	 * 返回云翻译模块的历史版本信息
	 * @param params
	 * @return
	 */
	public List<CloudVersion> findCloudVersion(Map<String,Object> params);
	/**
	 * 保存云翻译模块的历史版本信息
	 * @param params
	 * @return
	 */
	public int insertCloudVersion(ClientCloudVersionFormMap params);
	/**
	 * 保存云翻译模块操作日志
	 * @param params
	 * @return
	 */
	public int insertCloudLog(ClientCloudLogFormMap params);
	/**
	 * 查询云翻译模块操作日志
	 * @param params
	 * @return
	 */
	public List<ClientCloudLogFormMap> findCloudLog(ClientCloudLogFormMap params);
	/**
	 * 更新云翻译模块操作日志
	 * @param params
	 * @return
	 */
	public int updateCloudLog(ClientCloudLogFormMap params);
	/**
	 * 查询云翻译模块操作日志(返回单条数据)
	 * @param params
	 * @return
	 */
	public ClientCloudLogFormMap getCloudLog(ClientCloudLogFormMap params);
	/**
	 * 查询所有云翻译模块信息
	 * @param params
	 * @return
	 */
	public List<ClientCloudFormMap> findCloudInfos(ClientCloudFormMap params);
	/**
	 * 更新云翻译模块数据
	 * @param params
	 * @return
	 */
	public ClientCloudLogFormMap updateCloudData(Map<String,Object> params);
	/**
	 * 客户端软件用户认证
	 * @param params
	 * @return
	 */
	public Integer getTransInfo(Map<String,Object> params);
	

}
