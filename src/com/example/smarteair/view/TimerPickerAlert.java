
package com.example.smarteair.view;

import com.example.basen.R;
import com.example.smarteair.EairApplaction;
import com.example.smarteair.data.EairInfo;
import com.example.smarteair.net.EairControler;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class TimerPickerAlert {

	// Time changed flag
	private static boolean timeChanged = false;

	// Time scrolled flag
	private static boolean timeScrolled = false;

	private static int mTimerStartHour = 0;
	private static int mTimerStartMin = 0;

	private static int mTimerEndHour = 0;
	private static int mTimerEndMin = 0;

	private static Button mCancleButton;
	private static Button mConfirmButton;
	private static Dialog mDialog;
	private static Context mContext;
	
	 ImageView hour_up;
	ImageView hour_down;

	public static interface OnAlertSelectId {

		public abstract void onClick(int i);
	}

	public static Dialog showAlert(Context context, int i, int j, int k, final OnAlertSelectId alertDo) {

		EairInfo ei = EairApplaction.mControlDevice.getEairInfo();
		mTimerStartHour = ei.timerStartHour_Mon;
		mTimerStartMin = ei.timerStartMin_Mon;
		mTimerEndHour = ei.timerEndHour_Mon;
		mTimerEndMin = ei.timerEndMin_Mon;

		mDialog = new Dialog(context, R.style.MMTheme_DataSheet);
		RelativeLayout linearLayout = (RelativeLayout) ((LayoutInflater) context.getSystemService("layout_inflater"))
				.inflate(R.layout.timer_pick_layout, null);

		initWheelView((View) linearLayout, context);

		mDialog.setCanceledOnTouchOutside(true);
		mDialog.setContentView(linearLayout);

		mDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				mDialog = null;
			}
		});

		mDialog.show();
		return mDialog;
	}

	public static void initWheelView(View parent, Context context) {

		mConfirmButton = (Button) parent.findViewById(R.id.confirm_btn_abc);
		mCancleButton = (Button) parent.findViewById(R.id.cancel_btn_abc);
		if (mConfirmButton != null) {
			mConfirmButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					EairInfo ei = EairApplaction.mControlDevice.getEairInfo();
					EairControler ec = EairControler.getInstance(mDialog.getContext());
					ec.airSetTimer((byte) mTimerStartHour, (byte) mTimerStartMin, (byte) mTimerEndHour,
							(byte) mTimerEndMin, ei.sn);
					if (mDialog != null) {
						mDialog.dismiss();
						mDialog = null;
					}
				}
			});
		}
		if (mCancleButton != null) {
			mCancleButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (mDialog != null) {
						mDialog.dismiss();
						mDialog = null;
					}
				}
			});
		}

		final WheelView hours = (WheelView) parent.findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(context, 0, 23));
		hours.setCurrentItem(mTimerStartHour % 24);
		hours.setCyclic(true);

		final WheelView mins = (WheelView) parent.findViewById(R.id.mins);
		NumericWheelAdapter minAdapter = new NumericWheelAdapter(context, 0, 59, "%02d");
		mins.setViewAdapter(minAdapter);
		mins.setCurrentItem(mTimerStartMin % 60);
		mins.setCyclic(true);

		final WheelView endHours = (WheelView) parent.findViewById(R.id.end_hour);
		endHours.setViewAdapter(new NumericWheelAdapter(context, 0, 23));
		endHours.setCurrentItem(mTimerEndHour % 24);
		endHours.setCyclic(true);

		final WheelView endMins = (WheelView) parent.findViewById(R.id.end_mins);
		NumericWheelAdapter minEndAdapter = new NumericWheelAdapter(context, 0, 59, "%02d");

		endMins.setViewAdapter(minEndAdapter);
		endMins.setCurrentItem(mTimerEndMin % 60);
		endMins.setCyclic(true);

		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");

		OnWheelChangedListener wheelListenerStartHour = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {

				mTimerStartHour = newValue;

			}
		};

		OnWheelChangedListener wheelListenerStartMin = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {

				mTimerStartMin = newValue;

			}
		};

		OnWheelChangedListener wheelListenerEndHour = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				mTimerEndHour = newValue;

			}
		};

		OnWheelChangedListener wheelListenerEndMin = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				mTimerEndMin = newValue;
			}
		};

		hours.addChangingListener(wheelListenerStartHour);
		mins.addChangingListener(wheelListenerStartMin);

		endHours.addChangingListener(wheelListenerEndHour);
		endMins.addChangingListener(wheelListenerEndMin);

		OnWheelClickedListener click = new OnWheelClickedListener() {
			public void onItemClicked(WheelView wheel, int itemIndex) {
				wheel.setCurrentItem(itemIndex, true);
			}
		};
		OnWheelClickedListener click2 = new OnWheelClickedListener() {
			public void onItemClicked(WheelView wheel, int itemIndex) {
				wheel.setCurrentItem(itemIndex, true);
			}
		};

		hours.addClickingListener(click);
		mins.addClickingListener(click);
		endHours.addClickingListener(click2);
		endMins.addClickingListener(click2);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				timeChanged = false;
			}
		};
		OnWheelScrollListener scrollListener2 = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				timeChanged = false;
			}
		};
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		endHours.addScrollingListener(scrollListener2);
		endMins.addScrollingListener(scrollListener2);
		
		
		ImageView hour_up_view=(ImageView)parent.findViewById(R.id.hour_up);
		hour_up_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hours.scroll(-1, mTimerStartHour);
			}
		});
		ImageView hour_down_view=(ImageView)parent.findViewById(R.id.hour_down);
		hour_down_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hours.scroll(1, mTimerStartHour);
			}
		});
		
		ImageView min_up_view=(ImageView)parent.findViewById(R.id.mins_up);
		min_up_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mins.scroll(-1, mTimerStartMin);
			}
		});
		ImageView min_down_view=(ImageView)parent.findViewById(R.id.mins_down);
		min_down_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mins.scroll(1, mTimerStartMin);
			}
		});
		
		ImageView end_hour_up_view=(ImageView)parent.findViewById(R.id.end_hour_up);
		end_hour_up_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				endHours.scroll(-1, mTimerStartHour);
			}
		});
		ImageView end_hour_down_view=(ImageView)parent.findViewById(R.id.end_hour_down);
		end_hour_down_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				endHours.scroll(1, mTimerStartHour);
			}
		});
		
		ImageView end_min_up_view=(ImageView)parent.findViewById(R.id.end_mins_up);
		end_min_up_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				endMins.scroll(-1, mTimerEndHour);
			}
		});
		ImageView end_min_down_view=(ImageView)parent.findViewById(R.id.end_mins_down);
		end_min_down_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				endMins.scroll(1, mTimerEndMin);
			}
		});
		
	
	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * 
	 * @param wheel
	 *            the wheel
	 * @param label
	 *            the wheel label
	 */
	private static void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}

}
