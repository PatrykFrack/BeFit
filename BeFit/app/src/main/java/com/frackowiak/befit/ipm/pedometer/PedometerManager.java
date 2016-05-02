package com.frackowiak.befit.ipm.pedometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.frackowiak.befit.ipm.pedometer.accelerometer.AccelerometerEventListener;

/**
 * Created by PFRACKOW on 12.03.2016.
 */
public class PedometerManager extends Service {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private AccelerometerEventListener accEventList;


    @Override
    public void onCreate() {
        super.onCreate();
        initAccelerometer();
    }

    @Override
    public void onDestroy(){
        if(accEventList != null){
            accEventList.stopDetector();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void initAccelerometer(){
        Log.d("Befit/IPM", "PedometerManager init()");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accEventList = new AccelerometerEventListener(mSensorManager);
        accEventList.addListener(new MotionListener(this));
        accEventList.startDetector();
    }


}
