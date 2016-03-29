package com.sung.patterns.observer2;

/**
 * Created by sungang on 2016/2/18.10:42
 */
public class ObserverOne implements WeatherObservers {

    @Override
    public void proess() {
        System.out.println("ObserverOne exec...");
    }
}
