

package com.example.smarteair.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.basen.R;
import com.example.smarteair.net.EairControler;

public class HumiSetAlert
{
    public static interface OnAlertSelectId
    {

        public abstract void onClick(int i);
    }


    private static VerticalSeekBar verticalSeekBar = null;
    private static TextView verticalText = null;
    private static int mSn=0;
    private static int mProgress=0;
    public static Dialog showAlert(Context context, int sn, int humi, int xOffset, int yOffset, final OnAlertSelectId alertDo)
    {
        Dialog dialog = new Dialog(context, R.style.MMTheme_AlertOption);
        LinearLayout linearlayout = (LinearLayout)((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.humi_set_alert_layout, null);
		//linearlayout.setMinimumHeight(1000);
        
        
    	
        verticalText = (TextView) linearlayout.findViewById(R.id.verticalText);
    	
    	verticalSeekBar = (VerticalSeekBar) linearlayout.findViewById(R.id.verticalSeekBar);
    	verticalSeekBar.setOnSeekBarChangeListener(verticalSeekBarChangeListener);
    	verticalSeekBar.setMax(60);	
    	if(humi > 80)
    	{
    		humi = 80;
    	}
    	else if(humi < 20)
    	{
    		humi = 20;
    	}
    	
    	verticalSeekBar.setProgress(humi-20);
    	mProgress = humi-20;

    	mSn = sn;

        android.view.WindowManager.LayoutParams layoutparams = dialog.getWindow().getAttributes();
        layoutparams.x = xOffset;
        layoutparams.y = yOffset-200; 
        layoutparams.gravity = Gravity.LEFT|Gravity.TOP;

        dialog.onWindowAttributesChanged(layoutparams);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(linearlayout);
        dialog.show();
        return dialog;
    }
    
    private static OnSeekBarChangeListener verticalSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			if(verticalText != null)
			{
				verticalText.setText(Integer.toString(progress+20));
			}
			
			if(mProgress != progress)
			{
				EairControler.getInstance(seekBar.getContext()).airSetHumi((byte)(progress+20), mSn);
				mProgress = progress;
			}
			
		}
	};
}
