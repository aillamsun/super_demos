package com.sung.base.common.utils;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertyUtils {
	// 静态对象初始化[在其它对象之前
	private static Hashtable<String, Properties> register = new Hashtable<String, Properties>();
	private static Properties config = null;// 本系统的配置

	public static Properties load(String externalConfigFileLocation){
		Properties properties = new Properties();
		try {
			FileInputStream is = new FileInputStream(new File(externalConfigFileLocation));
			properties.load(is);
		} catch (IOException e) {
			LogUtils.logError("加载 -->"+externalConfigFileLocation +" 失败", e);
		}
		return properties;
	}

	public static Properties getProperties(String fileName) {
		InputStream in = null;
		try {
			config = register.get(fileName);
			if (config == null) {
				if (fileName.startsWith("/")) {
					in = PropertyUtils.class.getResourceAsStream(fileName);
				} else {
					in = PropertyUtils.class.getResourceAsStream("/" + fileName);
				}
				config = new Properties();
				config.load(in);
				register.put(fileName, config);
				in.close();
			}
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		}
		return config;
	}
	
	
	public static void writeData(String filePath, Set<Map<String, String>> writeData) { 
		FileWriter fw = null;
		BufferedWriter  bw = null;
		try {
			fw=new FileWriter(new File(filePath));
			//写入中文字符时会出现乱码
	        bw=new BufferedWriter(fw);
	        for (Map<String, String> map : writeData) {
	        	 for (String key : map.keySet()) {   
	                 bw.write(key+" = "+map.get(key)+"\n");
	             }   
	        	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			 try {
				bw.close();
			} catch (IOException e) {
				LogUtils.logError("", e.getMessage());
			}
			 try {
				fw.close();
			} catch (IOException e) {
				LogUtils.logError("", e.getMessage());
			}
		}
	}
	
	/** 
     * 修改或添加键值对 如果key存在，修改, 反之，添加。 
     * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties 
     * @param key 键 
     * @param value 键对应的值 
     */ 
	public static void writeDataPro(String filePath, String key, String value) { 
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(filePath));
			prop.load(fis);
			//一定要在修改值之前关闭is  
			fis.close();
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(key, value);  
			//保存，并加入注释  
            prop.store(fos, "Update '" + key + "' value");  
            fos.close();  
		} catch (IOException e) {
			LogUtils.logError("修改 -->"+filePath +" 失败，KEY = ["+key + "] , VALUE = ["+value+"]", e);
		}
	}
	
	
	public static String getConfigParam(String key) {
		return config.getProperty(key);
	}
}
