/*
 * Copyright (C) 2023 Fauzan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package my.com.vic3.spring.assessment1.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fauzan
 */
public class DateTimeUtil {

    /**
     *
     */
    public static String[] daySuffixes
            = //    0     1     2     3     4     5     6     7     8     9
            {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                //    10    11    12    13    14    15    16    17    18    19
                "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                //    20    21    22    23    24    25    26    27    28    29
                "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                //    30    31
                "th", "st"};

    /**
     *
     * @param timeString the time in HH:mm:ss format
     * @return the today's time in date object for the supplied timeString
     * @throws ParseException
     * @throws IllegalArgumentException
     */
    public static Date getTodaysTime(String timeString) throws ParseException, IllegalArgumentException {
        if (timeString == null) {
            throw new IllegalArgumentException("timeString null or invalid timeString " + timeString);
        }
        SimpleDateFormat todayFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat todayTimeFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

        Date todaysTime = null;
        try {
            Date today = new Date();
            String todayString = todayFormat.format(today);
            String todayTimeString = todayString + "-" + timeString;
            todaysTime = todayTimeFormat.parse(todayTimeString);
        } catch (ParseException e) {
            throw e;
        }
        return todaysTime;

    }

    /**
     *
     * @param time Date object representing the time
     * @param pattern Pattern string for time
     * @return The supplied time in string according to the pattern
     * @throws IllegalArgumentException
     */
    public static String getTimeString(Date time, String pattern) throws IllegalArgumentException {
        if (time == null || pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("One or both parameters are empty time : " + time + " pattern :" + pattern);
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String timeString = null;
        timeString = format.format(time);
        return timeString;
    }

    /**
     *
     * @param time Date object representing the time
     * @param minutes How many minutes to be added to the supplied time
     * @return The Date object representing the time after the minutes is added
     * @throws IllegalArgumentException
     */
    public static Date addMinutesToTime(Date time, Integer minutes) throws IllegalArgumentException {
        if (time == null || minutes == null) {
            throw new IllegalArgumentException("One or both parameters are empty time : " + time + " minutes :" + minutes);
        }
        return new Date(time.getTime() + (minutes.longValue() * 60l * 1000l));
    }
        /**
     *
     * @param time Date object representing the time
     * @param hour How many hours to be added to the supplied time
     * @return The Date object representing the time after the hour is added
     * @throws IllegalArgumentException
     */
    public static Date addHourToTime(Date time, Integer hour) throws IllegalArgumentException {
        if (time == null || hour == null) {
            throw new IllegalArgumentException("One or both parameters are empty time : " + time + " minutes :" + hour);
        }
        return new Date(time.getTime() + (hour.longValue() * 60l * 60l * 1000l));
    }
    

    /**
     *
     * @param time
     * @param minutes
     * @return
     * @throws IllegalArgumentException
     */
    public static Date minusMinutesFromTime(Date time, Integer minutes) throws IllegalArgumentException {
        if (time == null || minutes == null) {
            throw new IllegalArgumentException("One or both parameters are empty time : " + time + " minutes :" + minutes);
        }
        return new Date(time.getTime() - (minutes.longValue() * 60l * 1000l));
    }

    /**
     *
     * @param firstStartTime Starting of the first time period
     * @param firstEndTime Ending of the first time period
     * @param secondStartTime Starting of the second time period
     * @param secondEndTime Ending of the second time period
     * @return True if the time periods are overlapped, false if not
     * @throws IllegalArgumentException
     */
    public static boolean checkTimeClash(Date firstStartTime, Date firstEndTime, Date secondStartTime, Date secondEndTime) throws IllegalArgumentException {
        if (firstStartTime == null || firstEndTime == null || secondStartTime == null || secondEndTime == null) {
            throw new IllegalArgumentException("One or some parameters are empty time."
                    + "firstStartTime : " + firstStartTime + " firstEndTime : " + firstEndTime + " secondStartTime : " + secondStartTime + " secondEndTime : " + secondEndTime);
        }
        return firstStartTime.before(secondEndTime) && firstEndTime.after(secondStartTime);
    }

    /**
     *
     * @param firstTime
     * @param secondTime
     * @return Number of minutes difference. Always in positive value
     * @throws IllegalArgumentException
     */
    public static Integer timeDifferenceInMinutes(Date firstTime, Date secondTime) throws IllegalArgumentException {
        if (firstTime == null || secondTime == null) {
            throw new IllegalArgumentException("One or both parameters are empty firstTime : " + firstTime + " secondTime :" + secondTime);
        }
        Integer diff = new Long(Math.abs((secondTime.getTime() - firstTime.getTime()) / (1000l * 60l))).intValue();
        return diff;
    }

    public static Integer timeDifferenceInDays(Date firstTime, Date secondTime) throws IllegalArgumentException {
        if (firstTime == null || secondTime == null) {
            throw new IllegalArgumentException("One or both parameters are empty firstTime : " + firstTime + " secondTime :" + secondTime);
        }
        Integer diff = new Long(Math.abs((secondTime.getTime() - firstTime.getTime()) / (1000l * 60l * 60l * 24l))).intValue();
        return diff;
    }

    public static Integer timeDifferenceInDaysSigned(Date firstTime, Date secondTime) throws IllegalArgumentException {
        if (firstTime == null || secondTime == null) {
            throw new IllegalArgumentException("One or both parameters are empty firstTime : " + firstTime + " secondTime :" + secondTime);
        }
        Integer diff = new Long((secondTime.getTime() - firstTime.getTime()) / (1000l * 60l * 60l * 24l)).intValue();
        return diff;
    }

    /**
     *
     * @param firstTime
     * @param secondTime
     * @return
     * @throws IllegalArgumentException
     */
    public static Integer timeDifferenceInMinutesSigned(Date firstTime, Date secondTime) throws IllegalArgumentException {
        if (firstTime == null || secondTime == null) {
            throw new IllegalArgumentException("One or both parameters are empty firstTime : " + firstTime + " secondTime :" + secondTime);
        }
        Integer diff = new Long((secondTime.getTime() - firstTime.getTime()) / (1000l * 60l)).intValue();
        return diff;
    }

    /**
     *
     * @param firstTime
     * @param secondTime
     * @return Number of second difference. Always in positive value
     * @throws IllegalArgumentException
     */
    public static Integer timeDifferenceInSeconds(Date firstTime, Date secondTime) throws IllegalArgumentException {
        if (firstTime == null || secondTime == null) {
            throw new IllegalArgumentException("One or both parameters are empty firstTime : " + firstTime + " secondTime :" + secondTime);
        }
        Integer diff = new Long(Math.abs((secondTime.getTime() - firstTime.getTime()) / (1000l))).intValue();
        return diff;
    }

    /**
     * <p>
     * Checks if two dates are on the same day ignoring time.</p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) throws IllegalArgumentException {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if two calendars represent the same day ignoring time.</p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) throws IllegalArgumentException {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * <p>
     * Checks if a date is today.</p>
     *
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        return DateTimeUtil.isSameDay(date, Calendar.getInstance().getTime());
    }

    /**
     * <p>
     * Checks if a calendar date is today.</p>
     *
     * @param cal the calendar, not altered, not null
     * @return true if cal date is today
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    /**
     * <p>
     * Get yesterday date</p>
     *
     * @return yesterday date
     */
    public static Date getYesterday() {
        return new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    }

    /**
     * <p>
     * Convert string to date</p>
     *
     * @param dateString the date in string, not altered, not null.
     * @param pattern the date pattern, not altered, not null.
     * @return date
     */
    public static Date getDateFromString(String dateString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateString);
    }

