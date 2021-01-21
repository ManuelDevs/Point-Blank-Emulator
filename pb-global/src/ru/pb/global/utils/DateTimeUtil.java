package ru.pb.global.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Утилита для работы с датами
 *
 * @author Felixx
 */
public class DateTimeUtil {
	private static final SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmm");

	public static int getDateTime() {
		return getDateTime(0);
	}

	public static int getDateTime(int addDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addDays);
		return Integer.parseInt(format.format(cal.getTime()));
	}

	public static int getDateTime(Date date) {
		return Integer.parseInt(format.format(date.getTime()));
	}

	public static Date geDateFromInt(int date) {
		return geDateFromString(String.valueOf(date));
	}

	public static Date geDateFromString(String date) {
		try {
			return format.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static int getDateTime(SimpleDateFormat formatter) {
		return getDateTime(0, formatter);
	}

	public static int getDateTime(int addDays, SimpleDateFormat formatter) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addDays);
		return Integer.parseInt(formatter.format(cal.getTime()));
	}

	public static int getDateTime(Date date, SimpleDateFormat formatter) {
		return Integer.parseInt(formatter.format(date.getTime()));
	}

	public static Date geDateFromInt(int date, SimpleDateFormat formatter) {
		return geDateFromString(String.valueOf(date), formatter);
	}

	public static Date geDateFromString(String date, SimpleDateFormat formatter) {
		try {
			return formatter.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
