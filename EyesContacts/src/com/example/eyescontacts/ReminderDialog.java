package com.example.eyescontacts;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReminderDialog extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_reminder);
		
		final Bundle bundle = getIntent().getExtras();
		initUpcommingReminderUI(bundle);
	}

	private void initUpcommingReminderUI(final Bundle bundle) {
		final String message = bundle.getString("Message");

		final TextView txtMessage = (TextView) findViewById(R.id.txt_message);
		txtMessage.setText(message);

		findViewById(R.id.btn_see_offer).setVisibility(View.GONE);

		final Button btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setText("OK");
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				finish();
			}
		});
	}
}
