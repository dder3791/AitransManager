package com.aiTrans.util;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class LocalUtil {
	
	/* public static void main(String[] args) {  
	        LocalUtil lu =  LocalUtil.getInstance();  
	        List<String> list =   lu.getCities("中国", "河北");  
	        List<String> list =   lu.getProvinces("中国"); 
	        List<String> list =  lu.getCountry();
	      
	        for(int i=0; i<list.size(); i++){  
	            System.out.print(list.get(i) + " ");  
	        }  
	    }  */
	
	 //各地区xml文件路径  
   /* private static final String LOCAL_LIST_PATH = "\\Aitrans\\src\\LocList.xml";  */
	
/*	//有session值的构造方法
	public LocalUtil(){
		
	}*/
		
    //所有国家名称List  
    private static final Map<String,String> COUNTRY_REGION = new HashMap<String,String>();  
    private static LocalUtil localutil;  
    private SAXReader reader;  
    private Document document;  
    private Element rootElement;        //根元素  
      
    //初始化  
    private LocalUtil(String language){  
    	String path=getClass().getClassLoader().getResource("LocList.xml").getPath();
    	if("".equals(language)||language==null||"undefined".equals(language)||"zh".equals(language)){
    		path=getClass().getClassLoader().getResource("LocList.xml").getPath();
		}else if("en".equals(language)){
			path=getClass().getClassLoader().getResource("LocListEN.xml").getPath();
		}
    	File file=new File(path);
    	
    	
    	
        //1.读取  
        reader = new SAXReader();  
        try {  
            document = reader.read(file);          
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }  
        //2.获得根元素  
        rootElement =  document.getRootElement();     
        //3.初始化所有国家名称列表  
        Iterator it =  rootElement.elementIterator();  
        Element ele = null;  
        while(it.hasNext()){  
            ele = (Element)it.next();  
           /* COUNTRY_REGION.add(ele.attributeValue("Code"));  */
            COUNTRY_REGION.put(ele.attributeValue("Code"), ele.attributeValue("Name"));
        }  
    }  
      
    /** 
     *  
     * @author      LiuJinan 
     * @TODO        功能： 获取所有国家名称 
     * @time        2016-8-26 上午9:02:05 
     * @return      List<String> 
     */  
    public Map<String,String> getCountry(){  
        return COUNTRY_REGION;  
    }  
      
    /** 
     *  
     * @author      LiuJinan 
     * @TODO        功能： 根据国家名获取该国所有省份 
     * @time        2016-8-26 上午9:07:21 
     * @param countryName   国家名，从getCountry()从取出 
     * @return      List<Element> 
     */  
    private List<Element> provinces(String countryName){  
        Iterator it =  rootElement.elementIterator();  
        List<Element> provinces = new ArrayList<Element>();  
        Element ele = null;  
        while(it.hasNext()){  
            ele = (Element)it.next();  
            COUNTRY_REGION.put(ele.attributeValue("Code"),ele.attributeValue("Name"));  
            if(ele.attributeValue("Code").equals(countryName)){  
                provinces = ele.elements();  
                break;  
            }  
        }  
        return provinces;  
    }  
      
    /** 
     *  
     * @author      LiuJinan 
     * @TODO        功能： 根据国家名获取该国所有省份 
     * @time        2016-8-26 上午9:07:21 
     * @param countryName   国家名，从getCountry()从取出 
     * @return      List<String> 
     */  
    public Map<String,String> getProvinces(String countryName){  
        List<Element> tmp = this.provinces(countryName);  
        Map<String,String> list = new HashMap<String,String>();  
        for(int i=0; i<tmp.size(); i++){  
            list.put(tmp.get(i).attributeValue("Code"),tmp.get(i).attributeValue("Name"));  
        }  
        return list;  
    }  
      
    /** 
     *  
     * @author      LiuJinan 
     * @TODO        功能：根据国家名和省份名，获取该省城市名列表 
     * @time        2016-8-26 上午9:15:24 
     * @param province 
     * @param provinceName 
     * @return 
     */  
    private List<Element> cities(String countryName, String provinceName){  
        List<Element> provinces =  this.provinces(countryName);  
        List<Element> cities = new ArrayList<Element>();  
        if(provinces==null || provinces.size()==0){     //没有这个城市  
            return cities;  
        }  
          
        for(int i=0; i<provinces.size(); i++){  
            if(provinces.get(i).attributeValue("Code").equals(provinceName)){  
                cities = provinces.get(i).elements();  
                break;  
            }  
        }  
        return cities;  
    }  
      
    /** 
     *  
     * @author      LiuJinan 
     * @TODO        功能：根据国家名和省份名获取城市列表 
     * @time        2016-8-26 下午4:55:55 
     * @param countryName 
     * @param provinceName 
     * @return      List<String> 
     */  
    public Map<String,String> getCities(String countryName, String provinceName){  
        List<Element> tmp =  this.cities(countryName, provinceName);  
        Map<String,String> cities = new HashMap<String,String>();  
        for(int i=0; i<tmp.size(); i++){  
            cities.put(tmp.get(i).attributeValue("Code"),tmp.get(i).attributeValue("Name"));  
        }  
        return cities;  
    }  
      
    
    private static String languageSession=""; 
    public static LocalUtil getInstance(String language){
    	if(language!=null){
    		if(languageSession.equals(language)||languageSession==language){
        		
        	}else{
        		COUNTRY_REGION.clear();
        		localutil=null;
        	}
        	languageSession=language;
    	}
    	
        if(localutil==null){  
            localutil = new LocalUtil(languageSession);  
        }  
        return localutil;  
    }  
    
    @SuppressWarnings("unchecked")
	public Map<String,String> getAddressENAndCN(String country,String province,String city){
    	String countryStr = "";
    	String provinceStr = "";
    	String cityStr = "";
    	String path = "";
    	if("".equals(languageSession)||languageSession==null||"undefined".equals(languageSession)||"zh".equals(languageSession)){
    		path=getClass().getClassLoader().getResource("LocListEN.xml").getPath();
		}else if("en".equals(languageSession)){
			path=getClass().getClassLoader().getResource("LocList.xml").getPath();
		}
    	File file=new File(path);
        //1.读取  
        reader = new SAXReader();  
        try {  
        	Document otherDocument = reader.read(file); 
        	Element rootElement =  otherDocument.getRootElement();
        	Iterator<Element> itCN =  rootElement.elementIterator();  
        	List<Element> provinces = new ArrayList<Element>();
            Element eleCN = null;  
            while(itCN.hasNext()){  
            	eleCN = (Element)itCN.next();  
                /*COUNTRY_REGION.add(ele.attributeValue("Code")); */ 
            	if(!"".equals(country) && country!=null && ""!=country){
            		if(country.equals(eleCN.attributeValue("Code"))){
                		provinces = eleCN.elements();
                		countryStr = eleCN.attributeValue("Name");
                		break;
                	}
            	}
            	
            }
            for(int i=0; i<provinces.size(); i++){  
            	if(!"".equals(province) && province!=null && ""!=province){
	            	if(province.equals(provinces.get(i).attributeValue("Code"))){
	            		provinceStr = provinces.get(i).attributeValue("Name");
	            	}
            	}
            } 
            List<Element> cities = new ArrayList<Element>();  
            for(int i=0; i<provinces.size(); i++){  
            	if(!"".equals(province) && province!=null && ""!=province){
	                if(provinces.get(i).attributeValue("Code").equals(province)){  
	                    cities = provinces.get(i).elements();  
	                    break;  
	                }  
            	}
            }
            for(int i=0; i<cities.size(); i++){  
            	if(!"".equals(city) && city!=null && ""!=city){
	            	if(cities.get(i).attributeValue("Code").equals(city)){
	            		cityStr = cities.get(i).attributeValue("Name");
	            	}
            	}
            }  
        	//System.out.println(countryStr + " " + provinceStr + " " + cityStr);
        } catch (DocumentException e) {  
            e.printStackTrace();  
        }
        
        Map<String,String> map=new HashMap<String,String>();
        String address=countryStr+"  "+provinceStr+"  "+cityStr;
        map.put("countryStr", countryStr);
        map.put("address", address);
    	return map;
    }
}