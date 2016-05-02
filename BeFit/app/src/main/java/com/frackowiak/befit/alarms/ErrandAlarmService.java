package com.frackowiak.befit.alarms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by PFRACKOW on 02.12.2015.
 */
public class ErrandAlarmService extends Service
{
    ErrandAlarmManager alarm = new ErrandAlarmManager();
    Scheduler scheduler = new Scheduler();

    public void onCreate()
    {
        super.onCreate();
        Log.d("BeFit", "ErrandAlarmService created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("BeFit", "ErrandAlarmService: Errands will be started");
        alarm.setRepeatingErrand(this, scheduler.getDaySchedule(), scheduler.getErrandFrequency());
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        alarm.setRepeatingErrand(this, scheduler.getDaySchedule(), scheduler.getErrandFrequency());
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

}
