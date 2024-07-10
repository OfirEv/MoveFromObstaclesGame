package com.example.exe1;

import android.app.Application;

import com.example.exe1.Utillities.SharePreferencesManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesManager.init(this);
    }

}
