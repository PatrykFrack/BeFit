package com.frackowiak.befit.ipm.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//import com.frackowiak.befit.activities.TestActivity;
//import Beacon;


/**
 * Created by PFRACKOW on 16.12.2015.
 */
public class SelectedBeaconLEScanCallback implements BluetoothAdapter.LeScanCallback{

    private Context context;
    private String selectedBeaconUUID;

    public SelectedBeaconLEScanCallback(Context context){
        this.context = context;
    }
    public SelectedBeaconLEScanCallback(Context context, String uuid) {
        this.context = context;
        selectedBeaconUUID = uuid;
    }

    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return context;
    }
    public void setSelectedBeaconUUID(String uuid){
        selectedBeaconUUID = uuid;
    }

    @Override
    public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
        Log.d("BeFitTest", "Found beacon: " + device.getAddress());
        //TODO
        if(!(isLookingForSelected() || device.getAddress().equals(selectedBeaconUUID))) {
            return;
        }
        sendBroadcast(rssi);
    }

    private void sendBroadcast(int rssi){
        Intent intent = new Intent();
        intent.setAction(".ipm.RSSI_BROADCAST");
        intent.putExtra("rssi", rssi);
        context.sendBroadcast(intent);
    }

    public boolean isLookingForSelected(){
        return selectedBeaconUUID !=null || !selectedBeaconUUID.isEmpty();
    }
}
