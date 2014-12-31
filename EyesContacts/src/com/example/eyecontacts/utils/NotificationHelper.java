package com.example.eyecontacts.utils;

import java.util.Calendar;

import com.example.eyecontacts.data.EyesContact;
import com.example.eyecontacts.utils.DateHelper;
import com.example.eyescontacts.AlarmReceiver;
import com.example.eyescontacts.R;
import com.example.eyescontacts.manager.EyesContactPreference;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NotificationHelper {
	public static final int ONE_MINUTE = 60 * 1000;
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_CONTACT_ID = "contactId";

	public static void registerAlarm(Context context, EyesContact contact) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		for (int i = 7; i > 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, i);
			if (contact.getTimeChangeContact().compareTo(cal.getTime()) >= 0) {
				long secondLeft = contact.getTimeChangeContact().getTime() - i
						* DateHelper.ONE_DAY
						- Calendar.getInstance().getTimeInMillis();
				createAlarm(secondLeft, context, am, contact,
						context.getString(R.string.alert_to_change_contact), i);
			}
		}
	}

	private static void createAlarm(long secondsLeft, Context context,
			AlarmManager am, EyesContact contact, String message,
			int reminderType) {
		Calendar cal = Calendar.getInstance();
		final int minutes = (int) secondsLeft / ONE_MINUTE;
		final int seconds = (int) secondsLeft % ONE_MINUTE;
		cal.add(Calendar.MINUTE, minutes);
		cal.add(Calendar.SECOND, seconds);

		Bundle bundle = new Bundle();
		bundle.putString("Message", message);
		bundle.putInt(KEY_CONTACT_ID, contact.getId());

		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtras(bundle);

		final int id = contact.getId() * reminderType;
		PendingIntent sender = PendingIntent.getBroadcast(context, id, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

		final int numberRegisteredNotification = EyesContactPreference
				.getInstance().getNumberRegisteredNotification(context,
						contact.getId());
		EyesContactPreference.getInstance().saveNumberRegisteredNotification(
				context, numberRegisteredNotification + 1, contact.getId());
	}

	public static void removeAlarm(Context context, int contactId) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		for (int i = 7; i > 0; i--) {
			Bundle bundle = new Bundle();
			bundle.putString(KEY_MESSAGE,
					context.getString(R.string.alert_to_change_contact));
			bundle.putInt(KEY_CONTACT_ID, contactId);

			final int id = contactId * i;

			Intent intent = new Intent(context, AlarmReceiver.class);
			intent.putExtras(bundle);
			PendingIntent sender = PendingIntent.getBroadcast(context, id,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);

			am.cancel(sender);
		}

		EyesContactPreference.getInstance().saveNumberRegisteredNotification(
				context, 0, contactId);
	}
}
