package com.example.smarteair.activity;

import com.example.smarteair.view.VerticalSeekBar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.example.basen.R;

public class HelloSeekBarActivity extends Activity {
	private SeekBar horiSeekBar = null;
	private TextView horiText = null;

	private VerticalSeekBar verticalSeekBar = null;
	private TextView verticalText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_seek_bar);

		horiSeekBar = (SeekBar) findViewById(R.id.horiSeekBar);
		horiText = (TextView) findViewById(R.id.horiText);
		horiSeekBar.setOnSeekBarChangeListener(horiSeekBarListener);

		verticalSeekBar = (VerticalSeekBar) findViewById(R.id.verticalSeekBar);
		verticalText = (TextView) findViewById(R.id.verticalText);
		verticalSeekBar.setOnSeekBarChangeListener(verticalSeekBarChangeListener);

	}

	private OnSeekBarChangeListener horiSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			horiText.setText(Integer.toString(progress));

		}
	};

	private OnSeekBarChangeListener verticalSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			verticalText.setText(Integer.toString(progress));

		}
	};

}