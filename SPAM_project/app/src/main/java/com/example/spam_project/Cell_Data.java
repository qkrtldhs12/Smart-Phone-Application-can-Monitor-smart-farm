package com.example.spam_project;

public class Cell_Data {
    private String name;
    private String humi;
    private String lumi;
    private String soil;
    private String temp;

    public Cell_Data(String name, String humi, String lumi, String soil, String temp) {
        this.name = name;
        this.lumi = lumi;
        this.humi = humi;
        this.soil = soil;
        this.temp = temp;

    }

    //Getter
    public String getName(){
        return name;
    }
    public String getHumi(){
        return humi;
    }
    public String getLumi(){
        return lumi;
    }
    public String getSoil(){
        return soil;
    }
    public String getTemp(){
        return temp;
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setHumi(String humi) {
        this.humi = humi;
    }
    public void setLumi(String lumi) {
        this.lumi = lumi;
    }
    public void setSoil(String soil) {
        this.soil = soil;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }



}
