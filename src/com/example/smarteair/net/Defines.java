package com.example.smarteair.net;

public class Defines
{
	//主线程消息类型
	public final static int  MAIN_MSG_TYPE_GET_APP_SN = 0;
    public final static int  MAIN_MSG_TYPE_DATA = 1;
    public final static int  MAIN_MSG_TYPE_NETWORK_UP = 2;
    public final static int  MAIN_MSG_TYPE_NETWORK_DOWN = 3;
    public final static int  MAIN_MSG_TYPE_FIND_NEW_WIFI_DEV = 4;
    public final static int  MAIN_MSG_TYPE_MISS_NEW_WIFI_DEV = 5;  //发现一个wifi设备
    public final static int  MAIN_MSG_TYPE_ADD_NEW_WIFI_DEV_SUCCESS = 6;  //增加一个wifi device
    public final static int  MAIN_MSG_TYPE_DEL_WIFI_DEV_SUCCESS     = 7;  //删除一个wifi device\
    public final static int  MAIN_MSG_TYPE_SET_WIFI_PASSWORD_SUCCESS     = 8;  //删除一个wifi device
    public final static int  MAIN_MSG_TYPE_ONE_SECOND     = 10;  //删除一个wifi device
    public final static int  MAIN_MSG_TYPE_SEND_PACKET_RESULT  = 11;  //发送包结果
    
    //子线程消息类型
    public final static int  SUB_MSG_TYPE_SEND_DATA = 0;
    public final static int  SUB_MSG_TYPE_ADD_NEW_DEVICE = 1;
    public final static int  SUB_MSG_TYPE_DEL_DEVICE = 2;
    public final static int  SUB_MSG_TYPE_AP_CONFIG = 3;
    public final static int  SUB_MSG_TYPE_CANCEL_AP_CONFIG = 4; //发现一个wifi设备
    public final static int  SUB_MSG_TYPE_THREAD_EXIT = 5; //发现一个wifi设备

    
    public final static int  DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA = 0; 
    public final static int  DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT = 1; 
    
}