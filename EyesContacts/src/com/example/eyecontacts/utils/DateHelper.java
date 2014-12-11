package com.example.eyecontacts.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	public static final String FORMAT_FULL_DATE = "MM/dd/yyyy HH:mm:ss";
	public static final String FORMAT_DATE = "MM/dd/yyyy";
	public static final long ONE_DAY = 24 * 60 * 60 * 1000;

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		return sdf.format(date);
	}

	public static Date parse(String strDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static int getDayLeft(Date date1, Date date2) {
		long denta = date1.getTime() - date2.getTime();
		long rs = denta / ONE_DAY;
		if (rs < 0) {
			rs = 0;
		}

		if (denta % ONE_DAY > 0) {
			rs = rs + 1;
		}

		return (int) rs;
	}
}
