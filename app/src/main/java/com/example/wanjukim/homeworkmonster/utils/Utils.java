package com.example.wanjukim.homeworkmonster.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Wanju Kim on 2017-12-28.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    public static final String[] alarms = {"1 day", "2 days", "3 days", "4 days", "5 days", "6 days", "a week"};
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy (E)", Locale.ENGLISH);
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

    public static void test() {
        Log.d(TAG, "test is called");
    }

}
