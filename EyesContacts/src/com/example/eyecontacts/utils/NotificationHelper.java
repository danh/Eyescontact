package com.example.eyecontacts.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.example.eyecontacts.data.EyesContact;
import com.example.eyecontacts.utils.DateHelper;
import com.example.eyescontacts.AlarmReceiver;
import com.example.eyescontacts.MainActivity;
import com.example.eyescontacts.R;
import com.example.eyescontacts.manager.EyesContactPreference;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {
	public static final int ONE_SECOND = 1000;
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_CONTACT_ID = "contactId";

	public static void registerAlarm(Context context, EyesContact contact) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		for (int i = 7; i >= 0; i--) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, i);
			if (compareTime(contact.getTimeChangeContact(), cal.getTime()) > 0) {
				final long miliSecondLeft = contact.getTimeChangeContact()
						.getTime()
						- i
						* DateHelper.ONE_DAY
						- Calendar.getInstance().getTimeInMillis();
				createAlarm(miliSecondLeft, context, am, contact,
						context.getString(R.string.alert_to_change_contact), i);
			}
		}
	}

	private static void createAlarm(long miliSecondsLeft, Context context,
			AlarmManager am, EyesContact contact, String message,
			int reminderType) {
		Calendar cal = Calendar.getInstance();
		final int seconds = (int) miliSecondsLeft / ONE_SECOND;
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

	private static int compareTime(Date date1, Date date2) {
		final int absErrorMiliSeconds = 3000;
		if (Math.abs(date1.getTime() - date2.getTime()) <= absErrorMiliSeconds) {
			return 0;
		} else if (date1.getTime() > date2.getTime()) {
			return 1;
		}

		return -1;
	}

	public static void showNotification(Context context, Bundle bundle) {
		final String message = bundle.getString("Message");

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Bitmap
		Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);

		// Builds notification that will trigger a vibration, notification
		// message, and ticker message.
		NotificationCompat.Builder noti = new NotificationCompat.Builder(
				context)
				.setContentTitle("Message")
				.setContentText(message)
				.setSmallIcon(R.drawable.ic_launcher)
				.setLargeIcon(largeIcon)
				.setContentIntent(pIntent)
				.setAutoCancel(true)
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(message));

		final int noficationId = new Random().nextInt();
		notificationManager.notify(noficationId, noti.build());

		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
	}
}
