package edu.bluejack16_2.edmusiclo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Asus on 7/9/2017.
 */

public class Session {

    private SharedPreferences preferences;

    public Session(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUser(UserModel user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("MyObject", json);
        edit.apply();
        edit.commit();
    }

    public UserModel getUser() {
        Gson gson = new Gson();
        UserModel obj = null;
        String json = preferences.getString("MyObject", null);

        obj = gson.fromJson(json, UserModel.class);
        return obj;
    }
}

