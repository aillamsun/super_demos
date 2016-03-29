package com.sung.patterns.chain2;

/**
 * Created by sungang on 2016/2/18.10:15
 */
public abstract class Handler {

    /**
     * 持有下一个处理请求的对象
     */

    Handler next = null;

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }

    /**
     * 处理
     * @param userName
     * @param fee
     * @return
     */
    public abstract String handerRequest(String userName,double fee);
}
