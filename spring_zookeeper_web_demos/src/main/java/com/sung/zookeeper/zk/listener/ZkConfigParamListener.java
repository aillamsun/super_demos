package com.sung.zookeeper.zk.listener;

import com.sung.zookeeper.zk.ZkHelper;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ZkConfigParamListener {

    private final static String root = "/easy_view_conf";

    private final static String db = root + "/db_conf";
    private final static String dbUrl = db + "/url";
    private final static String dbUsername = db + "/username";
    private final static String dbPassword = db + "/password";


    private static ZooKeeper zk = ZkHelper.getZooKeeper();

    private static String db_url;
    private static String db_username;
    private static String db_passwd;

    public static void loadValue() {
        try {
            db_url = new String(zk.getData(dbUrl, true, null));
            //db_username = new String(zk.getData(dbUsername, true, null));
            //db_passwd = new String(zk.getData(dbPassword, true, null));

            //System.out.println("数据库URL : " + db_url);
            //System.out.println("数据库用户名 : " + db_username);
            //System.out.println("数据库密码 : " + db_passwd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
