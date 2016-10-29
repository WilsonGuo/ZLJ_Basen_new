// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst noctor space 

package com.zlj.basen.activity.sub;

import org.apache.http.util.ByteArrayBuffer;

import com.example.basen.R;
import com.example.smarteair.data.ApConfigCallback;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.net.WifiAdmin;
import com.zlj.basen.BaseActivity;
import com.zlj.basen.BasenMainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConfigDeviceActivity extends BaseActivity {

	private LinearLayout mConfigStartLayout;
	private LinearLayout mConfigingLayout;
	private boolean mInConfig;
	private EditText mPassWord;
	private String mSSID;
	private String strPassWord;

	private CheckBox mShowPassword;
	private EditText mSsidValue;
	private ImageButton mSubmitButton;
	private SharedPreferences mWifiSharedPreferences;

	private WifiAdmin mWifiAdmin;

	NetWorkManager mNetworkManager;
	ImageButton backBtn;
	
	Context context;
	final int START_AP_CONFIG=100;
	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case START_AP_CONFIG:
				
				break;

			default:
				break;
			}
			
			
		}
		
	};

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		context=this;
		setContentView(R.layout.config_device_layout);
		setTitle(R.string.add_device);
		mWifiSharedPreferences = getSharedPreferences("shared_wifi", 0);
		findView();
		initView();
		backBtn = (ImageButton) this.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConfigDeviceActivity.this.finish();
			}
		});
		mNetworkManager = NetWorkManager.getInstance(this);

		myWifiReceiver = new MyWifiReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		registerReceiver(myWifiReceiver, intentFilter);
	}

	protected void onResume() {
		super.onResume();
		checkWifi();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(myWifiReceiver);
	}

	public ConfigDeviceActivity() {
		mInConfig = false;

		ByteArrayBuffer bab = new ByteArrayBuffer(10);
		bab.length();
		bab.toByteArray();
	}

	private void closeInputMethod() {
		InputMethodManager inputmethodmanager = (InputMethodManager) getSystemService("input_method");
		if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null)
			inputmethodmanager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
	}

	private void findView() {
		mConfigingLayout = (LinearLayout) findViewById(R.id.config_layout);
		mConfigStartLayout = (LinearLayout) findViewById(R.id.config_start_layout);
		mSsidValue = (EditText) findViewById(R.id.ssid);
		mPassWord = (EditText) findViewById(R.id.password);
		mShowPassword = (CheckBox) findViewById(R.id.pass_show);
		mSubmitButton = (ImageButton) findViewById(R.id.config_btn);
	}

	private void initView() {

		mConfigingLayout.setVisibility(View.GONE);
		mSubmitButton.setVisibility(View.VISIBLE);
		mConfigStartLayout.setVisibility(View.VISIBLE);

		mShowPassword.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton compoundbutton, boolean flag) {
				if (flag) {
					mPassWord.setInputType(144);
					mPassWord.setSelection(mPassWord.getText().length());
					return;
				} else {
					mPassWord.setInputType(129);
					mPassWord.setSelection(mPassWord.getText().length());
					return;
				}
			}

		});

		mSubmitButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				String s = Context.WIFI_SERVICE;
				 mNetworkManager.JniExitScanMode();
				if (!mInConfig) {
					mInConfig = true;
					mConfigingLayout.setVisibility(View.VISIBLE);
					mConfigStartLayout.setVisibility(View.GONE);
					mSubmitButton.setVisibility(View.GONE);
					mSSID = mSsidValue.getText().toString();
					strPassWord = mPassWord.getText().toString();
					newDeviceConfigWiFi();
					mPassWord.setEnabled(false);
					mSsidValue.setEnabled(false);

					mNetworkManager.setAPConfigCallback(mAPConfigCallback1);
					mNetworkManager.configAP(mSSID, strPassWord);

				}

			}
		});

	}

	private void newDeviceConfigWiFi() {
		android.content.SharedPreferences.Editor editor = mWifiSharedPreferences.edit();
		editor.putString(mSSID, mPassWord.getText().toString());
		editor.commit();

	}

	private void toHomePageActivity() {
		Intent intent = new Intent();
		intent.setClass(this, BasenMainActivity.class);
		startActivity(intent);
		finish();
	}

	public void back() {
		if (mInConfig) {
			mInConfig = false;
			mNetworkManager.cancleAPConfig();
		}

		//mNetworkManager.JniExitScanMode();

		finish();
	}

	public void checkWifi() {
		WifiManager wifimanager;
		wifimanager = (WifiManager) getSystemService("wifi");
		mSSID = "";
		String s;
		String s1 = mSsidValue.getText().toString();

		if (s1 == null || !s1.isEmpty()) {
			return;
		}

		WifiInfo wifiinfo = wifimanager.getConnectionInfo();
		s = wifiinfo.getSSID();

		if (s != null) {
			mSSID = s;
		}

		mSSID = mSSID.replace("\"", "");

		if (WifiAdmin.isWifiConnect(this) && mSSID != null && !mSSID.isEmpty()) {
			mSsidValue.setText(mSSID);
			mSsidValue.setSelection(mSsidValue.length());
			mSsidValue.clearFocus();
			mSsidValue.requestFocus();

			String passwd = mWifiSharedPreferences.getString(mSSID, null);
			if (passwd != null) {
				passwd = passwd.replace("\"", "");
				mPassWord.setText(passwd);
				mPassWord.setSelection(passwd.length());
				mPassWord.clearFocus();
				mPassWord.requestFocus();
			}
		}

	}

	public boolean onTouchEvent(MotionEvent motionevent) {
		if (motionevent.getAction() == 0)
			closeInputMethod();

		return super.onTouchEvent(motionevent);
	}

	private ApConfigCallback mAPConfigCallback1 = new ApConfigCallback() {

		@Override
		public void notifyConfigResult(Message msg) {
			int resultCode = msg.arg1;

			if (resultCode == 0) {
				Toast.makeText(ConfigDeviceActivity.this, "配置错误,请检查输入信息", Toast.LENGTH_SHORT).show();
				mInConfig = false;
				// mNetworkManager.exit();
//				toHomePageActivity();
				finish();
			} else {
				Toast.makeText(ConfigDeviceActivity.this, "配置成功", Toast.LENGTH_SHORT).show();
//				toHomePageActivity();
				finish();
				// mNetworkManager.exit();
				mInConfig = false;

				mWifiAdmin=WifiAdmin.getInstance(context);
				mWifiAdmin.closeWifi();
				mWifiAdmin.removewifi("WIFI-CFG");
				mHandler.postDelayed( new Runnable() {
					public void run() {
						mWifiAdmin.openWifi();
					}
				}, 1000);
				
			}
		}
	};

	MyWifiReceiver myWifiReceiver;

	class MyWifiReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			Log.e("TAG", " intent is " + intent.getAction());
			if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
				// 有可能是正在获取，或者已经获取了
				Log.e("TAG", " intent is " + WifiManager.RSSI_CHANGED_ACTION);
