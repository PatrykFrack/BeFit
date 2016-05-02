package com.frackowiak.befit.errand;

import android.util.Log;

import com.frackowiak.database.dao.DoneErrand;
import com.frackowiak.database.dao.Errand;
import com.frackowiak.befit.database.DatabaseSessionManager;
import com.frackowiak.database.db.DaoSession;

import java.util.List;

/**
 * Created by PFRACKOW on 20.12.2015.
 */
public class ErrandManager {

    private static final int RSSI_CONSTANT = -30;
    private Errand errand;
    private DoneErrand doneErrand;
    private DaoSession daoSession;
    private List<Errand> errands;

    public ErrandManager(){
        daoSession = DatabaseSessionManager.getSession();
        errands = daoSession.getErrandDao().loadAll();
    }

    public Errand getErrand() {
        return errand;
    }

    public void setErrand(Errand errand) {
        this.errand = errand;
    }
    public void setCurrentErrand(Long currentErrandId) {
        this.errand = daoSession.getErrandDao().load(currentErrandId);
        Log.d("BeFit/Errand", "Loaded errand " + errand.getId() + ": " + errand.getName());
    }

    public void startErrand(){
        //TODO FIX_ME
        doneErrand = new DoneErrand();
        doneErrand.setPoints(0);
        doneErrand.setGoalAchieved(false);
        doneErrand.setWorkDayId(9999l);
        doneErrand.setErrandId(9999l);
    }

    public boolean checkErrandGoalAchieved(int rssi){
        Log.d("BeFitTest", "Rssi: " + rssi + " >= " + RSSI_CONSTANT);
        return rssi >= RSSI_CONSTANT;
    }

    /**
     * Adds points from current errand and creates a new errand for the user
     */
    public void completeErrand(){
        //TODO add movement points + achieved goal to current errand
        addPointsFromCurrentErrand();
    }

    /**
     * finishes the errand, sums up all points and sends to DB
     */
    public void finishErrand() {
        if(doneErrand != null) {
            daoSession.getDoneErrandDao().insert(doneErrand);
            doneErrand = null;
        }
    }

    private void addPointsFromCurrentErrand(){
        doneErrand.setPoints(errand.getPoints());
    }

    private Errand getNewErrand(){
        //Get the one after the current errand or the first one in DB
        int currentErrandIndex = errands.indexOf(errand);
        if(currentErrandIndex < (errands.size() -1 )){
            return errands.get(currentErrandIndex + 1);
        }
        else{
            return errands.get(0);
        }
    }

    public int getPoints() {
        return doneErrand.getPoints();
    }
    public boolean isGoalAchieved() {
        return doneErrand.getGoalAchieved();
    }

    public void updateInfo(Integer rssi) {
        if(checkErrandGoalAchieved(rssi))
        {
            completeErrand();
        }
    }

    public void addPoints(int points) {
        doneErrand.setPoints(doneErrand.getPoints() + points);
    }
}
