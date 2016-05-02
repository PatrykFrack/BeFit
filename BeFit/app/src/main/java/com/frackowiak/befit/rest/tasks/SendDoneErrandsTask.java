package com.frackowiak.befit.rest.tasks;

import android.util.Log;

import com.frackowiak.befit.database.dto.JSONMapper;
import com.frackowiak.database.dao.DoneErrand;
import com.frackowiak.database.dao.WorkDay;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by PFRACKOW on 11.04.2016.
 */
public class SendDoneErrandsTask extends AbstractTask {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            StringBuffer response = null;

            JSONObject jsonObject = JSONMapper.mapDoneErrandListToJSON(testDE());
            Log.d("Befit/Conn", "params.tostring" + jsonObject.toString());

            response = sendRequest("POST", jsonObject.toString(), urls);

            return response.toString();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
        }

        return null;
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

    private DoneErrand testDoneErrand(){
        DoneErrand doneErrand = new DoneErrand();
        WorkDay workDay = new WorkDay();

        workDay.setDate(Calendar.getInstance().getTime());
        doneErrand.setPoints(123456);
        doneErrand.setErrandId(9999l);
        doneErrand.setGoalAchieved(true);
        doneErrand.setWorkDay(workDay);
        doneErrand.setName("JSONErrand");
        doneErrand.setFinishedTime(Calendar.getInstance().getTime());

        return doneErrand;
    }

    private List<DoneErrand> testDE(){
        List<DoneErrand> errands = new ArrayList<>();
        for(int i = 0; i<5; i++){
            errands.add(testDoneErrand());
        }
        return errands;
    }
}