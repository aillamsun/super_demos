package com.sung.zk.web.controller;

import com.sung.zk.op.Zk;
import com.sung.zk.web.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@Controller
@RequestMapping("/op")
public class ZkOpController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkOpController.class);
	private static final String SEPARATOR = "/";

	@RequestMapping("/create")
	public String create(Model model, String parent, String name, String data) {
		String cxnstr = getCxnstr();
		if (StringUtils.isBlank(cxnstr)) {
			return "redirect:/";
		}
		parent = StringUtils.isBlank(parent) ? SEPARATOR : StringUtils.trimToEmpty(parent);
		parent = StringUtils.endsWith(parent, SEPARATOR) ? parent : parent + SEPARATOR;
		name = StringUtils.startsWith(name, SEPARATOR) ? StringUtils.substring(name, 1) : name;
		Zk zk = new Zk(cxnstr);
		String path = parent + name;
		zk.create(path, data.getBytes(Charset.forName("UTF-8")));
		return "redirect:/read/node?path=" + path;
	}

	@RequestMapping("/edit")
	public String edit(Model model, String path, String data) {
		String cxnstr = getCxnstr();
		if (StringUtils.isBlank(cxnstr)) {
			return "redirect:/";
		}
		path = StringUtils.isBlank(path) ? SEPARATOR : StringUtils.trimToEmpty(path);
		path = StringUtils.endsWith(path, "/") ? StringUtils.substring(path, 0, path.length()-1) : path;
		Zk zk = new Zk(cxnstr);
		zk.edit(path, data.getBytes(Charset.forName("UTF-8")));
		return "redirect:/read/node?path=" + path;
	}

	@RequestMapping("/delete")
	public String delete(Model model, String path, String data) {
		String cxnstr = getCxnstr();
		if (StringUtils.isBlank(cxnstr)) {
			return "redirect:/";
		}
		path = StringUtils.isBlank(path) ? SEPARATOR : StringUtils.trimToEmpty(path);
		path = StringUtils.endsWith(path, "/") ? StringUtils.substring(path, 0, path.length()-1) : path;
		Zk zk = new Zk(cxnstr);
		zk.delete(path);
		return "redirect:/read/node?path=" + StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "/"));
	}

	@RequestMapping("/rmrdel")
	public String rmrdel(Model model, String path, String data) {
		String cxnstr = getCxnstr();
		if (StringUtils.isBlank(cxnstr)) {
			return "redirect:/";
		}
		path = StringUtils.isBlank(path) ? SEPARATOR : StringUtils.trimToEmpty(path);
		path = StringUtils.endsWith(path, "/") ? StringUtils.substring(path, 0, path.length()-1) : path;
		Zk zk = new Zk(cxnstr);
		zk.deleteRecursive(path);
		LOGGER.info("deleteRecursive, cxnstr:{}, path:{}", cxnstr, path);
		return "redirect:/read/node?path=" + StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "/"));
	}

	private String getCxnstr() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (String) req.getSession().getAttribute(Constants.CX_STR);
	}

}
