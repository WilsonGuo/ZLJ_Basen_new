package com.example.smarteair.data;

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
    
    public int speed_XF;
    public int speed_PF;
    public int motor_speed_XF;
    public int motor_speed_PF;
    public int fuctionSetBit;
    public int fuctionBit;
    
    public int timerStartMin_Mon;
    public int timerStartHour_Mon;    
    public int timerEndMin_Mon;
    public int timerEndHour_Mon;
    public int timerStartMin_Tue;
    public int timerStartHour_Tue;    
    public int timerEndMin_Tue;
    public int timerEndHour_Tue;
    public int timerStartMin_Wed;
    public int timerStartHour_Wed;    
    public int timerEndMin_Wed;
    public int timerEndHour_Wed;
    public int timerStartMin_Thu;
    public int timerStartHour_Thu;    
    public int timerEndMin_Thu;
    public int timerEndHour_Thu;
    public int timerStartMin_Fri;
    public int timerStartHour_Fri;    
    public int timerEndMin_Fri;
    public int timerEndHour_Fri;
    public int timerStartMin_Sat;
    public int timerStartHour_Sat;    
    public int timerEndMin_Sat;
    public int timerEndHour_Sat;
    public int timerStartMin_Sun;
    public int timerStartHour_Sun;    
    public int timerEndMin_Sun;
    public int timerEndHour_Sun;
    
    
    public int hengshidu;
    
    public boolean o1;
    public boolean heat;
    public boolean windIn;
    public boolean timerOn;
    public boolean windOut;
    public boolean o3;
    public boolean uv;
    
    public boolean set_co2;
    public boolean set_pm25;
    public boolean set_tvoc;
    public boolean set_hcho;
    public boolean set_dingshi;

    public int sn;
}
