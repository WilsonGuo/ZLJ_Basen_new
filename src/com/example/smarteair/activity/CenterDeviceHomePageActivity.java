

package com.example.smarteair.activity;


import java.text.DecimalFormat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.data.DataInfo;
import com.example.smarteair.data.DeviceStatusChangeListener;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.net.Defines;
import com.example.smarteair.net.EairControler;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.view.BLWindAlert;
import com.example.smarteair.view.CentralBLTimeAlert;
import com.example.smarteair.view.FullProgressDialog;
import com.example.smarteair.view.TimerPickerAlert;
import com.zlj.basen.BaseActivity;

public class CenterDeviceHomePageActivity extends BaseActivity implements DeviceStatusChangeListener
{
	
	
    class GetWeatherTask extends AsyncTask
    {

		@Override
		protected Object doInBackground(Object... arg0) {
			
			return null;
		}
    }

    class RefreshAirThread extends Thread
    {

        public void run()
        {
            
        }


        RefreshAirThread()
        {
        }
    }


    private TextView mSystemTime; 
    
    private TextView mTimerStart;  
    private TextView mTimerEnd;   
    
    private TextView mButtonWindIn;	  
    private TextView mButtonWindOut;   
    private TextView mButtonHeat;  
    private TextView mButtonO1;    
    private TextView mButtonUV;  
    private TextView mButtonO3;  
    
    private TextView mFixHumi; 
    
    private LinearLayout mBackGround;
    private Button mCloseButton;
    private boolean mInRefreshIng;

    private Thread mRefreshEairThread;
    private Animation mRotateAnimation;
    private TextView mRunTimeText;
    private View mTimeIconBgView;
    private View mTimeIconView;
    private Animation mTimerAnimation;
    private LinearLayout mTimerLayout;
    private Animation mWindAnimation;
    private View mWindIconView;
    private LinearLayout mWindLayout;
    private EairInfo mEairInfo;
    private EairControler mEairController;
    
    private TextView mCenterTemp;
    private TextView mCenterHumi;

    private TextView mCenterPM25;
    private TextView mCenterCO2;
    private TextView mCenterCH2O;
    private TextView mCenterTVOC;

    private LinearLayout mSensorPM25;
    private LinearLayout mSensorCO2;
    private LinearLayout mSensorCH2O;
    private LinearLayout mSensorTVOC;
    
    private TextView mAutoButton;
    
    private ImageView mAirState;
    private TextView mAirStateTitle;
    
    
    private int mWifiSN;
    
    private FullProgressDialog mProgressDialog;


    
    //private TextView mHumiSet;
    private LinearLayout mTimerInfo;
    
    Looper looper = Looper.myLooper();   
 	NetWorkManager mNetworkManager;
 	private Dialog mModeDialog;
 	private final String TITLE = "itemTitle";
 	private int mOldWorkMode=0xee;
 	
    public CenterDeviceHomePageActivity()
    {
        mInRefreshIng = false;
    }

    private void controlEair(byte abyte0[])
    {
        
    }

