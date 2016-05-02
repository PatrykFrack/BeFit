package com.frackowiak.befit.ipm.ble.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by PFRACKOW on 23.12.2015.
 */
public class BLEReceiver extends BroadcastReceiver{

    private IBLEListener bleListener;

    public void setBleListener(IBLEListener bleListener){
        this.bleListener = bleListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int rssi = intent.getExtras().getInt("rssi");
        Log.d("BeFit/IPM", "onReceive() rssi: " + String.valueOf(rssi));
        bleListener.updateBLEInfo(String.valueOf(rssi));
    }
}
