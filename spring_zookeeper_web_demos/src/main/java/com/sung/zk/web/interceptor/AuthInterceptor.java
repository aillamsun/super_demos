package com.sung.zk.web.interceptor;

import com.sung.zk.web.util.AuthUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!AuthUtils.isLogin()) {
			throw new RuntimeException("没有登录");
		}
		return true;
	}
}
