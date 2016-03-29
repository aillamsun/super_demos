package com.sungang.concurrent.thread.paChong.example01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sungang on 2016/3/9.15:35
 */
public class WebPageSource {
    public static void main(String args[]) {
        URL url;
        int responsecode;
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String line;
        try {
            //生成一个URL对象，要获取源代码的网页地址为：http://www.sina.com.cn
            url = new URL("http://www.sina.com.cn");
            //打开URL
            urlConnection = (HttpURLConnection) url.openConnection();
            //获取服务器响应代码
            responsecode = urlConnection.getResponseCode();
            if (responsecode == 200) {
                //得到输入流，即获得了网页的内容
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } else {
                System.out.println("获取不到网页的源码，服务器响应代码为：" + responsecode);
            }
        } catch (Exception e) {
            System.out.println("获取不到网页的源码,出现异常：" + e);
        }
    }
}
