
package com.example.smarteair.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.net.Defines;
import com.example.smarteair.net.EairControler;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.view.FullProgressDialog;
import com.example.smarteair.view.OnSingleClickListener;
import com.zlj.basen.BaseActivity;
import com.zlj.basen.activity.main.DeviceInfoActivity;

public class OpenActivity extends BaseActivity implements DeviceStatusChangeListener {
	class GetWeatherTask extends AsyncTask {

		protected Object doInBackground(Object aobj[]) {
			return aobj;
		}

		protected void onPostExecute(Object obj) {

		}

		GetWeatherTask() {
		}
	}

	class RefreshAirThread extends Thread {
		public void run() {

		}

		RefreshAirThread() {
		}
	}

	private boolean mInRefreshIng;
	private TextView mLocationPmText;
	private TextView mLocationWeatherText;
	private Button mOpenButton;
	private TextView mPMText;
	private TextView mPmValueText;
	private RefreshAirThread mRefreshEairThread;
	private EairInfo mEairInfo;
	private EairControler mEairController;
	private FullProgressDialog mProgressDialog;
	private NetWorkManager mNetworkManager;
	private int mOldWorkMode;

	private void controlEair(byte abyte0[]) {

	}

	private void findView() {
		mOpenButton = (Button) findViewById(R.id.btn_open);
		mLocationPmText = (TextView) findViewById(R.id.location_pm);
		mLocationWeatherText = (TextView) findViewById(R.id.location_text);
		mPMText = (TextView) findViewById(R.id.pm);
		mPmValueText = (TextView) findViewById(R.id.pm_value);
	}

	private void initView() {

	}

	private void setListener() {
		mOpenButton.setOnClickListener(new OnSingleClickListener() {

			public void doOnClick(View view) {
				showProgressDialog();
				mEairController.airOpen(mEairInfo.sn);

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.local_weath_layout);

		findView();
		setListener();

		mEairInfo = EairApplaction.mControlDevice.getEairInfo();

		mEairController = EairControler.getInstance(this);
		mNetworkManager = NetWorkManager.getInstance(this);

	}

	protected void onPause() {
		super.onPause();
		mInRefreshIng = false;
		if (mRefreshEairThread != null) {
			mRefreshEairThread.interrupt();
			mRefreshEairThread = null;
		}

	}

	protected void onResume() {
		super.onResume();
		initView();
		mInRefreshIng = true;
		if (mRefreshEairThread == null) {
			mRefreshEairThread = new RefreshAirThread();
			mRefreshEairThread.start();
		}

		mNetworkManager.setDeviceStatusChangeListener(this);
	}

	@Override
	public void doCallBack(int type, int data, DataInfo datainfo) {

		if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT) {
			if (data != 0) {
				dismissProgessDialog();
			}
		} else if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA) {
			// if(datainfo.len == EairControler.CONTROL_PACKERT_IN_SIZE)
			{
				if (mEairInfo.sn != datainfo.sn) {
					return;
				}
				mEairController.parseInfo(datainfo.sn, mEairInfo, datainfo.pkt);

				dismissProgessDialog();

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						if (mOldWorkMode != mEairInfo.workMode) {
							mOldWorkMode = mEairInfo.workMode;
							if (mEairInfo.workMode > 0) {
								OpenActivity.this.finish();

								if (mEairInfo.deviceType == EairControler.TYPE_T2) {
									Intent intent = new Intent();
									intent.setClass(OpenActivity.this, DeviceInfoActivity.class);
									startActivity(intent);
								} else if (mEairInfo.deviceType == EairControler.TYPE_T1) {
									Intent intent = new Intent();
									intent.setClass(OpenActivity.this, DeviceInfoActivity.class);
									startActivity(intent);
								} else if (mEairInfo.deviceType == EairControler.TYPE_CENTER) {
									Intent intent = new Intent();
									intent.setClass(OpenActivity.this, DeviceInfoActivity.class);
									startActivity(intent);
								}

							}
						}
					}
				});
			}
		}
	}

	private OnDismissListener mDialogListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface arg0) {

			mProgressDialog = null;
		}
	};

	private void showProgressDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new FullProgressDialog();
			mProgressDialog.showProgressDialog(OpenActivity.this, mDialogListener);
		}
	}

	private void dismissProgessDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismissProressDialog();
		}
	}

}
