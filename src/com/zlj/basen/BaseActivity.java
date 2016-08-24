
package com.zlj.basen;

import com.example.smarteair.EairApplaction;
import com.example.smarteair.data.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.content.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity
{
	

	protected EairApplaction mApplication;
    private volatile DatabaseHelper mHelper;
    private BroadcastReceiver mReceiver;
    
    private class BaseReceiver extends BroadcastReceiver
    {
        
        public void onReceive(Context context, Intent intent)
        {
            
        }

        private BaseReceiver()
        {
        }

        BaseReceiver(BaseReceiver basereceiver)
        {
            this();
        }
    }

    public DatabaseHelper getHelper()
    {
        if (mHelper == null)
        {
            mHelper = (DatabaseHelper)OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return mHelper;
    }
    
    public BaseActivity()
    {
        //mReceiver = new BaseReceiver(null);
    }

    protected void back()
    {
        finish();
    }


    protected void onCreate(Bundle bundle)
    {
        mApplication = (EairApplaction)getApplication();
        mApplication.mActivityList.add(this);
        super.onCreate(bundle);
    }

    protected void onDestroy()
    {
        if (mHelper != null)
        {
            OpenHelperManager.releaseHelper();
            mHelper = null;
        }
        mApplication.mActivityList.remove(this);
        
        super.onDestroy();
    }

    protected void onPause()
    {
        super.onPause();
        //unregisterReceiver(mReceiver);
    }

    protected void onResume()
    {
        /*IntentFilter intentfilter = new IntentFilter("android.intent.action.MEDIA_MOUNTED");
        intentfilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentfilter.addAction("android.intent.action.MEDIA_EJECT");
        intentfilter.addAction("android.intent.action.MEDIA_REMOVED");
        intentfilter.addAction("android.intent.action.MEDIA_SHARED");
        intentfilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        intentfilter.addDataScheme("file");
        registerReceiver(mReceiver, intentfilter);*/
        super.onResume();
    }
}
