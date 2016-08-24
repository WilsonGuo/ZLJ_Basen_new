

package com.example.smarteair.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.view.OnSingleClickListener;


public class FilterActivity extends TitleActivity
{

    private ImageButton mBackButton;
    private TextView mDelayTime;
    private TextView mNet;
    private TextView mServerTel;


    private void findView()
    {
        mBackButton = (ImageButton)findViewById(R.id.btn_return);
        mDelayTime = (TextView)findViewById(R.id.delay_time);
        mServerTel = (TextView)findViewById(R.id.server_tel);
        mNet = (TextView)findViewById(R.id.net);
    }

    private void initView()
    {
    	int filterTime = 0;
    	if(EairApplaction.mControlDevice != null
    			&& EairApplaction.mControlDevice.getEairInfo() != null)
    	{
    		filterTime = EairApplaction.mControlDevice.getEairInfo().filterTime;
    	}
    	
        int i = filterTime/2;
        if (i < 56)
            mDelayTime.setTextColor(getResources().getColor(R.color.color_filter_warn_text));
        else
            mDelayTime.setTextColor(getResources().getColor(R.color.color_filter_text));
        
        if (i < 0)
            i = 0;
        
        TextView textview = mDelayTime;
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(i);
        textview.setText(getString(R.string.format_time, aobj));
    }

    private void setListener()
    {
        mBackButton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                finish();
            }

        }
);
        mServerTel.setOnClickListener(new OnSingleClickListener() {


            public void doOnClick(View view)
            {
                /*(new android.app.AlertDialog.Builder(FilterActivity.this))
                .setTitle(R.string.hint)
                .setMessage(R.string.call_phone_hint)
                .setPositiveButton(R.string.yes, new android.content.DialogInterface.OnClickListener() 
                {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:4008-123456"));
                        startActivity(intent);
                    }
                }
                ).setNegativeButton(R.string.cancel, null).show();*/
            }

        }
);
        mNet.setOnClickListener(new OnSingleClickListener() {

            public void doOnClick(View view)
            {
               /* Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(getString(R.string.net_value)));
                startActivity(intent);*/
            }

        }
);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.filter_layout);
        setBackVisible();
        setTitle(R.string.filter_case);
        setTitleBackgroundColor(getResources().getColor(R.color.color_main_bg));
        findView();
        setListener();
        initView();
    }
}
