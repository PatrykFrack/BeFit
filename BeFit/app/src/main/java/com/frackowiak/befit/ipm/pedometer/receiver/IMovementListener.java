package com.frackowiak.befit.ipm.pedometer.receiver;

/**
 * Created by PFRACKOW on 14.03.2016.
 */

public interface IMovementListener {
    /**
     * Called when phone in motion
     */
    void onMovementActivity(boolean activity);
}