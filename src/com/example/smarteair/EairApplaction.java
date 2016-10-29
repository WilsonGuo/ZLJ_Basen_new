
package com.example.smarteair;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.smarteair.common.UserInfoUnit;
import com.example.smarteair.data.DatabaseHelper;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.data.ManageDevice;
import com.example.smarteair.data.ManageDeviceDao;
import com.example.smarteair.data.ScanDevice;
import com.example.smarteair.net.NetWorkManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.os.AsyncTask;
import android.text.TextUtils;

public class EairApplaction extends Application {
	
	public static List<ManageDevice> allDeviceList = new ArrayList<ManageDevice>();
    public static ManageDevice mControlDevice;
	public static ManageDevice mEditDevice;
    public static long mTimeDiff = 0L;
	
//    public List<Activity> mActivityList;

    private ManageDeviceDao mManageDeviceDao;
    public String mNetIP="0.0.0.0";
    public UpdateTask mUpdateTask;
    public UserInfoUnit mUserInfo;
    public static NetWorkManager mNetWorkManager;
	private static final boolean ExceptionEnable = true;
	
	class AddDeviceTask extends AsyncTask<Object, Object, Object> {

		AddDeviceTask() {
		}

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	class GetNetIpTask extends AsyncTask {

		GetNetIpTask() {

		}

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public class UpdateTask extends AsyncTask {

		public Notification getNotification() {
			return null;
		}

		protected void onPostExecute(File file) {
		}

		protected void onPreExecute() {

		}

		protected void onProgressUpdate(Integer ainteger[]) {
		}

		protected void onProgressUpdate(Object aobj[]) {
			onProgressUpdate((Integer[]) aobj);
		}

		public void stop() {

		}

		public UpdateTask() {

		}

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public EairApplaction() 
	{
//		mActivityList = new ArrayList<Activity>();
	}

	private int getStatusBarHeight() 
	{
		int j;
		try 
		{
			Class class1 = Class.forName("com.android.internal.R$dimen");
			Object obj = class1.newInstance();
			
			int i = Integer.parseInt(class1.getField("status_bar_height")
					.get(obj).toString());
			
			j = getResources().getDimensionPixelSize(i);
			
		} 
		catch (Exception exception) 
		{
			return 0;
		}
		
		return j;
	}

	private void init() 
	{
		mUserInfo = new UserInfoUnit(this);
	}

	private void initDataBaseDao() 
	{
		try
        {
            if (mManageDeviceDao == null)
                mManageDeviceDao = new ManageDeviceDao((DatabaseHelper)OpenHelperManager.getHelper(this, DatabaseHelper.class));
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
	}


//	public void clearActivityList() 
//	{
//		if(mActivityList == null)
//		{
//			return;
//			
//		}
//		
//        int i = 0;
//        do
//        {
//            if (i >= mActivityList.size())
//            {
//                mActivityList.clear();
//                return;
//            }
//            
//            ((Activity)mActivityList.get(i)).finish();
//            
//            i++;
//        } while (true);
//	}

	public void finish() 
	{
//		clearActivityList();
        System.gc();
	}

	public void initNetWork() 
	{
		if (mNetWorkManager == null)
			mNetWorkManager = NetWorkManager.getInstance(this);
		
			/*mNetWorkManager.setScanDeviceListener(
				new ScanDeviceListener() {

            public void deviceInfoCallBack(DataInfo info)
            {
                if (info != null && info.sn != 0)
                {
                    //Log.d((new StringBuilder(String.valueOf(getPackageName()))).append("------------->scanDevice").toString(), (new StringBuilder(String.valueOf(scandevice.deviceName))).append(" : ").append(scandevice.mac).append("\ntype:").append(scandevice.deviceType).append("\nid: ").append(scandevice.id).toString());

					ScanDevice scandevice = new ScanDevice();
					scandevice.id = info.sn;
					scandevice.deviceName = ""+info.sn;
					scandevice.mac = ""+info.sn;
					scandevice.lock = 0;
					scandevice.password = info.sn;
					scandevice.publicKey = "1234".getBytes();
										
					checkCreateOrUpdateScanDevice(scandevice);
                }
            }
        }
);*/
	}

	public void onCreate() 
	{
		super.onCreate();
		
		init();
		
		if(ExceptionEnable)
		{
			Thread.setDefaultUncaughtExceptionHandler
			(
					
				new Thread.UncaughtExceptionHandler() 
				{
					public void uncaughtException(Thread thread, Throwable throwable) 
					{
						(new Thread(new Runnable() 
						{
				
							public void run() 
							{
								
								System.exit(0);
							}
				
						})).start();
		
					}
		
				}
	         );
		}

	}

	

    public void checkCreateOrUpdateScanDevice(ScanDevice scandevice, EairInfo info)
    {

        ManageDevice managedevice = null;
        initDataBaseDao();
        
        try 
        {
			managedevice = (ManageDevice)mManageDeviceDao.queryForId(scandevice.mac);
			
			if (managedevice == null) 
	        {
	            if (TextUtils.isEmpty(mUserInfo.getUserName()) 
	            		|| scandevice.lock == 1) 
	            {
	    			return;
	            }
	            else
	            {
	            	ManageDevice managedevice2;
	                managedevice2 = new ManageDevice();
	                managedevice2.setUserName(mUserInfo.getUserName());
	                managedevice2.setDeviceMac(scandevice.mac);
	                managedevice2.setDeviceName(scandevice.deviceName);
	                managedevice2.setDevicePassword(scandevice.password);
	                managedevice2.setDeviceType(scandevice.deviceType);
	                managedevice2.setDeviceLock(scandevice.lock);
	                managedevice2.setPublicKey(scandevice.publicKey);
	                managedevice2.setTerminalId(scandevice.id);
	                managedevice2.setSubDevice(scandevice.subDevice);
	                managedevice2.setNetIp(mNetIP);
	                
	                List list = mManageDeviceDao.queryAllDevices();
					
					if (!list.isEmpty())
	                {
	                    managedevice2.setOrder(1L + ((ManageDevice)list.get(-1 + list.size())).getOrder());
	                }
	                
	                mManageDeviceDao.createOrUpdate(managedevice2);
	                allDeviceList.add(managedevice2);
	                managedevice2.setEairInfo(info);
					
	                //mBlNetworkUnit.addDevice(scandevice);
	        		
	                (new AddDeviceTask()).execute(new ManageDevice[] {
	                    managedevice2
	                });
	            }
	        }
		 else 
		 {
			 /*if (!managedevice.getDeviceName().equals(scandevice.deviceName) 
						|| managedevice.getDeviceLock() != scandevice.lock 
						|| managedevice.getDevicePassword() != scandevice.password 
						|| !CommonUnit.checkByteArrayEqual(managedevice.getPublicKey(), 
						scandevice.publicKey)) 
			 {
				 
			 }
			 else
			 {
			        int j;
			        int k;
			        j = managedevice.getTerminalId();
			        k = scandevice.id;
			        if (j == k) 
			        {
			        	return;
			        }
			 }*/
			 

		        //mBlNetworkUnit.removeDevice(managedevice.getDeviceMac());
		        //mBlNetworkUnit.addDevice(scandevice);
		        if (scandevice.lock != 1) 
		        {
		        	managedevice.setDevicePassword(scandevice.password);
			       managedevice.setDeviceLock(0);
		        }
		        else
		        {
		        	managedevice.setDeviceLock(1);
		        }
		        
		        managedevice.setDeviceName(scandevice.deviceName);
		        managedevice.setPublicKey(scandevice.publicKey);
		        managedevice.setTerminalId(scandevice.id);
		        mManageDeviceDao.createOrUpdate(managedevice);
		        
				
		        int i = 0;
		        
		        while(i < allDeviceList.size())
		        {
		        	ManageDevice managedevice1;
		            managedevice1 = (ManageDevice)allDeviceList.get(i);
		            
		            if (!managedevice1.getDeviceMac().equals(scandevice.mac))
		            {
		            	i++;
		            	continue;
		            }
		    		
		            if (scandevice.lock != 1) 
		            {
		            	managedevice1.setDevicePassword(scandevice.password);
		                managedevice1.setDeviceLock(0);
		            }
		            else 
		            {
		            	managedevice1.setDeviceLock(1);
		            }
		    		
					managedevice1.setDeviceName(scandevice.deviceName);
					managedevice1.setPublicKey(scandevice.publicKey);
					managedevice1.setTerminalId(scandevice.id);
	
					managedevice1.setEairInfo(info);
					
		        	i++;
		        }

			
			          
		 }
			
		}
        catch (SQLException e1) 
		{

			e1.printStackTrace();
		}

        
    }
	
    
   

}
