package com.example.wanjukim.homeworkmonster;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Wanju Kim on 2017-11-29.
 */

public class HMApplication extends Application {
    // singleton (application 살아있는 동안 해당 객체도 살아있음)
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config=new RealmConfiguration.Builder()
                .name("HMDB").schemaVersion(0).build();
        Realm.setDefaultConfiguration(config);
    }
}
