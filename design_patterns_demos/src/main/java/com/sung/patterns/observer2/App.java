package com.sung.patterns.observer2;

/**
 * Created by sungang on 2016/2/18.10:47
 */
public class App {

    public static void main(String[] args) {
        Weather weather = new Weather();

        weather.addObservers(new ObserverOne());
        weather.addObservers(new ObserverTwo());

        weather.weather();
    }
}
