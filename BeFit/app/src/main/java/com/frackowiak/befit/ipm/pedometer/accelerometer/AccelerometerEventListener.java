package com.frackowiak.befit.ipm.pedometer.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.frackowiak.befit.ipm.utils.ScalarKalmanFilter;

import java.util.ArrayList;

/**
 * Created by PFRACKOW on 12.03.2016.
 */
public class AccelerometerEventListener implements SensorEventListener{

    protected static final String 	LOG_TAG 		= "BeFit/IPM";
    protected static final float    THRESHOLD_AMPLITUDE = 1.0f;
    protected static final int      THRESHOLD_INACTIVITY = 10;
    protected static final int      SENSOR_RATE = 1000000; //SensorManager.SENSOR_DELAY_UI;


    private final ArrayList<IMotionListener> mListeners = new ArrayList<IMotionListener>();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mLastZ;
    private boolean isLastInMotion = false;
    public boolean     isInMotion = false;
    private int mInactivityCount = 0;

    private ScalarKalmanFilter mFiltersCascade[] = new ScalarKalmanFilter[3];

    public AccelerometerEventListener(SensorManager sensorManager){
        mSensorManager = sensorManager;
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mFiltersCascade[0] = new ScalarKalmanFilter(1, 1, 0.01f, 0.0025f);
        mFiltersCascade[1] = new ScalarKalmanFilter(1, 1, 0.01f, 0.0025f);
        mFiltersCascade[2] = new ScalarKalmanFilter(1, 1, 0.01f, 0.0025f);
    }

    /********************* Public methods ******************************/

    /**
     * Starts detecting movement
     */
    public void startDetector(){
        mSensorManager.registerListener(this, mAccelerometer, SENSOR_RATE);
    }

    /**
     * Stops detecting movement
     */
    public void stopDetector(){
        mSensorManager.unregisterListener(this);
    }

    /**
     * Adds listener
     */
    public void addListener(IMotionListener listener){
        mListeners.add(listener);
    }

    /********************* SensorEventListener *************************/

    @Override
    public void onAccuracyChanged(Sensor sensor, int value) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER){
            return;
        }
        final float z = filter(event.values[2]);
        if (Math.abs(z - mLastZ) > THRESHOLD_AMPLITUDE)
        {
            mInactivityCount = 0;
            notifyListeners(true);
            isLastInMotion = true;

        } else {
            if (mInactivityCount > THRESHOLD_INACTIVITY) {
                if (isLastInMotion != false){
                    isLastInMotion = false;
                    notifyListeners(false);
                }
            } else {
                mInactivityCount++;
            }
        }
        mLastZ = z;
    }

    /********************* Private methods *****************************/

    /**
     * Smoothes the signal from accelerometer
     */
    private float filter(float measurement){
        float f1 = mFiltersCascade[0].correct(measurement);
        float f2 = mFiltersCascade[1].correct(f1);
        float f3 = mFiltersCascade[2].correct(f2);
        return f3;
    }

    /**
     * Calls registered event listeners
     */
    private void notifyListeners(boolean isInMotion){
        Log.i(LOG_TAG, "Is in Motion: " + String.valueOf(isInMotion));
        if (!isInMotion) return;
        for (IMotionListener listener : mListeners){
            listener.onMotionActivity(true);
            Log.i(LOG_TAG, String.valueOf(isInMotion));
        }
    }



}
