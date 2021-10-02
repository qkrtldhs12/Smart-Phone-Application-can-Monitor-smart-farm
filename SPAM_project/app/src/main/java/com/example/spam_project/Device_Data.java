package com.example.spam_project;

public class Device_Data {
    private String name;
    private String connected;

    public Device_Data(String name, String connected) {
        this.name = name;
        this.connected = connected;

    }

    //Getter
    public String getName(){
        return name;
    }
    public String getConnected(){
        return connected;
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setConnected(String connected) {
        this.connected = connected;
    }



}
