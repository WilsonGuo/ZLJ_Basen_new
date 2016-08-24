
package com.example.smarteair.adapter;

import java.util.List;

import com.example.smarteair.EairApplaction;
import com.example.basen.R;
import com.example.smarteair.activity.EditDeviceInfoActivity;
import com.example.smarteair.data.ManageDevice;
import com.example.smarteair.net.NetWorkManager;
import com.example.smarteair.view.OnSingleClickListener;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceAdapter extends BaseAdapter
{
    class ViewHolder
    {

//        TextView deviceEair;
        ImageView deviceIcon;
        TextView deviceName;
        TextView deviceState;
        TextView qrInfo;

        ViewHolder()
        {
        }
    }


    private Context mContext;
    private List<ManageDevice> mDeviceList;
    private LayoutInflater mInflater;

    public DeviceAdapter(Context context, List<ManageDevice> list)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDeviceList = list;
    }

    public int getCount()
    {
        return mDeviceList.size();
    }

    public ManageDevice getItem(int i)
    {
        return (ManageDevice)mDeviceList.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(final int position, View view, ViewGroup viewgroup)
    {
        ViewHolder viewholder;
		
	if (view == null)
	{
		viewholder = new ViewHolder();
		
		view = mInflater.inflate(R.layout.device_list_item_layout, null);
		
//		viewholder.deviceIcon = (ImageView)view.findViewById(R.id.device_icon);
//		viewholder.deviceName = (TextView)view.findViewById(R.id.device_name);
//		viewholder.deviceEair = (TextView)view.findViewById(R.id.eair);
//		viewholder.deviceState = (TextView)view.findViewById(R.id.device_state);
//		viewholder.qrInfo = (TextView)view.findViewById(R.id.qr_message);
		
		
		view=mInflater.inflate(R.layout.basen_main_list_item, null);
		viewholder.deviceIcon=(ImageView) view.findViewById(R.id.item_img);
		viewholder.deviceName=(TextView) view.findViewById(R.id.item_name);
		viewholder.qrInfo=(TextView) view.findViewById(R.id.item_id);
		viewholder.deviceState =(TextView) view.findViewById(R.id.item_status);
		
		
		view.setTag(viewholder);
	} 
	else
	{
		viewholder = (ViewHolder)view.getTag();
	}
		ManageDevice md = mDeviceList.get(position);
		if(md != null && md.getDeviceName().length() > 0)
		{
			viewholder.deviceName.setText(md.getDeviceName());
		}
		else
		{
			viewholder.deviceName.setText(R.string.goodneight_device);
			
		}

		if(md != null)
		{
			if(NetWorkManager.getInstance(mContext).JniGetDeviceLinkStatus(md.terminalId) >  0)
			{
				viewholder.deviceState.setText(mContext.getResources().getString(R.string.device_online));
			}
			else
			{
				viewholder.deviceState.setText(mContext.getResources().getString(R.string.device_offline));
			}
		}
		else
		{
			viewholder.deviceState.setText(mContext.getResources().getString(R.string.device_offline));
		}

		 
        viewholder.deviceIcon.setImageResource(R.drawable.device_online);
        viewholder.qrInfo.setText("ID:"+Integer.toString(getItem(position).terminalId & 0x7fffffff));
//        viewholder.deviceEair.setText("");

        
        viewholder.deviceIcon.setOnClickListener
        (
        		new OnSingleClickListener() 
			        {
			           	
			            public void doOnClick(View view1)
			            {
			                EairApplaction.mEditDevice = getItem(position);
			                Intent intent = new Intent();
			                intent.setClass(mContext, EditDeviceInfoActivity.class);
			                mContext.startActivity(intent);
			            }
			
			        }
        	);
        
        return view;
    }

}
