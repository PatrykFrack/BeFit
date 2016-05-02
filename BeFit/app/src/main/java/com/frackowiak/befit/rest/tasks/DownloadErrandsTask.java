package com.frackowiak.befit.rest.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.frackowiak.befit.database.DatabaseSessionManager;
import com.frackowiak.befit.database.dto.JSONMapper;
import com.frackowiak.befit.other.DummyUser;
import com.frackowiak.database.dao.Errand;
import com.frackowiak.database.db.BeaconDao;
import com.frackowiak.database.db.DaoSession;
import com.frackowiak.database.db.ErrandDao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by PFRACKOW on 11.04.2016.
 */
public class DownloadErrandsTask extends AbstractTask {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            StringBuffer response = null;

            response = sendRequest("GET", null, urls);

            return response.toString();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
        }

        return null;
    }

    protected void onPostExecute(String feed) {
        if(feed!=null && !feed.isEmpty()){
            updateErrandsInDatabase(feed);
        }
    }

    private void updateErrandsInDatabase(String errandsJson){
        try {
            List<Errand> errandList = JSONMapper.mapJSONToErrandList(new JSONArray(errandsJson));
            DaoSession session = DatabaseSessionManager.getSession();
            ErrandDao errandDao =  DatabaseSessionManager.getSession().getErrandDao();
            errandDao.deleteAll();
            errandDao.insertInTx(errandList);
        }catch (Exception e){
            System.out.print(e.getStackTrace());
        }
    }
}
