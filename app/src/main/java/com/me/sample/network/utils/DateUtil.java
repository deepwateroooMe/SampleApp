package com.me.sample.network.utils;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;

import androidx.core.net.ParseException;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateUtil {
    public static final String STANDARD_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_CN = "yyy Year MM Month dd Day";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String HOUR_MINUTE_SECOND_CN = "HH Hour mm Minute ss Second";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String MILLISECOND = "SSS";
    public static final String YESTERDAY = "Yesterday";
    public static final String TODAY = "Today";
    public static final String TOMORROW = "Tomorrow";
    public static final String SUNDAY = "Sundasy";
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final String[] weekDays = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    public static String getDateTime() {
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date());
    }

    public static String getFullDateTime() {
        return new SimpleDateFormat(FULL_TIME, Locale.CHINESE).format(new Date());
    }

    public static String getTheYearMonthAndDay() {
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(new Date());
    }

    public static String getTheYearMonthAndDayCn() {
        return new SimpleDateFormat(YEAR_MONTH_DAY_CN, Locale.CHINESE).format(new Date());
    }

    public static String getTheYearMonthAndDayDelimiter(CharSequence delimiter) {
        return new SimpleDateFormat(YEAR + delimiter + MONTH + delimiter + DAY, Locale.CHINESE).format(new Date());
    }

    public static String getHoursMinutesAndSeconds() {
        return new SimpleDateFormat(HOUR_MINUTE_SECOND, Locale.CHINESE).format(new Date());
    }

    public static String getHoursMinutesAndSecondsCn() {
        return new SimpleDateFormat(HOUR_MINUTE_SECOND_CN, Locale.CHINESE).format(new Date());
    }

    public static String getHoursMinutesAndSecondsDelimiter(CharSequence delimiter) {
        return new SimpleDateFormat(HOUR + delimiter + MINUTE + delimiter + SECOND, Locale.CHINESE).format(new Date());
    }

    public static String getYear() {
        return new SimpleDateFormat(YEAR, Locale.CHINESE).format(new Date());
    }

    public static String getMonth() {
        return new SimpleDateFormat(MONTH, Locale.CHINESE).format(new Date());
    }

    public static String getDay() {
        return new SimpleDateFormat(DAY, Locale.CHINESE).format(new Date());
    }

    public static String getHour() {
        return new SimpleDateFormat(HOUR, Locale.CHINESE).format(new Date());
    }

    public static String getMinute() {
        return new SimpleDateFormat(MINUTE, Locale.CHINESE).format(new Date());
    }

    public static String getSecond() {
        return new SimpleDateFormat(SECOND, Locale.CHINESE).format(new Date());
    }

    public static String getMilliSecond() {
        return new SimpleDateFormat(MILLISECOND, Locale.CHINESE).format(new Date());
    }

    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static long dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(date).getTime();
    }

    public static String stampToDate(long timeMillis) {
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date(timeMillis));
    }

    public static String getTodayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) {
            index = 0;
        }
        return weekDays[index];
    }

    public static String getWeek(String dateTime) {
        Calendar cal = Calendar.getInstance();
        if ("".equals(dateTime)) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException | java.text.ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String getYesterday(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date);
    }

    public static String getTomorrow(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date);
    }

    public static String getDayInfo(String dateTime) {
        String dayInfo;
        String yesterday = getYesterday(new Date());
        String today = getTheYearMonthAndDay();
        String tomorrow = getTomorrow(new Date());

        if (dateTime.equals(yesterday)) {
            dayInfo = YESTERDAY;
        } else if (dateTime.equals(today)) {
            dayInfo = TODAY;
        } else if (dateTime.equals(tomorrow)) {
            dayInfo = TOMORROW;
        } else {
            dayInfo = getWeek(dateTime);
        }
        return dayInfo;
    }

    public static int getCurrentMonthDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    public static int getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }
}