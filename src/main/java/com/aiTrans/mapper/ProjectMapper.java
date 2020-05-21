package com.aiTrans.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aiTrans.entity.ProcedureTypeFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ProjectMapper extends BaseMapper{
	
	
	/**
	 * 查看项目的所有信息
	 * @param map
	 * @return Map
	 */
	public Map findAll(Map map);
	
	

	/**
	 * 查看我的任务
	 * @param transferFormMap
	 * @return
	 */
	public List<ProjectFormMap> findMyProjectPage(ProjectFormMap projectFormMap);
	
	/**添加客户的项目
	 * 
	 * @param projectFormMap
	 * @return
	 */
	public List<ProjectFormMap> addCustomerProject(ProjectFormMap projectFormMap);
	
	/**通过项目id查找项目
	 * 
	 * @param projectFormMap
	 * @return
	 */
	public List<ProjectFormMap> findProjectById(ProjectFormMap projectFormMap);
//	public List<ProjectFormMap> findProjectById(@Param("id")String id,@Param("table")String table,@Param("fk")String fk);
	
	/**
	 * 查看客户的协同项目
	 * @param projectFormMap
	 * @return
	 */
	public List<ProjectFormMap> findCustomerProjectPage(ProjectFormMap projectFormMap);
	
	public List<ProjectTypeFormMap> findProjectTypePage(ProjectTypeFormMap projecttypeFormMap);
	
	public List<ProcedureTypeFormMap> findProcessesTypePage(ProcedureTypeFormMap processesTypeFormMap);

	/**分页展示所有未完成项目（项目流程）
	 * 
	 * @param projectFormMap
	 * @return
	 */
	public List<ProjectFormMap> findProjectflowPage(ProjectFormMap projectFormMap);
	
	/**通过名字查找，完成模糊查询 
	 * 
	 * @param projectFormMap
	 * @return
	 */
	public  List<ProjectFormMap> findByName(ProjectFormMap projectFormMap);
	
	public ProjectFormMap lookProject(int id);
	
	/**	通过公司案号reference 查找项目id
	 * 
	 * @param projectFormMap
	 * @return
	 */
	public List<ProjectFormMap> findByreference(ProjectFormMap projectFormMap);

	/**	通过客户id查找该客户对应的项目个数
	 * 
	 * @param int
	 * @return
	 */
	public int findProjectCountByCustomerId(String id);

	/**
	 * 接受项目
	 * @param 任务id
	 */
	public void accept(String projectId);


	/**
	 * 完成项目
	 * @param projectId
	 */
	public void complete(String projectId);


	/**
	 * 通过客户案号customerreference 查找项目id
	 * @param projectD.id
	 */
	public List<ProjectFormMap> findBycustomerReference(ProjectFormMap projectFormMap);


	/**
	 * 分页展示所有未启动项目
	 * @param projectD.id
	 */
	public List<ProjectFormMap> findProjectVerifyPage(ProjectFormMap projectFormMap);
	
	/**
	 * 根据proejctId查找项目案号
	 * @param projectId
	 * @return
	 */
	public String findReference(String projectId);
	
	
	/**
	 * 根据项目id查找项目
	 * @param id
	 * @return
	 */
	public String findProject(ProjectFormMap projectFormMap);
	
	/**
	 * 根据id查找项目名称
	 * @param name
	 * @return
	 */
	public String findProjectName(String name);
}
