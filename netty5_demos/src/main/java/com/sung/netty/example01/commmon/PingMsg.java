package com.sung.netty.example01.commmon;

/**
 * Created by sungang on 2016/1/21.9:13
 * 心跳检测Ping类型消息
 */
public class PingMsg extends BaseMsg {

    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
