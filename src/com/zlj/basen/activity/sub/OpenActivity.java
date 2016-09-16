
package com.zlj.basen.activity.sub;

import com.example.basen.R;
import com.example.smarteair.EairApplaction;
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

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpenActivity extends BaseActivity implements DeviceStatusChangeListener {

	private boolean mInRefreshIng;
	private Button mOpenButton;
	private EairInfo mEairInfo;
	private EairControler mEairController;
	private FullProgressDialog mProgressDialog;
	private NetWorkManager mNetworkManager;
	private int mOldWorkMode;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.local_weath_layout);

		findView();
		setListener();

		mEairInfo = EairApplaction.mControlDevice.getEairInfo();
		mEairController = EairControler.getInstance(this);
		mNetworkManager = NetWorkManager.getInstance(this);

	}

	private void findView() {
		mOpenButton = (Button) findViewById(R.id.btn_open);
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

	protected void onPause() {
		super.onPause();
		mInRefreshIng = false;
	}

	protected void onResume() {
		super.onResume();
		initView();
		mInRefreshIng = true;
		mNetworkManager.setDeviceStatusChangeListener(this);
	}

	@Override
	public void doCallBack(int type, int data, DataInfo datainfo) {

		if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT) {
			if (data != 0) {
				dismissProgessDialog();
			}
		} else if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA) {
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