//				if (mWifiAdmin.isWifiContected(context) == WifiAdmin.WIFI_CONNECTED) {
//					handler.sendEmptyMessage(REFLUSH);
//				} else if (mWifiAdmin.isWifiContected(mContext) == WifiAdmin.WIFI_CONNECT_FAILED) {
//					handler.sendEmptyMessage(REFLUSH_UNCONNECT);
//				} else if (mWifiAdmin.isWifiContected(mContext) == WifiAdmin.WIFI_CONNECTING) {
//					handler.sendEmptyMessage(REFLUSH_CONNECTING);
//				}
			} else if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
				// 取得WifiManager对象
				WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				// 取得WifiInfo对象
				WifiInfo curWifiInfo = mWifiManager.getConnectionInfo();
                Log.e("TAG", "wifi***************curWifiInfo.getSSID()= "+curWifiInfo.getSSID() );
				if (curWifiInfo==null||curWifiInfo.getSSID().equals("WIFI-CFG")) {
					
				}
			
			}
			
			
//			if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
//				// signal strength changed
//			} else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {// wifi连接上与否
//                Log.e("TAG", "wifi***************wifi网络状态改变 " );
//
//				NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//				if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//                    Log.e("TAG", "wifi***************wifi网络连接断开 " );
//
//				} else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
//
//					WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//
//					// 获取当前wifi名称
//                     Log.e("TAG", "wifi***************连接到网络 " + wifiInfo.getSSID());
//				}
//
//			} else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {// wifi打开与否
//				int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
//
//				if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
//                    Log.e("TAG", "wifi***************系统关闭wifi " );
//
//				} else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
//                    Log.e("TAG", "wifi***************系统开启wifi");
//
//				}
//			}
		}
	}

}
