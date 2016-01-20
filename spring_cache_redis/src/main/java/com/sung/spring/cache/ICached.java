package com.sung.spring.cache;

import java.util.List;
import java.util.Set;


public interface ICached {
	/**
	 * 删除 缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	String deleteCached(byte[] key)throws Exception;
	/**
	 * 更新 缓存
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	Object updateCached(byte[] key, byte[] value, Long expire)throws Exception;
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Object getCached(byte[] key)throws Exception;
	/**
	 * 根据 正则表达式key 获取 列表
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	Set getKeys(byte[] keys)throws Exception;
	
	/**
	 * 根据 正则表达式key 获取 列表
	 * @return
	 * @throws Exception
	 */
	Set getHashKeys(byte[] key)throws Exception;
	
	
	
	/**
	 * 更新 缓存
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	Boolean updateHashCached(byte[] key, byte[] mapkey, byte[] value, Long expire)throws Exception;
	

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Object getHashCached(byte[] key, byte[] mapkey)throws Exception;
	
	
	/**
	 * 删除 缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Long deleteHashCached(byte[] key, byte[] mapkey)throws Exception;
	
	/**
	 * 获取 map的长度
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Long getHashSize(byte[] key)throws Exception;
/**
 * 获取 map中的所有值
 * @param key
 * @return
 * @throws Exception
 */
	List getHashValues(byte[] key)throws Exception;
	
	
	/**
	 * 获取 map的长度
	 * @return
	 * @throws Exception
	 */
	Long getDBSize()throws Exception;
	
	/**
	 * 获取 map的长度
	 * @return
	 * @throws Exception
	 */
	void clearDB()throws Exception;
}
