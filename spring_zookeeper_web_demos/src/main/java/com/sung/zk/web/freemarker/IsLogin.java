package com.sung.zk.web.freemarker;

import com.sung.zk.web.util.AuthUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class IsLogin implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	public Object exec(List arg0) throws TemplateModelException {
		return AuthUtils.isLogin();
	}

}
