package com.frackowiak.befit.ipm.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PFRACKOW on 21.12.2015.
 */
public class BLEManager extends Service{

    private SelectedBeaconLEScanCallback callback;
    private BluetoothAdapter adapter;
    private final int SCAN_PERIOD = 1000;
    private static Timer timer;
    private static boolean isScanning = false;
    private Handler handler;
    private static TimerTask refreshStatusesOnIntervalTask;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        callback = new SelectedBeaconLEScanCallback(this);
        BluetoothManager bluetoothManager =(BluetoothManager) this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = bluetoothManager.getAdapter();
        handler = new Handler();
    }

    @Override
    public void onDestroy(){
        scanLeDeviceViaTask(false);
//        isScanning = false;
        Log.d("BeFit/IPM", "BLEManager.onDestroy()");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BeFit/IPM", "BLEManager.onStartCommand() ");
        scanLeDeviceViaTask(true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void scanLeDeviceViaTask(boolean enable){
        if(enable) {
            Log.d("BeFit/IPM", "scan LE devices: " + enable);
            isScanning = enable;
            scanLeDeviceViaTask();
        }
        else
        {
            stopBLEDiscovery();
        }
    }

    private void scanLeDeviceViaTask(){
        refreshStatusesOnIntervalTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        RefreshBeaconStatusesTask refreshStatusesAsyncTask = new RefreshBeaconStatusesTask();
                        refreshStatusesAsyncTask.execute();
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(refreshStatusesOnIntervalTask, 0, SCAN_PERIOD);
    }

    private void stopBLEDiscovery(){
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            refreshStatusesOnIntervalTask.cancel();
            refreshStatusesOnIntervalTask = null;
        }
        adapter.stopLeScan(callback);
        adapter.cancelDiscovery();
    }

    private class RefreshBeaconStatusesTask extends AsyncTask<Boolean, Integer, Boolean> {

        protected void onPreExecute() {
            adapter.stopLeScan(callback);
        }

        protected Boolean doInBackground(Boolean... beacons) {
            if (isScanning) {
                adapter.startLeScan(callback);
                Log.i("BeFitTest", "Refresh beacons");
                return true;
            } else {
                timer.cancel();
                this.cancel(true);
                return false;
            }

        }

        protected void onPostExecute(Boolean isStarted){
            if(!isStarted){
                adapter.stopLeScan(callback);
            }
        }
    }
}
