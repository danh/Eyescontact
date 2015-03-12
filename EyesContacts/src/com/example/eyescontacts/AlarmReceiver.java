package com.example.eyescontacts;

import com.example.eyecontacts.utils.NotificationHelper;
import com.example.eyescontacts.manager.EyesContactPreference;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiver extends WakefulBroadcastReceiver {
	public static final String TAG = AlarmReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			NotificationHelper.showNotification(context, intent.getExtras());
			final int contactId = intent.getIntExtra(
					NotificationHelper.KEY_CONTACT_ID, 0);
			if (contactId == 0) {
				throw new Exception();
			}

			final int numberRegisteredNotification = EyesContactPreference
					.getInstance().getNumberRegisteredNotification(context,
							contactId);
			EyesContactPreference.getInstance()
					.saveNumberRegisteredNotification(context,
							numberRegisteredNotification - 1, contactId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.v(TAG, "Receive an alarm");
	}

}
