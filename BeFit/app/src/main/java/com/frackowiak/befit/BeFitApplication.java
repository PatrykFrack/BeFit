package com.frackowiak.befit;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.frackowiak.befit.alarms.DailyAlarmManager;
import com.frackowiak.befit.database.DatabaseSessionManager;
import com.frackowiak.befit.other.DummyDatabase;
import com.frackowiak.database.db.DaoMaster;
import com.frackowiak.database.db.DaoSession;

/**
 * Created by PFRACKOW on 09.12.2015.
 */
public class BeFitApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        DatabaseSessionManager.init(this);

        //dropDB();
         new DummyDatabase(DatabaseSessionManager.getSession());

        DailyAlarmManager dailyAlarmManager= new DailyAlarmManager();
        dailyAlarmManager.SetErrandAlarmNow(this);

    }

    private void dropDB(){
        SQLiteDatabase db = DatabaseSessionManager.getSession().getDatabase();
        DaoMaster.dropAllTables(db, true);
    }
}