    private void findView()
    {


    	mAirStateTitle = (TextView)findViewById(R.id.center_state_title);
    	mAirState = (ImageView)findViewById(R.id.center_airstate_bg);
    	
    	mCenterTemp = (TextView)findViewById(R.id.center_temp);
    	mCenterHumi = (TextView)findViewById(R.id.center_humi);

        
        mCenterPM25 = (TextView)findViewById(R.id.pm25);
        mCenterCO2 = (TextView)findViewById(R.id.ppm);
        mCenterCH2O = (TextView)findViewById(R.id.ch2o);
        mCenterTVOC = (TextView)findViewById(R.id.tvoc);
        
        mCloseButton = (Button)findViewById(R.id.btn_close);

        mWindIconView = findViewById(R.id.wind_icon);
        mTimeIconBgView = findViewById(R.id.timer_icon_bg);
        mTimeIconView = findViewById(R.id.timer_icon);
        mWindLayout = (LinearLayout)findViewById(R.id.wind_layout);
        mTimerLayout = (LinearLayout)findViewById(R.id.timer_layout);
        
        mSystemTime = (TextView)findViewById(R.id.goodnight_system_time);
        
        mTimerStart = (TextView)findViewById(R.id.goodnight_timer_start);
        mTimerEnd = (TextView)findViewById(R.id.goodnight_timer_stop);
        

        mButtonWindIn = (TextView)findViewById(R.id.btn_wind_in);
        mButtonWindOut = (TextView)findViewById(R.id.btn_wind_out);
        mButtonHeat = (TextView)findViewById(R.id.btn_heat);
        mButtonO1 = (TextView)findViewById(R.id.btn_o1);
        mButtonUV = (TextView)findViewById(R.id.btn_uv);
        mButtonO3 = (TextView)findViewById(R.id.btn_o3);

        
        //mButtonWifi = (TextView)findViewById(R.id.wifi_info);

        //mFilterInfo = (TextView)findViewById(R.id.filter_info_content);
        //mEairValueText = (TextView)findViewById(R.id.eair_value);
        
        //mFixHumi = (TextView)findViewById(R.id.humi_set);

        /*mHumiIndoor = (TextView)findViewById(R.id.goodnight_humidity_indoor);
        mHumiOutdoor = (TextView)findViewById(R.id.goodnight_humidity_outdoor);
        mTempIndoor = (TextView)findViewById(R.id.goodnight_temperature_indoor);
        mTempOutdoor = (TextView)findViewById(R.id.goodnight_temperature_outdoor);*/
        
        
        //mOpenLayout = (LinearLayout)findViewById(R.id.open_layout);
        mBackGround = (LinearLayout)findViewById(R.id.eair_bg);

        //mFilterLayout = (LinearLayout)findViewById(R.id.filter_info_layout);
        
        //mHumiSet = (TextView)findViewById(R.id.humi_set);
        
        mTimerInfo = (LinearLayout)findViewById(R.id.goodnight_timer_info);
        
        mRunTimeText = (TextView)findViewById(R.id.run_time);
        
        
        mSensorPM25 = (LinearLayout)findViewById(R.id.sensor_pm25);
        mSensorCO2 = (LinearLayout)findViewById(R.id.sensor_co2);
        mSensorCH2O = (LinearLayout)findViewById(R.id.sensor_ch2o);
        mSensorTVOC = (LinearLayout)findViewById(R.id.sensor_tvoc);
        
        mAutoButton = (TextView)findViewById(R.id.auto_button);
        
    }

