package com.mohan.raksha;

public class MyListData{

    private String name;
    private String number;

    public MyListData() {

    }

    public MyListData(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}