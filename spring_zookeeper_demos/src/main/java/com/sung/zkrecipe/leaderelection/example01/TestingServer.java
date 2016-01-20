package com.sung.zkrecipe.leaderelection.example01;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by sungang on 2016/1/8.16:28
 */
public class TestingServer implements Closeable {


    public static String getConnectString(){
        return "10.143.132.232:2181";
    }

    @Override
    public void close() throws IOException {

    }
}
