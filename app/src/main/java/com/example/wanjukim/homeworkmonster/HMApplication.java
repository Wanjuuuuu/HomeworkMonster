package com.example.wanjukim.homeworkmonster;

import android.app.Application;

/**
 * Created by Wanju Kim on 2017-11-29.
 */

public class HMApplication extends Application {
    // singleton (application 살아있는 동안 해당 객체도 살아있음)
    @Override
    public void onCreate() {
        super.onCreate();
        // realm init
    }
}
