package com.example.eyescontacts;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.example.eyecontacts.data.EyesContact;
import com.example.eyecontacts.utils.DateHelper;
import com.example.eyescontacts.manager.EyesContactPreference;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements Observer {
	private TextView txtLeftLastChanged;
	private TextView txtRightLastChanged;
	private TextView txtLeftRemainDay;
	private TextView txtRightRemainDay;
	private TextView txtTitleLeftDay;
	private TextView txtTitleRightDay;

	MyTimerTask myTimerTask;
	Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EyesContactPreference.getInstance().addObserver(this);
		setContentView(R.layout.activity_main);
		setupViews();

		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		myTimerTask = new MyTimerTask();
		timer.schedule(myTimerTask, 0, DateHelper.ONE_DAY);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EyesContactPreference.getInstance().deleteObserver(this);
		if (timer != null) {
			timer.cancel();
		}
	}

	private void setupViews() {
		Button btn = (Button) findViewById(R.id.btn_change_now);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ChangeNowActivity.class);
				startActivity(intent);
			}
		});

		txtLeftLastChanged = (TextView) findViewById(R.id.txt_left_eye_last_chaged);
		txtRightLastChanged = (TextView) findViewById(R.id.txt_right_eye_last_chaged);
		txtLeftRemainDay = (TextView) findViewById(R.id.txt_left_eye_remain_day);
		txtRightRemainDay = (TextView) findViewById(R.id.txt_right_eye_remain_day);
		txtTitleLeftDay = (TextView) findViewById(R.id.txt_title_left_eye_day);
		txtTitleRightDay = (TextView) findViewById(R.id.txt_title_right_eye_day);
	}

	private void updateUI() {
		final EyesContact leftContact = EyesContactPreference.getInstance()
				.getEyeContact(getApplicationContext(), EyesContact.LEFT_EYE);
		txtLeftLastChanged.setText(DateHelper.format(
				leftContact.getTimeLastChange(), DateHelper.FORMAT_DATE));
		txtLeftRemainDay.setText(leftContact.getRemainDay() + "");
		if (leftContact.getRemainDay() <= 1) {
			txtTitleLeftDay.setText(getString(R.string.day));
		} else {
			txtTitleLeftDay.setText(getString(R.string.days));
		}

		final EyesContact rightContact = EyesContactPreference.getInstance()
				.getEyeContact(getApplicationContext(), EyesContact.RIGHT_EYE);
		txtRightLastChanged.setText(DateHelper.format(
				rightContact.getTimeLastChange(), DateHelper.FORMAT_DATE));
		txtRightRemainDay.setText(rightContact.getRemainDay() + "");
		if (rightContact.getRemainDay() <= 1) {
			txtTitleRightDay.setText(getString(R.string.day));
		} else {
			txtTitleRightDay.setText(getString(R.string.days));
		}
	}

	@Override
	public void update(Observable arg0, Object data) {
		updateUI();
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					updateUI();
				}
			});
		}
	}
}
