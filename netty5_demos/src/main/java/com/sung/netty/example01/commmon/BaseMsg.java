package com.sung.netty.example01.commmon;

import java.io.Serializable;

/**
 * Created by sungang on 2016/1/21.9:11
 * <p/>
 * 必须实现序列,serialVersionUID 一定要有,否者在netty消息序列化反序列化会有问题，接收不到消息
 */
public class BaseMsg implements Serializable {


    private static final long serialVersionUID = 1L;

    private MsgType type;
    //必须唯一，否者会出现channel调用混乱
    private String clientId;

    //初始化客户端id
    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
}
