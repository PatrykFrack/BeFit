package com.frackowiak.befit.rest.tasks;

import com.frackowiak.befit.database.DatabaseSessionManager;
import com.frackowiak.befit.database.dto.JSONMapper;
import com.frackowiak.database.dao.Beacon;
import com.frackowiak.database.db.BeaconDao;
import com.frackowiak.database.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by PFRACKOW on 11.04.2016.
 */
public class DownloadBeaconsTask extends AbstractTask{

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
             updateBeaconsInDatabase(feed);
         }
    }

    private void updateBeaconsInDatabase(String jSONbeacons){
        try{
            List<Beacon> beaconList = JSONMapper.mapJSONToBeaconsList(new JSONArray(jSONbeacons));
            DaoSession session = DatabaseSessionManager.getSession();
            BeaconDao dao = session.getBeaconDao();
            dao.deleteAll();
            dao.insertInTx(beaconList);
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }

    }
}
