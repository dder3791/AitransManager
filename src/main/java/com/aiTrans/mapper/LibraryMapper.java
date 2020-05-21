package com.aiTrans.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiTrans.entity.LibraryFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface LibraryMapper extends BaseMapper{

	
	/**
	 * 2017年4月21日11:23:40
	 * @param libraryFormMap
	 * @return List<LibraryFormMap>
	 */
//	public List<LibraryFormMap> findFirstPage(LibraryFormMap libraryFormMap,@Param("language")String language);
	public List<LibraryFormMap> findFirstPage(LibraryFormMap libraryFormMap);
	
	public List<LibraryFormMap> findENPage(LibraryFormMap libraryFormMap);
	
	public List<LibraryFormMap> findJPPage(LibraryFormMap libraryFormMap);
	
	public List<LibraryFormMap> findKORPage(LibraryFormMap libraryFormMap);
	
	public List<LibraryFormMap> findGERPage(LibraryFormMap libraryFormMap);
	
	public List<LibraryFormMap> findFRPage(LibraryFormMap libraryFormMap);

	
	public List<LibraryFormMap> findENFirstPage(LibraryFormMap libraryFormMap);
	public List<LibraryFormMap> findJPFirstPage(LibraryFormMap libraryFormMap);
	public List<LibraryFormMap> findKORFirstPage(LibraryFormMap libraryFormMap);
	public List<LibraryFormMap> findGERFirstPage(LibraryFormMap libraryFormMap);
	public List<LibraryFormMap> findFRFirstPage(LibraryFormMap libraryFormMap);

	
	
	
	
}
