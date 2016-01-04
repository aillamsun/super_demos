package hadoop2_6.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by sungang on 2015/11/14.13:05
 */
public class HiveJDBCConnection {

    private static  String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
    private static  String url = "jdbc:hive://192.168.3.141:10000/default";
    private static  String username = "";
    private static  String password = "";
    public static void main(String[] args) throws Exception{

        Class.forName(driverName);

        Connection conn = DriverManager.getConnection(url, username, password);
        Statement statement = conn.createStatement();
        System.out.println(statement);

    }
}
