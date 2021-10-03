package com.example.spam_project;

public class Device_Data {
    private String name;
    private String connected;
    private int viewtype;

    public Device_Data(String name, String connected, int viewtype) {
        this.name = name;
        this.connected = connected;
        this.viewtype = viewtype;

    }
    public Device_Data(int viewtype) {
        this.viewtype = viewtype;
    }

    //Getter
    public String getName(){
        return name;
    }
    public String getConnected(){
        return connected;
    }
    public int getViewtype() { return viewtype; }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setConnected(String connected) {
        this.connected = connected;
    }



}
