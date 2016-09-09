package com.spreys.booklisting;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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
            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            response = sb.toString();
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
