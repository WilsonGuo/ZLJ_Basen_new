
package com.example.smarteair.activity;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.net.Defines;
import com.example.smarteair.net.EairControler;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.view.BLTimeAlert;
import com.example.smarteair.view.BLWindAlert;
import com.example.smarteair.view.FullProgressDialog;
import com.example.smarteair.view.HumiSetAlert;
import com.example.smarteair.view.TimerPickerAlert;
import com.zlj.basen.BaseActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceHomePageActivity extends BaseActivity implements DeviceStatusChangeListener {

	class GetWeatherTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	class RefreshAirThread extends Thread {

		public void run() {

		}

		RefreshAirThread() {
		}
	}

	private TextView mSystemTime;

	private TextView mTimerStart;
	private TextView mTimerEnd;

	private TextView mButtonWindIn;
	private TextView mButtonWindOut;
	private TextView mButtonHeat;
	private TextView mButtonO1;
	private TextView mButtonWifi;

	private TextView mFilterInfo;

	private TextView mEairValueText;

	private TextView mFixHumi;

	private TextView mHumiIndoor;
	private TextView mHumiOutdoor;
	private TextView mTempIndoor;
	private TextView mTempOutdoor;

	private LinearLayout mBackGround;
	private Button mCloseButton;
	private TextView mEairRunStateText;

	private LinearLayout mFilterLayout;
	private boolean mInRefreshIng;

	private LinearLayout mOpenLayout;
	private Thread mRefreshEairThread;
	private Animation mRotateAnimation;
	private View mRunProgress;
	private TextView mRunTimeText;
	// private TextView mSilenceButton;
	private View mTimeIconBgView;
	private View mTimeIconView;
	private Animation mTimerAnimation;
	private LinearLayout mTimerLayout;
	private Animation mWindAnimation;
	private View mWindIconView;
	private LinearLayout mWindLayout;
	private TextView mWindInTextView;
	private TextView mWindOutTextView;
	private EairInfo mEairInfo;
	private EairControler mEairController;

	private int mWifiSN;

	

	private LinearLayout mTimerInfo;
	
	private Dialog mHumiDialog;
	private Dialog mModeDialog;
	private FullProgressDialog mProgressDialog;
	
	Looper looper = Looper.myLooper();
	NetWorkManager mNetworkManager;

	private final String TITLE = "itemTitle";
	private int mOldWorkMode = 0xee;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.device_home_page_layout);

		mEairInfo = EairApplaction.mControlDevice.getEairInfo();
		mWifiSN = mEairInfo.sn;

		loadAnim();
		findView();
		setListener();

		mEairController = EairControler.getInstance(this);
		mNetworkManager = NetWorkManager.getInstance(this);
		mNetworkManager.setDeviceStatusChangeListener(this);
	}

	public DeviceHomePageActivity() {
		mInRefreshIng = false;
	}

	private void controlEair(byte abyte0[]) {

	}

	private void findView() {
		mRunProgress = findViewById(R.id.run_progress);
		mEairValueText = (TextView) findViewById(R.id.eair_value);
		mEairRunStateText = (TextView) findViewById(R.id.eair_run_state);
		mWindInTextView = (TextView) findViewById(R.id.wind_in_textview);
		mWindOutTextView = (TextView) findViewById(R.id.wind_out_textview);

		mCloseButton = (Button) findViewById(R.id.btn_close);

		mWindIconView = findViewById(R.id.wind_icon);
		mTimeIconBgView = findViewById(R.id.timer_icon_bg);
		mTimeIconView = findViewById(R.id.timer_icon);
		mWindLayout = (LinearLayout) findViewById(R.id.wind_layout);
		mTimerLayout = (LinearLayout) findViewById(R.id.timer_layout);

		mSystemTime = (TextView) findViewById(R.id.goodnight_system_time);

		mTimerStart = (TextView) findViewById(R.id.goodnight_timer_start);
		mTimerEnd = (TextView) findViewById(R.id.goodnight_timer_stop);

		mButtonWindIn = (TextView) findViewById(R.id.btn_wind_in);
		mButtonWindOut = (TextView) findViewById(R.id.btn_wind_out);
		mButtonHeat = (TextView) findViewById(R.id.btn_heat);
		mButtonO1 = (TextView) findViewById(R.id.btn_o1);
		mButtonWifi = (TextView) findViewById(R.id.wifi_info);

		mFilterInfo = (TextView) findViewById(R.id.filter_info_content);
		mEairValueText = (TextView) findViewById(R.id.eair_value);

		mHumiIndoor = (TextView) findViewById(R.id.goodnight_humidity_indoor);
		mHumiOutdoor = (TextView) findViewById(R.id.goodnight_humidity_outdoor);
		mTempIndoor = (TextView) findViewById(R.id.goodnight_temperature_indoor);
		mTempOutdoor = (TextView) findViewById(R.id.goodnight_temperature_outdoor);

		mOpenLayout = (LinearLayout) findViewById(R.id.open_layout);
		mBackGround = (LinearLayout) findViewById(R.id.eair_bg);

		mFilterLayout = (LinearLayout) findViewById(R.id.filter_info_layout);

		mFixHumi = (TextView) findViewById(R.id.humi_set);

		mTimerInfo = (LinearLayout) findViewById(R.id.goodnight_timer_info);

		mRunTimeText = (TextView) findViewById(R.id.run_time);

	}

	private void initEairView() {
		if (mFilterInfo != null) {
			mFilterInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_frame));
		}

		if (mFixHumi != null) {
			mFixHumi.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_frame));
		}

		if (mEairInfo == null && !mInRefreshIng)
			return;

		// mEairRunStateText.setText(R.string.room_air);
		mRunProgress.setVisibility(View.VISIBLE);
		// mRunProgress.setBackgroundDrawable(new
		// ColorDrawable(R.color.color_air_good));
		mEairValueText.setBackgroundDrawable(null);

		mEairValueText.setText(Integer.toString(mEairInfo.air));

		if (mEairInfo.air < 2) {
			mEairRunStateText.setText(R.string.air_best);
		} else if (mEairInfo.air < 4) {
			mEairRunStateText.setText(R.string.air_good);
		} else if (mEairInfo.air < 8) {
			mEairRunStateText.setText(R.string.air_normal);
		} else {
			mEairRunStateText.setText(R.string.air_bad);
		}

		int id = R.string.mode_manual;
		switch (mEairInfo.workMode) {
		case EairControler.MODE_AUTO:
			id = R.string.mode_auto;
			break;
		case EairControler.MODE_WINTER:
			id = R.string.mode_winter;
			break;
		

		default:
		
		}

		if (mRunTimeText != null) {
			mRunTimeText.setText(id);
		}

		if (mHumiIndoor != null) {
			mHumiIndoor.setText(String.format("%d%%", mEairInfo.indoorHumi));
		}

		if (mHumiOutdoor != null) {
			mHumiOutdoor.setText(String.format("%d%%", mEairInfo.outdoorHumi));
		}

		if (mTempIndoor != null) {
			mTempIndoor
					.setText(String.format("%d%s", mEairInfo.indoorTemp, getResources().getString(R.string.degree_c)));
		}

		if (mTempOutdoor != null) {
			mTempOutdoor
					.setText(String.format("%d%s", mEairInfo.outdoorTemp, getResources().getString(R.string.degree_c)));
		}

		if (mFixHumi != null) {
			mFixHumi.setText(String.format("%s%d%%", getResources().getString(R.string.humi_set), mEairInfo.fixHumi));
		}

		if (mSystemTime != null) {
			mSystemTime.setText(String.format("%s%02d:%02d", getResources().getString(R.string.device_time),
					mEairInfo.sysTimeHour, mEairInfo.sysTimeMin));
		}

		if (mTimerStart != null) {
			mTimerStart.setText(String.format("%s%02d:%02d", getResources().getString(R.string.timer_start_pre),
					mEairInfo.timerStartHour, mEairInfo.timerStartMin));
		}

		if (mTimerEnd != null) {
			mTimerEnd.setText(String.format("%s%02d:%02d", getResources().getString(R.string.timer_stop_pre),
					mEairInfo.timerEndHour, mEairInfo.timerEndMin));
		}

		if (1440 - mEairInfo.filterTime < 56) {
			/*
			 * if (mEairInfo.air == 4)
			 * mFilterIconView.setImageResource(R.drawable.info_yellow); else
			 * mFilterIconView.setImageResource(R.drawable.info_red);
			 */
		} else {
			// mFilterIconView.setImageResource(R.drawable.info);
		}

		Drawable topDrawable;
		if (mEairInfo.heat) {
			topDrawable = getResources().getDrawable(R.drawable.state_heat_high);
			mButtonHeat.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		} else {
			topDrawable = getResources().getDrawable(R.drawable.state_heat);
			mButtonHeat.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		}

		if (mEairInfo.o1) {
			topDrawable = getResources().getDrawable(R.drawable.state_o1_high);
			mButtonO1.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);

		} else {
			topDrawable = getResources().getDrawable(R.drawable.state_o1);
			mButtonO1.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		}

		if (mEairInfo.windIn) {

			topDrawable = getResources().getDrawable(R.drawable.state_wind_in_high);
			mButtonWindIn.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		} else {
			topDrawable = getResources().getDrawable(R.drawable.state_wind_in);
			mButtonWindIn.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		}

		if (mEairInfo.windOut) {
			topDrawable = getResources().getDrawable(R.drawable.state_wind_out_high);
			mButtonWindOut.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		} else {
			topDrawable = getResources().getDrawable(R.drawable.state_wind_out);
			mButtonWindOut.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
		}

		if (mWindInTextView != null) {
			if (!mEairInfo.windIn) {
				mWindInTextView.setText(R.string.text_wind_in_stop);
				;
			} else {
				String s = String.format("%s %d", getResources().getString(R.string.text_wind_in),
						mEairInfo.windInLevel);
				mWindInTextView.setText(s);
			}
		}

		if (mWindOutTextView != null) {
			if (!mEairInfo.windOut) {
				mWindOutTextView.setText(R.string.text_wind_out_stop);
				;
			} else {
				String s = String.format("%s %d", getResources().getString(R.string.text_wind_out),
						mEairInfo.windOutLevel);
				mWindOutTextView.setText(s);
			}
		}

		if ((!mEairInfo.windIn) && (!mEairInfo.windOut)) {
			mWindIconView.clearAnimation();
		} else {
			mWindIconView.startAnimation(mWindAnimation);
		}

		mWindInTextView = (TextView) findViewById(R.id.wind_in_textview);
		mWindOutTextView = (TextView) findViewById(R.id.wind_out_textview);

	}

	private void initWeatherView() {

	}

	private void loadAnim() {
		mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.air_speed_rotate);
		mWindAnimation = AnimationUtils.loadAnimation(this, R.anim.wind_anim);
		mWindAnimation.setDuration(20000L);
		mTimerAnimation = AnimationUtils.loadAnimation(this, R.anim.timer_anim);

	}

	private void setListener() {
		mWindLayout.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View view) {
				showWindDialog();
			}
		});
		mTimerLayout.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View view) {
				showTimerDialog();

			}

		});
		mFilterLayout.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(DeviceHomePageActivity.this, FilterActivity.class);
				startActivity(intent);
			}
		});
		mFilterInfo.setOnTouchListener(mOnTouchListener);

		/*
		 * mButtonO1.setOnClickListener ( new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * mEairController.airOpenO1(mWifiSN); showProgressDialog(); } } );
		 */

		mCloseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (mNetworkManager.JniGetServiceLinkStatus() <= 0) {
					Toast.makeText(DeviceHomePageActivity.this,
							DeviceHomePageActivity.this.getResources().getString(R.string.network_lost),
							Toast.LENGTH_SHORT).show();
					return;
				}
				mEairController.airClose(mWifiSN);
				showProgressDialog();

			}

		});
		mOpenLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}

		});

		mFixHumi.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {

				int[] location = new int[2];
				view.getLocationOnScreen(location);

				showHumiSetDialog(location[0], location[1]);
			}

		});
		mFixHumi.setOnTouchListener(mOnTouchListener);

		mTimerInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				TimerPickerAlert.showAlert(DeviceHomePageActivity.this, 0, 0, 0, null);

			}
		});

		mButtonWindIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (mEairInfo.workMode == EairControler.MODE_FIX) {
					Toast.makeText(DeviceHomePageActivity.this, getResources().getString(R.string.fix_mode_no_wind_set),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (mEairInfo.windIn) {
					mEairController.airCloseWindIn(mEairInfo.sn);
				} else {
					mEairController.airOpenWindIn(mEairInfo.sn);
				}

			}
		});

		mButtonWindOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (mEairInfo.workMode == EairControler.MODE_FIX) {
					Toast.makeText(DeviceHomePageActivity.this, getResources().getString(R.string.fix_mode_no_wind_set),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (mEairInfo.windOut) {
					mEairController.airCloseWindOut(mEairInfo.sn);
				} else {
					mEairController.airOpenWindOut(mEairInfo.sn);
				}
			}
		});

		mButtonHeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (mEairInfo.heat) {
					mEairController.airOpenHeat(mEairInfo.sn);
				} else {
					mEairController.airCloseHeat(mEairInfo.sn);
				}
			}
		});

		mButtonO1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (mEairInfo.o1) {
					mEairController.airOpenO1(mEairInfo.sn);
				} else {
					mEairController.airCloseO1(mEairInfo.sn);
				}
			}
		});
	}

	private void showTimerDialog() {
		if (mEairInfo != null) {
			mModeDialog = BLTimeAlert.showAlert(this, mEairInfo.workMode, new BLTimeAlert.OnAlertSelectId() {
				public void onClick(int i) {
					mEairController.airSetMode(i, mEairInfo.sn);
					if (mModeDialog != null) {
						mModeDialog.dismiss();
						mModeDialog = null;
					}
				}
			});
		}
	}

	private void showWindDialog() {
		if (mEairInfo != null) {

			if (mEairInfo.workMode == EairControler.MODE_AUTO) {
				Toast.makeText(DeviceHomePageActivity.this, getResources().getString(R.string.auto_mode_no_wind_set),
						Toast.LENGTH_SHORT).show();
				return;
			}

			int level1 = mEairInfo.windInLevel;
			int level2 = mEairInfo.windOutLevel;

			if (!mEairInfo.windIn) {
				level1 = 0;
			}

			if (!mEairInfo.windOut) {
				level2 = 0;
			}

			BLWindAlert.showAlert(this, level1, level2, new BLWindAlert.OnAlertSelectId() {
				public void onClick(int windinLevel, int windoutLevel) {
					if (mEairInfo.windInLevel != windinLevel) {
						mEairInfo.windInLevel = windinLevel;
						if (windinLevel > 0) {
							mEairController.airSetWindInLevel((byte) windinLevel, mEairInfo.sn);
						} else {
							mEairController.airCloseWindIn(mEairInfo.sn);
						}
					}

					if (mEairInfo.windOutLevel != windoutLevel) {
						mEairInfo.windOutLevel = windoutLevel;
						if (windoutLevel > 0) {
							mEairController.airSetWindOutLevel((byte) windoutLevel, mEairInfo.sn);
						} else {
							mEairController.airCloseWindOut(mEairInfo.sn);
						}
					}

					// showProgressDialog();
				}
			});

		}
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
		mRunProgress.setVisibility(View.INVISIBLE);
		mRunProgress.clearAnimation();
		mInRefreshIng = false;

		if (mRefreshEairThread != null) {
			mRefreshEairThread.interrupt();
			mRefreshEairThread = null;
		}

		// mNetworkManager.setDeviceStatusChangeListener(null);
		mNetworkManager.JniNetWorkPause();
	}

	protected void onResume() {
		super.onResume();
		initWeatherView();
		initEairView();

		// mWindIconView.startAnimation(mWindAnimation);
		mRunProgress.startAnimation(mRotateAnimation);
		mInRefreshIng = true;

		if (mRefreshEairThread == null) {
			// mRefreshEairThread = new RefreshAirThread();
			// mRefreshEairThread.start();
		}

		mNetworkManager.setDeviceStatusChangeListener(this);
		mNetworkManager.JniNetWorkResume();

		if (mOldWorkMode != mEairInfo.workMode) {
			mOldWorkMode = mEairInfo.workMode;
			if (mEairInfo.workMode == 0) {
				DeviceHomePageActivity.this.finish();
				Intent intent = new Intent();
				intent.setClass(DeviceHomePageActivity.this, OpenActivity.class);
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

						initEairView();

						if (mOldWorkMode != mEairInfo.workMode) {
							mOldWorkMode = mEairInfo.workMode;
							if (mEairInfo.workMode == 0) {
								DeviceHomePageActivity.this.finish();
								Intent intent = new Intent();
								intent.setClass(DeviceHomePageActivity.this, OpenActivity.class);
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
			mProgressDialog.showProgressDialog(DeviceHomePageActivity.this, mDialogListener);
		}
	}

	private void dismissProgessDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismissProressDialog();
		}
	}

	private OnTouchListener mOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundColor(getResources().getColor(R.color.color_my_half_transparent));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {

				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_frame));
			}

			/*
			 * if(event.getAction() == MotionEvent.ACTION_DOWN) home_frame {
			 * v.getBackground().setAlpha(0xff); } else if(event.getAction() ==
			 * MotionEvent.ACTION_UP) { v.getBackground().setAlpha(0x00); }
			 */

			return false;
		}
	};

	private void showHumiSetDialog(int xOffset, int yOffset) {

		mHumiDialog = HumiSetAlert.showAlert(DeviceHomePageActivity.this, mEairInfo.sn, mEairInfo.fixHumi, xOffset,
				yOffset, new HumiSetAlert.OnAlertSelectId() {
					public void onClick(int i) {

						if (mHumiDialog != null) {
							mHumiDialog.dismiss();
							mHumiDialog = null;
						}

					}
				});

		mHumiDialog.setOnDismissListener(mHumiDialogListener);
	}

	private final OnDismissListener mHumiDialogListener = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface arg0) {
			mHumiDialog = null;
		}
	};

}
