package com.example.eyescontacts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	public static final String TAG = AlarmReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			final Intent newIntent = new Intent(context, ReminderDialog.class);
			newIntent.putExtras(intent.getExtras());
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(newIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.v(TAG, "Receive an alarm");
	}

}
