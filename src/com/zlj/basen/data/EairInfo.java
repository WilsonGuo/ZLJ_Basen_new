package com.zlj.basen.data;

import java.io.Serializable;

public class EairInfo implements Serializable 
{
    private static final long serialVersionUID = 0x63b1987cf974b596L;
    
    public int indoorTemp;
    public int indoorHumi;
    public int outdoorTemp;
    public int outdoorHumi;
    public int fixHumi;
    public int air;
    public int workMode;
    public int windInLevel;
    public int windOutLevel;
    public int deviceType;
    public int funcSet;    
    public int sysTimeMin;
    public int sysTimeHour;  
    public int timerStartMin;
    public int timerStartHour;    
    public int timerEndMin;
    public int timerEndHour;
    public int filterTime;
    public int pm25;
    public float tvoc;
    public float hcoh;
    public int co2;
    public int autoSensorIndex;
    
    public boolean o1;
    public boolean heat;
    public boolean windIn;
    public boolean timerOn;
    public boolean windOut;
    public boolean o3;
    public boolean uv;
    
    public int sn;
}
