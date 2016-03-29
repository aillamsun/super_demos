package com.sung.patterns.strategy2;

/**
 * Created by sungang on 2016/2/18.10:38
 */
public class StrategyCentext {

    Strategy strategy;

    public StrategyCentext(Strategy strategy){
        this.strategy = strategy;
    }

    public void oper(){
        strategy.oper();
    }
}
