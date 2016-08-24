
package com.zlj.basen.activity.sub;

import java.util.ArrayList;
import java.util.List;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.adapter.SelectAdapter;
import com.example.smarteair.data.AddDeviceCallBack;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.data.ManageDevice;
import com.example.smarteair.data.ScanDevice;
import com.example.smarteair.data.ScanDeviceListener;
import com.example.smarteair.net.Defines;
import com.example.smarteair.net.EairControler;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.view.FullProgressDialog;
import com.zlj.basen.BaseActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScanDeviceActivity extends BaseActivity implements AddDeviceCallBack, DeviceStatusChangeListener {

	private boolean mInConfig;

	private ImageButton mConfirmButton;

	NetWorkManager mNetworkManager;

	private ListView mDeviceListView;
	private TextView mNoDevice;
	private List<ScanDevice> mGotDevice;
	private List<ScanDevice> mCheckedDevice;
	private List<ScanDevice> mQueryDevice;

	private FullProgressDialog mProgressDialog;
	private SelectAdapter mSimpleAdapter;
	private ScanDevice mNewDevice;
	private ScanDevice mProcDevice;
	private EairControler mEairController;
	private boolean mLastOneAdd;

	public ScanDeviceActivity() {
		mInConfig = false;
	}

	private void findView() {
		mConfirmButton = (ImageButton) findViewById(R.id.add_to_list_btn);
		mDeviceListView = (ListView) findViewById(R.id.scaning_device_list);
		mNoDevice = (TextView) findViewById(R.id.no_device_found);

	}

	private void initView() {

		mConfirmButton.setVisibility(View.VISIBLE);
		mConfirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mNetworkManager.setScanDeviceListener(null);

				if (mSimpleAdapter != null) {
					showProgressDialog();

					mCheckedDevice.clear();
					mQueryDevice.clear();
					for (int i = 0; i < mSimpleAdapter.getCount(); i++) {

						if (mGotDevice.get(i).checked) {
							mCheckedDevice.add(mGotDevice.get(i));
						}

					}

					if (mCheckedDevice.size() == 0) {
						back();
					} else {
						SaveConfig();
					}
				} else {
					back();
				}

			}
		});

	}

	public void back() {
		finish();
	}

	private void queryProc() {
		Log.e("TAG", "*************************************>>>>>queryProc()");

		if (mQueryDevice.size() > 0) {
			Log.e("TAG", "*************************************>>>>>mQueryDevice.size() > 0");

			ScanDevice sd = mQueryDevice.get(0);

			EairApplaction.mNetWorkManager.JniActiveDevice(sd.id);
			Log.e("TAG", "**************************************>>>>>JniActiveDevice");
			mProcDevice = sd;
			mEairController.airQueryState(mProcDevice.id);
			Log.e("TAG", "**************************************>>>>>airQueryState");

			mQueryDevice.remove(0);
			
		} else {
			Log.e("TAG", "**************************************>>>>>mQueryDevice.size() < 0");

			back();
		}

	}

	private void SaveConfig() {
		Log.e("TAG", "*************************************SaveConfig();");
		ScanDevice di = mCheckedDevice.get(0);
		mNewDevice = di;
		NetWorkManager.getInstance(this).AddNewWifiDev(di.id);
		mCheckedDevice.remove(0);

	}

	private boolean isNewID(int id) {
		for (ScanDevice di : mGotDevice) {
			if (di != null) {
				if (di != null && di.id == id) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		dismissProgessDialog();
		mNetworkManager.JniExitScanMode();
	}

	ImageButton backBtn;
	ImageButton showNewBtn;
	ImageButton showAllBtn;
	ImageButton addToListBtn;
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.scandevice_layout);
		setTitle(R.string.search_device);

		findView();
		initView();
		backBtn = (ImageButton) this.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ScanDeviceActivity.this.finish();
			}
		});
		
		showNewBtn=(ImageButton) this.findViewById(R.id.show_new_btn);
		showNewBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					showNewBtn.setImageResource(R.drawable.search_show_new_device_selected);
					showAllBtn.setImageResource(R.drawable.search_show_all);
					showNew=true;
					refreshView();
				}
				
				return false;
			}
		});
		
		showAllBtn=(ImageButton) this.findViewById(R.id.show_all_btn);
		showAllBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					
					showNewBtn.setImageResource(R.drawable.search_show_new_device);
					showAllBtn.setImageResource(R.drawable.search_show_all_selected);
					showNew=false;
					refreshView();
				}
				
				return false;
			}
		});
		
	
		mGotDevice = new ArrayList<ScanDevice>();
		mQueryDevice = new ArrayList<ScanDevice>();
		mCheckedDevice = new ArrayList<ScanDevice>();
		mNetworkManager = NetWorkManager.getInstance(this);
		// mNetworkManager.JniEntryScanMode();
		mEairController = EairControler.getInstance(this);
		mNetworkManager.JniEntryScanMode();

		mNetworkManager.setScanDeviceListener(new ScanDeviceListener() {

			public void deviceInfoCallBack(int id) {
				ScanDevice info = new ScanDevice();
				info.id = id;
				info.mac = Integer.toString(id & 0x7fffffff);
				info.deviceName = info.mac;
               Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>>info.deviceName="+info.deviceName);
				if (info != null && info.id != 0) {
					if (!isNewID(info.id)) {
						mGotDevice.add(info);
						refreshView();
					}
				}
			}
		});

		mNetworkManager.setAddDeviceCallBack(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
		// mNetworkManager.JniExitScanMode();
	}

	protected void onResume() {
		super.onResume();
		// mNetworkManager.JniEntryScanMode();
		mNetworkManager.setDeviceStatusChangeListener(this);
	}
    boolean showNew=true;
	private void refreshView() {

		if (mGotDevice.size() > 0) {
			mNoDevice.setVisibility(View.GONE);
		} else {
			mNoDevice.setVisibility(View.VISIBLE);
		}

		mSimpleAdapter = new SelectAdapter(this, mGotDevice);

		if (showNew) {
			mSimpleAdapter = new SelectAdapter(this, mQueryDevice);
		}else{
			mSimpleAdapter = new SelectAdapter(this, mGotDevice);
		}
		mDeviceListView.setAdapter(mSimpleAdapter);
		mDeviceListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				mProgressDialog = null;
				if (mGotDevice.get(position).checked) {
					mGotDevice.get(position).checked = false;
					mCheckedDevice.remove(mGotDevice.get(position));
				} else {
					mCheckedDevice.add(mGotDevice.get(position));
					mGotDevice.get(position).checked = true;
				}
				mSimpleAdapter.notifyDataSetChanged();

			}
		});

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
			mProgressDialog.showProgressDialog(ScanDeviceActivity.this, mDialogListener);
		}
	}

	private void dismissProgessDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismissProressDialog();
		}
	}

	@Override
	public void onDeviceAdded(int wifi_sn, int res) {

		if (res == 0) {
			Log.e("TAG", "*************************************res===0");
			mQueryDevice.add(mNewDevice);
		} else {
			Log.e("TAG", "*************************************res!!!=0");

			String s = getResources().getString(R.string.add_device) + wifi_sn
					+ getResources().getString(R.string.fail);
			Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
		}

		if (mCheckedDevice.size() > 0) {
			Log.e("TAG", "*************************************mCheckedDevice.size() > 0");

			SaveConfig();
		} else {
			Log.e("TAG", "*************************************mCheckedDevice.size() < 0");

			if (mQueryDevice.size() > 0) {
				Log.e("TAG", "*************************************mQueryDevice.size() > 0");

				mNetworkManager.JniExitScanMode();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						queryProc();
						Log.e("TAG", "*************************************queryProc();");

					}
				}, 3000L);
			} else {
				Log.e("TAG", "*************************************mQueryDevice.size() < 0");

				back();
			}
		}

	}

	@Override
	public void doCallBack(int type, int data, DataInfo datainfo) {

		if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT) {
			if (data != 0) {
				// dismissProgessDialog();

				if (datainfo != null) {
					String s = getResources().getString(R.string.add_device) + datainfo.sn
							+ getResources().getString(R.string.fail);
					Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
				}

			}

		} else if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA) {

			if (mProcDevice == null || datainfo == null) {
				return;
			}

			if (datainfo.sn != mProcDevice.id) {
				return;
			}

			String s = getResources().getString(R.string.add_device) + getResources().getString(R.string.success);
			Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

			boolean newOne = true;
			for (ManageDevice md : EairApplaction.allDeviceList) {
				if (md.terminalId == datainfo.sn) {
					newOne = false;
					break;
				}
			}

			if (newOne) {

				EairInfo eairinfo = new EairInfo();
				if (eairinfo != null) {
					mEairController.parseInfo(datainfo.sn, eairinfo, datainfo.pkt);
				}

				// ManageDevice md = new ManageDevice();
				String name = getResources().getString(R.string.goodneight_device);
				if (eairinfo.deviceType == EairControler.TYPE_T2) {
					name = getResources().getString(R.string.t2_name);
				} else if (eairinfo.deviceType == EairControler.TYPE_CENTER) {
					name = getResources().getString(R.string.center_name);
				} else if (eairinfo.deviceType == EairControler.TYPE_T1) {
					name = getResources().getString(R.string.t1_name);
				}

				EairApplaction app = (EairApplaction) getApplication();
				if (app != null) {
					mProcDevice.deviceType = (short) eairinfo.deviceType;
					mProcDevice.deviceName = name;
					mProcDevice.mac = Integer.toString(eairinfo.sn & 0x7fffffff);
					app.checkCreateOrUpdateScanDevice(mProcDevice, eairinfo);

					EairApplaction.mNetWorkManager.JniActiveDevice(eairinfo.sn);
				}
			}

			if (mQueryDevice.size() > 0) {
				queryProc();
			} else {
				dismissProgessDialog();
				back();
			}

		}

	}

}
