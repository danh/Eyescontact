package com.example.eyescontacts;

import java.util.Calendar;
import java.util.Date;

import com.example.eyecontacts.data.EyesContact;
import com.example.eyecontacts.utils.DateHelper;
import com.example.eyecontacts.utils.NotificationHelper;
import com.example.eyescontacts.manager.EyesContactPreference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeNowActivity extends BaseActivity {
	private TextView txtLeftLastChanged;
	private TextView txtRightLastChanged;
	private EditText editLeftRemainDays;
	private EditText editRightRemainDays;
	private TextView txtTitleLeftDay;
	private TextView txtTitleRightDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_now);
		setupViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	private void setupViews() {
		((TextView) findViewById(R.id.txt_title_bar))
				.setText(getString(R.string.change_lense_now));
		((Button) findViewById(R.id.btn_change_now))
				.setText(getString(R.string.save_info));

		Button btn = (Button) findViewById(R.id.btn_change_now);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String leftEdit = editLeftRemainDays.getText().toString();
				final String rightEdit = editRightRemainDays.getText()
						.toString();

				if (TextUtils.isEmpty(leftEdit) || (leftEdit.length() >= 5)
						|| TextUtils.isEmpty(rightEdit)
						|| (rightEdit.length() >= 5)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChangeNowActivity.this);
					builder.setTitle("Alert");
					builder.setMessage("Remain day must be from 0 to 9999");
					builder.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
								}
							});
					builder.show();
					return;
				}

				try {
					int leftRemainDays = Integer.parseInt(leftEdit);
					final EyesContact leftContact = new EyesContact(
							EyesContact.LEFT_EYE);
					leftContact.setTimeLastChange(Calendar.getInstance()
							.getTime());

					Date date = Calendar.getInstance().getTime();
					date.setTime(date.getTime() + leftRemainDays
							* DateHelper.ONE_DAY);
					leftContact.setTimeChangeContact(date);
					EyesContactPreference.getInstance().saveContact(
							getApplicationContext(), leftContact);
				} catch (NumberFormatException e) {
					return;
				}

				try {
					int rightRemainDays = Integer.parseInt(rightEdit);
					final EyesContact rightContact = new EyesContact(
							EyesContact.RIGHT_EYE);
					rightContact.setTimeLastChange(Calendar.getInstance()
							.getTime());

					Date date = Calendar.getInstance().getTime();
					date.setTime(date.getTime() + rightRemainDays
							* DateHelper.ONE_DAY);
					rightContact.setTimeChangeContact(date);
					EyesContactPreference.getInstance().saveContact(
							getApplicationContext(), rightContact);
				} catch (NumberFormatException e) {
					return;
				}
				NotificationHelper.removeAlarm(getApplicationContext(),
						EyesContact.LEFT_EYE);
				NotificationHelper.removeAlarm(getApplicationContext(),
						EyesContact.RIGHT_EYE);
				NotificationHelper.registerAlarm(
						getApplicationContext(),
						EyesContactPreference.getInstance().getEyeContact(
								getApplicationContext(), EyesContact.LEFT_EYE));
				NotificationHelper
						.registerAlarm(
								getApplicationContext(),
								EyesContactPreference.getInstance()
										.getEyeContact(getApplicationContext(),
												EyesContact.RIGHT_EYE));

				finish();
			}
		});

		txtLeftLastChanged = (TextView) findViewById(R.id.txt_left_eye_last_changed);
		txtRightLastChanged = (TextView) findViewById(R.id.txt_right_eye_last_changed);
		editLeftRemainDays = (EditText) findViewById(R.id.edit_left_eye_remain_days);
		editRightRemainDays = (EditText) findViewById(R.id.edit_right_eye_remain_days);
		txtTitleLeftDay = (TextView) findViewById(R.id.txt_title_left_eye_day);
		txtTitleRightDay = (TextView) findViewById(R.id.txt_title_right_eye_day);

		findViewById(R.id.layout_left_contact).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (findViewById(R.id.layout_left_contact_content)
								.isShown()) {
							findViewById(R.id.layout_left_contact_content)
									.setVisibility(View.GONE);
						} else {
							findViewById(R.id.layout_left_contact_content)
									.setVisibility(View.VISIBLE);
						}
					}
				});
		findViewById(R.id.layout_right_contact).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (findViewById(R.id.layout_right_contact_content)
								.isShown()) {
							findViewById(R.id.layout_right_contact_content)
									.setVisibility(View.GONE);
						} else {
							findViewById(R.id.layout_right_contact_content)
									.setVisibility(View.VISIBLE);
						}
					}
				});

	}

	private void updateUI() {
		final EyesContact leftContact = EyesContactPreference.getInstance()
				.getEyeContact(getApplicationContext(), EyesContact.LEFT_EYE);
		txtLeftLastChanged.setText(DateHelper.format(
				leftContact.getTimeLastChange(), DateHelper.FORMAT_DATE));
		editLeftRemainDays.setText(leftContact.getRemainDay() + "");
		if (leftContact.getRemainDay() <= 1) {
			txtTitleLeftDay.setText(getString(R.string.day));
		} else {
			txtTitleLeftDay.setText(getString(R.string.days));
		}

		final EyesContact rightContact = EyesContactPreference.getInstance()
				.getEyeContact(getApplicationContext(), EyesContact.RIGHT_EYE);
		txtRightLastChanged.setText(DateHelper.format(
				rightContact.getTimeLastChange(), DateHelper.FORMAT_DATE));
		editRightRemainDays.setText(rightContact.getRemainDay() + "");
		if (rightContact.getRemainDay() <= 1) {
			txtTitleRightDay.setText(getString(R.string.day));
		} else {
			txtTitleRightDay.setText(getString(R.string.days));
		}
	}

}
