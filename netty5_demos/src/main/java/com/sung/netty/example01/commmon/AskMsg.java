package com.sung.netty.example01.commmon;


/**
 * Created by sungang on 2016/1/21.9:14
 * 请求类型消息
 */
public class AskMsg extends BaseMsg {
    public AskMsg() {
        super();
        setType(MsgType.ASK);
    }

    private AskParams params;

    public AskParams getParams() {
        return params;
    }

    public void setParams(AskParams params) {
        this.params = params;
    }
}

