package com.sung.patterns.observer2;

/**
 * Created by sungang on 2016/2/18.10:43
 */
public class ObserverTwo implements WeatherObservers {

    @Override
    public void proess() {
        System.out.println("ObserverTwo exec...");
    }
}
