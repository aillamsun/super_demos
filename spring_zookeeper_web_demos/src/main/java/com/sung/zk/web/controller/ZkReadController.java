package com.sung.zk.web.controller;

import com.sung.zk.entity.ZkData;
import com.sung.zk.op.Zk;
import com.sung.zk.util.ConfUtils;
import com.sung.zk.web.constants.Constants;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Controller
@RequestMapping("/read")
public class ZkReadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkReadController.class);

	@RequestMapping("/addr")
	public String addr(HttpServletRequest request, RedirectAttributes attr, String cxnstr) {
		Properties props = ConfUtils.getConxtions();
		if (StringUtils.isBlank(cxnstr)) {
			//return "redirect:/";
			cxnstr =  props.getProperty("zk.host")+":"+props.getProperty("zk.port")+props.getProperty("zk.root.name");
		}
		HttpSession session = request.getSession();
		session.setAttribute(Constants.CX_STR, cxnstr);
		attr.addFlashAttribute(Constants.CX_STR, StringUtils.trimToEmpty(cxnstr));
		return "redirect:/read/node/";
	}

	@RequestMapping("/node")
	public String node(String cxnstr,Model model, String path) {
//		HttpSession session = request.getSession();
//		String cxnstr = (String) session.getAttribute(Constants.CX_STR);
		Properties props = ConfUtils.getConxtions();
		if (StringUtils.isBlank(cxnstr)) {
			//return "redirect:/";
			cxnstr =  props.getProperty("zk.host")+":"+props.getProperty("zk.port")+props.getProperty("zk.root.name");
		}
		path = StringUtils.endsWith(path, "/") ? StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "/")): path;
		path = StringUtils.isBlank(path) ? "/" : StringUtils.trimToEmpty(path);
		model.addAttribute("pathList", Arrays.asList(StringUtils.split(path, "/")));

		Zk reader = new Zk(cxnstr);

		List<String> children = reader.getChildren(path);
		if (CollectionUtils.isNotEmpty(children)) {
			Collections.sort(children);
		}
		model.addAttribute("children", children);

		ZkData zkData = reader.readData(path);

		model.addAttribute("data", zkData.getDataString());
		model.addAttribute("dataSize", zkData.getData().length);
		try {
			Map<String, Object> statMap = PropertyUtils.describe(zkData.getStat());
			statMap.remove("class");
			model.addAttribute("stat", statMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return "node";
	}

}
