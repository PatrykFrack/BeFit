package com.frackowiak.befit.rest.tasks;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PFRACKOW on 11.04.2016.
 */
abstract class AbstractTask extends AsyncTask<String, Void, String> {

    static final String COOKIES_HEADER = "Set-Cookie";

    protected StringBuffer sendRequest(String method, String message, String... urls) throws Exception {
        StringBuffer response;
        URL url = new URL(urls[0]);


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        if(getCookies().size() > 0)
        {
            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            conn.setRequestProperty("Cookie",
                    TextUtils.join(";", getCookies()));
        }
        OutputStream out = new BufferedOutputStream(conn.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.write(message);

        writer.close();
        out.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending " + method + " request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("Response in universal: " + response.toString());
        return response;
    }

    protected List<String> getCookies(){
        List<String> dummyCookies = (new ArrayList());
        dummyCookies.add("DUMMY COOKIE");
        return dummyCookies;
    }
}
