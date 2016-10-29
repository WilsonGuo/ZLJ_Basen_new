
package com.example.smarteair.net;

import android.util.Log;
import android.widget.Toast;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.content.Context;
//import android.net.NetworkInfo; 

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.net.InetAddress;
import java.io.IOException;
import java.net.*;
import java.util.ListIterator;
//import java.util.Iterator;  

import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.data.AddDeviceCallBack;
import com.example.smarteair.data.ApConfigCallback;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DelDeviceCallBack;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.NetworkStateCallback;
import com.example.smarteair.data.ScanDevice;
import com.example.smarteair.data.ScanDeviceListener;
import com.zlj.basen.activity.sub.ConfigDeviceActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class NetWorkManager {

	private static Context mContext;

	private Looper looper = Looper.myLooper();

	private static Handler mainHandler;// = new
										// MainHandler(Looper.getMainLooper());
	private static Handler subHandler = null;

	private String str_Text;
	private static ScanDeviceListener mScanDeviceListener;
	private static DeviceStatusChangeListener mChangeListener;
	private static NetworkStateCallback mNetworkStateCallback;
	private static ApConfigCallback mAPConfigCallback;
	private static NetWorkManager singleton;
	private static WifiAdmin mWifiAdmin;
	private static AddDeviceCallBack mAddDeviceCallBack;
	private static DelDeviceCallBack mDelDeviceCallBack;
	private int add_wifi_status = 0;
	private int new_wifi_sn = 0;
	private int time_cnt = 0;
	private boolean mInConfig;
	private String mSSID;
	private String strPassWord;
	private boolean mNetworkLost;
	private int time_sum;

	int test_index = 0;
	public byte[] CtoJvBuff;
	static CustomThread subThreadId;
	int groupId = 1002;// 公司识别码

	public void setAddDeviceCallBack(AddDeviceCallBack callback) {
		mAddDeviceCallBack = callback;
	}

	public void setDelDeviceCallBack(DelDeviceCallBack callback) {
		mDelDeviceCallBack = callback;
	}

	private NetWorkManager(Context context) {
		long crc1 = 0;
		long crc2 = 0;

		mContext = context;
		mainHandler = new MainHandler(Looper.getMainLooper());

		CtoJvBuff = new byte[100];

		SharedPreferences sharedPreferences = mContext.getSharedPreferences("WIFI-CFG", Context.MODE_PRIVATE);
		int app_sn = sharedPreferences.getInt("APP-ID", 0);
		crc1 = sharedPreferences.getLong("crc1", 0);
		crc2 = sharedPreferences.getLong("crc2", 0);

		ByteConvert.longToBytes(crc1, CtoJvBuff, 0);
		ByteConvert.longToBytes(crc2, CtoJvBuff, 8);

		JniNetWorkInit(app_sn, groupId, CtoJvBuff);
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>app_sn="+app_sn);
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>groupId="+groupId);
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>CtoJvBuff="+CtoJvBuff);
		subThreadId = new CustomThread();
		subThreadId.start();

	}

	public static NetWorkManager getInstance(Context context) {
		if (singleton == null) {
			singleton = new NetWorkManager(context);
		} else {
			 Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>singleton != null");
		}

		return singleton;
	}

	public static NetWorkManager getInstance(Context context, int port, int ip) {
		if (singleton == null) {
			singleton = new NetWorkManager(context);
		} else {
			// mDevinfo.init();
		}

		return singleton;
	}

	public void setScanDeviceListener(ScanDeviceListener listener) {
		mScanDeviceListener = listener;
	}

	public void setDeviceStatusChangeListener(DeviceStatusChangeListener listener) {
		mChangeListener = listener;
	}

	public void setNetworkStateCallback(NetworkStateCallback callback) {
		mNetworkStateCallback = callback;
	}

	public void setAPConfigCallback(ApConfigCallback callback) {
		mAPConfigCallback = callback;
	}

	public void configAP(String ssid, String passwd) {
		mInConfig = true;
		mSSID = ssid;
		strPassWord = passwd;
		SendMsgToSub(Defines.SUB_MSG_TYPE_AP_CONFIG, 0, 0, null);
	}

	public void cancleAPConfig() {
		JniCancelAPConfig();
	}

	public void networkDestory() {
		// SendMsgToSub(Defines.SUB_MSG_TYPE_THREAD_EXIT, 0, 0, null);
		subHandler.getLooper().quit();
		JniNetWorkExit();
		singleton = null;
	}

	public void AddNewWifiDev(int wifi_sn) {
		SendMsgToSub(Defines.SUB_MSG_TYPE_ADD_NEW_DEVICE, wifi_sn, 0, null);
	}

	public void removeWifiDev(int wifi_sn) {
		SendMsgToSub(Defines.SUB_MSG_TYPE_DEL_DEVICE, wifi_sn, 0, null);
	}

	public void SendPkt(int wifi_sn, byte[] buff, int len) {
		Log.e("NetWorkManager", "SendPkt");
		SendMsgToSub(Defines.SUB_MSG_TYPE_SEND_DATA, wifi_sn, len, buff);
	}

	public int exit() {
		return 0;
	}

	public native int JniNetWorkInit(int app_sn, int group_id, byte[] check);

	public native int JniNetWorkExit();

	public native int JniNetWorkPause();

	public native int JniNetWorkResume();

	public native int JniEntryScanMode();

	public native int JniExitScanMode();

	public native int JniAddDevie(int sn); // ����

	public native int JniDelDevie(int sn); // ����

	public native int JniActiveDevice(int sn);

	public native int JniDisActiveDevice(int sn);

	public native int JniDisActiveAll();

	public native int JniGetDeviceLinkStatus(int sn);

	public native int JniGetServiceLinkStatus();

	public native int JniApConfig(byte[] ssid, byte[] password, int add_flag); // ����

	public native int JniCancelAPConfig();

	public native int JniSendData(int sn, int datalen, byte[] data); // ����

	private static void SendMsgToUi(int msgType, int DevId, int len, byte[] buff) {
		if (mainHandler != null) {
			Message msg = mainHandler.obtainMessage(msgType, DevId, len, buff);
			mainHandler.sendMessage(msg); 
		}
	}

	private static void SendMsgToSub(int msgType, int DevId, int len, byte[] buff) {
		if (subHandler != null) {
			Message msg = subHandler.obtainMessage(msgType, DevId, len, buff);
			subHandler.sendMessage(msg); 
		}
	}

	class MainHandler extends Handler {
		public MainHandler() {
		}

		public MainHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			int wifi_id = msg.arg1;
			int datalen = msg.arg2;

			switch (msg.what) {

			case Defines.MAIN_MSG_TYPE_SEND_PACKET_RESULT: {
				int res = msg.arg2;
				if (mChangeListener != null) {
					mChangeListener.doCallBack(Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT, res, null);
				}
				break;
			}

			case Defines.MAIN_MSG_TYPE_DATA: {
				byte[] buff;

				buff = (byte[]) msg.obj;

				byte[] buffer2 = new byte[buff.length];
				for (int l = 0; l < buff.length; l++) {
					buffer2[l] = buff[l];
				}
				DataInfo di = new DataInfo();
				di.len = datalen;
				di.pkt = buffer2;
				di.sn = wifi_id;

				if (mChangeListener != null) {
					mChangeListener.doCallBack(Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA, 0, di);
					di = null;
					buffer2 = null;
				}

				break;
			}

			case Defines.MAIN_MSG_TYPE_GET_APP_SN: {
				long crc1 = 0;
				long crc2 = 0;
				byte[] buff;

				buff = (byte[]) msg.obj;

				crc1 = ByteConvert.bytesToLong(buff, 0);
				crc2 = ByteConvert.bytesToLong(buff, 8);

				SharedPreferences sharedPreferences = mContext.getSharedPreferences("WIFI-CFG", Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.putInt("APP-ID", wifi_id);
				editor.putLong("crc1", crc1);
				editor.putLong("crc2", crc2);
				editor.commit();
				break;
			}

			case Defines.MAIN_MSG_TYPE_NETWORK_DOWN: {
				// Toast.makeText(this, "����豸�ɹ�������",
				// Toast.LENGTH_SHORT).show();
				mNetworkLost = true;
				if (wifi_id == 0) {
					Toast.makeText(mContext, mContext.getResources().getString(R.string.network_lost),
							Toast.LENGTH_SHORT).show();
				}
				/*
				 * else { Toast.makeText(mContext, "�豸��"+
				 * Integer.toString(wifi_id & 0x7fffffff)+"���ӳ�ʱ ��",
				 * Toast.LENGTH_SHORT).show(); }
				 */

				break;
			}

			case Defines.MAIN_MSG_TYPE_NETWORK_UP: {
				if (mNetworkLost) {
					mNetworkLost = false;
					if (wifi_id == 0) {
						Toast.makeText(mContext, mContext.getResources().getString(R.string.network_recovery),
								Toast.LENGTH_SHORT).show();
					}
				}
				/*
				 * else { Toast.makeText(mContext, "�豸��"+
				 * Integer.toString(wifi_id & 0x7fffffff)+"���ӳɹ���",
				 * Toast.LENGTH_SHORT).show(); }
				 */

				break;
			}

			case Defines.MAIN_MSG_TYPE_FIND_NEW_WIFI_DEV: {
				if (mScanDeviceListener != null) {
					// DataInfo di = new DataInfo();
					// di.sn = 0xC000012;
					mScanDeviceListener.deviceInfoCallBack(wifi_id);
				}
				break;
			}

			case Defines.MAIN_MSG_TYPE_SET_WIFI_PASSWORD_SUCCESS: {
				if (mAPConfigCallback != null) {
					mAPConfigCallback.notifyConfigResult(msg);
				}

				break;
			}

			case Defines.MAIN_MSG_TYPE_ADD_NEW_WIFI_DEV_SUCCESS: {
				/*
				 * int wifi_sn; boolean flag = false;
				 * 
				 * if(msg.arg2 == 0) { SharedPreferences sharedPreferences =
				 * mContext.getSharedPreferences("WIFI-CFG",
				 * Context.MODE_PRIVATE); int wifiNum =
				 * sharedPreferences.getInt("WIFINum", 0);
				 * 
				 * int i = 0; while(i < wifiNum) { wifi_sn =
				 * sharedPreferences.getInt("WIFI"+ Integer.toString(i)+"-ID",
				 * 0); if(wifi_sn == wifi_id) { flag = true; break; } i++; }
				 * 
				 * if(!flag) { Editor editor = sharedPreferences.edit();
				 * editor.putInt("WIFI"+
				 * Integer.toString(wifiNum)+"-ID",wifi_id);
				 * editor.putInt("WIFINum", wifiNum+1); editor.commit(); } }
				 */

				if (mAddDeviceCallBack != null) {
					wifi_id = msg.arg1;
					mAddDeviceCallBack.onDeviceAdded(wifi_id, msg.arg2);
				}
				break;
			}

			case Defines.MAIN_MSG_TYPE_DEL_WIFI_DEV_SUCCESS: {
				if (mDelDeviceCallBack != null) {
					wifi_id = msg.arg1;
					mDelDeviceCallBack.onDeviceDeled(wifi_id, msg.arg2);
				}

				break;
			}
			}

			if (mNetworkStateCallback != null) {
				mNetworkStateCallback.notifyMessage(msg);
			}
		}
	}

	class CustomThread extends Thread {

		@Override
		public void run() {
		
			Looper.prepare();
			subHandler = new Handler() {
				public void handleMessage(Message msg) {
					int devic_sn = msg.arg1;
					int data_slen = msg.arg2;
					byte[] data;

					switch (msg.what) {
					case Defines.SUB_MSG_TYPE_SEND_DATA: {
						data = (byte[]) msg.obj;
						// int rc = 0;
						int rc = JniSendData(devic_sn, data_slen, data); 
						for(int i=0;i<data.length;i++){
							Log.e("TAG", "******************"+i+"="+data[i]);
						}
						
						Log.e("JniSendData", "JniSendData return");
						SendMsgToUi(Defines.MAIN_MSG_TYPE_SEND_PACKET_RESULT, devic_sn, rc, null);
						break;
					}

					case Defines.SUB_MSG_TYPE_ADD_NEW_DEVICE: {
						int rc;
						rc = JniAddDevie(devic_sn);
						SendMsgToUi(Defines.MAIN_MSG_TYPE_ADD_NEW_WIFI_DEV_SUCCESS, devic_sn, rc, null);
						break;
					}

					case Defines.SUB_MSG_TYPE_DEL_DEVICE: {
						int rc;
						rc = JniDelDevie(devic_sn);
						SendMsgToUi(Defines.MAIN_MSG_TYPE_DEL_WIFI_DEV_SUCCESS, devic_sn, rc, null);
						break;
					}

					case Defines.SUB_MSG_TYPE_AP_CONFIG: {
						int add_wifi_flg = 0;
						byte[] ssid = mSSID.getBytes();
						byte[] password = strPassWord.getBytes();

						mWifiAdmin = WifiAdmin.getInstance(mContext);
						mWifiAdmin.openWifi();
						mWifiAdmin.addNetwork(mWifiAdmin.CreateWifiInfo("WIFI-CFG", "", 1));
                        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>strPassWord="+mSSID);
                        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>strPassWord="+strPassWord);
                        
                        
						int dev_sn = JniApConfig(ssid, password, add_wifi_flg);
						
//						mWifiAdmin.closeWifi();
//						mWifiAdmin.removewifi("WIFI-CFG");
//						mWifiAdmin.openWifi();

						if ((add_wifi_flg != 0) && (dev_sn != 0)) {
							int wifi_sn;
							boolean flag = false;

							SharedPreferences sharedPreferences = mContext.getSharedPreferences("WIFI-CFG",
									Context.MODE_PRIVATE);
							int wifiNum = sharedPreferences.getInt("WIFINum", 0);

							int i = 0;
							while (i < wifiNum) {
								wifi_sn = sharedPreferences.getInt("WIFI" + Integer.toString(i) + "-ID", 0);
								if (wifi_sn == dev_sn) {
									flag = true;
									break;
								}

								i++;
							}

							if (!flag) {
								Editor editor = sharedPreferences.edit();
								editor.putInt("WIFI" + Integer.toString(wifiNum) + "-ID", dev_sn);
								editor.putInt("WIFINum", wifiNum + 1);
								editor.commit();
							}

						}

						SendMsgToUi(Defines.MAIN_MSG_TYPE_SET_WIFI_PASSWORD_SUCCESS, dev_sn, 0, null);

						break;
					}

					case Defines.SUB_MSG_TYPE_CANCEL_AP_CONFIG: {
						JniCancelAPConfig();
						break;
					}
					case Defines.SUB_MSG_TYPE_THREAD_EXIT: {
						// this.getLooper().quit();
						break;
					}
					}
				}
			};

			Looper.loop();// 4��������Ϣѭ��
		}
	}

	static {
		System.loadLibrary("NetWorkManager");
	}
}
