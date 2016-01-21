package com.sung.rpc;

/**
 * Created by sungang on 2016/1/20.16:53
 */
public class HelloWorldImpl implements HelloWorld{

    @Override
    public Result queryUser(User user) {
        return new Result("hello " + user.getUsername());
    }
}
