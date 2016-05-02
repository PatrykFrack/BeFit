package com.frackowiak.befit.ipm.pedometer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by PFRACKOW on 12.03.2016.
 */
public class MovementReceiver extends BroadcastReceiver {

    private IMovementListener movementListener;

    public MovementReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        movementListener.onMovementActivity(true);
    }

    public void setMovementListener(IMovementListener movementListener){
        this.movementListener = movementListener;
    }
}
