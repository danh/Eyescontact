package com.example.eyecontacts.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	public static final String FORMAT_DATE = "MM/dd/yyyy"; // "03-14-2013 00:00:00"
	public static final long ONE_DATE = 24 * 60 * 60 * 1000;

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

	public static Date removeHMS(Date date) {
		String s = format(date, FORMAT_DATE);
		return parse(s, FORMAT_DATE);
	}

	public static int getDayLeft(Date date1, Date date2) {
		long denta = date1.getTime() - date2.getTime();
		long rs = denta / (1000 * 60 * 60 * 24);
		if (rs < 0) {
			rs = 0;
		}
		return (int) rs;
	}
}
