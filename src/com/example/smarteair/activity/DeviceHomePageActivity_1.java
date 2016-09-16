
package com.example.smarteair.activity;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.net.Defines;
import com.example.smarteair.net.EairControler;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.view.FullProgressDialog;
import com.zlj.basen.BaseActivity;
import com.zlj.basen.activity.sub.OpenActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DeviceHomePageActivity_1 extends BaseActivity implements DeviceStatusChangeListener {

	private boolean mInRefreshIng = false;

	private EairInfo mEairInfo;
	private EairControler mEairController;

	private int mWifiSN;

	private FullProgressDialog mProgressDialog;

	Looper looper = Looper.myLooper();
	NetWorkManager mNetworkManager;
	private int mOldWorkMode = 0xee;

	ImageButton onButton;
	ImageButton offButton;
	ImageButton pauseButton;

	ImageView imageView;
	private AnimationDrawable animationDrawable;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.device_home_page_layout_1);

		onButton = (ImageButton) this.findViewById(R.id.onBtn);
		offButton = (ImageButton) this.findViewById(R.id.offBtn);
		pauseButton = (ImageButton) this.findViewById(R.id.pauseBtn);
		imageView = (ImageView) this.findViewById(R.id.img);

		onButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageView.setImageResource(R.anim.open);
				animationDrawable = (AnimationDrawable) imageView.getDrawable();
				animationDrawable.start();
			}
		});
		offButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageView.setImageResource(R.anim.close);
				animationDrawable = (AnimationDrawable) imageView.getDrawable();
				animationDrawable.start();

			}
		});
		pauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				animationDrawable = (AnimationDrawable) imageView.getDrawable();
				animationDrawable.stop();
			}
		});

		mEairInfo = EairApplaction.mControlDevice.getEairInfo();
		mWifiSN = mEairInfo.sn;

		mEairController = EairControler.getInstance(this);
		mNetworkManager = NetWorkManager.getInstance(this);
		mNetworkManager.setDeviceStatusChangeListener(this);
	}

	public boolean onKeyDown(int i, KeyEvent keyevent) {
		if (i == 82) {
			/*
			 * Intent intent = new Intent(); intent.setClass(this,
			 * com/tcl/eair/activity/WebActivity); startActivity(intent);
			 */
		}
		return super.onKeyDown(i, keyevent);
	}

	protected void onPause() {
		super.onPause();
		mInRefreshIng = false;

		// mNetworkManager.setDeviceStatusChangeListener(null);
		mNetworkManager.JniNetWorkPause();
	}

	protected void onResume() {
		super.onResume();

		mInRefreshIng = true;

		mNetworkManager.setDeviceStatusChangeListener(this);
		mNetworkManager.JniNetWorkResume();

		if (mOldWorkMode != mEairInfo.workMode) {
			mOldWorkMode = mEairInfo.workMode;
			if (mEairInfo.workMode == 0) {
				DeviceHomePageActivity_1.this.finish();
				Intent intent = new Intent();
				intent.setClass(DeviceHomePageActivity_1.this, OpenActivity.class);
				startActivity(intent);
			}
		}

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
							if (mEairInfo.workMode == 0) {
								DeviceHomePageActivity_1.this.finish();
								Intent intent = new Intent();
								intent.setClass(DeviceHomePageActivity_1.this, OpenActivity.class);
								startActivity(intent);
							}
						}

					}
				});
			}
		}

	}

	@Override
	protected void onDestroy() {
		dismissProgessDialog();

		// mNetworkManager.setDeviceStatusChangeListener(null);
		super.onDestroy();
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
			mProgressDialog.showProgressDialog(DeviceHomePageActivity_1.this, mDialogListener);
		}
	}

	private void dismissProgessDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismissProressDialog();
		}
	}

}
