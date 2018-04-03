package com.baking.siva.bakingrecipe.util;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

public class HttpRequest {
    private final String httpUrl;
    private final String url;

    public HttpRequest(String url){

        this.url = url;
        httpUrl = buildUrl();
    }

    private String buildUrl() {
        Log.v("HttpRequest",url);
        Uri builtUri = Uri.parse(url).buildUpon()
                .build();
        String uriStr = builtUri.toString();
        Log.v("HTTP",uriStr);
        return uriStr;
    }

    public String getJsonString(){
        HttpURLConnection urlConnection = null;
        String res =null;

        try {
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isw);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line= reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            res = sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return  res;
    }
}
