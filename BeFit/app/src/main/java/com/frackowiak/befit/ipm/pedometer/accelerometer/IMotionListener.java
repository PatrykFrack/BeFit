package com.frackowiak.befit.ipm.pedometer.accelerometer;

/**
 * Created by PFRACKOW on 14.03.2016.
 */
public interface IMotionListener {

    /**
     * Notify if phone is in motion
     * @param isInMotion
     */
    public void onMotionActivity(boolean isInMotion);
}
