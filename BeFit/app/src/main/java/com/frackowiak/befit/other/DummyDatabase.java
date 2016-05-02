package com.frackowiak.befit.other;

import com.frackowiak.database.dao.Beacon;
import com.frackowiak.database.dao.Configuration;
import com.frackowiak.database.dao.Day;
import com.frackowiak.database.dao.Errand;
import com.frackowiak.database.dao.Player;
import com.frackowiak.database.db.DaoSession;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by PFRACKOW on 09.12.2015.
 */
public class DummyDatabase {

    final int HOUR = 60 * 60 * 1000;
    final int MINUTE = 60 * 1000;
    final int SECOND = 1000;

    public DummyDatabase(DaoSession daoSession){
//        Entity teamEntity = addTeamEntity(schema);
//        Entity playerEntity = addPlayerEntity(schema, teamEntity);
//        Entity achievementEntity = addAchievementEntity(schema, playerEntity);
//        Entity configurationEntity = addConfigurationEntity(schema, playerEntity);
//        Entity dayEntity = addDayEntity(schema, configurationEntity);
//        Entity workDayEntity = addWorkDayEntity(schema, playerEntity, dayEntity);
//        Entity beaconEntity = addBeaconEntity(schema);
//        Entity errandEntity = addErrandEntity(schema, beaconEntity);
//        Entity doneErrandEntity = addDoneErrandEntity(schema, workDayEntity, errandEntity);
        fillDummyPlayer(daoSession);
        fillDummyConfiguration(daoSession);
        fillDummyDay(daoSession);
        fillDummyBeacons(daoSession);
        fillDummyErrands(daoSession);
    }

    protected long getTimeMillis(int hour, int minutes, int seconds){
        return hour * HOUR + minutes * MINUTE + seconds * SECOND;
    }

    public void fillDummyBeacons(DaoSession daoSession){
        Beacon beacon = new Beacon();
        beacon.setId(9999l);
        beacon.setMACAddress("20:FA:BB:01:77:D0");
        beacon.setUUID("F2A74FC4-7625-44DB-9B08-CB7E130B2029");
        beacon.setMajor("65535");
        beacon.setMinor("195");

        daoSession.insert(beacon);
    }
    public void fillDummyErrands(DaoSession daoSession){

        for(int i=1; i<=10; i++){
            Errand errand = new Errand();
            errand.setName("Name"+i);
            errand.setDescription("Description" + i);
            errand.setPoints(10 + i);
            errand.setBeaconMain(9999l);
            daoSession.insert(errand);
        }
    }
    public void fillDummyPlayer(DaoSession daoSession){
        Player player = new Player(9999l, "Dummy", 987654, "Dummix", 9999l);
        daoSession.insert(player);
    }
    public void fillDummyConfiguration(DaoSession daoSession){
        Configuration configuration = new Configuration(9999l, "DummyConf", getTimeMillis(1,0,0), 9999l);
        daoSession.insert(configuration);
    }
    public void fillDummyDay(DaoSession daoSession){
        Day day = new Day();
        day.setId(9999l);
        day.setStartTime(getTimeMillis(9, 0, 0));
        day.setEndTime(getTimeMillis(15, 0, 0));
        day.setConfigurationId(9999l);
        daoSession.insert(day);
    }
}
