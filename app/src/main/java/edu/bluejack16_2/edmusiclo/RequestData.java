package edu.bluejack16_2.edmusiclo;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Adrian Lukito Lo on 10/07/2017.
 */

public class RequestData extends AsyncTask<String,String,JSONObject>{

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject json = null;
        HttpURLConnection http = null;
        StringBuilder builder = new StringBuilder();

        try {
            http = (HttpURLConnection) new URL(strings[0]).openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty ("Authorization", "Bearer 9555816a366e0a6416deecafb52075ae1c738e04342d4e057137648c26df2927");

            String temp;

            BufferedReader buff = new BufferedReader(new InputStreamReader(http.getInputStream()));

            while((temp = buff.readLine()) != null){
                builder.append(temp);
            }
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            http.disconnect();
        }

        try {
            json = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
