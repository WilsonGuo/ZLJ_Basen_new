
package com.zlj.basen;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.activity.OptionActivity;
import com.example.smarteair.adapter.DeviceAdapter;
import com.example.smarteair.common.LogManager;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DelDeviceCallBack;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.data.ManageDevice;
import com.example.smarteair.data.ManageDeviceDao;
import com.example.smarteair.net.Defines;
import com.example.smarteair.net.EairControler;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.net.WifiAdmin;
import com.example.smarteair.view.FullProgressDialog;
import com.example.smarteair.view.ListOptionAlert;
import com.tencent.bugly.crashreport.CrashReport;
import com.zlj.basen.activity.main.DeviceInfoActivity;
import com.zlj.basen.activity.sub.EditDeviceInfoActivity;
import com.zlj.basen.activity.sub.OpenActivity;
import com.zlj.basen.activity.sub.ScanDeviceActivity;
import com.zlj.basen.activity.sub.SetActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BasenMainActivity extends BaseActivity implements DeviceStatusChangeListener {
	Button setBtn;
	private Button mAddDevice;
	private boolean mCanExit;
	private List<ManageDevice> mDeviceList;
	private ListView mDeviceListView;
	private DeviceAdapter mDeviceAdapter;
	private Timer mExitTimer;
	private Timer mRefreshDeviceTimer;

	private NetWorkManager mNetworkManager;
	private FullProgressDialog mProgressDialog;
	private EairControler mEairController;
	private TextView mNoDeviceView;
	private Dialog mOptionDialog;

	private int mOptionDeviceIndex = -1;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.basen_activity_main);
		setTitle(R.string.device_mange);
	

		findView();

		setListener();

		mEairController = EairControler.getInstance(this);
		mNetworkManager = NetWorkManager.getInstance(BasenMainActivity.this);
		
		initBugly();
	}
	
	/**
	 * 初始化Buggly
	 */
	public void initBugly() {
		CrashReport.initCrashReport(mApplication, "de2cb339f2", false);
	}

	protected void onPause() {
		super.onPause();
		if (mRefreshDeviceTimer != null) {
			mRefreshDeviceTimer.cancel();
			mRefreshDeviceTimer = null;
		}

		if (mProgressDialog != null) {
			mProgressDialog.dismissProressDialog();
		}

		if (mOptionDialog != null) {
			mOptionDialog.dismiss();
		}

		// mNetworkManager.setDeviceStatusChangeListener(null);

	}

	protected void onResume() {
		super.onResume();

		EairApplaction.mControlDevice = null;

	
		refreshState();

		mRefreshDeviceTimer = new Timer();
		mRefreshDeviceTimer.schedule(new TimerTask() {
			public void run() {
				refreshDeviceList();
			}
		}, 0L, 3000L);

		mNetworkManager.setDeviceStatusChangeListener(this);

	}

	private class CheckUpdateTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			return null;
		}
	}

	class DeleteDeviceThread extends Thread {

		public void run() {
		}
	}

	public BasenMainActivity() {
		// mDeviceList = new ArrayList<ManageDevice>();
		mDeviceList = EairApplaction.allDeviceList;
	}

	private void findView() {
		mDeviceListView = (ListView) findViewById(R.id.device_list_view);
		mAddDevice = (Button) findViewById(R.id.search_btn);
		setBtn = (Button) findViewById(R.id.set_btn);
		mNoDeviceView = (TextView) findViewById(R.id.no_device_view);
        setBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BasenMainActivity.this, SetActivity.class);
				startActivity(intent);
			}
		});
	}

	private void refreshDeviceList() {
		// mDeviceList.clear();
		// mDeviceList.addAll(EairApplaction.allDeviceList);

		runOnUiThread(new Runnable() {

			public void run() {
				// mDeviceAdapter.notifyDataSetChanged();
				refreshState();
			}
		});
	}

	private void setListener() {
		mAddDevice.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View view) {

				if (!WifiAdmin.isWifiConnect(BasenMainActivity.this)) {
					Toast.makeText(BasenMainActivity.this, getResources().getString(R.string.wifi_disconnect),
							Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent();
				intent.setClass(BasenMainActivity.this, ScanDeviceActivity.class);
				startActivity(intent);

			}
		}

		);

		mDeviceListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView adapterview, View view, int i, long l) {

				// EairApplaction.mNetWorkManager.JniDisActiveAll();

				if (!LogManager.isLogEnable()) {
					ManageDevice device = mDeviceList.get(i);

					if (EairApplaction.mNetWorkManager.JniGetServiceLinkStatus() <= 0) {
						Toast.makeText(BasenMainActivity.this, getResources().getString(R.string.check_network),
								Toast.LENGTH_SHORT).show();
						return;
					}
					if (EairApplaction.mNetWorkManager.JniGetDeviceLinkStatus(device.getEairInfo().sn) <= 0) {
						Toast.makeText(BasenMainActivity.this, getResources().getString(R.string.this_device_online),
								Toast.LENGTH_SHORT).show();
						return;
					}
					EairApplaction.mControlDevice = device;
					showProgressDialog();
					EairControler controller = EairControler.getInstance(BasenMainActivity.this);
					controller.airQueryState(device.terminalId);
				} else {
					ManageDevice device = mDeviceList.get(i);
					EairApplaction.mControlDevice = device;
					device.getEairInfo().workMode = 1;
					Intent intent = new Intent();
					intent.setClass(BasenMainActivity.this, DeviceInfoActivity.class);
					startActivity(intent);
				}
			}
		});

		mDeviceListView.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView adapterview, View view, int i, long l) {
				int[] location = new int[2];
				view.getLocationOnScreen(location);

				mOptionDeviceIndex = i;

				showOptionDialog(location[1]);
				return true;
			}
		});
	}

	public void onBackPressed() {
		// mNetworkManager.exit();
		if (mCanExit) {
			if (EairApplaction.mNetWorkManager != null) {
				EairApplaction.mNetWorkManager.networkDestory();
				EairApplaction.mNetWorkManager = null;
			}
			// finish();

			System.exit(0);

			return;
		} else {
			mCanExit = true;
			Toast.makeText(this, R.string.double_click_exit, 0).show();
			if (mExitTimer == null) {
				mExitTimer = new Timer();
			} else {
				mExitTimer.cancel();
			}

			mExitTimer.schedule(new TimerTask() {
				public void run() {
					mCanExit = false;
				}
			}, 1000L);
			return;
		}

	}

	private void refreshState() {
	
		for (ManageDevice device : mDeviceList) {
			int wifi_sn = device.terminalId;
			EairApplaction.mNetWorkManager.JniActiveDevice(wifi_sn);

		}

		if (mDeviceList.size() > 0) {
			mNoDeviceView.setVisibility(View.GONE);
			DeviceAdapter mSimpleAdapter = new DeviceAdapter(this, mDeviceList);

			mDeviceListView.setAdapter(mSimpleAdapter);
			mSimpleAdapter.notifyDataSetChanged();
		} else {
			mNoDeviceView.setVisibility(View.VISIBLE);
		}

	}

	private void getAllDeviceState() {
		Iterator iterator = EairApplaction.allDeviceList.iterator();
		do {
			if (!iterator.hasNext()) {
				// mPullToRefreshScrollView.onRefreshComplete();
				return;
			}

			ManageDevice managedevice = (ManageDevice) iterator.next();

			if (managedevice != null) {
				// managedevice1.setTclEairInfo(mBlHoneyWellDataParse.tclEairInfo(senddataresultinfo.data));
				mDeviceAdapter.notifyDataSetChanged();
			}
		} while (true);
	}

	private final OnClickListener mOptionClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(BasenMainActivity.this, OptionActivity.class);
			startActivity(intent);
		}
	};

	@Override
	public void doCallBack(int type, int data, DataInfo datainfo) {
		if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT) {
			if (data != 0) {
				dismissProgessDialog();
			}
		} else if (type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA) {
			// if(datainfo.len == EairControler.CONTROL_PACKERT_IN_SIZE)
			{

				if (EairApplaction.mControlDevice != null) {
					EairInfo eairinfo = EairApplaction.mControlDevice.getEairInfo();

					if (eairinfo.sn != datainfo.sn) {
						return;
					}
					mEairController.parseInfo(datainfo.sn, eairinfo, datainfo.pkt);

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							EairInfo eairinfo = EairApplaction.mControlDevice.getEairInfo();
							// EairApplaction.mNetWorkManager.JniActiveDevice(eairinfo.sn);
							Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>stateData:10="+eairinfo.workMode);
							if (eairinfo.workMode == 0) {
								Intent intent = new Intent();
								intent.setClass(BasenMainActivity.this, OpenActivity.class);
								startActivity(intent);
							} 
							else if (eairinfo.deviceType == EairControler.TYPE_T2) {
								Intent intent = new Intent();
								intent.setClass(BasenMainActivity.this, DeviceInfoActivity.class);
								startActivity(intent);
							} else if (eairinfo.deviceType == EairControler.TYPE_T1) {
								Intent intent = new Intent();
								intent.setClass(BasenMainActivity.this, DeviceInfoActivity.class);
								startActivity(intent);
							} else if (eairinfo.deviceType == EairControler.TYPE_CENTER) {
								Intent intent = new Intent();
								intent.setClass(BasenMainActivity.this, DeviceInfoActivity.class);
								startActivity(intent);
							} 
							else {
								Intent intent = new Intent();
								intent.setClass(BasenMainActivity.this, DeviceInfoActivity.class);
								startActivity(intent);
							}
							dismissProgessDialog();
						}
					});
				} else {

					/*
					 * for(ManageDevice md:mDeviceList) { EairInfo eairinfo =
					 * md.getEairInfo(); if(eairinfo.sn == datainfo.sn); {
					 * mEairController.parseInfo(datainfo.sn, eairinfo,
					 * datainfo.pkt); } }
					 */
				}
			}
		}

	}

	private final OnDismissListener mDialogListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface arg0) {
			mProgressDialog = null;
		}
	};

	private void showProgressDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new FullProgressDialog();
			mProgressDialog.showProgressDialog(BasenMainActivity.this, mDialogListener);
		}
	}

	private void dismissProgessDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismissProressDialog();
		}
	}

	private void showOptionDialog(int yOffset) {

		NetWorkManager.getInstance(this).setDelDeviceCallBack(mDelCallback);

		mOptionDialog = ListOptionAlert.showAlert(this, yOffset, new ListOptionAlert.OnAlertSelectId() {
			public void onClick(int i) {

				if (mOptionDialog != null) {
					mOptionDialog.dismiss();
					mOptionDialog = null;
				}

				if (i == 0 && mOptionDeviceIndex >= 0) {
					EairApplaction.mEditDevice = mDeviceList.get(mOptionDeviceIndex);

					Intent intent = new Intent();
					intent.setClass(BasenMainActivity.this, EditDeviceInfoActivity.class);
					startActivity(intent);
					mOptionDeviceIndex = -1;
				} else if (i == 1 && mOptionDeviceIndex >= 0) {
					if (mDeviceList.get(mOptionDeviceIndex) != null) {

						if (mDeviceList.get(mOptionDeviceIndex) != null) {
							try {
								(new ManageDeviceDao(getHelper())).deleteDevice(mDeviceList.get(mOptionDeviceIndex));
								mDeviceList.remove(mOptionDeviceIndex);
								refreshState();
							} catch (java.sql.SQLException e) {
								e.printStackTrace();
							}
						}

						mOptionDeviceIndex = -1;

						// showProgressDialog();
						// NetWorkManager.getInstance(DeviceActivity.this).removeWifiDev(mDeviceList.get(mOptionDeviceIndex).terminalId);
					}
				}
				/*
				 * else if(i == 2) {
				 * 
				 * try { ManageDeviceDao mdd = new ManageDeviceDao(getHelper());
				 * 
				 * for(ManageDevice md:mDeviceList) { mdd.deleteDevice(md); }
				 * 
				 * mDeviceList.clear(); refreshState(); } catch
				 * (java.sql.SQLException e) {
				 * 
				 * e.printStackTrace(); }
				 * 
				 * 
				 * }
				 */

			}
		});

		mOptionDialog.setOnDismissListener(mOptionDialogListener);
	}

	private final OnDismissListener mOptionDialogListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface arg0) {
			mOptionDialog = null;
		}
	};

	private final DelDeviceCallBack mDelCallback = new DelDeviceCallBack() {

		@Override
		public void onDeviceDeled(int id, int res) {
			/*
			 * dismissProgessDialog();
			 * 
			 * if(res == 0) {
			 * 
			 * if(mDeviceList.get(mOptionDeviceIndex) != null &&
			 * mDeviceList.get(mOptionDeviceIndex).terminalId == id) { try {
			 * (new ManageDeviceDao(getHelper())).deleteDevice(mDeviceList.get(
			 * mOptionDeviceIndex)); mDeviceList.remove(mOptionDeviceIndex);
			 * refreshState(); } catch (java.sql.SQLException e) {
			 * e.printStackTrace(); } } } else {
			 * 
			 * }
			 * 
			 * mOptionDeviceIndex = -1;
			 */

		}

	};

}
