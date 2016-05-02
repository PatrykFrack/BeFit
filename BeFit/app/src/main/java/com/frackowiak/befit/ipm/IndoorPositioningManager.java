package com.frackowiak.befit.ipm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.frackowiak.befit.ipm.ble.BLEManager;
import com.frackowiak.befit.ipm.ble.receiver.BLEReceiver;
import com.frackowiak.befit.ipm.ble.receiver.IBLEListener;
import com.frackowiak.befit.ipm.pedometer.receiver.IMovementListener;
import com.frackowiak.befit.ipm.pedometer.receiver.MovementReceiver;
import com.frackowiak.befit.ipm.pedometer.PedometerManager;


/**
 * Created by PFRACKOW on 23.12.2015.
 */
public class IndoorPositioningManager {

    private BLEReceiver bleReceiver;
    private MovementReceiver movementReceiver;

    public void startListening(Context context){
        Log.d("BeFit/IPM", "startListening()");
        startBLEManagerService(context);
        startPedometerManagerService(context);
    }

    private void startBLEManagerService(Context context){
        Intent intent = new Intent(context, BLEManager.class);
        context.startService(intent);
    }

    private void startPedometerManagerService(Context context){
        Intent intent = new Intent(context, PedometerManager.class);
        context.startService(intent);
    }

    public void stopListening(Context context){
        Log.d("BeFit/IPM", "stopListening()");
        stopBLEManagerService(context);
        stopPedometerManagerService(context);
        unregisterBLEReceiver(context);
    }

    private void stopBLEManagerService(Context context){
        Intent intent = new Intent(context, BLEManager.class);
        context.stopService(intent);
    }

    private void stopPedometerManagerService(Context context){
        Intent intent = new Intent(context, PedometerManager.class);
        context.stopService(intent);
    }

    private void unregisterBLEReceiver(Context context){
        if(bleReceiver != null) {
            context.unregisterReceiver(bleReceiver);
        }
    }

    public void addBLEListener(Context context, IBLEListener bleListener){
        bleReceiver = new BLEReceiver();
        bleReceiver.setBleListener(bleListener);
        IntentFilter intentFilter = new IntentFilter(".ipm.RSSI_BROADCAST");
        context.registerReceiver(bleReceiver, intentFilter);
    }

    public void addMovementListener(Context context, IMovementListener movementListener){
        movementReceiver = new MovementReceiver();
        movementReceiver.setMovementListener(movementListener);
        IntentFilter intentFilter = new IntentFilter(".ipm.MOVEMENT_BROADCAST");
        context.registerReceiver(movementReceiver, intentFilter);
    }

}
