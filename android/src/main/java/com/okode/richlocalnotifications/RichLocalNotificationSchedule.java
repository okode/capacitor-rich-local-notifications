package com.okode.richlocalnotifications;

import android.text.format.DateUtils;

import com.getcapacitor.JSObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RichLocalNotificationSchedule {

    private static String JS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String EVERY = "every";
    private static final String AT = "at";
    private static final String REPEATS = "repeats";

    private Date at;
    private Boolean repeats;
    private String every;

    private RichLocalNotificationSchedule() { }

    public static RichLocalNotificationSchedule buildFromJson(JSObject schedule) throws ParseException {
        if (schedule == null) { return null; }

        String every = schedule.getString(EVERY);
        String at = schedule.getString(AT);
        Boolean repeats = schedule.getBool(REPEATS);

        if (every != null) { return buildEvery(every); }
        if (at != null) { return buildAtElement(at, repeats); }
        return null;
    }

    public static RichLocalNotificationSchedule buildEvery(String every) {
        RichLocalNotificationSchedule schedule = new RichLocalNotificationSchedule();
        // 'year'|'month'|'two-weeks'|'week'|'day'|'hour'|'minute'|'second';
        schedule.setEvery(every);
        return schedule;
    }

    public static RichLocalNotificationSchedule buildAtElement(String at, boolean repeats) throws ParseException {
        RichLocalNotificationSchedule schedule = new RichLocalNotificationSchedule();
        if (at != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(JS_DATE_FORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            schedule.setAt(sdf.parse(at));
        }
        schedule.setRepeats(repeats);
        return schedule;
    }


    public Date getAt() {
        return at;
    }

    public void setAt(Date at) {
        this.at = at;
    }

    public Boolean getRepeats() {
        return repeats;
    }

    public void setRepeats(Boolean repeats) {
        this.repeats = repeats;
    }

    public String getEvery() {
        return every;
    }

    public void setEvery(String every) {
        this.every = every;
    }

    public boolean isRepeating() {
        return Boolean.TRUE.equals(this.repeats);
    }

    public boolean isRemovable() {
        if (every == null) {
            if (at != null) {
                return !isRepeating();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Get constant long value representing specific interval of time (weeks, days etc.)
     */
    public Long getEveryInterval() {
        switch (every) {
            case "year":
                return DateUtils.YEAR_IN_MILLIS;
            case "month":
                // This case is just approximation as months have different number of days
                return 30 * DateUtils.DAY_IN_MILLIS;
            case "two-weeks":
                return 2 * DateUtils.WEEK_IN_MILLIS;
            case "week":
                return DateUtils.WEEK_IN_MILLIS;
            case "day":
                return DateUtils.DAY_IN_MILLIS;
            case "hour":
                return DateUtils.HOUR_IN_MILLIS;
            case "minute":
                return DateUtils.MINUTE_IN_MILLIS;
            case "second":
                return DateUtils.SECOND_IN_MILLIS;
            default:
                return null;
        }
    }

}
