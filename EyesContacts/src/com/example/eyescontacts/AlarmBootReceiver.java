package com.example.eyescontacts;

import java.util.Calendar;

import com.example.eyecontacts.data.EyesContact;
import com.example.eyecontacts.utils.NotificationHelper;
import com.example.eyescontacts.manager.EyesContactPreference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

			if (EyesContactPreference.getInstance()
					.getNumberRegisteredNotification(context,
							EyesContact.LEFT_EYE) > 0) {
				EyesContactPreference.getInstance()
						.saveNumberRegisteredNotification(context, 0,
								EyesContact.LEFT_EYE);

				final EyesContact eyesContact = EyesContactPreference
						.getInstance().getEyeContact(context,
								EyesContact.LEFT_EYE);
				final Calendar currentDate = Calendar.getInstance();
				currentDate.add(Calendar.DATE, 1);
				if (eyesContact.getTimeChangeContact().compareTo(
						currentDate.getTime()) <= 0) {
					try {
						Bundle bundle = new Bundle();
						bundle.putString(
								NotificationHelper.KEY_MESSAGE,
								context.getString(R.string.alert_to_change_contact));
						NotificationHelper.showNotification(context, bundle);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					NotificationHelper.registerAlarm(context, eyesContact);
				}
			}
			if (EyesContactPreference.getInstance()
					.getNumberRegisteredNotification(context,
							EyesContact.RIGHT_EYE) > 0) {
				EyesContactPreference.getInstance()
						.saveNumberRegisteredNotification(context, 0,
								EyesContact.RIGHT_EYE);

				final EyesContact eyesContact = EyesContactPreference
						.getInstance().getEyeContact(context,
								EyesContact.RIGHT_EYE);

				final Calendar currentDate = Calendar.getInstance();
				currentDate.add(Calendar.DATE, 1);
				if (eyesContact.getTimeChangeContact().compareTo(
						currentDate.getTime()) <= 0) {
					try {
						final Intent newIntent = new Intent(context,
								ReminderDialog.class);
						Bundle bundle = new Bundle();
						bundle.putString(
								NotificationHelper.KEY_MESSAGE,
								context.getString(R.string.alert_to_change_contact));
						newIntent.putExtras(bundle);
						newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						context.startActivity(newIntent);

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					NotificationHelper.registerAlarm(context, eyesContact);
				}
			}
		}
	}

}
