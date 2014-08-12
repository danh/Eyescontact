package com.example.eyecontacts.data;

import java.util.Calendar;
import java.util.Date;

import com.example.eyecontacts.utils.DateHelper;

public class EyesContact {
	private Date timeLastChange;
	private Date timeChangeContact;
	private int remainDay;

	public Date getTimeLastChange() {
		return timeLastChange;
	}

	public void setTimeLastChange(Date timeLastChange) {
		this.timeLastChange = timeLastChange;
	}

	public Date getTimeChangeContact() {
		return timeChangeContact;
	}

	public void setTimeChangeContact(Date timeChangeContact) {
		this.timeChangeContact = timeChangeContact;
	}

	public int getRemainDay() {
		if (getTimeChangeContact() == null || getTimeLastChange() == null) {
			return 0;
		}

		return DateHelper.getDayLeft(getTimeChangeContact(),
				DateHelper.removeHMS(Calendar.getInstance().getTime()));
	}

}
