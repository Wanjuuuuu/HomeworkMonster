package com.example.wanjukim.homeworkmonster;

import android.app.Application;
import android.content.Context;

import com.example.wanjukim.homeworkmonster.realm.DBMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Wanju Kim on 2017-11-29.
 */

public class HMApplication extends Application {
    private static Context context;

    // singleton (application 살아있는 동안 해당 객체도 살아있음)
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("HMDB").schemaVersion(8).migration(new DBMigration()).build();
        Realm.setDefaultConfiguration(config);
    }

    public static Context getContext() {
        return context;
    }
}
