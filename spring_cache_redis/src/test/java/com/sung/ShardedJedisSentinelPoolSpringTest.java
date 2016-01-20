package com.sung;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sungang on 2016/1/5.15:32
 */
public class ShardedJedisSentinelPoolSpringTest{


    public static void after(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-data-redis-config.xml");
    }

    public static void main(String[] args) {
        after();

    }
}
