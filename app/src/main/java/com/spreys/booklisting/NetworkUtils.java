package com.spreys.booklisting;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with Android Studio
 *
 * @author vspreys
 *         Date: 8/09/16
 *         Project: BookListing
 *         Contact by: vlad@spreys.com
 */
public class NetworkUtils {

    public static JSONObject GetJsonObjectFromUrl(URL url) {
        String response = GetStringFromUrl(url);
        try {
            return new JSONObject(GetStringFromUrl(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String GetStringFromUrl(URL url) {
        String response = "";
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://www.mysite.se/index.asp?data=99");

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                response += current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return response;
    }
}
