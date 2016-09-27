
package com.zlj.basen.activity.sub;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.example.basen.R;
import com.example.smarteair.EairApplaction;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.data.ManageDevice;
import com.example.smarteair.data.ManageDeviceDao;
import com.zlj.basen.BaseActivity;
import com.zlj.basen.BasenMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LauncherActivity extends BaseActivity {

	LinearLayout basenLayout;
	LinearLayout nomalLayout;
	ImageView nomalImg;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.basen_launcher_layout);
		basenLayout = (LinearLayout) this.findViewById(R.id.basen_layout);
		nomalLayout = (LinearLayout) this.findViewById(R.id.nomal_layout);
		nomalImg = (ImageView) this.findViewById(R.id.nomal_bottom);
		querAllDevice();
		showBasen();
		(new Timer()).schedule(

				new TimerTask() {
					public void run() {
						toActivity();
					}
				}, 3000L);
	}

	public void showBasen() {
		basenLayout.setVisibility(View.VISIBLE);
		nomalLayout.setVisibility(View.INVISIBLE);
		nomalImg.setVisibility(View.INVISIBLE);
	}

	public void showNomal() {
		basenLayout.setVisibility(View.INVISIBLE);
		nomalLayout.setVisibility(View.VISIBLE);
		nomalImg.setVisibility(View.VISIBLE);
	}

	private void querAllDevice() {
		Iterator iterator = null;
		ManageDeviceDao managedevicedao;

		if (EairApplaction.mNetWorkManager == null) {
			mApplication.initNetWork();
		}
		try {
			managedevicedao = new ManageDeviceDao(getHelper());
			EairApplaction.allDeviceList.clear();
			EairApplaction.allDeviceList.addAll(managedevicedao.queryAllDevices());

			for (ManageDevice md : EairApplaction.allDeviceList) {
				EairInfo eairinfo = new EairInfo();
				eairinfo.sn = md.terminalId;
				md.setEairInfo(eairinfo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void toActivity() {

		Intent intent = new Intent();
		intent.setClass(this, BasenMainActivity.class); 
		startActivity(intent);

		finish();

	}
}
