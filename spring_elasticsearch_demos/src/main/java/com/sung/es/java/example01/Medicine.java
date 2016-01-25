package com.sung.es.java.example01;

/**
 * Created by sungang on 2016/1/21.11:34
 */
public class Medicine {

    private Integer id;
    private String name;
    private String function;

    public Medicine() {
        super();
    }

    public Medicine(Integer id, String name, String function) {
        super();
        this.id = id;
        this.name = name;
        this.function = function;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
