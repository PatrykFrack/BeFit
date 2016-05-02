package com.frackowiak.befit.alarms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.frackowiak.befit.errand.ErrandGiverManager;


/**
 * Created by PFRACKOW on 02.12.2015.
 */
public class ErrandAlarmManager extends AbstractAlarmManager
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("BeFit", "Errand Service loaded at start");
    }

    public void setRepeatingErrand(Context context, long triggerAtMillis, long intervalAtMillis) {
        Log.d("BeFit", "Repeat errand " + triggerAtMillis + ": " + intervalAtMillis);
        super.SetRepeatingService(context, ErrandGiverManager.class, ERRANDALARM_ID, triggerAtMillis, intervalAtMillis);
    }

    public void CancelAlarm(Context context)
    {
        Log.d("BeFit", "Errand alarm canceled");
        super.CancelAlarmService(context, ErrandGiverManager.class, ERRANDALARM_ID);
    }

}
