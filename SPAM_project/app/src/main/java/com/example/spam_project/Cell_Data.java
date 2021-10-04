package com.example.spam_project;

public class Cell_Data {
    private String name;
    private String humi;
    private String soil;
    private String temp;
    private int viewtype;

    public Cell_Data(String name, String humi, String soil, String temp, int viewtype) {
        this.name = name;
        this.humi = humi;
        this.soil = soil;
        this.temp = temp;
        this.viewtype = viewtype;
    }
    public Cell_Data(int viewtype) {
        this.viewtype = viewtype;
    }

    //Getter
    public String getName(){
        return name;
    }
    public String getHumi(){
        return humi;
    }
    public String getSoil(){
        return soil;
    }
    public String getTemp(){
        return temp;
    }
    public int getViewtype() { return viewtype; }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setHumi(String humi) {
        this.humi = humi;
    }
    public void setSoil(String soil) {
        this.soil = soil;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }



}
