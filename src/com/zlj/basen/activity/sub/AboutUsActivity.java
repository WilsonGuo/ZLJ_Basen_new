package com.zlj.basen.activity.sub;

import com.example.basen.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AboutUsActivity extends Activity {
	ImageButton backBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basen_about_layout);
		backBtn=(ImageButton) this.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutUsActivity.this.finish();
			}
		});
	}


}
