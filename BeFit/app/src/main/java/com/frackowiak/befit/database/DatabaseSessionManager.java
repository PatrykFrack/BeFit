package com.frackowiak.befit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.frackowiak.database.db.DaoMaster;
import com.frackowiak.database.db.DaoSession;

/**
 * Created by PFRACKOW on 09.12.2015.
 */
public class DatabaseSessionManager {


    private static DaoSession daoSession;
    private static DatabaseHelper helper;

    public static void init(Context context) {
        helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getSession() {
        if (daoSession == null) {
            throw new IllegalStateException("DatabaseSession should be initialized first");
        }

        return daoSession;
    }

    public static DatabaseHelper getDatabaseHelper() {
        if (helper == null) {
            throw new IllegalStateException("DatabaseSession should be initialized first");
        }

        return helper;
    }
}
