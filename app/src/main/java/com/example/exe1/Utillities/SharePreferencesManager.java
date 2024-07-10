package com.example.exe1.Utillities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharePreferencesManager {
    private static final String PREFS_NAME = "player_prefs";
    private static final String KEY_PLAYERS = "players";
    private static SharePreferencesManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private SharePreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new SharePreferencesManager(context.getApplicationContext());
        }
    }

    public static SharePreferencesManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SharePreferencesManager is not initialized, call init() method first.");
        }
        return instance;
    }

    public void savePlayerList(List<Player> playerList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(playerList);
        editor.putString(KEY_PLAYERS, json);
        editor.apply();
    }

    public List<Player> loadPlayerList() {
        String json = sharedPreferences.getString(KEY_PLAYERS, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Player>>() {}.getType();
            return gson.fromJson(json, type);
        }
    }
}
