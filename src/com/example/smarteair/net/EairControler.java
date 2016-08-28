package com.example.smarteair.net;

import com.example.basen.R;
import com.example.smarteair.data.EairInfo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class EairControler {

	public static final int TYPE_T1 = 2;
	public static final int TYPE_T2 = 0;
	public static final int TYPE_CENTER = 1;

	public static final int MODE_SHUTDOWN = 0;
	public static final int MODE_AP = 1;
	public static final int MODE_SLEEP = 2;
	public static final int MODE_MANUAL = 3;
	public static final int MODE_HENGSHI = 4;
	public static final int MODE_NEIXUNHUAN = 5;
	public static final int MODE_SENSOR = 6;
	public static final int MODE_RF_DUIMA = 7;

	public static final int MODE_AUTO = 4;
	public static final int MODE_WINTER = 2;
	public static final int MODE_FIX = 3;

	public static final int T1_MODE_SHUTDOWN = 0;
	public static final int T1_MODE_MANUAL = 1;
	public static final int T1_MODE_CIRCLE = 2;
	public static final int T1_MODE_AUTO = 3;

	public static final int CENTRAL_MODE_LOW_SPEED = 1;
	public static final int CENTRAL_MODE_MID_SPEED = 2;
	public static final int CENTRAL_MODE_HIGH_SPEED = 3;
	public static final int CENTRAL_MODE_MANUAL = 4;
	public static final int CENTRAL_MODE_AUTO = 5;

	private static byte SENSOR_STATE_MASK_O1 = 0x01;
	private static byte SENSOR_STATE_MASK_HEAT = 0x02;
	private static byte SENSOR_STATE_MASK_WIND_IN = 0x04;
	private static byte SENSOR_STATE_MASK_TIMER = 0x08;
	private static byte SENSOR_STATE_MASK_WIND_OUT = 0x10;
	private static byte SENSOR_STATE_MASK_UV = 0x40;
	private static byte SENSOR_STATE_MASK_O3 = 0x20;

	private static int STATE_DATA_INDEX_CMD = 0;;
	private static int STATE_DATA_INDEX_FIX_HUMI = 5;
	private static int STATE_DATA_INDEX_WIFI_REQ = 6;
	private static int STATE_DATA_INDEX_AIR_STATE = 7;

	private static int STATE_DATA_INDEX_WIND_IN = 9;
	private static int STATE_DATA_INDEX_WIND_OUT = 10;
	private static int STATE_DATA_INDEX_FUNC_SET = 12;
	private static int STATE_DATA_INDEX_SYSTEM_TIME = 13;
	private static int STATE_DATA_INDEX_TIMER_START = 15;
	private static int STATE_DATA_INDEX_TIMER_END = 17;

	private static int STATE_DATA_INDEX_PM25 = 21;
	private static int STATE_DATA_INDEX_TVOC = 23;
	private static int STATE_DATA_INDEX_CO2 = 25;
	private static int STATE_DATA_INDEX_HCOH = 27;
	private static int STATE_DATA_INDEX_AUTO_SENSOR = 28;

	// ********************************Basen***************************************
	private static int STATE_DATA_INDEX_DEVICE_TYPE = 2;// 8

	private static int STATE_DATA_INDEX_FUCTION_SET_BIT_H = 6;// 8
	private static int STATE_DATA_INDEX_FUCTION_SET_BIT_L = 7;// 8
	private static int STATE_DATA_INDEX_FUCTION_BIT_H = 8;// 8
	private static int STATE_DATA_INDEX_FUCTION_BIT_L = 9;// 8

	private static int STATE_DATA_INDEX_FUNCTION_BIT = 2;// 8
	private static int STATE_DATA_INDEX_WORK_MODE = 10;// 8

	private static int STATE_DATA_INDEX_AIR_LEVER = 11;// 8

	private static int STATE_DATA_INDEX_INDOOR_TEMP = 12;
	private static int STATE_DATA_INDEX_INDOOR_HUMI = 13;
	private static int STATE_DATA_INDEX_OUTDOOR_TEMP = 14;
	private static int STATE_DATA_INDEX_OUTDOOR_HUMI = 15;

	private static int STATE_DATA_INDEX_HENG_HUMI = 16;

	private static int STATE_DATA_INDEX_PM25_H = 17;
	private static int STATE_DATA_INDEX_PM25_L = 18;
	private static int STATE_DATA_INDEX_TVOC_H = 19;
	private static int STATE_DATA_INDEX_TVOC_L = 20;
	private static int STATE_DATA_INDEX_CO2_H = 21;
	private static int STATE_DATA_INDEX_CO2_L = 22;
	private static int STATE_DATA_INDEX_HCOH_H = 23;
	private static int STATE_DATA_INDEX_HCOH_L = 24;

	private static int STATE_DATA_INDEX_SPEED_XF = 25;
	private static int STATE_DATA_INDEX_SPEED_PF = 26;

	private static int STATE_DATA_INDEX_MOTO_SPEED_XF_H = 27;
	private static int STATE_DATA_INDEX_MOTO_SPEED_XF_L = 28;
	private static int STATE_DATA_INDEX_MOTO_SPEED_PF_H = 29;
	private static int STATE_DATA_INDEX_MOTO_SPEED_PF_L = 30;

	private static int STATE_DATA_INDEX_SYSDATE_YEAR = 31;
	private static int STATE_DATA_INDEX_SYSDATE_MONTH = 32;
	private static int STATE_DATA_INDEX_SYSDATE_DAY = 33;
	private static int STATE_DATA_INDEX_SYSDATE_HOUR = 34;
	private static int STATE_DATA_INDEX_SYSDATE_MIN = 35;
	private static int STATE_DATA_INDEX_SYSDATE_SEC = 36;

	private static int STATE_DATA_INDEX_KAI_MIN_MON = 37;
	private static int STATE_DATA_INDEX_KAI_HOUR_MON = 38;
	private static int STATE_DATA_INDEX_GUAN_MIN_MON = 39;
	private static int STATE_DATA_INDEX_GUAN_HOUR_MON = 40;

	private static int STATE_DATA_INDEX_KAI_MIN_TUE = 41;
	private static int STATE_DATA_INDEX_KAI_HOUR_TUE = 42;
	private static int STATE_DATA_INDEX_GUAN_MIN_TUE = 43;
	private static int STATE_DATA_INDEX_GUAN_HOUR_TUE = 44;

	private static int STATE_DATA_INDEX_KAI_MIN_WED = 45;
	private static int STATE_DATA_INDEX_KAI_HOUR_WED = 46;
	private static int STATE_DATA_INDEX_GUAN_MIN_WED = 47;
	private static int STATE_DATA_INDEX_GUAN_HOUR_WED = 48;

	private static int STATE_DATA_INDEX_KAI_MIN_THU = 49;
	private static int STATE_DATA_INDEX_KAI_HOUR_THU = 50;
	private static int STATE_DATA_INDEX_GUAN_MIN_THU = 51;
	private static int STATE_DATA_INDEX_GUAN_HOUR_THU = 52;

	private static int STATE_DATA_INDEX_KAI_MIN_FRI = 53;
	private static int STATE_DATA_INDEX_KAI_HOUR_FRI = 54;
	private static int STATE_DATA_INDEX_GUAN_MIN_FRI = 55;
	private static int STATE_DATA_INDEX_GUAN_HOUR_FRI = 56;

	private static int STATE_DATA_INDEX_KAI_MIN_SAT = 57;
	private static int STATE_DATA_INDEX_KAI_HOUR_SAT = 58;
	private static int STATE_DATA_INDEX_GUAN_MIN_SAT = 59;
	private static int STATE_DATA_INDEX_GUAN_HOUR_SAT = 60;

	private static int STATE_DATA_INDEX_KAI_MIN_SUN = 61;
	private static int STATE_DATA_INDEX_KAI_HOUR_SUN = 62;
	private static int STATE_DATA_INDEX_GUAN_MIN_SUN = 63;
	private static int STATE_DATA_INDEX_GUAN_HOUR_SUN = 64;

	private static int STATE_DATA_INDEX_FILTER_TIME_H = 65;
	private static int STATE_DATA_INDEX_FILTER_TIME_L = 66;

	public static int BIT_O1 = 0;
	public static int BIT_HOT = 1;
	public static int BIT_XF = 2;
	public static int BIT_SET_BIT_YEAR_MOM_DAY = 3;
	public static int BIT_PF = 4;
	public static int BIT_UV = 5;
	public static int BIT_O3 = 6;
	public static int BIT_SET_BIT_CO2 = 7;
	public static int BIT_SET_BIT_PM25 = 8;
	public static int BIT_SET_BIT_TVOC = 9;
	public static int BIT_SET_BIT_HCHO = 10;
	public static int BIT_SET_BIT_DINGSHI = 11;

	private final int CMD_A2M_QUERY_STATE = 0x00;
	private final int CMD_A2M_SET_WORK_MODE = 0x01;
	private final int CMD_A2M_SET_WIND_IN = 0x02;
	private final int CMD_A2M_SET_WIND_OUT = 0x03;
	private final int CMD_A2M_SET_TIMER = 0x04;
	private final int CMD_A2M_SET_HEAT = 0x05;
	private final int CMD_A2M_SET1_O1 = 0x06;
	private final int CMD_A2M_SET1_UV = 0x07;
	private final int CMD_A2M_SET1_O3 = 0x08;
	private final int CMD_A2M_SET1_HUMI = 0x09;
	private final int CMD_A2M_SET1_AUTO_SENSOR = 0x0A;

	// public static int CONTROL_PACKERT_IN_SIZE = 23;
	private static int CONTROL_PACKERT_OUT_SIZE = 10;

	private Context mContext;
	private byte[] mPacket = new byte[CONTROL_PACKERT_OUT_SIZE];

	private EairControler(Context context) {
		mContext = context;
	}

	private void initPacket() {
		for (int i = 0; i < mPacket.length; i++) {
			mPacket[i] = 0;
		}
	}

	private void sendControlData(byte[] data, int sn) {
		NetWorkManager netManager = NetWorkManager.getInstance(mContext);

		if (netManager.JniGetServiceLinkStatus() <= 0) {
			Toast.makeText(mContext, mContext.getResources().getString(R.string.network_lost), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		netManager.SendPkt(sn, data, data.length);
	}

	public void airSetMode(int mode, int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WORK_MODE;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = (byte) mode;
		sendControlData(mPacket, wifiSN);
	}

	public void airClose(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WORK_MODE;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = MODE_SHUTDOWN;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpen(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WORK_MODE;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = (byte) 0xff;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpenO1(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_O1;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 1;
		sendControlData(mPacket, wifiSN);
	}

	public void airCloseO1(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_O1;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 0;
		sendControlData(mPacket, wifiSN);
	}

	public void airQueryState(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_QUERY_STATE;
		sendControlData(mPacket, wifiSN);
	}

	public void airSetTimer(byte startHour, byte startMin, byte endHour, byte endMin, int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_TIMER;
		mPacket[1] = 4;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;

		mPacket[5] = startMin;
		mPacket[6] = startHour;
		mPacket[7] = endMin;
		mPacket[8] = endHour;
		sendControlData(mPacket, wifiSN);
	}

	public void airSetTimerAll(byte startHour_1, byte startMin_1, byte endHour_1, byte endMin_1, byte startHour_2,
			byte startMin_2, byte endHour_2, byte endMin_2, byte startHour_3, byte startMin_3, byte endHour_3,
			byte endMin_3, byte startHour_4, byte startMin_4, byte endHour_4, byte endMin_4, byte startHour_5,
			byte startMin_5, byte endHour_5, byte endMin_5, byte startHour_6, byte startMin_6, byte endHour_6,
			byte endMin_6, byte startHour_7, byte startMin_7, byte endHour_7, byte endMin_7, int wifiSN) {
		byte[] mPacket = new byte[33];
		for (int i = 0; i < mPacket.length; i++) {
			mPacket[i] = 0;
		}
		mPacket[0] = CMD_A2M_SET_TIMER;
		mPacket[1] = 28;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;

		mPacket[5] = startMin_1;
		mPacket[6] = startHour_1;
		mPacket[7] = endMin_1;
		mPacket[8] = endHour_1;

		mPacket[9] = startMin_2;
		mPacket[10] = startHour_2;
		mPacket[11] = endMin_2;
		mPacket[12] = endHour_2;

		mPacket[13] = startMin_3;
		mPacket[14] = startHour_3;
		mPacket[15] = endMin_3;
		mPacket[16] = endHour_3;

		mPacket[17] = startMin_4;
		mPacket[18] = startHour_4;
		mPacket[19] = endMin_4;
		mPacket[20] = endHour_4;

		mPacket[21] = startMin_5;
		mPacket[22] = startHour_5;
		mPacket[23] = endMin_5;
		mPacket[24] = endHour_5;

		mPacket[25] = startMin_6;
		mPacket[26] = startHour_6;
		mPacket[27] = endMin_6;
		mPacket[28] = endHour_6;

		mPacket[29] = startMin_7;
		mPacket[30] = startHour_7;
		mPacket[31] = endMin_7;
		mPacket[32] = endHour_7;

		sendControlData(mPacket, wifiSN);
	}

	public void airSetWindInLevel(byte level, int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WIND_IN;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = level;

		sendControlData(mPacket, wifiSN);
	}

	public void airCloseWindIn(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WIND_IN;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 0;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpenWindIn(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WIND_IN;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = (byte) 255;
		sendControlData(mPacket, wifiSN);
	}

	public void airSetWindOutLevel(byte level, int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WIND_OUT;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = level;

		sendControlData(mPacket, wifiSN);
	}

	public void airSetHumi(byte level, int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_HUMI;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = level;

		sendControlData(mPacket, wifiSN);
	}

	public void airCloseWindOut(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WIND_OUT;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 0;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpenWindOut(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_WIND_OUT;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = (byte) 255;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpenHeat(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_HEAT;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 1;
		sendControlData(mPacket, wifiSN);
	}

	public void airCloseHeat(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET_HEAT;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 0;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpenUV(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_UV;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 1;
		sendControlData(mPacket, wifiSN);
	}

	public void airCloseUV(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_UV;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 0;
		sendControlData(mPacket, wifiSN);
	}

	public void airOpenO3(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_O3;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 1;
		sendControlData(mPacket, wifiSN);
	}

	public void airCloseO3(int wifiSN) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_O3;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = 0;
		sendControlData(mPacket, wifiSN);
	}

	public void airSetAutoSensor(int wifiSN, int sensorIndex) {
		initPacket();
		mPacket[0] = CMD_A2M_SET1_AUTO_SENSOR;
		mPacket[1] = 1;// 负载数据长度
		mPacket[2] = 0;// 设备类型
		mPacket[3] = 0;// 报文下标;
		mPacket[4] = 0;// 报文下标;
		mPacket[5] = (byte) sensorIndex;
		sendControlData(mPacket, wifiSN);
	}

	private int getDataByIndex(int index, byte stateData[], int dataBytes) {
		if (index < stateData.length) {
			int low = stateData[index];
			if (low < 0) {
				int i = 128 + low;
				low = i | 0x80;
			}
			if (dataBytes < 2) {
				return low;
			}

			int high = stateData[index + 1];
			if (high < 0) {
				int i = 128 + high;
				high = i | 0x80;
			}

			return ((high << 8) | (low));
		}

		return 0;
	}

	private int getDataByIndexForFilter(int index, byte stateData[], int dataBytes) {
		if (index < stateData.length) {
			int low = stateData[index + 1];
			if (low < 0) {
				int i = 128 + low;
				low = i | 0x80;
			}
			if (dataBytes < 2) {
				return low;
			}

			int high = stateData[index];
			if (high < 0) {
				int i = 128 + high;
				high = i | 0x80;
			}

			return ((high << 8) | (low));
		}

		return 0;
	}

	public void parseInfo(int sn, EairInfo eairInfo, byte stateData[]) {

		Log.e("TAG", "**********************************parseInfo***********************");

		for (int i = 0; i < stateData.length; i++) {
			int v = stateData[i] & 0xFF;
			String hv = Integer.toHexString(v);
			Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>stateData:" + i + "=" + hv);
		}

		eairInfo.sn = sn;
		byte setBit_H = stateData[STATE_DATA_INDEX_FUCTION_SET_BIT_H];
		byte setBit_L = stateData[STATE_DATA_INDEX_FUCTION_SET_BIT_L];
		String stringH = byteToBit(setBit_H);
		String stringL = byteToBit(setBit_L);
		Log.e("TAG", "**********************************stringH=" + stringH + "  " + stringL);

		byte bit_H = stateData[STATE_DATA_INDEX_FUCTION_SET_BIT_H];
		byte bit_L = stateData[STATE_DATA_INDEX_FUCTION_SET_BIT_L];
		String striH = byteToBit(bit_H);
		String striL = byteToBit(bit_L);
		Log.e("TAG", "**********************************striH=" + striH + "  " + striH);

		byte[] setBitsH = getBooleanArray(setBit_H);
		byte[] setBitsL = getBooleanArray(setBit_L);
		byte[] BitsH = getBooleanArray(bit_H);
		byte[] BitsL = getBooleanArray(bit_L);
		if (setBitsL[setBitsL.length - 1] == 1 && BitsL[BitsL.length - 1] == 1) {
			eairInfo.o1 = true;
		} else {
			eairInfo.o1 = false;
		}

		if (setBitsL[setBitsL.length - 2] == 1 && BitsL[BitsL.length - 2] == 1) {
			eairInfo.heat = true;
		} else {
			eairInfo.heat = false;
		}
		if (setBitsL[setBitsL.length - 3] == 1 && BitsL[BitsL.length - 3] == 1) {
			eairInfo.windIn = true;
		} else {
			eairInfo.windIn = false;
		}

		if (setBitsL[setBitsL.length - 4] == 1 && BitsL[BitsL.length - 4] == 1) {
			eairInfo.timerOn = true;
		} else {
			eairInfo.timerOn = false;
		}

		if (setBitsL[setBitsL.length - 5] == 1 && BitsL[BitsL.length - 5] == 1) {
			eairInfo.windOut = true;
		} else {
			eairInfo.windOut = false;
		}

		if (setBitsL[setBitsL.length - 6] == 1 && BitsL[BitsL.length - 6] == 1) {
			eairInfo.uv = true;
		} else {
			eairInfo.uv = false;
		}

		if (setBitsL[setBitsL.length - 7] == 1 && BitsL[BitsL.length - 7] == 1) {
			eairInfo.o3 = true;
		} else {
			eairInfo.o3 = false;
		}

		if (setBitsL[0] == 1 && BitsL[0] == 1) {
			eairInfo.set_co2 = true;
		} else {
			eairInfo.set_co2 = false;
		}

		if (setBitsH[setBitsH.length - 1] == 1 && BitsH[BitsH.length - 1] == 1) {
			eairInfo.set_pm25 = true;
		} else {
			eairInfo.set_pm25 = false;
		}

		if (setBitsH[setBitsH.length - 2] == 1 && BitsH[BitsH.length - 2] == 1) {
			eairInfo.set_tvoc = true;
		} else {
			eairInfo.set_tvoc = false;
		}
		if (setBitsH[setBitsH.length - 3] == 1 && BitsH[BitsH.length - 3] == 1) {
			eairInfo.set_hcho = true;
		} else {
			eairInfo.set_hcho = false;
		}

		if (setBitsH[setBitsH.length - 4] == 1 && BitsH[BitsH.length - 4] == 1) {
			eairInfo.set_dingshi = true;
		} else {
			eairInfo.set_dingshi = false;
		}

		eairInfo.fuctionSetBit = getDataByIndexForFilter(STATE_DATA_INDEX_FUCTION_SET_BIT_H, stateData, 2);
		eairInfo.fuctionBit = getDataByIndexForFilter(STATE_DATA_INDEX_FUCTION_BIT_H, stateData, 2);

		eairInfo.indoorTemp = stateData[STATE_DATA_INDEX_INDOOR_TEMP] & 0x7f;
		if ((stateData[STATE_DATA_INDEX_INDOOR_TEMP] & 0x70) == 1) {// 负值
			eairInfo.indoorTemp = -eairInfo.indoorTemp;
		}
		eairInfo.indoorHumi = getDataByIndex(STATE_DATA_INDEX_INDOOR_HUMI, stateData, 1);

		eairInfo.outdoorTemp = stateData[STATE_DATA_INDEX_OUTDOOR_TEMP] & 0x7f;
		
		if ((stateData[STATE_DATA_INDEX_OUTDOOR_TEMP] & 0x70) == 1) {// 负值
			eairInfo.outdoorTemp = -eairInfo.outdoorTemp;
		}
		eairInfo.outdoorHumi = getDataByIndex(STATE_DATA_INDEX_OUTDOOR_HUMI, stateData, 1);

		eairInfo.fixHumi = getDataByIndex(STATE_DATA_INDEX_FIX_HUMI, stateData, 1);

		eairInfo.air = getDataByIndex(STATE_DATA_INDEX_AIR_LEVER, stateData, 1);

		eairInfo.workMode = getDataByIndex(STATE_DATA_INDEX_WORK_MODE, stateData, 1);

		eairInfo.windInLevel = getDataByIndex(STATE_DATA_INDEX_WIND_IN, stateData, 1);
		eairInfo.windOutLevel = getDataByIndex(STATE_DATA_INDEX_WIND_OUT, stateData, 1);

		eairInfo.deviceType = getDataByIndex(STATE_DATA_INDEX_DEVICE_TYPE, stateData, 1);

		if (eairInfo.set_dingshi) {// 以星期定时方式 每天可以独立设置
			eairInfo.timerStartMin_Mon = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_MON, stateData, 1);
			eairInfo.timerStartHour_Mon = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_MON, stateData, 1);
			eairInfo.timerEndMin_Mon = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_MON, stateData, 1);
			eairInfo.timerEndHour_Mon = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_MON, stateData, 1);

			eairInfo.timerStartMin_Tue = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_TUE, stateData, 1);
			eairInfo.timerStartHour_Tue = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_TUE, stateData, 1);
			eairInfo.timerEndMin_Tue = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_TUE, stateData, 1);
			eairInfo.timerEndHour_Tue = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_TUE, stateData, 1);

			eairInfo.timerStartMin_Wed = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_WED, stateData, 1);
			eairInfo.timerStartHour_Wed = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_WED, stateData, 1);
			eairInfo.timerEndMin_Wed = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_WED, stateData, 1);
			eairInfo.timerEndHour_Wed = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_WED, stateData, 1);

			eairInfo.timerStartMin_Thu = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_THU, stateData, 1);
			eairInfo.timerStartHour_Thu = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_THU, stateData, 1);
			eairInfo.timerEndMin_Thu = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_THU, stateData, 1);
			eairInfo.timerEndHour_Thu = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_THU, stateData, 1);

			eairInfo.timerStartMin_Fri = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_FRI, stateData, 1);
			eairInfo.timerStartHour_Fri = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_FRI, stateData, 1);
			eairInfo.timerEndMin_Fri = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_FRI, stateData, 1);
			eairInfo.timerEndHour_Fri = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_FRI, stateData, 1);

			eairInfo.timerStartMin_Sat = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_SAT, stateData, 1);
			eairInfo.timerStartHour_Sat = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_SAT, stateData, 1);
			eairInfo.timerEndMin_Sat = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_SAT, stateData, 1);
			eairInfo.timerEndHour_Sat = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_SAT, stateData, 1);

			eairInfo.timerStartMin_Sun = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_SUN, stateData, 1);
			eairInfo.timerStartHour_Sun = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_SUN, stateData, 1);
			eairInfo.timerEndMin_Sun = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_SUN, stateData, 1);
			eairInfo.timerEndHour_Sun = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_SUN, stateData, 1);

		} else {// 0=只有一个定时方式 每天一样
			eairInfo.timerStartMin = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_MON, stateData, 1);
			eairInfo.timerStartHour = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_MON, stateData, 1);
			eairInfo.timerEndMin = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_MON, stateData, 1);
			eairInfo.timerEndHour = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_MON, stateData, 1);

			eairInfo.timerStartMin_Mon = getDataByIndex(STATE_DATA_INDEX_KAI_MIN_MON, stateData, 1);
			eairInfo.timerStartHour_Mon = getDataByIndex(STATE_DATA_INDEX_KAI_HOUR_MON, stateData, 1);
			eairInfo.timerEndMin_Mon = getDataByIndex(STATE_DATA_INDEX_GUAN_MIN_MON, stateData, 1);
			eairInfo.timerEndHour_Mon = getDataByIndex(STATE_DATA_INDEX_GUAN_HOUR_MON, stateData, 1);

		}

		// eairInfo.funcSet = getDataByIndex(STATE_DATA_INDEX_FUNC_SET,
		// stateData, 1);
		// eairInfo.o1 = false;
		// eairInfo.heat = false;
		// eairInfo.windIn = false;
		// eairInfo.windOut = false;
		// eairInfo.timerOn = false;
		// eairInfo.uv = false;
		// eairInfo.o3 = false;
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_HEAT) > 0) {
		// eairInfo.heat = true;
		// }
		//
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_O1) > 0) {
		// eairInfo.o1 = true;
		// }
		//
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_WIND_IN) > 0) {
		// eairInfo.windIn = true;
		// }
		//
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_WIND_OUT) > 0) {
		// eairInfo.windOut = true;
		// }
		//
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_TIMER) > 0) {
		// eairInfo.timerOn = true;
		// }
		//
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_UV) > 0) {
		// eairInfo.uv = true;
		// }
		//
		// if ((eairInfo.funcSet & SENSOR_STATE_MASK_O3) > 0) {
		// eairInfo.o3 = true;
		// }

		eairInfo.sysTimeMin = getDataByIndex(STATE_DATA_INDEX_SYSDATE_MIN, stateData, 1);
		eairInfo.sysTimeHour = getDataByIndex(STATE_DATA_INDEX_SYSDATE_HOUR, stateData, 1);

		eairInfo.timerStartMin = getDataByIndex(STATE_DATA_INDEX_TIMER_START, stateData, 1);
		eairInfo.timerStartHour = getDataByIndex(STATE_DATA_INDEX_TIMER_START + 1, stateData, 1);

		eairInfo.timerEndMin = getDataByIndex(STATE_DATA_INDEX_TIMER_END, stateData, 1);
		eairInfo.timerEndHour = getDataByIndex(STATE_DATA_INDEX_TIMER_END + 1, stateData, 1);

		eairInfo.filterTime = getDataByIndexForFilter(STATE_DATA_INDEX_FILTER_TIME_H, stateData, 2) / 2;

		eairInfo.pm25 = getDataByIndexForFilter(STATE_DATA_INDEX_PM25_H, stateData, 2);
		

		int tvoc = getDataByIndexForFilter(STATE_DATA_INDEX_TVOC_H, stateData, 2);
		eairInfo.tvoc = (float) tvoc / 100.0f;

		int cho2 = getDataByIndexForFilter(STATE_DATA_INDEX_HCOH_H, stateData, 2);
		eairInfo.hcoh = (float) cho2 / 100.0f;

		eairInfo.co2 = getDataByIndexForFilter(STATE_DATA_INDEX_CO2_H, stateData, 2);

		eairInfo.speed_XF = getDataByIndex(STATE_DATA_INDEX_SPEED_XF, stateData, 1);
		eairInfo.speed_PF = getDataByIndex(STATE_DATA_INDEX_SPEED_PF, stateData, 1);

		eairInfo.motor_speed_XF = getDataByIndex(STATE_DATA_INDEX_MOTO_SPEED_XF_H, stateData, 2);
		eairInfo.motor_speed_PF = getDataByIndex(STATE_DATA_INDEX_MOTO_SPEED_PF_H, stateData, 2);

		eairInfo.hengshidu = getDataByIndex(STATE_DATA_INDEX_HENG_HUMI, stateData, 1);

		eairInfo.autoSensorIndex = 0;
	}

	public static EairControler mInstance;

	public static EairControler getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new EairControler(context);
		}

		return mInstance;
	}

	/**
	 * Byte转Bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}

	public static byte[] getBooleanArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

}
