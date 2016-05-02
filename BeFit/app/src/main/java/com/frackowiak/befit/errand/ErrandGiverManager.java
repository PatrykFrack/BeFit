package com.frackowiak.befit.errand;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.frackowiak.befit.activities.ErrandActivity;
import com.frackowiak.befit.R;
import com.frackowiak.befit.alarms.ErrandAlarmManager;
import com.frackowiak.database.dao.Errand;
import com.frackowiak.befit.database.DatabaseSessionManager;
import com.frackowiak.database.db.DaoSession;

import java.util.Calendar;

/**
 * Created by PFRACKOW on 07.12.2015.
 */
public class ErrandGiverManager extends Service{

    final static int NOTIFICATION_ID = 111;
    final static int ERRAND_ID = 121;
    boolean flag = false;

    @Override
    public void onCreate(){
        super.onCreate();
//        createDirectory("/BeFit/");
//        createMeasurementFile();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("BeFit", "BAM");
        // TODO
        //check if next errand should be given, if not cancel EAM
        //if given, update date and count of errands
        if(checkIfCancelErrandAlarmManagerTEST()){
            Log.d("BeFit", "Cancel Errand Alarm");
            cancelErrandAlarm();
        }else{
            Errand errand = getNextErrand();
            displayNotification(errand);
        }

//        saveMeasurementToFile();
//        showMeasurementFile();
//        displayNotification();

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //TODO
    private boolean checkIfCancelErrandAlarmManagerTEST() {
        if(true)
            return false;
        Calendar calendarFinish = Calendar.getInstance();
        calendarFinish.set(Calendar.HOUR_OF_DAY, 23);
        calendarFinish.set(Calendar.MINUTE, 0);
        calendarFinish.set(Calendar.SECOND, 0);

        Calendar calendarCurrent = Calendar.getInstance();

        return calendarCurrent.after(calendarFinish);
    }

    private void cancelErrandAlarm(){
        ErrandAlarmManager errandAlarmManager = new ErrandAlarmManager();
        errandAlarmManager.CancelAlarm(getApplicationContext());
    }

    private Errand getNextErrand(){
        //TODO

        DaoSession session = DatabaseSessionManager.getSession();
        Errand errand = session.getErrandDao().loadAll().get(flag ? 0 : 1);
        flag=!flag;
        return errand;
    }

    private void displayNotification(Errand errand){ //Context context){
        Context context = getApplicationContext();
        Intent intent = new Intent(context, ErrandActivity.class);
        intent.putExtra("ErrandId", errand.getId());
        PendingIntent pIntent = PendingIntent.getActivity(context, ERRAND_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("New Errand")
                .setContentText(errand.getName())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notification);

    }


}
