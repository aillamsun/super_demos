package com.sung.zookeeper.init;

import com.sung.base.common.utils.LogUtils;
import com.sung.zookeeper.config.LoadConfiguration;
import com.sung.zookeeper.zk.ZkHelper;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

public class Initialization extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 4191303794602069576L;

    public void init() {

        initSystem();

    }

    private void initSystem() {
        LogUtils.logInfo("=================================Start to init system===========================");
        // 加载一些配置文件
        new LoadConfiguration();
        //初始化ZK 链接
        new ZkHelper();

        // 监听 ZK 配置
        try {
            //ZkConfigParamListener.loadValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
