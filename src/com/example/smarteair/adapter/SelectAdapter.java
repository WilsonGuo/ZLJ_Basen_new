
package com.example.smarteair.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.basen.R;
import com.example.smarteair.data.ScanDevice;
import com.example.smarteair.net.EairControler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectAdapter extends BaseAdapter {
	class ViewHolder {

		TextView deviceEair;
		ImageView deviceIcon;
		TextView deviceName;
		TextView deviceState;
		TextView qrInfo;
		ImageView check;

		ViewHolder() {
		}
	}

	private Context mContext;
	private List<ScanDevice> mDeviceList;
	private List<CheckBox> mCheckBoxs;
	private LayoutInflater mInflater;
	boolean showNew;
	public SelectAdapter(Context context, List<ScanDevice> list,boolean showNew) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mDeviceList = list;
		mCheckBoxs = new ArrayList<CheckBox>();
		this.showNew=showNew;
	}

	public int getCount() {
		return mDeviceList.size();
	}

	public ScanDevice getItem(int i) {
		return (ScanDevice) mDeviceList.get(i);
	}



	public long getItemId(int i) {
		return (long) i;
	}

	public View getView(final int position, View view, ViewGroup viewgroup) {
		ViewHolder viewholder;

		if (view == null) {
			viewholder = new ViewHolder();

			view = mInflater.inflate(R.layout.basen_search_list_item, null);

			viewholder.deviceIcon = (ImageView) view.findViewById(R.id.item_img);
			viewholder.deviceName = (TextView) view.findViewById(R.id.item_name);
			viewholder.qrInfo = (TextView) view.findViewById(R.id.item_id);
			viewholder.check = (ImageView) view.findViewById(R.id.check_button);

			view.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) view.getTag();
		}
		viewholder.deviceIcon.setImageResource(R.drawable.device_seleted);
		viewholder.deviceName.setText(R.string.goodneight_device);
		viewholder.qrInfo.setText(getItem(position).mac);

		if(showNew){
			viewholder.deviceName.setText("" + mContext.getResources().getString(R.string.default_name));
		}else{
			if(getItem(position).isNew){
				viewholder.deviceName.setText("" + mContext.getResources().getString(R.string.default_name));
			}else{
				if (getItem(position).deviceType == EairControler.TYPE_T2) {
					viewholder.deviceName.setText("" + mContext.getResources().getString(R.string.t2_name));
				} else if (getItem(position).deviceType == EairControler.TYPE_T1) {
					viewholder.deviceName.setText("" + mContext.getResources().getString(R.string.t1_name));
				} else if (getItem(position).deviceType == EairControler.TYPE_CENTER) {
					viewholder.deviceName.setText("" + mContext.getResources().getString(R.string.center_name));
				}
			}
			
		}
	
		
		
		if (mDeviceList.get(position).checked) {
			viewholder.check.setImageResource(R.drawable.item_selected);
		} else {
			viewholder.check.setImageResource(R.drawable.item_selected_bg);
		}

		return view;
	}

}
