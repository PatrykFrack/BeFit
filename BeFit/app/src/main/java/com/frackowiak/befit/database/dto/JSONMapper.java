package com.frackowiak.befit.database.dto;

import com.frackowiak.database.dao.Beacon;
import com.frackowiak.database.dao.DoneErrand;
import com.frackowiak.database.dao.Errand;
import com.frackowiak.database.db.DoneErrandDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PFRACKOW on 29.03.2016.
 */
public class JSONMapper {

    public static JSONObject mapDoneErrandToJSON(DoneErrand doneErrand){
        try {
            JSONObject parameters = new JSONObject();

            parameters.put(DoneErrandDao.Properties.Name.name, doneErrand.getName());
            parameters.put(DoneErrandDao.Properties.ErrandId.name, doneErrand.getErrandId());
            parameters.put(DoneErrandDao.Properties.FinishedTime.name, doneErrand.getFinishedTime());
            parameters.put(DoneErrandDao.Properties.GoalAchieved.name, doneErrand.getGoalAchieved());
            parameters.put(DoneErrandDao.Properties.Points.name, doneErrand.getPoints());

            return parameters;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject mapDoneErrandListToJSON(List<DoneErrand> doneErrands){
        try {
            JSONArray parameters = new JSONArray();
            int i=0;
            for (DoneErrand de: doneErrands) {
                parameters.put(mapDoneErrandToJSON(de));
            }
            JSONObject result = new JSONObject();
            result.put("DoneErrands", parameters);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Errand> mapJSONToErrandList(JSONArray jsonArray){
        try {
            List<Errand> errandList = new ArrayList<>();
            for(int i=0; i< jsonArray.length(); i++) {
                errandList.add(mapJSONToErrand(jsonArray.getJSONObject(i)));
            }
            return errandList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Errand mapJSONToErrand(JSONObject jsonErrand) throws JSONException{
        Errand errand = new Errand();
        errand.setName(jsonErrand.get("Name").toString());
        errand.setDescription(jsonErrand.get("Description").toString());
        //TODO uuid beacona? czy id beacona z bazy?
        errand.setBeaconMain(Long.valueOf(jsonErrand.get("Beacon").toString()));

        return errand;
    }

    public static List<Beacon> mapJSONToBeaconsList(JSONArray jsonArray){
        try {
            List<Beacon> beaconList = new ArrayList<>();
            for(int i=0; i< jsonArray.length(); i++) {
                beaconList.add(mapJSONToBeacon(jsonArray.getJSONObject(i)));
            }
            return beaconList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Beacon mapJSONToBeacon(JSONObject jsonErrand) throws JSONException{
        Beacon beacon = new Beacon();
//        beacon.setName(jsonErrand.get("Name").toString());
//        beacon.setDescription(jsonErrand.get("Description").toString());
//        //TODO uuid beacona? czy id beacona z bazy?
//        beacon.setBeaconMain(Long.valueOf(jsonErrand.get("Beacon").toString()));

        return beacon;
    }
}
