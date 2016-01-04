package com.sung.zookeeper.servlet;

import com.sung.zookeeper.utils.ResponseUtils;
import com.sung.zookeeper.zk.ZkClient;
import com.sung.zookeeper.zk.ZkHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoadZkNodeDataForPath")
public class LoadZkNodeDataForPath extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ZkClient zkClient = null;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		zkClient = ZkHelper.getZkClient();
		String currentPath = request.getParameter("currentPath");
		String currentValue = "";
		if (null != currentPath && !"".equals(currentPath)) {
			currentValue = new String(zkClient.readData(currentPath, null));
		}
		ResponseUtils.responseOutWithJson(response, currentPath + "-"
				+ currentValue);
	}

}
