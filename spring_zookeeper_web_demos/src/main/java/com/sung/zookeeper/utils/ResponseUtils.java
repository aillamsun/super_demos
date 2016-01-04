package com.sung.zookeeper.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtils {

	public static void responseOutWithJson(HttpServletResponse response,
			Object jsonObject) {

		String json = JSON.toJSONString(jsonObject);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.append(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
