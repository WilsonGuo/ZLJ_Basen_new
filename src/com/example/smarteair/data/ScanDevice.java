
package com.example.smarteair.data;


public class ScanDevice
{

    public String deviceName;
    public short deviceType;
    public int id;
    public int lock;
    public String mac;
    public int password;
    public byte publicKey[];
    public short subDevice;
    public boolean checked;
    public EairInfo eairInfo;
    public boolean isNew=true;
	 
    public ScanDevice()
    {
    	mac = "0.0.0.0";
    }
}
