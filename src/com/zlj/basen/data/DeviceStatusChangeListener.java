
package com.zlj.basen.data;

public interface DeviceStatusChangeListener
{
    
    public abstract void doCallBack(int type, int data, DataInfo datainfo);
}
