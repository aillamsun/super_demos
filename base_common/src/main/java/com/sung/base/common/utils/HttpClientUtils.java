package com.sung.base.common.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @包名 com.tiger.common.utils
 * @类名: HttpClientUtils
 * @描述: http client请求工具类
 * @作者： 孙刚
 * @创建时间： 2014年9月21日 上午10:41:19
 * 
 * @修改人：xxx
 * @修改时间:xxxx-xx-xx
 * 
 */
public class HttpClientUtils {

	/**
	 * 默认的编码,解决中文乱码
	 */
	public static String defaultEncode = "UTF-8";

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @return 响应体
	 */
	public static String getSendGet(String url, Map<String, String> paramMap) {
		return getSendGet(url, paramMap, defaultEncode);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @param encode
	 *            编码
	 * @return 响应体
	 */
	public static String getSendGet(String url, Map<String, String> paramMap, String encode) {
		StringBuffer buf = new StringBuffer();
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		if (paramMap.size() > 0) {
			NameValuePair[] params = new NameValuePair[paramMap.size()];
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Entry<String, String> map = it.next();
				params[i] = new NameValuePair(map.getKey(), map.getValue());
				i++;
			}
			getMethod.setQueryString(params); // post请求参数用setQueryString
		}
		try {
			client.executeMethod(getMethod);
			byte[] responseBody = getMethod.getResponseBody();
			String content = new String(responseBody, encode);
			buf.append(content);
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		} finally {
			getMethod.releaseConnection();
		}
		return buf.toString();
	}

	/**
	 * 发送Post请求
	 *
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @return 响应体
	 */
	public static String getSendPost(String url, Map<String, String> paramMap) {
		return getSendPost(url, paramMap, defaultEncode);
	}

	/**
	 * 发送Post请求
	 *
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @return 响应体
	 */
	public static String getSendPost(String url, Map<String, String> paramMap, String encode) {
		StringBuffer buf = new StringBuffer();
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode);
		if (paramMap.size() > 0) {
			NameValuePair[] params = new NameValuePair[paramMap.size()];
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Entry<String, String> map = it.next();
				params[i] = new NameValuePair(map.getKey(), map.getValue());
				i++;
			}
			postMethod.setRequestBody(params); // post请求参数用setRequestBody
		}
		try {
			client.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody();
			String content = new String(responseBody, encode);
			buf.append(content);
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return buf.toString();
	}

	/**
	 * 发送Post请求
	 *
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @return 响应体
	 */
	public static String getSendPut(String url, Map<String, String> paramMap) {
		return getSendPut(url, paramMap, defaultEncode);
	}

	/**
	 * 发送Put请求
	 *
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @param encode
	 *            编码
	 * @return 响应体
	 */
	public static String getSendPut(String url, Map<String, String> paramMap, String encode) {
		StringBuffer buf = new StringBuffer();
		HttpClient client = new HttpClient();

		HttpClientParams clientParams = client.getParams();
		clientParams.setContentCharset("UTF-8");

		PutMethod putMethod = new PutMethod(url);
		if (paramMap.size() > 0) {
			NameValuePair[] params = new NameValuePair[paramMap.size()];
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Entry<String, String> map = it.next();
				params[i] = new NameValuePair(map.getKey(), map.getValue());
				i++;
			}
			putMethod.setQueryString(params); // put请求参数用setQueryString
		}
		try {
			client.executeMethod(putMethod);
			byte[] responseBody = putMethod.getResponseBody();
			String content = new String(responseBody, encode);
			buf.append(content);
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		} finally {
			putMethod.releaseConnection();
		}
		return buf.toString();
	}

	/**
	 * 发送Delete请求
	 *
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @return 响应体
	 */
	public static String getSendDelete(String url, Map<String, String> paramMap) {
		return getSendDelete(url, paramMap, defaultEncode);
	}

	/**
	 * 发送Delete请求
	 *
	 * @param url
	 *            请求路径
	 * @param paramMap
	 *            参数
	 * @param encode
	 *            编码
	 * @return 响应体
	 */
	public static String getSendDelete(String url, Map<String, String> paramMap, String encode) {
		StringBuffer buf = new StringBuffer();
		HttpClient client = new HttpClient();

		HttpClientParams clientParams = client.getParams();
		clientParams.setContentCharset("UTF-8");

		DeleteMethod putMethod = new DeleteMethod(url);
		if (paramMap.size() > 0) {
			NameValuePair[] params = new NameValuePair[paramMap.size()];
			Iterator<Entry<String, String>> it = paramMap.entrySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Entry<String, String> map = it.next();
				params[i] = new NameValuePair(map.getKey(), map.getValue());
				i++;
			}
			putMethod.setQueryString(params); // put请求参数用setQueryString
		}
		try {
			client.executeMethod(putMethod);
			byte[] responseBody = putMethod.getResponseBody();
			String content = new String(responseBody, encode);
			buf.append(content);
		} catch (Exception e) {
			LogUtils.logError("", e.getMessage());
		} finally {
			putMethod.releaseConnection();
		}
		return buf.toString();
	}

}
