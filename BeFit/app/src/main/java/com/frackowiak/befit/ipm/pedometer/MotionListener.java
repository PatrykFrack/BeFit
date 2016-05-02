package com.frackowiak.befit.ipm.pedometer;

import android.content.Context;
import android.content.Intent;

import com.frackowiak.befit.ipm.pedometer.accelerometer.IMotionListener;

/**
 * Created by PFRACKOW on 14.03.2016.
 */
public class MotionListener implements IMotionListener{

    private Context context;

    public MotionListener(Context context){
        this.context = context;
    }

    @Override
    public void onMotionActivity(boolean isInMotion) {
        sendBroadcast(isInMotion);
    }

    private void sendBroadcast(boolean isInMotion){
        Intent intent = new Intent();
        intent.setAction(".ipm.MOVEMENT_BROADCAST");
        intent.putExtra("isInMotion", isInMotion);
        context.sendBroadcast(intent);
    }
}
