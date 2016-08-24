
package com.example.smarteair.data;

public interface DeviceStatusChangeListener
{
    
    public abstract void doCallBack(int type, int data, DataInfo datainfo);
}
