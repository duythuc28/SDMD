package com.example.swinburne.accessiblitymap.config;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.BuildingDA;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(BuildingDA.class);
        ActiveAndroid.initialize(config.create());
    }
}
