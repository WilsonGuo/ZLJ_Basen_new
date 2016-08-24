package com.example.smarteair.activity;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.app.Activity;
import android.os.Bundle;
import com.example.basen.R;

public class TimeActivity extends Activity {
	// Time changed flag
	private boolean timeChanged = false;
	
	// Time scrolled flag
	private boolean timeScrolled = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.timer_pick_layout);
	
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		mins.setCyclic(true);
	
	
		final WheelView endHours = (WheelView) findViewById(R.id.end_hour);
		endHours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		final WheelView endMins = (WheelView) findViewById(R.id.end_mins);
		endMins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		endMins.setCyclic(true);
		
		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
	
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);
		endHours.setCurrentItem(curHours);
		endMins.setCurrentItem(curMinutes);
	
		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) 
				{
					timeChanged = true;
					timeChanged = false;
				}
			}
		};
		
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
		endHours.addChangingListener(wheelListener);
		endMins.addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        
        hours.addClickingListener(click);
        mins.addClickingListener(click);
        endHours.addClickingListener(click);
        endMins.addClickingListener(click);
        
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
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		endHours.addScrollingListener(scrollListener);
        endMins.addScrollingListener(scrollListener);
	}
	
	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				//wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
	
}
