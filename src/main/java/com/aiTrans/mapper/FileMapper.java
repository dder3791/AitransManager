package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.FileFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface FileMapper extends BaseMapper{

	/**
	 * 通过条件fileUrl和fileType查找数据id
	 * @param fileUrl
	 * @param fileType
	 * @return
	 */
	public List<FileFormMap> findParentId(String path);
	
	/**
	 * 查找fileUrl=url的个数
	 * @param url
	 * @return
	 */
	public List<FileFormMap> findFileUrl(String url); 
	
	/**
	 * 条件查找 path路径下所有的文件
	 * @param path
	 * @return
	 */
	public List<FileFormMap> findFile(String path);
	
	/**
	 * 重命名
	 * @param fileFormMap
	 */
	public void rename(FileFormMap fileFormMap);
	
	/**
	 * 删除文件夹
	 * @param id
	 */
	public void deleteDir(int id);
	
	/**
	 * 查找fileName为新建文件夹的记录数
	 * @param fileFormMap
	 * @return
	 */
	public int countByNPT(FileFormMap fileFormMap);
	
	/**
	 * 根据fileurl模糊查询file
	 * @param fileFormMap
	 * @return
	 */
	public List<FileFormMap> findLikeURL(FileFormMap fileFormMap);
	
	public void addFile(FileFormMap fileFormMap);
}
