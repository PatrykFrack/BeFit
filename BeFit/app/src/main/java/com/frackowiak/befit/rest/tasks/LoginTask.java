package com.frackowiak.befit.rest.tasks;

import android.util.Log;

import com.frackowiak.befit.other.DummyUser;

import org.json.JSONObject;

/**
 * Created by PFRACKOW on 11.04.2016.
 */
public class LoginTask extends AbstractTask{

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            StringBuffer response = null;

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", DummyUser.EMAIL);
            jsonObject.put("password", DummyUser.PASSWORD);
            Log.d("Befit/Conn", "params.tostring" + jsonObject.toString());

            sendRequest("GET", jsonObject.toString(), urls);

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

//    private void String getCookies(){
        //TODO
//        static final String COOKIES_HEADER = "Set-Cookie";
//        HttpURLConnection connection = ... ;
//        static java.net.CookieManager msCookieManager = new java.net.CookieManager();
//
//        Map<String, List<String>> headerFields = connection.getHeaderFields();
//        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
//
//        if(cookiesHeader != null)
//        {
//            for (String cookie : cookiesHeader)
//            {
//                msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
//            }
//        }
//
//        +++++++++++Cookies form cookieManager and load them to connection:
//
//        if(msCookieManager.getCookieStore().getCookies().size() > 0)
//        {
//            //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
//            connection.setRequestProperty("Cookie",
//                    TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
//        }
//    }

}
