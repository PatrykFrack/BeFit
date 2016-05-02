package com.frackowiak.befit.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by PFRACKOW on 07.12.2015.
 */
abstract class AbstractAlarmManager extends BroadcastReceiver {

    final static int BOOTALARM_ID = 101;
    final static int DAILYALARM_ID = 102;
    final static int ERRANDALARM_ID = 103;
    final static int UPDATEALARM_ID = 104;
    /**
     *
     * @param context
     * @param intentClass class that will be used in the intent
     * @param ALARM_ID
     * @param triggerAtMillis time in milliseconds that the alarm should first
     * go off, using the appropriate clock (depending on the alarm type).
     * @param intervalMillis interval in milliseconds between subsequent repeats
     * of the alarm.
     */
    public void SetRepeatingAlarm(Context context, Class<?> intentClass, final int ALARM_ID, long triggerAtMillis, long intervalMillis)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, intentClass);
        PendingIntent pi = PendingIntent.getBroadcast(context, ALARM_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pi);
    }
    public void SetRepeatingService(Context context, Class<?> intentClass, final int ALARM_ID, long triggerAtMillis, long intervalMillis)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, intentClass);
        PendingIntent pi = PendingIntent.getService(context, ALARM_ID, i, PendingIntent.FLAG_CANCEL_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pi);
    }

    public void CancelAlarm(Context context, Class<?> intentClass, final int ALARM_ID){
        Intent intent = new Intent(context, intentClass);
        PendingIntent sender = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void CancelAlarmService(Context context, Class<?> intentClass, final int ALARM_ID){
        Intent intent = new Intent(context, intentClass);
        PendingIntent sender = PendingIntent.getService(context, ALARM_ID, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    protected long getTimeMillis(int hour, int minutes, int seconds){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        return cal.getTimeInMillis();
    }
}
