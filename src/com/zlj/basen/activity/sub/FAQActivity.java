package com.zlj.basen.activity.sub;

import com.example.basen.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;

public class FAQActivity extends Activity {
	ImageButton backBtn;
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basen_faq_layout);
		backBtn = (ImageButton) this.findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FAQActivity.this.finish();
			}
		});

		webView = (WebView) this.findViewById(R.id.webView);

		try {
			webView.getBackground().setAlpha(0);
		} catch (Exception exception) {
		}

		if (getString(R.string.locale).equals("zh")) {
			webView.loadUrl("file:///android_asset/faq_cn.html");
			return;
		} else {
			webView.loadUrl("file:///android_asset/faq.html");
			return;
		}
	}

}
