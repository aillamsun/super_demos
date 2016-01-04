package com.sung.zookeeper.servlet;

import com.sung.base.common.utils.LogUtils;
import com.sung.zookeeper.utils.ResponseUtils;
import com.sung.zookeeper.zk.IZkChildListener;
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
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/ZkCUDOper")
public class ZkCUDOper extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ZkClient zkClient = null;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        zkClient = ZkHelper.getZkClient();
        String oper_type = request.getParameter("oper_type");
        LogUtils.logInfo("当前操作类型是: 【" + oper_type + "】");

        // 添加
        if ("insert".equals(oper_type)) {
            insert(request, response);
        } else if ("update".equals(oper_type)) {
            update(request, response);
        } else if ("delete".equals(oper_type)) {
            delete(request, response);
        }
    }

    // 添加节点
    public void insert(HttpServletRequest request, HttpServletResponse response) {
        String current_path = "";
        String falg = "success";

        // 当前添加节点所有上级目录
        String insert_old_current_path = request
                .getParameter("insert_old_current_path");
        // 当前添加节点名称
        String insert_current_path = request.getParameter("insert_current_path");

        current_path = insert_old_current_path + "/" + insert_current_path;

        // 当前添加节点的数据
        String insert_current_path_data = request
                .getParameter("insert_current_path_data");
        LogUtils.logInfo("当前 添加 节点 ：[ " + current_path + " ] 的数据为 ："
                + insert_current_path_data);
        if (!zkClient.exists(current_path)) {
            zkClient.createPersistent(current_path,
                    ZkClient.toBytes(insert_current_path_data));
        }
        ResponseUtils.responseOutWithJson(response, falg);
    }

    // 更新节点数据
    @SuppressWarnings("static-access")
    public void update(HttpServletRequest request, HttpServletResponse response) {

        final AtomicInteger count = new AtomicInteger(0);
        final ArrayList<String> children = new ArrayList<String>();

        String falg = "success";
        // 当前修改节点名称
        String update_current_path = request
                .getParameter("update_current_path");
        // 当前节点之前的数据
        String update_old_path_data = request
                .getParameter("update_old_path_data");
        // 当前修改数据
        String update_path_data = request.getParameter("update_path_data");
        // 如果相等于 代表没有修改数据
        if (update_old_path_data.equals(update_path_data)) {
            ResponseUtils.responseOutWithJson(response, "no_update");
            return;
        } else {
            try {
                IZkChildListener listener = new IZkChildListener() {
                    @Override
                    public void handleChildChange(String parentPath,
                                                  List<String> currentChildren) throws Exception {
                        count.incrementAndGet();
                        children.clear();
                        if (currentChildren != null) {
                            children.addAll(currentChildren);
                        }
                        LogUtils.logInfo("handle childchange " + parentPath
                                + ", " + currentChildren);
                    }
                };
                zkClient.subscribeChildChanges(update_current_path, listener);
                zkClient.writeData(update_current_path,
                        zkClient.toBytes(update_path_data));
            } catch (Exception e) {
                falg = "fail";
                LogUtils.logError("修改节点 : [ " + update_current_path
                        + " ] 对应数据 ： " + update_path_data + "失败 ！", e);
            }
        }
        ResponseUtils.responseOutWithJson(response, falg);
    }

    // 删除节点
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        String falg = "success";
        String current_delete_data = "";
        // 当前删除的节点名称
        String delete_current_path = request
                .getParameter("delete_current_path");
        try {
            // current_delete_data = new String(zk.getData(delete_current_path,
            // false, null));
            // zk.delete(delete_current_path, -1);
            current_delete_data = new String(zkClient.readData(
                    delete_current_path, null));
            zkClient.deleteRecursive(delete_current_path);
        } catch (Exception e) {
            falg = "fail";
            LogUtils.logError("删除节点 : [ " + delete_current_path + " ] 对应数据 ： "
                    + current_delete_data + "失败 ！", e);
        }
        ResponseUtils.responseOutWithJson(response, falg);
    }
}
