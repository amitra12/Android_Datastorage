package com.androidstorageapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AndroidStorage {

	/**
	 * 
	 * @return Date Time in String with the MM/dd/yyyy hh:mm:ss a format
	 */
	public static String getCurrentDateTime() {
		long timeInMilliseconds = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMilliseconds);
		SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		String strDate = date.format(calendar.getTime());
		return strDate;
	}
}
