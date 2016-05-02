package com.frackowiak.befit.alarms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.frackowiak.befit.rest.UpdateBeaconsAndErrandsService;

/**
 * Created by PFRACKOW on 06.12.2015.
 */
public class DailyAlarmManager extends AbstractAlarmManager
{
    private static final int ERRAND_START_HOUR = 10;
    private static final int ERRAND_START_MINUTE = 20;
    private static final int ERRAND_START_SECOND = 0;
    private static final int UPDATE_START_HOUR = 10;
    private static final int UPDATE_START_MINUTE = 0;
    private static final int UPDATE_START_SECOND = 0;
    private static final long EACH_DAY = 60000 * 60 * 24;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BeFit", "DailyAlarmManager loaded at start");
        SetErrandAlarm(context);
    }

    public void SetErrandAlarm(Context context)
    {
        Log.d("BeFit", "DailyAlarmManager: Errands will start at: " + ERRAND_START_HOUR + ":" + ERRAND_START_MINUTE + ":" + ERRAND_START_SECOND);
        super.SetRepeatingService(context, ErrandAlarmService.class, DAILYALARM_ID, getTimeMillis(ERRAND_START_HOUR, ERRAND_START_MINUTE, ERRAND_START_SECOND), EACH_DAY);
        super.SetRepeatingService(context, UpdateBeaconsAndErrandsService.class, ERRANDALARM_ID, getTimeMillis(UPDATE_START_HOUR, UPDATE_START_MINUTE, UPDATE_START_SECOND), EACH_DAY);
    }

    public void CancelAlarm(Context context){
        super.CancelAlarm(context, DailyAlarmManager.class, DAILYALARM_ID);
    }

    public void SetErrandAlarmNow(Context context){
        Log.d("BeFit", "DailyAlarmManager: Errands will start now");
        super.SetRepeatingService(context, ErrandAlarmService.class, DAILYALARM_ID, System.currentTimeMillis(), EACH_DAY);
    }
}