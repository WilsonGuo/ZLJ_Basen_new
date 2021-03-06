package com.zlj.basen.activity.main;

import java.text.DecimalFormat;

import com.example.basen.R;
import com.example.smarteair.EairApplaction;
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
import com.zlj.basen.activity.sub.OpenActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceInfoActivity extends BaseActivity implements DeviceStatusChangeListener {
	ImageButton backBtn;
	private Dialog mHumiDialog;
	private Dialog mModeDialog;
	private FullProgressDialog mProgressDialog;

	private EairInfo mEairInfo;
	private EairControler mEairController;

	Looper looper = Looper.myLooper();
	NetWorkManager mNetworkManager;

	private int mOldWorkMode = 0xee;
	private boolean mInRefreshIng = false;
	LinearLayout base_timer_layout;
	TextView device_time;
	ImageView lever_procress;
	TextView lever_value;
	TextView lever_img;
	TextView lever_txt;

	TextView lever_pm25_value;
	TextView lever_CO2_value;
	TextView lever_TVOC_value;
	TextView lever__CH4O_value;
	TextView time_open;
	TextView time_close;

	TextView temp_in;
	TextView hump_in;
	TextView temp_out;
	TextView hump_out;
	TextView basen_hengshi_txt;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basen_device_info_layout);
		context = this;
		String name=getIntent().getStringExtra("name");
		inflater = LayoutInflater.from(context);
		viewLayout = (RelativeLayout) this.findViewById(R.id.viewLayout);
		backBtn = (ImageButton) this.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeviceInfoActivity.this.finish();
			}
		});

		base_timer_layout = (LinearLayout) this.findViewById(R.id.basen_timer_layout);
		device_time = (TextView) this.findViewById(R.id.device_time);
		lever_procress = (ImageView) this.findViewById(R.id.lever_procress);
		lever_value = (TextView) this.findViewById(R.id.lever_value);
		lever_img = (TextView) this.findViewById(R.id.lever_img);
		lever_txt = (TextView) this.findViewById(R.id.lever_txt);

		lever_pm25_value = (TextView) this.findViewById(R.id.lever_pm25_value);
		lever_CO2_value = (TextView) this.findViewById(R.id.lever_CO2_value);
		lever_TVOC_value = (TextView) this.findViewById(R.id.lever_TVOC_value);
		lever__CH4O_value = (TextView) this.findViewById(R.id.lever__CH4O_value);
		time_open = (TextView) this.findViewById(R.id.time_open);
		time_close = (TextView) this.findViewById(R.id.time_close);

		temp_in = (TextView) this.findViewById(R.id.temp_in);
		hump_in = (TextView) this.findViewById(R.id.hump_in);
		temp_out = (TextView) this.findViewById(R.id.temp_out);
		hump_out = (TextView) this.findViewById(R.id.hump_out);
		basen_hengshi_txt = (TextView) this.findViewById(R.id.basen_hengshi_txt);

		paifeng_lever = (TextView) this.findViewById(R.id.paifeng_lever);
		xinfeng_lever = (TextView) this.findViewById(R.id.xinfeng_lever);
		btn_paifeng_bg = (ImageView) this.findViewById(R.id.btn_paifeng_bg);
		btn_xinfeng_bg = (ImageView) this.findViewById(R.id.btn_xinfeng_bg);

		new_wind_layout = (RelativeLayout) this.findViewById(R.id.new_wind_layout);
		guanji_layout = (LinearLayout) this.findViewById(R.id.guanji_layout);
		paifeng_layout = (RelativeLayout) this.findViewById(R.id.paifeng_layout);
		mode_layout = (LinearLayout) this.findViewById(R.id.mode_layout);
		gongneng_layout = (LinearLayout) this.findViewById(R.id.gongneng_layout);
		fengsu_layout = (LinearLayout) this.findViewById(R.id.fengsu_layout);
		lvwang_layout = (LinearLayout) this.findViewById(R.id.lvwang_layout);
		hengshi_main_layout = (LinearLayout) this.findViewById(R.id.hengshi_mg_layout);
		btn_xinfeng = (ImageView) this.findViewById(R.id.btn_xinfeng);
		btn_guanji = (ImageButton) this.findViewById(R.id.btn_guanji);
		btn_paifeng = (ImageView) this.findViewById(R.id.btn_paifeng);
		btn_moshi = (ImageView) this.findViewById(R.id.btn_moshi);
		btn_gongneng = (ImageView) this.findViewById(R.id.btn_gongneng);
		btn_fengliang = (ImageView) this.findViewById(R.id.btn_fengliang);
		btn_lvWang = (ImageView) this.findViewById(R.id.btn_lvwang);
		txt_mode = (TextView) this.findViewById(R.id.txt_mode);
		txt_func = (TextView) this.findViewById(R.id.txt_func);
		txt_wind = (TextView) this.findViewById(R.id.txt_wind);
		txt_hengshi = (TextView) this.findViewById(R.id.basen_hengshi_txt);
		txt_lvwang = (TextView) this.findViewById(R.id.txt_wang);
		btn_xinfeng_bg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEairController.airOpenWindIn(mEairInfo.sn);
			}
		});
		btn_guanji.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEairController.airClose(mEairInfo.sn);
			}
		});
		btn_paifeng_bg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mEairController.airOpenWindOut(mEairInfo.sn);
			}
		});
		btn_moshi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addModeView());
			}
		});
		txt_mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addModeView());
			}
		});
		btn_gongneng.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addFuncView());
			}
		});
		txt_func.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addFuncView());
			}
		});
		btn_fengliang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addWindView());
			}
		});
		txt_wind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addWindView());
			}
		});
		hengshi_main_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addHengshiView());
			}
		});
		
		txt_hengshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addHengshiView());
			}
		});
		btn_lvWang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addFilterTimeView(mEairInfo.filterTime));

			}
		});
		
		txt_lvwang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewLayout.setVisibility(View.VISIBLE);
				viewLayout.removeAllViews();
				viewLayout.addView(addFilterTimeView(mEairInfo.filterTime));
			}
		});
		viewLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewLayout.setVisibility(View.GONE);

			}
		});
		base_timer_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimerPickerAlert.showAlert(DeviceInfoActivity.this, 0, 0, 0, null);

			}
		});
		mEairInfo = EairApplaction.mControlDevice.getEairInfo();

		titleView = (TextView) this.findViewById(R.id.title_txt);
		if(name==null||name.equals("")||name.equals("null")){
			if (mEairInfo.deviceType == EairControler.TYPE_T2) {
				titleView.setText("" + context.getResources().getString(R.string.t2_name));
			} else if (mEairInfo.deviceType == EairControler.TYPE_T1) {
				titleView.setText("" + context.getResources().getString(R.string.t1_name));
			} else if (mEairInfo.deviceType == EairControler.TYPE_CENTER) {
				titleView.setText("" + context.getResources().getString(R.string.center_name));
			}
		}else{
			titleView.setText(name);
		}
		
		
		

		mEairController = EairControler.getInstance(this);
		mNetworkManager = NetWorkManager.getInstance(this);
		mNetworkManager.setDeviceStatusChangeListener(this);

		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.air_speed_rotate);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);

	}

	Animation operatingAnim;
	TextView titleView;

	protected void onResume() {
		super.onResume();
		initEairView();

		mNetworkManager.setDeviceStatusChangeListener(this);
		mNetworkManager.JniNetWorkResume();

		if (mOldWorkMode != mEairInfo.workMode) {
			mOldWorkMode = mEairInfo.workMode;
			if (mEairInfo.workMode == 0) {
				DeviceInfoActivity.this.finish();
				Intent intent = new Intent();
				intent.setClass(DeviceInfoActivity.this, OpenActivity.class);
				startActivity(intent);
			}
		}

	}

	protected void onPause() {
		super.onPause();
		dismissProgessDialog();
		mInRefreshIng = false;
		mNetworkManager.JniNetWorkPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	TextView hengshi_value;
	SeekBar progress_hengshi;

	public View addHengshiView() {
		View view = inflater.inflate(R.layout.basen_hengshi_layout, null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		hengshi_value = (TextView) view.findViewById(R.id.hengshi_value);
		progress_hengshi = (SeekBar) view.findViewById(R.id.progress_hengshi);
		progress_hengshi.setProgress(mEairInfo.hengshidu - 20);
		hengshi_value.setText("" + mEairInfo.hengshidu + "%");
		progress_hengshi.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				hengshi_value.setText("" + Integer.toString(seekBar.getProgress() + 20) + "%");
				mEairController.airSetHumi((byte) (seekBar.getProgress() + 20), mEairInfo.sn);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

	TextView wind_in_value;
	TextView wind_out_value;
	SeekBar progress_wind_out;
	SeekBar progress_wind_in;

	public View addWindView() {
		View view = inflater.inflate(R.layout.basen_wind_layout, null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		wind_in_value = (TextView) view.findViewById(R.id.wind_in_value);
		wind_in_value.setText("0");
		wind_out_value = (TextView) view.findViewById(R.id.wind_out_value);
		wind_out_value.setText("0");

		progress_wind_out = (SeekBar) view.findViewById(R.id.progress_wind_out);
		progress_wind_in = (SeekBar) view.findViewById(R.id.progress_wind_in);
		progress_wind_in.setProgress(mEairInfo.speed_XF);
		progress_wind_out.setProgress(mEairInfo.speed_PF);

		wind_out_value.setText("" + mEairInfo.speed_PF);
		wind_in_value.setText("" + mEairInfo.speed_XF);
		progress_wind_out.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() >= 1) {
					wind_out_value.setText("" + seekBar.getProgress());
					mEairController.airSetWindOutLevel((byte) seekBar.getProgress(), mEairInfo.sn);
				} else {
					wind_out_value.setText("1");
					mEairController.airSetWindOutLevel((byte) 1, mEairInfo.sn);
				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});
		;

		progress_wind_in.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() >= 1) {
					wind_in_value.setText("" + seekBar.getProgress());
					mEairController.airSetWindInLevel((byte) seekBar.getProgress(), mEairInfo.sn);

				} else {
					wind_in_value.setText("1");
					mEairController.airSetWindInLevel((byte) 1, mEairInfo.sn);

				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});
		;

		return view;
	}

	RelativeLayout new_wind_layout;
	LinearLayout guanji_layout;
	RelativeLayout paifeng_layout;
	LinearLayout mode_layout;
	LinearLayout gongneng_layout;
	LinearLayout fengsu_layout;
	LinearLayout lvwang_layout;
	LinearLayout hengshi_main_layout;

	ImageView btn_xinfeng;
	ImageButton btn_guanji;
	ImageView btn_paifeng;
	ImageView btn_moshi;
	ImageView btn_gongneng;
	ImageView btn_fengliang;
	ImageView btn_lvWang;

	TextView txt_mode;
	TextView txt_func;
	TextView txt_wind;
	TextView txt_hengshi;
	TextView txt_lvwang;

	RelativeLayout viewLayout;
	Context context;
	LayoutInflater inflater;

	TextView xinfeng_lever;
	TextView paifeng_lever;
	ImageView btn_paifeng_bg;
	ImageView btn_xinfeng_bg;

	public View addFilterTimeView(int timeValue) {
		View view = inflater.inflate(R.layout.basen_filter_time_layout, null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		TextView textView = (TextView) view.findViewById(R.id.basen_filter_time_view);

		ImageView basenLogo = (ImageView) view.findViewById(R.id.lvwang_logo);
		LinearLayout phoneLayout = (LinearLayout) view.findViewById(R.id.lvwang_phone_layout);
		LinearLayout emailLayout = (LinearLayout) view.findViewById(R.id.lvwang_email_layout);

		textView.setText("" + timeValue + "小时");
		return view;
	}

	TextView funcName;
	LinearLayout heat_layout;
	LinearLayout shadu_layout;
	LinearLayout fulizi_Layuot;
	ImageView basen_mode_heat_view;
	ImageView basen_mode_shadu_view;
	ImageView basen_mode_fulizi_view;

	public View addFuncView() {
		View view = inflater.inflate(R.layout.basen_function_layout, null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		funcName = (TextView) view.findViewById(R.id.func_name);
		heat_layout = (LinearLayout) view.findViewById(R.id.heat_layout);
		shadu_layout = (LinearLayout) view.findViewById(R.id.shajun_layout);
		fulizi_Layuot = (LinearLayout) view.findViewById(R.id.fulizi_layout);
		basen_mode_heat_view = (ImageView) view.findViewById(R.id.basen_mode_heat_view);
		basen_mode_shadu_view = (ImageView) view.findViewById(R.id.basen_mode_shadu_view);
		basen_mode_fulizi_view = (ImageView) view.findViewById(R.id.basen_mode_fulizi_view);

		initEairView();

		heat_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEairController.airOpenHeat(mEairInfo.sn);//

			}
		});

		shadu_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEairController.airOpenUV(mEairInfo.sn);//

			}
		});

		fulizi_Layuot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEairController.airOpenO1(mEairInfo.sn);//

			}
		});
		return view;
	}

	TextView modeName;
	LinearLayout hengshi_layout;
	LinearLayout auto_layout;
	LinearLayout sleep_layout;
	LinearLayout manLayuot;
	ImageView basen_mode_man_view;
	ImageView basen_mode_hengshi_view;
	ImageView basen_mode_auto_view;
	ImageView basen_mode_sleep_view;

	public View addModeView() {
		View view = inflater.inflate(R.layout.basen_mode_layout, null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		modeName = (TextView) view.findViewById(R.id.mode_name);
		hengshi_layout = (LinearLayout) view.findViewById(R.id.hengshi_layout);
		auto_layout = (LinearLayout) view.findViewById(R.id.auto_layout);
		sleep_layout = (LinearLayout) view.findViewById(R.id.sleep_layout);
		manLayuot = (LinearLayout) view.findViewById(R.id.man_layout);
		basen_mode_man_view = (ImageView) view.findViewById(R.id.basen_mode_man_view);
		basen_mode_hengshi_view = (ImageView) view.findViewById(R.id.basen_mode_hengshi_view);
		basen_mode_auto_view = (ImageView) view.findViewById(R.id.basen_mode_auto_view);
		basen_mode_sleep_view = (ImageView) view.findViewById(R.id.basen_mode_sleep_view);

		initEairView();

		manLayuot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modeName.setText("手动模式");
				// TODO Auto-generated method stub
				mEairController.airSetMode(EairControler.MODE_MANUAL, mEairInfo.sn);
				// mEairController.airOpenO1(mEairInfo.sn);//负离子
				// mEairController.airCloseO3(mEairInfo.sn);//负离子
				// mEairController.airOpenO3(mEairInfo.sn);//臭氧
				// mEairController.airOpenHeat(mEairInfo.sn);//
				// mEairController.airCloseHeat(mEairInfo.sn);//
				// mEairController.airCloseUV(mEairInfo.sn);
				// mEairController.airOpenUV(mEairInfo.sn);
				// mEairController.airCloseO1(mEairInfo.sn);//负离子

				// mEairController.airSetWindInLevel((byte)7, mEairInfo.sn);
				// mEairController.airSetWindOutLevel((byte)8, mEairInfo.sn);;

				// mEairController.airSetTimer((byte) 17, (byte) 38, (byte) 18,
				// (byte) 01, mEairInfo.sn);
				// mEairController.airSetTimerAll((byte) 15, (byte) 38, (byte)
				// 18, (byte) 01, (byte) 17, (byte) 38,
				// (byte) 18, (byte) 01, (byte) 10, (byte) 38, (byte) 18, (byte)
				// 01, (byte) 12, (byte) 58,
				// (byte) 18, (byte) 01, (byte) 17, (byte) 38, (byte) 18, (byte)
				// 01, (byte) 17, (byte) 38,
				// (byte) 18, (byte) 01, (byte) 17, (byte) 38, (byte) 18, (byte)
				// 01, mEairInfo.sn);
				//
				//
			}
		});

		hengshi_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modeName.setText("恒湿模式");
				mEairController.airSetMode(EairControler.MODE_HENGSHI, mEairInfo.sn);

			}
		});
		auto_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modeName.setText("智能模式");
				mEairController.airSetMode(EairControler.MODE_SENSOR, mEairInfo.sn);

			}
		});
		sleep_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modeName.setText("睡眠模式");
				mEairController.airSetMode(EairControler.MODE_SLEEP, mEairInfo.sn);

			}
		});
		return view;
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
				Toast.makeText(DeviceInfoActivity.this, getResources().getString(R.string.auto_mode_no_wind_set),
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
								DeviceInfoActivity.this.finish();
								Intent intent = new Intent();
								intent.setClass(DeviceInfoActivity.this, OpenActivity.class);
								startActivity(intent);
							}
						}

					}
				});
			}
		}

	}

	private void initEairView() {

		if (mEairInfo == null && !mInRefreshIng)
			return;
		if (mEairInfo.air == 0) {// 优
			lever_img.setText("优");
			lever_img.setBackgroundResource(R.drawable.lever_you);
			lever_txt.setTextColor(Color.argb(255, 56, 144, 206));
			lever_procress.setImageResource(R.drawable.blue_full);

		} else if (mEairInfo.air == 1) {// 良
			lever_img.setText("良");
			lever_img.setBackgroundResource(R.drawable.lever_liang);
			lever_txt.setTextColor(Color.argb(255, 0, 155, 76));
			lever_procress.setImageResource(R.drawable.green_full);
		} else if (mEairInfo.air == 2) {// 差
			lever_img.setText("差");
			lever_img.setBackgroundResource(R.drawable.lever_cha);
			lever_txt.setTextColor(Color.argb(255, 255, 243, 78));
			lever_procress.setImageResource(R.drawable.yellow_full);
		} else if (mEairInfo.air == 3) {// 污
			lever_img.setText("污");
			lever_img.setBackgroundResource(R.drawable.lever_wu);
			lever_txt.setTextColor(Color.argb(255, 230, 33, 41));
			lever_procress.setImageResource(R.drawable.red_full);
		}else if (mEairInfo.air == 4) {//等待
			lever_img.setText("");
			lever_img.setBackgroundResource(R.drawable.lever_wait);
			lever_txt.setTextColor(Color.argb(255, 230, 230, 230));
			lever_procress.setImageResource(R.drawable.wait_full);
		}else if (mEairInfo.air == 5) {//错误
			lever_img.setText("--");
			lever_img.setBackgroundResource(R.drawable.lever_wait);
			lever_txt.setTextColor(Color.argb(255, 230, 230, 230));
			lever_procress.setImageResource(R.drawable.wait_full);
			
		}
		// // mEairRunStateText.setText(R.string.room_air);
		// mRunProgress.setVisibility(View.VISIBLE);
		// // mRunProgress.setBackgroundDrawable(new
		// // ColorDrawable(R.color.color_air_good));
		// mEairValueText.setBackgroundDrawable(null);
		//
		// mEairValueText.setText(Integer.toString(mEairInfo.air));
		//
		// if (mEairInfo.air < 2) {
		// mEairRunStateText.setText(R.string.air_best);
		// } else if (mEairInfo.air < 4) {
		// mEairRunStateText.setText(R.string.air_good);
		// } else if (mEairInfo.air < 8) {
		// mEairRunStateText.setText(R.string.air_normal);
		// } else {
		// mEairRunStateText.setText(R.string.air_bad);
		// }
		//
		if (funcName != null) {
			if (mEairInfo.uv) {
				basen_mode_shadu_view.setImageResource(R.drawable.basen_func_shadu_elected);
			} else {
				basen_mode_shadu_view.setImageResource(R.drawable.basen_func_shadu);
			}
			if (mEairInfo.o1) {
				basen_mode_fulizi_view.setImageResource(R.drawable.basen_func_fulizi_selected);
			} else {
				basen_mode_fulizi_view.setImageResource(R.drawable.basen_func_fulizi);
			}
			if (mEairInfo.heat) {
				basen_mode_heat_view.setImageResource(R.drawable.basen_func_heat_selected);
			} else {
				basen_mode_heat_view.setImageResource(R.drawable.basen_func_heat);
			}

		}
		if (modeName != null) {
			if (mEairInfo.workMode == EairControler.MODE_MANUAL) {
				modeName.setText("手动模式");
				basen_mode_man_view.setImageResource(R.drawable.basen_mode_man_selected);
				basen_mode_auto_view.setImageResource(R.drawable.basen_mode_auto);
				basen_mode_hengshi_view.setImageResource(R.drawable.basen_mode_hengshi);
				basen_mode_sleep_view.setImageResource(R.drawable.basen_mode_sleep);

			} else if (mEairInfo.workMode == EairControler.MODE_SENSOR) {
				modeName.setText("智能模式");
				basen_mode_auto_view.setImageResource(R.drawable.basen_mode_auto_selected);
				basen_mode_man_view.setImageResource(R.drawable.basen_mode_man);
				basen_mode_hengshi_view.setImageResource(R.drawable.basen_mode_hengshi);
				basen_mode_sleep_view.setImageResource(R.drawable.basen_mode_sleep);
			} else if (mEairInfo.workMode == EairControler.MODE_HENGSHI) {
				modeName.setText("恒湿模式");
				basen_mode_hengshi_view.setImageResource(R.drawable.basen_mode_hengshi_selected);
				basen_mode_auto_view.setImageResource(R.drawable.basen_mode_auto);
				basen_mode_man_view.setImageResource(R.drawable.basen_mode_man);
				basen_mode_sleep_view.setImageResource(R.drawable.basen_mode_sleep);
			} else if (mEairInfo.workMode == EairControler.MODE_SLEEP) {
				modeName.setText("睡眠模式");
				basen_mode_sleep_view.setImageResource(R.drawable.basen_mode_sleep_selected);
				basen_mode_man_view.setImageResource(R.drawable.basen_mode_man);
				basen_mode_auto_view.setImageResource(R.drawable.basen_mode_auto);
				basen_mode_hengshi_view.setImageResource(R.drawable.basen_mode_hengshi);

			}
		}
		basen_hengshi_txt.setText("恒湿" + mEairInfo.hengshidu + "%");
		if (hump_in != null) {
			if (mEairInfo.indoorHumi == 255) {
				hump_in.setText("--");
			} else {
				hump_in.setText(String.format("%d%%", mEairInfo.indoorHumi));
			}

		}

		if (hump_out != null) {
			if (mEairInfo.outdoorHumi == 255) {
				hump_out.setText("--");
			} else {
				hump_out.setText(String.format("%d%%", mEairInfo.outdoorHumi));
			}

		}

		if (temp_in != null) {
			if (mEairInfo.indoorTemp == 127) {
				temp_in.setText("--");
			} else {
				temp_in.setText(
						String.format("%d%s", mEairInfo.indoorTemp, getResources().getString(R.string.degree_c)));
			}
		}

		if (temp_out != null) {
			if (mEairInfo.outdoorTemp == 127) {
				temp_out.setText("--");
			} else {
				temp_out.setText(
						String.format("%d%s", mEairInfo.outdoorTemp, getResources().getString(R.string.degree_c)));
			}
		}
		if (lever_pm25_value != null) {
			lever_pm25_value.setText(String.format("%d", mEairInfo.pm25));
			if (mEairInfo.pm25 == 65535) {
				lever_pm25_value.setText("--");
			}
		}

		if (lever__CH4O_value != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			String newff = df.format(mEairInfo.hcoh);
			lever__CH4O_value.setText(newff);

			if (mEairInfo.hcoh == 655.35f) {
				lever__CH4O_value.setText("--");
			}
		}
		if (lever_CO2_value != null) {
			lever_CO2_value.setText(String.format("%d", mEairInfo.co2));
			if (mEairInfo.co2 == 65535) {
				lever_CO2_value.setText("--");
			}
		}

		if (lever_TVOC_value != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			String newff = df.format(mEairInfo.tvoc);
			lever_TVOC_value.setText(newff);
			if (mEairInfo.tvoc == 655.35f) {
				lever_TVOC_value.setText("--");
			}
		}

		if (device_time != null) {
			device_time.setText(String.format("%s%02d:%02d", getResources().getString(R.string.device_time),
					mEairInfo.sysTimeHour, mEairInfo.sysTimeMin));
		}

		if (time_open != null) {
			time_open.setText(String.format("%s%02d:%02d", getResources().getString(R.string.timer_start_pre),
					mEairInfo.timerStartHour_Mon, mEairInfo.timerStartMin_Mon));
		}

		if (time_close != null) {
			time_close.setText(String.format("%s%02d:%02d", getResources().getString(R.string.timer_stop_pre),
					mEairInfo.timerEndHour_Mon, mEairInfo.timerEndMin_Mon));
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

		if (mEairInfo.windIn) {
			btn_xinfeng.setVisibility(View.VISIBLE);
			btn_xinfeng_bg.setImageResource(R.drawable.basen_paifeng_bg);
			xinfeng_lever.setTextColor(Color.argb(255, 54, 144, 206));
			if (mEairInfo.speed_XF == 0) {
				btn_xinfeng.clearAnimation();
				xinfeng_lever.setText("新风");
			} else {
				if (btn_xinfeng.getAnimation() != null) {
					btn_xinfeng.clearAnimation();
				}
				xinfeng_lever.setText("新风 " + mEairInfo.speed_XF);
				btn_xinfeng.startAnimation(operatingAnim);
			}
		} else {
			btn_xinfeng_bg.setImageResource(R.drawable.basen_xinfeng_gray);
			btn_xinfeng.clearAnimation();
			xinfeng_lever.setText("新风");
			btn_xinfeng.setVisibility(View.INVISIBLE);
			xinfeng_lever.setTextColor(Color.argb(255, 230, 230, 230));
		}

		if (mEairInfo.windOut) {
			btn_paifeng.setVisibility(View.VISIBLE);
			btn_paifeng_bg.setImageResource(R.drawable.basen_paiwu_bg);
			paifeng_lever.setTextColor(Color.argb(255, 54, 144, 206));

			if (mEairInfo.speed_PF == 0) {
				btn_paifeng.clearAnimation();
				paifeng_lever.setText("排风 ");
			} else {
				if (btn_paifeng.getAnimation() != null) {
					btn_paifeng.clearAnimation();
				}
				paifeng_lever.setText("排风 " + mEairInfo.speed_PF);
				btn_paifeng.startAnimation(operatingAnim);
			}
		} else {
			btn_paifeng_bg.setImageResource(R.drawable.basen_paifeng_gray);
			btn_paifeng.clearAnimation();
			btn_paifeng.setVisibility(View.INVISIBLE);
			paifeng_lever.setText("排风 ");
			paifeng_lever.setTextColor(Color.argb(255, 230, 230, 230));
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
			mProgressDialog.showProgressDialog(DeviceInfoActivity.this, mDialogListener);
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

		mHumiDialog = HumiSetAlert.showAlert(DeviceInfoActivity.this, mEairInfo.sn, mEairInfo.fixHumi, xOffset, yOffset,
				new HumiSetAlert.OnAlertSelectId() {
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
