package com.example.spam_project;

import android.bluetooth.BluetoothClass;

public class Device_Data {
    private String name;
    private String connected;
    private String model_id;
    private String door;
    private String heat;
    private String humidifier;
    private String light;
    private String vent;
    private int viewtype;

    public Device_Data(String name, String connected, String model_id, String door, String heat, String humidifier, String light, String vent, int viewtype) {
        this.name = name;
        this.connected = connected;
        this.model_id = model_id;
        this.door = door;
        this.heat = heat;
        this.humidifier = humidifier;
        this.light = light;
        this.vent = vent;
        this.viewtype = viewtype;

    }
    public Device_Data(int viewtype) {
        this.viewtype = viewtype;
    }

    public Device_Data() { this.viewtype = 1; }

    //Getter
    public String getName(){
        return name;
    }
    public String getConnected(){
        return connected;
    }
    public String getModel_id(){
        return model_id;
    }
    public String getDoor(){
        return door;
    }
    public String getHeat(){
        return heat;
    }
    public String getHumidifier(){
        return humidifier;
    }
    public String getLight(){
        return light;
    }
    public String getVent(){
        return vent;
    }
    public int getViewtype() { return viewtype; }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setConnected(String connected) {
        this.connected = connected;
    }
    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }
    public void setDoor(String door) {
        this.door = door;
    }
    public void setHeat(String heat) {
        this.heat = heat;
    }
    public void setHumidifier(String humidifier) {
        this.humidifier = humidifier;
    }
    public void setLight(String light) {
        this.light = light;
    }
    public void setVent(String name) {
        this.vent = vent;
    }




}
