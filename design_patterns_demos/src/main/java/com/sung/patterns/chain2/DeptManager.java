package com.sung.patterns.chain2;

/**
 * Created by sungang on 2016/2/18.10:20
 */
public class DeptManager extends Handler {

    @Override
    public String handerRequest(String userName, double fee) {

        String str = "";
        //部门经理的权限只能在1000以内
        if (fee < 1000) {
            //为了测试，简单点，只同意张三的请求
            if ("张三".equals(userName)) {
                str = "成功：部门经理同意【" + userName + "】的聚餐费用，金额为" + fee + "元";
            } else {
                //其他人一律不同意
                str = "失败：部门经理不同意【" + userName + "】的聚餐费用，金额为" + fee + "元";
            }
        } else {
            //超过1000，继续传递给级别更高的人处理
            if (getNext() != null) {
                return getNext().handerRequest(userName, fee);
            }
        }
        return str;
    }
}
