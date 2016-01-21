package com.sung.netty.example01.commmon;

/**
 * Created by sungang on 2016/1/21.9:17
 */
public class ReplyServerBody extends ReplyBody {

    private String serverInfo;

    public ReplyServerBody(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }
}
