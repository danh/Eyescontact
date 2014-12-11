/**
 * 
 */
package com.example.eyescontacts.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

import com.example.eyecontacts.data.EyesContact;
import com.example.eyecontacts.utils.DateHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class EyesContactPreference extends Observable {
	private static final String LEFT_EYE_PREFS_NAME = "LeftEyePreference";
	private static final String RIGHT_EYE_PREFS_NAME = "RightEyePreference";

	// key contact
	private static final String KEY_ID = "id";
	private static final String KEY_TIME_LAST_SAVE_CHANGE = "timeLastSaveChange";
	private static final String KEY_TIME_CHANGE_CONTACT = "timeChangeContact";

	private volatile static EyesContactPreference instance;

	private EyesContactPreference() {

	}

	public static EyesContactPreference getInstance() {
		if (instance == null) {
			synchronized (EyesContactPreference.class) {
				if (instance == null) {
					instance = new EyesContactPreference();
				}
			}
		}

		return instance;
	}

	public EyesContact getLeftEyeContact(Context context) {
		SharedPreferences pre = context.getSharedPreferences(
				LEFT_EYE_PREFS_NAME, 0);
		EyesContact contact = new EyesContact();
		contact.setId(EyesContact.LEFT_EYE);
		Date dateLastChange = DateHelper.parse(
				pre.getString(KEY_TIME_LAST_SAVE_CHANGE, ""),
				DateHelper.FORMAT_FULL_DATE);
		if (dateLastChange == null) {
			dateLastChange = Calendar.getInstance().getTime();
		}
		contact.setTimeLastChange(dateLastChange);
		Date dateChangeContact = DateHelper.parse(
				pre.getString(KEY_TIME_CHANGE_CONTACT, ""),
				DateHelper.FORMAT_FULL_DATE);
		if (dateChangeContact == null) {
			dateChangeContact = Calendar.getInstance().getTime();
		}
		contact.setTimeChangeContact(dateChangeContact);

		return contact;
	}

	public void saveLeftContact(Context context, EyesContact contact) {
		SharedPreferences pre = context.getSharedPreferences(
				LEFT_EYE_PREFS_NAME, 0);
		SharedPreferences.Editor editor = pre.edit();
		editor.putInt(KEY_ID, contact.getId());
		editor.putString(KEY_TIME_LAST_SAVE_CHANGE, DateHelper.format(
				contact.getTimeLastChange(), DateHelper.FORMAT_FULL_DATE));
		editor.putString(KEY_TIME_CHANGE_CONTACT, DateHelper.format(
				contact.getTimeChangeContact(), DateHelper.FORMAT_FULL_DATE));

		editor.commit();
		setChanged();
		notifyObservers(contact);
	}

	public EyesContact getRightEyeContact(Context context) {
		SharedPreferences pre = context.getSharedPreferences(
				RIGHT_EYE_PREFS_NAME, 0);
		EyesContact contact = new EyesContact();
		contact.setId(EyesContact.RIGHT_EYE);
		Date dateLastChange = DateHelper.parse(
				pre.getString(KEY_TIME_LAST_SAVE_CHANGE, ""),
				DateHelper.FORMAT_FULL_DATE);
		if (dateLastChange == null) {
			dateLastChange = Calendar.getInstance().getTime();
		}
		contact.setTimeLastChange(dateLastChange);
		Date dateChangeContact = DateHelper.parse(
				pre.getString(KEY_TIME_CHANGE_CONTACT, ""),
				DateHelper.FORMAT_FULL_DATE);
		if (dateChangeContact == null) {
			dateChangeContact = Calendar.getInstance().getTime();
		}
		contact.setTimeChangeContact(dateChangeContact);

		return contact;
	}

	public void saveRightContact(Context context, EyesContact contact) {
		SharedPreferences pre = context.getSharedPreferences(
				RIGHT_EYE_PREFS_NAME, 0);
		SharedPreferences.Editor editor = pre.edit();
		editor.putInt(KEY_ID, contact.getId());
		editor.putString(KEY_TIME_LAST_SAVE_CHANGE, DateHelper.format(
				contact.getTimeLastChange(), DateHelper.FORMAT_FULL_DATE));
		editor.putString(KEY_TIME_CHANGE_CONTACT, DateHelper.format(
				contact.getTimeChangeContact(), DateHelper.FORMAT_FULL_DATE));

		editor.commit();
		setChanged();
		notifyObservers(contact);
	}

}
