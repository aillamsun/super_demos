package com.sung.patterns.chain2;

/**
 * Created by sungang on 2016/2/18.10:22
 */
public class GeneralManager extends Handler {


    @Override
    public String handerRequest(String userName, double fee) {

        String str = "";
        //总经理的权限很大，只要请求到了这里，他都可以处理
        if(fee >= 1000)
        {
            //为了测试，简单点，只同意张三的请求
            if("张三".equals(userName))
            {
                str = "成功：总经理同意【" + userName + "】的聚餐费用，金额为" + fee + "元";
            }else
            {
                //其他人一律不同意
                str = "失败：总经理不同意【" + userName + "】的聚餐费用，金额为" + fee + "元";
            }
        }else
        {
            //如果还有后继的处理对象，继续传递
            if(getNext() != null)
            {
                return getNext().handerRequest(userName, fee);
            }
        }
        return str;
    }
}
