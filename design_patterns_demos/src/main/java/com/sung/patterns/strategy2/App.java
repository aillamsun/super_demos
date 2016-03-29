package com.sung.patterns.strategy2;

/**
 * Created by sungang on 2016/2/18.10:39
 */
public class App {


    public static void main(String[] args) {
        StrategyCentext centext = null;

        centext = new StrategyCentext(new StrategyOne());
        centext.oper();

        centext = new StrategyCentext(new StrategyTeo());
        centext.oper();
    }
}
