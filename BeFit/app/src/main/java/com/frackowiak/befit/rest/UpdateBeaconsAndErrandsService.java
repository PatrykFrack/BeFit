package com.frackowiak.befit.rest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.frackowiak.befit.rest.tasks.DownloadBeaconsTask;
import com.frackowiak.befit.rest.tasks.DownloadErrandsTask;
import com.frackowiak.befit.rest.tasks.LoginTask;

/**
 * Created by PFRACKOW on 29.03.2016.
 */
public class UpdateBeaconsAndErrandsService extends Service{

    private static final String BEFIT_URL = "http://befit-klaskowski.c9users.io/";
    private static final String DONE_ERRANDS_URL = BEFIT_URL + "errand";
    private static final String GET_ERRANDS_URL = BEFIT_URL + "start";
    private static final String GET_BEACONS_URL = BEFIT_URL + "???";
    private static final String LOGIN_URL = BEFIT_URL + "mobile-login";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        loginUser();
        downloadErrands();
        downloadBeacons();
    }

    private void loginUser(){
        new LoginTask().execute(LOGIN_URL);
    }

    private void downloadErrands(){
        new DownloadErrandsTask().execute(GET_ERRANDS_URL);
    }

    private void downloadBeacons(){
        new DownloadBeaconsTask().execute(GET_BEACONS_URL);
    }
}
