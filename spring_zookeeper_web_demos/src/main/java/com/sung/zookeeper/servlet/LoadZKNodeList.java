package com.sung.zookeeper.servlet;

import com.sung.zookeeper.config.ServerConfig;
import com.sung.zookeeper.tree.ZTreeNode;
import com.sung.zookeeper.tree.ZkTreeBuildUtils;
import com.sung.zookeeper.utils.ResponseUtils;
import com.sung.zookeeper.zk.ZkClient;
import com.sung.zookeeper.zk.ZkHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙刚
 * @ClassName: LoadZKNodeList
 * @Description: 使用注解描述Servlet
 * @date 2014年9月1日 下午12:07:54
 * @修改人 xxx
 * @修改描述 xxx
 * @修改时间 xxxx-xx-xx
 * <p/>
 * <p/>
 * 注解WebServlet用来描述一个Servlet 属性name描述Servlet的名字,可选
 * 属性urlPatterns定义访问的URL,或者使用属性value定义访问的URL.(定义访问的URL是必选属性)
 */
@WebServlet("/LoadZKNodeList")
public class LoadZKNodeList extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ZkClient zkClient = null;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        zkClient = ZkHelper.getZkClient();
        List<ZTreeNode> zTreeNodes = new ArrayList<ZTreeNode>();
        if (zkClient != null) {
            try {
                ZkTreeBuildUtils.getZkTreeData(zTreeNodes,
                        ServerConfig.zk_root_name, zkClient, "0");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ResponseUtils.responseOutWithJson(response, zTreeNodes);
    }

}
