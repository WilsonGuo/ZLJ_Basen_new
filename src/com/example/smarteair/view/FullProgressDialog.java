
package com.example.smarteair.view;

import com.example.basen.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class FullProgressDialog {

	private Dialog mDialog;

	public void showProgressDialog(Context context, OnDismissListener listener) {
		if (mDialog != null) {
			return;
		}

		// mDialog = new Dialog(context,
		// android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		mDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
		LinearLayout linearlayout = (LinearLayout) ((LayoutInflater) context.getSystemService("layout_inflater"))
				.inflate(R.layout.progress_dialog_layout, null);

		android.view.WindowManager.LayoutParams layoutparams = mDialog.getWindow().getAttributes();
		mDialog.onWindowAttributesChanged(layoutparams);
		mDialog.setContentView(linearlayout);
		mDialog.show();

		if (listener != null) {
			mDialog.setOnDismissListener(listener);
		}
	}

	public void dismissProressDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

}
