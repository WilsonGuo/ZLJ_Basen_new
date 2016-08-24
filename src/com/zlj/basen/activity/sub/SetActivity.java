package com.zlj.basen.activity.sub;

import com.example.basen.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SetActivity extends Activity {
	ImageButton backBtn;
	ImageButton addDeviceBtn;
	ImageButton userNoteBtn;
	ImageButton feedBackBtn;
	ImageButton aboutUsBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basen_set_layout);
		backBtn=(ImageButton) this.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SetActivity.this.finish();
			}
		});
		addDeviceBtn=(ImageButton) this.findViewById(R.id.btn_add_device);
		userNoteBtn=(ImageButton) this.findViewById(R.id.btn_user_note);
		feedBackBtn=(ImageButton) this.findViewById(R.id.btn_feedback);
		aboutUsBtn=(ImageButton) this.findViewById(R.id.btn_about_us);
	
		
		addDeviceBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SetActivity.this, ConfigDeviceActivity.class);
				startActivity(intent);
			}
		});
		
		userNoteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SetActivity.this, UserNoteActivity.class);
				startActivity(intent);
			}
		});
		feedBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SetActivity.this, FAQActivity.class);
				startActivity(intent);
			}
		});
		aboutUsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SetActivity.this, AboutUsActivity.class);
				startActivity(intent);
			}
		});
		
	}


}
