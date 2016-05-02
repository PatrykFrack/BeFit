package com.frackowiak.befit.presenters;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.frackowiak.befit.activities.ErrandActivity;
import com.frackowiak.befit.R;
import com.frackowiak.befit.ipm.pedometer.receiver.IMovementListener;
import com.frackowiak.database.dao.Errand;
import com.frackowiak.befit.errand.ErrandManager;
import com.frackowiak.befit.ipm.IndoorPositioningManager;
import com.frackowiak.befit.ipm.ble.receiver.IBLEListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * Created by PFRACKOW on 01.12.2015.
 */
public class ErrandActivityPresenter implements IBLEListener, IMovementListener {

    private final static int REQUEST_ENABLE_BT = 1;
    private final static int ONE_SECOND = 1000;
    private final static int WORK_PAUSE_LENGTH = 100 * ONE_SECOND;

    private static Timer timer;

    private ErrandActivity viewActivity;
    private Errand currentErrand;
    private IndoorPositioningManager ipmanager;
    private ErrandManager errandManager;
    private boolean isErrandFinished = false;

    public ErrandActivityPresenter(ErrandActivity errandActivity, Long errandId) {
        this.viewActivity = errandActivity;
        ipmanager = new IndoorPositioningManager();
        initErrand(errandId);
        scanLeDevice(true);
        initTimer();
    }

    private void initErrand(long errandId){
        errandManager = new ErrandManager();
        errandManager.setCurrentErrand(errandId);
//        errandManager.setErrand(9999l);
        currentErrand = errandManager.getErrand();
        errandManager.startErrand();
        updateActivityWithErrandInfo(currentErrand);
        Log.d("BeFit", currentErrand.getName());
    }

    public void onDestroy(){
        finishErrand();
//        if(!timer.equals(null)) {
//            timer.cancel();
//        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                scanLeDevice(true);
            } else {
                scanLeDevice(false);
                Toast.makeText(viewActivity.getBaseContext(), R.string.ipm_bluetooth_enable_canceled_alert, Toast.LENGTH_LONG).show();
                Log.e("BeFit", "BlueTooth not enabled");
            }
        }
    }

    private void scanLeDevice(boolean shouldScan) {
        if (shouldScan) {
            setScanBLEService();
        } else {
            cancelScanBLEService();
        }
    }

    private void setScanBLEService() {
        ipmanager.addBLEListener(viewActivity, this);
        ipmanager.addMovementListener(viewActivity, this);
        ipmanager.startListening(viewActivity);
    }

    private void cancelScanBLEService() {
        ipmanager.stopListening(viewActivity);
    }

    @Override
    public void updateBLEInfo(String info) {
        TextView tv = (TextView) viewActivity.findViewById(R.id.goalTextView);
        tv.setText(info);
        errandManager.updateInfo(Integer.valueOf(info));
        updateActivityWithErrandGoal();
    }

    private void initTimer(){
        final TextView _tv = (TextView) viewActivity.findViewById(R.id.timeErrandTextView);
        new CountDownTimer(WORK_PAUSE_LENGTH, ONE_SECOND) {

            public void onTick(long millisUntilFinished) {
                _tv.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                _tv.setText("KONIEC");
                finishErrand();
            }
        }.start();
    }

    private void finishErrand(){
        if(isErrandFinished){
            return;
        }else {
            scanLeDevice(false);
            errandManager.finishErrand();
            isErrandFinished = true;
        }
    }

    private void updateActivityWithErrandInfo(Errand errand){
        TextView descriptionTv = (TextView) viewActivity.findViewById(R.id.descriptionTextView);
        TextView goalTv = (TextView) viewActivity.findViewById(R.id.goalTextView);
        descriptionTv.setText(errand.getDescription());
        goalTv.setText(errand.getBeacon().getUUID());
    }

    private void updateActivityWithErrandPoints(){
        TextView pointsTv = (TextView) viewActivity.findViewById(R.id.pointsErrandTextView);
        pointsTv.setText((String.valueOf(errandManager.getPoints())));
    }

    private void updateActivityWithErrandGoal() {
        TextView goalAchievedTv = (TextView) viewActivity.findViewById(R.id.caloriesErrandTextView);
        goalAchievedTv.setText(errandManager.isGoalAchieved() ? "YES" : "NO");
    }

    @Override
    public void onMovementActivity(boolean activity) {
        errandManager.addPoints(1);
        updateActivityWithErrandPoints();
    }
}