    public static String milisecondsToLongStrings(long millis) {
        int day = (int) TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) - (day * 24);
        long minute = TimeUnit.MILLISECONDS.toMinutes(millis) - (TimeUnit.MILLISECONDS.toHours(millis) * 60);
        long second = TimeUnit.MILLISECONDS.toSeconds(millis) - (TimeUnit.MILLISECONDS.toMinutes(millis) * 60);
        long millisecond = millis - (TimeUnit.MILLISECONDS.toSeconds(millis) * 1000);
        return (day > 0 ? day + "d " : "")
                + (hours > 0 ? hours + "h " : "")
                + (minute > 0 ? minute + "m " : "")
                + (second > 0 ? second + "s " : "")
                + (millisecond > 0 ? millisecond + "ms " : "");

    }

    public static Date addDayToTime(Date date, int numberOfDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, numberOfDay);
        return c.getTime();
    }

    public static Date minusDayFromTime(Date date, int numberOfDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -numberOfDay);
        return c.getTime();
    }

    public static Date getToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        return today.getTime();
    }

    public static Date getDateOnly(Date date) throws ParseException {
        String pattern = "dd/MM/yyyy";
        return getDateFromString(getTimeString(date, pattern),pattern );
   
    }

    public static String getDayNameFromDate(Date date) {
        return getTimeString(date, "EEEE");
    }

    public static String changeStringPattern(String dateString, String patternFrom, String patternTo) throws ParseException {
        return DateTimeUtil.getTimeString(getDateFromString(dateString, patternFrom), patternTo);
    }

    public static Date truncateTime(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Date getPrecedenceCutOff(Date datetime, int hour, int minute){  
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set( Calendar.HOUR_OF_DAY, hour);
        cal.set( Calendar.MINUTE, minute);
        cal.set( Calendar.SECOND, 0);
        cal.set( Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static String getHourMinuteAmPm(String datetime){
        String hourMinuteAmPm = "";
        try{
            Date date = DateTimeUtil.getDateFromString(datetime, "dd/MM/yyyy HH:mm:ss");
            hourMinuteAmPm = DateTimeUtil.getTimeString(date, "HH:mm a");

        }catch(Exception e){
        }
        return hourMinuteAmPm;
    }
    
    public static boolean isFriday(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        
        if(dayOfWeek == Calendar.FRIDAY){
            return true;
        }else{
            return false;
        }
    }
    
}
