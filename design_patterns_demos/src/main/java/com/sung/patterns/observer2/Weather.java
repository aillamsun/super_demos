package com.sung.patterns.observer2;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by sungang on 2016/2/18.10:41
 */
public class Weather {

    List<WeatherObservers> observers;

    public Weather(){
        observers = Lists.newArrayList();
    }

    public void addObservers(WeatherObservers observers1){
        observers.add(observers1);
    }

    public void weather(){
        notifyObservers();
    }


    public void notifyObservers(){
        for (WeatherObservers observer:observers){
            observer.proess();
        }
    }
}
