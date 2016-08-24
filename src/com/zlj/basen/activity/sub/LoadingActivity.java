
package com.zlj.basen.activity.sub;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.data.ManageDevice;
import com.example.smarteair.data.ManageDeviceDao;
import com.zlj.basen.BaseActivity;
import com.zlj.basen.BasenMainActivity;

public class LoadingActivity extends BaseActivity {

	private void querAllDevice() {
		Iterator iterator = null;
		ManageDeviceDao managedevicedao;

		if (EairApplaction.mNetWorkManager == null) {
			mApplication.initNetWork();
		}
		/*
		 * else { EairApplaction.mNetWorkManager.networkRestart(); }
		 * 
		 * EairApplaction.mNetWorkManager.probeRestart();
		 */

		try {
			managedevicedao = new ManageDeviceDao(getHelper());
			EairApplaction.allDeviceList.clear();
			EairApplaction.allDeviceList.addAll(managedevicedao.queryAllDevices());

			for (ManageDevice md : EairApplaction.allDeviceList) {
				EairInfo eairinfo = new EairInfo();

				eairinfo.sn = md.terminalId;
				Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>eairinfo.sn ="+eairinfo.sn );
				md.setEairInfo(eairinfo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * iterator = EairApplaction.allDeviceList.iterator();
		 * while(iterator.hasNext()) { ManageDevice managedevice =
		 * (ManageDevice)iterator.next(); ScanDevice scandevice = new
		 * ScanDevice(); scandevice.deviceName = managedevice.getDeviceName();
		 * scandevice.deviceType = managedevice.getDeviceType(); scandevice.id =
		 * managedevice.getTerminalId(); scandevice.lock =
		 * managedevice.getDeviceLock(); scandevice.mac =
		 * managedevice.getDeviceMac(); scandevice.password =
		 * managedevice.getDevicePassword(); scandevice.publicKey =
		 * managedevice.getPublicKey(); scandevice.subDevice =
		 * (short)managedevice.getSubDevice();
		 * EairApplaction.mNetWorkManager.addDevice(scandevice); }
		 */

	}

	private void toActivity() {

		Intent intent = new Intent();
		intent.setClass(this, BasenMainActivity.class); // �豸����
		startActivity(intent);

		finish();

	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.basen_launcher_layout);

		querAllDevice();
         Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>LoadingActivity");
		(new Timer()).schedule(

				new TimerTask() {
					public void run() {
						toActivity();
					}
				}, 2000L);
	}

}
