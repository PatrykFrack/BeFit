package com.frackowiak.befit.alarms;

import java.util.Calendar;

/**
 * Created by PFRACKOW on 06.12.2015.
 */
public class Scheduler {

    final int HOUR = 60 * 60 * 1000;
    final int MINUTE = 60 * 1000;
    final int SECOND = 1000;

    public void getNextSchedule(){}

    public long getDaySchedule(){
        //TODO
        return System.currentTimeMillis();
//        return getTimeMillis(9,0,0);
    }

    public long getErrandFrequency(){
        //TODO
        return getTimeMillis(0,1,0);
    }


    protected long getTodayTimeMillis(int hour, int minutes, int seconds){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        return cal.getTimeInMillis();
    }

    protected long getTimeMillis(int hour, int minutes, int seconds){
        return hour * HOUR + minutes * MINUTE + seconds * SECOND;
    }
}
