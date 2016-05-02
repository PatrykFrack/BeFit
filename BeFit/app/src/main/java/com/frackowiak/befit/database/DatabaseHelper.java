package com.frackowiak.befit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.frackowiak.database.db.*;
import com.frackowiak.database.db.DaoMaster;

/**
 * Created by PFRACKOW on 09.12.2015.
 */
public class DatabaseHelper extends DaoMaster.OpenHelper {

    private static final String DATABASE_NAME = "tolinoPoC.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        dropAllTables(db, true);
        onCreate(db);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        AchievementDao.dropTable(db, ifExists);
        BeaconDao.dropTable(db, ifExists);
        ConfigurationDao.dropTable(db, ifExists);
        DayDao.dropTable(db, ifExists);
        DoneErrandDao.dropTable(db, ifExists);
        ErrandDao.dropTable(db, ifExists);
        PlayerDao.dropTable(db, ifExists);
        TeamDao.dropTable(db, ifExists);
        WorkDayDao.dropTable(db, ifExists);
    }

}