    private void initEairView()
    {
    	if (mEairInfo == null && !mInRefreshIng)
            return;
    	
            //mEairRunStateText.setText(R.string.room_air);
            //mRunProgress.setVisibility(View.VISIBLE);
           // mRunProgress.setBackgroundDrawable(new ColorDrawable(R.color.color_air_good));
            //mEairValueText.setBackgroundDrawable(null);
            

            //mEairValueText.setText(Integer.toString(mEairInfo.air));
            
           /* if(mEairInfo.air < 2)
            {
            	mEairRunStateText.setText(R.string.air_best);
            }
            else if(mEairInfo.air < 4)
            {
            	mEairRunStateText.setText(R.string.air_good);
            }
            else if(mEairInfo.air < 8)
            {
            	mEairRunStateText.setText(R.string.air_normal);
            }
            else
            {
            	mEairRunStateText.setText(R.string.air_bad);
            }*/

            
            int id = R.string.mode_nolimit_speed;
    		switch(mEairInfo.workMode)
    		{
    			default:
    			case EairControler.CENTRAL_MODE_MANUAL:
    				id = R.string.mode_nolimit_speed;
    				break;
    			case EairControler.CENTRAL_MODE_AUTO:
    				id = R.string.mode_auto;
    				break;
    			case EairControler.CENTRAL_MODE_LOW_SPEED:
    				id = R.string.mode_low_speed;
    				break;
    			case EairControler.CENTRAL_MODE_MID_SPEED:
    				id = R.string.mode_mid_speed;
    				break;
    			case EairControler.CENTRAL_MODE_HIGH_SPEED:
    				id = R.string.mode_high_speed;
    				break;
    		}
    		
    		
    		mAirStateTitle = (TextView)findViewById(R.id.center_state_title);
        	mAirState = (ImageView)findViewById(R.id.center_airstate_bg);
        	
        	if(mAirStateTitle != null && mAirState != null)
    		{
    			if(mEairInfo.pm25 < 75)
    			{
    				mAirState.setImageResource(R.drawable.center_state_icon_fresh);
    				mAirStateTitle.setText(R.string.center_state_title_fresh);
    				mAirStateTitle.setTextColor(getResources().getColor(R.color.white));
    				
    			}
    			else if(mEairInfo.pm25 > 150)
    			{
    				mAirState.setImageResource(R.drawable.center_state_icon_bad);
    				mAirStateTitle.setText(R.string.center_state_title_bad);
    				mAirStateTitle.setTextColor(getResources().getColor(R.color.red));
    			}
    			else
    			{
    				mAirState.setImageResource(R.drawable.center_state_icon_good);
    				mAirStateTitle.setText(R.string.center_state_title_good);
    				mAirStateTitle.setTextColor(getResources().getColor(R.color.color_my_blue));
    			}
    		}
        	
        	
    		if(mRunTimeText != null)
    		{
    			mRunTimeText.setText(id);
    		}

    		
            if(mCenterHumi != null)
            {
            	mCenterHumi.setText(String.format("%s%d%%", getResources().getString(R.string.text_humi_pre), mEairInfo.outdoorHumi));
            }

            if(mCenterTemp != null)
            {
            	mCenterTemp.setText(String.format("%s%d%s", getResources().getString(R.string.text_temp_pre), mEairInfo.outdoorTemp, getResources().getString(R.string.degree_c)));
            }
            
            if(mCenterPM25 != null)
            {
            	mCenterPM25.setText(String.format("%d", mEairInfo.pm25));
            }
            
            if(mCenterCO2 != null)
            {
            	mCenterCO2.setText(String.format("%d", mEairInfo.co2));
            }

            if(mCenterCH2O != null)
            {
            	DecimalFormat df = new DecimalFormat("0.00");  
            	String newff = df.format(mEairInfo.hcoh);  
            	
            	mCenterCH2O.setText(newff);
            }
            
            if(mCenterTVOC != null)
            {
            	DecimalFormat df = new DecimalFormat("0.00");  
            	String newff = df.format(mEairInfo.tvoc);  
            	mCenterTVOC.setText(newff);
            }

            
            /*if(mFixHumi != null)
            {
            	mFixHumi.setText(String.format("%s%d%%", getResources().getString(R.string.humi_set), mEairInfo.fixHumi));
            }*/
            
            if(mSystemTime != null)
            {
            	mSystemTime.setText(String.format("%s%02d:%02d", getResources().getString(R.string.device_time), mEairInfo.sysTimeHour, mEairInfo.sysTimeMin));
            }
            
            if(mTimerStart != null)
            {
            	mTimerStart.setText(String.format("%s%02d:%02d", getResources().getString(R.string.timer_start_pre), mEairInfo.timerStartHour, mEairInfo.timerStartMin));
            }
            
            if(mTimerEnd != null)
            {
            	mTimerEnd.setText(String.format("%s%02d:%02d", getResources().getString(R.string.timer_stop_pre), mEairInfo.timerEndHour, mEairInfo.timerEndMin));
            }

	        
	        Drawable topDrawable;
	        if(mEairInfo.heat)
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_heat_high);
	        	mButtonHeat.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        else
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_heat);
	        	mButtonHeat.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        
	        if(mEairInfo.o1)
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_o1_high);
	        	mButtonO1.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        	
	        }
	        else
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_o1);
	        	mButtonO1.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        
	        if(mEairInfo.uv)
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_in_high);
	        	mButtonUV.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        	
	        }
	        else
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_in);
	        	mButtonUV.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        
	        
	        if(mEairInfo.o3)
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_out_high);
	        	mButtonO3.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        	
	        }
	        else
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_out);
	        	mButtonO3.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }

	        
	        
	        if(mEairInfo.windIn)
	        {
	        	
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_in_high);
	        	mButtonWindIn.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        else
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_in);
	        	mButtonWindIn.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        
	        if(mEairInfo.windOut)
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_out_high);
	        	mButtonWindOut.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	        else
	        {
	        	topDrawable = getResources().getDrawable(R.drawable.state_wind_out);
	        	mButtonWindOut.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
	        }
	
	        
	        
	        if((!mEairInfo.windIn) && (!mEairInfo.windOut))
	        {
	        	mWindIconView.clearAnimation();
	        }
	        else
	        {
	        	mWindIconView.startAnimation(mWindAnimation);
	        }
	        
	        
	        mSensorPM25.setBackgroundResource(R.drawable.circle_bg_selector_normal);
        	mSensorCO2.setBackgroundResource(R.drawable.circle_bg_selector_normal);
        	mSensorCH2O.setBackgroundResource(R.drawable.circle_bg_selector_normal);
        	mSensorTVOC.setBackgroundResource(R.drawable.circle_bg_selector_normal);
        	
	        if(mEairInfo.workMode == EairControler.CENTRAL_MODE_AUTO)
	        {
	        	
	        	if(mEairInfo.autoSensorIndex == 0)
		        {
	        		mSensorPM25.setBackgroundResource(R.drawable.circle_bg_selector_high);
		        }
		        else if(mEairInfo.autoSensorIndex == 1)
		        {
		        	mSensorCO2.setBackgroundResource(R.drawable.circle_bg_selector_high);
		        }
		        else if(mEairInfo.autoSensorIndex == 2)
		        {
		        	mSensorCH2O.setBackgroundResource(R.drawable.circle_bg_selector_high);
		        }
		        else if(mEairInfo.autoSensorIndex == 3)
		        {
		        	mSensorTVOC.setBackgroundResource(R.drawable.circle_bg_selector_high);
		        }
	        	
	        	mAutoButton.setVisibility(View.VISIBLE);
	        }
	        else
	        {
	        	mAutoButton.setVisibility(View.GONE);
	        }

	        
	        

	        
	        
    }

    private void initWeatherView()
    {
        
    }

    private void loadAnim()
    {
        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.air_speed_rotate);
        mWindAnimation = AnimationUtils.loadAnimation(this, R.anim.wind_anim);
        mWindAnimation.setDuration(20000L);
        mTimerAnimation = AnimationUtils.loadAnimation(this, R.anim.timer_anim);

    }


    private void setListener()
    {
        mWindLayout.setOnClickListener(new android.view.View.OnClickListener() {


            public void onClick(View view)
            {
                showWindDialog();
            }
        }
);
        mTimerLayout.setOnClickListener(new android.view.View.OnClickListener() {


            public void onClick(View view)
            {
                showTimerDialog();
            	
            }

        }
);
        /*mFilterLayout.setOnClickListener(new android.view.View.OnClickListener() {


            public void onClick(View view)
            {
            	Intent intent = new Intent();
                intent.setClass(CenterDeviceHomePageActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        }
);*/

        
        
        mButtonO1.setOnClickListener
        (
        		new OnClickListener() 
        		{
					
					@Override
					public void onClick(View arg0) 
					{
						mEairController.airOpenO1(mWifiSN);
		            	showProgressDialog();
					}
				}
        );
        

        
        mCloseButton.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				
				if(mNetworkManager.JniGetServiceLinkStatus() <= 0)
				{
					Toast.makeText(CenterDeviceHomePageActivity.this, CenterDeviceHomePageActivity.this.getResources().getString(R.string.network_lost), Toast.LENGTH_SHORT).show();
					return;
				}
				
				mEairController.airClose(mWifiSN);
            	showProgressDialog();
				
			}

        }
        		);
        
        
        mButtonUV.setOnClickListener
        (
        		new OnClickListener() 
        		{
					
					@Override
					public void onClick(View arg0) 
					{
						if(mEairInfo.uv)
						{
							mEairController.airOpenUV(mEairInfo.sn);
						}
						else
						{
							mEairController.airCloseUV(mEairInfo.sn);
						}
					}
				}
        );
        
        mButtonO3.setOnClickListener
        (
        		new OnClickListener() 
        		{
					
					@Override
					public void onClick(View arg0) 
					{
						if(mEairInfo.o3)
						{
							mEairController.airOpenO3(mEairInfo.sn);
						}
						else
						{
							mEairController.airCloseO3(mEairInfo.sn);
						}
					}
				}
        );
        
        /*mOpenLayout.setOnClickListener(new OnClickListener() {



			@Override
			public void onClick(View arg0) {
				
				
			}

        }
);*/
        
        /*mHumiSet.setOnClickListener(new OnClickListener() {

	            public void onClick(View view)
	            {
	            		
	            }
		
        	}
        	);*/
        
        mTimerInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				TimerPickerAlert.showAlert(CenterDeviceHomePageActivity.this, 0, 0, 0, null);
				
			}
		});
        
        
        mButtonWindIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				/*if(mEairInfo.workMode == EairControler.MODE_FIX)
	    		 {
	    			 Toast.makeText(CenterDeviceHomePageActivity.this, getResources().getString(R.string.fix_mode_no_wind_set), Toast.LENGTH_SHORT).show();
	    			 return;
	    		 }*/
				
				if(mEairInfo.windIn)
				{
					mEairController.airCloseWindIn(mEairInfo.sn);
				}
				else
				{
					mEairController.airOpenWindIn(mEairInfo.sn);
				}
				
			}
		});
        
        mButtonWindOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				if(mEairInfo.workMode == EairControler.MODE_FIX)
	    		 {
	    			 Toast.makeText(CenterDeviceHomePageActivity.this, getResources().getString(R.string.fix_mode_no_wind_set), Toast.LENGTH_SHORT).show();
	    			 return;
	    		 }
				
				if(mEairInfo.windOut)
				{
					mEairController.airCloseWindOut(mEairInfo.sn);
				}
				else
				{
					mEairController.airOpenWindOut(mEairInfo.sn);
				}
			}
		});
        
        mButtonHeat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				if(mEairInfo.heat)
				{
					mEairController.airOpenHeat(mEairInfo.sn);
				}
				else
				{
					mEairController.airCloseHeat(mEairInfo.sn);
				}
			}
		});
        
        mButtonO1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				if(mEairInfo.o1)
				{
					mEairController.airOpenO1(mEairInfo.sn);
				}
				else
				{
					mEairController.airCloseO1(mEairInfo.sn);
				}
			}
		});
        
        
        mSensorPM25.setOnClickListener(mAutoSensorProc);
        mSensorCO2.setOnClickListener(mAutoSensorProc);
        mSensorCH2O.setOnClickListener(mAutoSensorProc);
        mSensorTVOC.setOnClickListener(mAutoSensorProc);
        
        
    }

    private OnClickListener mAutoSensorProc = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			int id = v.getId();
			if(id == R.id.sensor_pm25)
			{
				mEairController.airSetAutoSensor(mEairInfo.sn, 0);
			}
			else if(id == R.id.sensor_co2)
			{
				mEairController.airSetAutoSensor(mEairInfo.sn, 1);
			}
			else if(id == R.id.sensor_ch2o)
			{
				mEairController.airSetAutoSensor(mEairInfo.sn, 2);
			}
			else if(id == R.id.sensor_tvoc)
			{
				mEairController.airSetAutoSensor(mEairInfo.sn, 3);
			}
		}
	};
    private void showTimerDialog()
    {
        if (mEairInfo != null)
        {
        	mModeDialog = CentralBLTimeAlert.showAlert(this, mEairInfo.workMode, new CentralBLTimeAlert.OnAlertSelectId() 
            {
                public void onClick(int i)
                {
                	mEairController.airSetMode(i, mEairInfo.sn);
                	if(mModeDialog != null)
                	{
		            	mModeDialog.dismiss();
		            	mModeDialog = null;
                	}
                }
            });
        }
    }

    private void showWindDialog()
    {
    	 if (mEairInfo != null)
    	 {
    		 
    		 if(mEairInfo.workMode == EairControler.CENTRAL_MODE_AUTO)
    		 {
    			 Toast.makeText(CenterDeviceHomePageActivity.this, getResources().getString(R.string.auto_mode_no_wind_set), Toast.LENGTH_SHORT).show();
    			 return;
    		 }
    		 
    		 int level1 = mEairInfo.windInLevel;
    		 int level2 = mEairInfo.windOutLevel;
    		 
    		 if(!mEairInfo.windIn)
    		 {
    			 level1 = 0;
    		 }
    		 
    		 if(!mEairInfo.windOut)
    		 {
    			 level2 = 0;
    		 }
    		 
    		 BLWindAlert.showAlert(this, level1, level2, new BLWindAlert.OnAlertSelectId()
	             {
	                 public void onClick(int windinLevel, int windoutLevel)
	                 {
	                	 if(mEairInfo.windInLevel != windinLevel)
	                	 {
	                		 mEairInfo.windInLevel = windinLevel;
	                		 if(windinLevel > 0)
	                		 {
	                			 mEairController.airSetWindInLevel((byte)windinLevel, mEairInfo.sn);
	                		 }
	                		 else
	                		 {
	                			 mEairController.airCloseWindIn(mEairInfo.sn);
	                		 }
	                	 }
	                	 
	                	 if(mEairInfo.windOutLevel != windoutLevel)
	                	 {
	                		 mEairInfo.windOutLevel = windoutLevel;
	                		 if(windoutLevel > 0)
	                		 {
		                		 mEairController.airSetWindOutLevel((byte)windoutLevel, mEairInfo.sn);
	                		 }
	                		 else
	                		 {
	                			 mEairController.airCloseWindOut(mEairInfo.sn);
	                		 }
	                	 }
	                	 
	                     //showProgressDialog();
	                 }
	             }
            );
            
    	 }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.center_device_home_page_layout);
       
        
        mEairInfo = EairApplaction.mControlDevice.getEairInfo();
        mWifiSN = mEairInfo.sn;

        loadAnim();
        findView();
        setListener();
        
        mEairController = EairControler.getInstance(this);
        mNetworkManager = NetWorkManager.getInstance(this);
    	mNetworkManager.setDeviceStatusChangeListener(this);    
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if (i == 82)
        {
            /*Intent intent = new Intent();
            intent.setClass(this, com/tcl/eair/activity/WebActivity);
            startActivity(intent);*/
        }
        return super.onKeyDown(i, keyevent);
    }

    protected void onPause()
    {
        super.onPause();
        //mRunProgress.setVisibility(View.INVISIBLE);
        //mRunProgress.clearAnimation();
        mInRefreshIng = false;
        
        if (mRefreshEairThread != null)
        {
            mRefreshEairThread.interrupt();
            mRefreshEairThread = null;
        }
        
        //mNetworkManager.setDeviceStatusChangeListener(null);
        mNetworkManager.JniNetWorkPause();
    }

    protected void onResume()
    {
        super.onResume();
        initWeatherView();
        initEairView();
        
        
        
        //mWindIconView.startAnimation(mWindAnimation);
        //mRunProgress.startAnimation(mRotateAnimation);
        mInRefreshIng = true;
        
        if (mRefreshEairThread == null)
        {
            //mRefreshEairThread = new RefreshAirThread();
            //mRefreshEairThread.start();
        }
        
        mNetworkManager.setDeviceStatusChangeListener(this);
        mNetworkManager.JniNetWorkResume();
        
        
        if(mOldWorkMode != mEairInfo.workMode)
		{
			mOldWorkMode = mEairInfo.workMode;
			if(mEairInfo.workMode == 0)
	        {
				CenterDeviceHomePageActivity.this.finish();
	        	Intent intent = new Intent();
                intent.setClass(CenterDeviceHomePageActivity.this, OpenActivity.class);
                startActivity(intent);
	        }
		}
        
    }

	@Override
	public void doCallBack(int type, int data, DataInfo datainfo) 
	{
		if(type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_RESULT)
		{
			if(data != 0)
			{
				dismissProgessDialog();
			}
		}
		else if(type == Defines.DEVICE_STATUS_CHANGE_CALLBACK_TYPE_DATA)
		{
			//if(datainfo.len == EairControler.CONTROL_PACKERT_IN_SIZE)
			{
				if(mEairInfo.sn != datainfo.sn)
				{
					return;
				}
				
				mEairController.parseInfo(datainfo.sn, mEairInfo, datainfo.pkt);
				
				dismissProgessDialog();
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						initEairView();
						
							if(mOldWorkMode != mEairInfo.workMode)
							{
								mOldWorkMode = mEairInfo.workMode;
								if(mEairInfo.workMode == 0)
						        {
									CenterDeviceHomePageActivity.this.finish();
						        	Intent intent = new Intent();
					                intent.setClass(CenterDeviceHomePageActivity.this, OpenActivity.class);
					                startActivity(intent);
						        }
							}

					}
				});
			}
		}
		
	}

	@Override
	protected void onDestroy() 
	{
		dismissProgessDialog();
		
		//mNetworkManager.setDeviceStatusChangeListener(null);
		super.onDestroy();
	}
	
	
			
    private OnDismissListener mDialogListener = new OnDismissListener() 
    {
		
		@Override
		public void onDismiss(DialogInterface arg0) 
		{
			
			mProgressDialog = null;
		}
	};	
	private void showProgressDialog()
	{
		if(mProgressDialog == null)
		{
			mProgressDialog = new FullProgressDialog();
			mProgressDialog.showProgressDialog(CenterDeviceHomePageActivity.this, mDialogListener);
		}
	}
	
	private void dismissProgessDialog()
	{
		if(mProgressDialog != null)
		{
			mProgressDialog.dismissProressDialog();
		}
	}

	
}
