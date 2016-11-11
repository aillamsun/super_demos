package com.sungang.concurrent.thread.lock.countDownlatch;

/**
 * Created by sungang on 2016/11/11.
 */
public class Participater implements Runnable {

    private String name;
    private Conference conference;

    public Participater(String name, Conference conference) {
        this.name = name;
        this.conference = conference;
    }

    @Override
    public void run() {
        conference.arrive(name);
    }
}
