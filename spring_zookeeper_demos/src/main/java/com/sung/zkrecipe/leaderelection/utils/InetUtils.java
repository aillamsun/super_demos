package com.sung.zkrecipe.leaderelection.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by sungang on 2016/1/18.16:40
 */
public class InetUtils {

    public static String getLocalHostname() {
        String hostname;
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (final UnknownHostException e) {
            hostname = "unknown";
        }
        return hostname;
    }

    public static void main(String[] args) {
        System.out.println(getLocalHostname());
    }
}
