package com.example.wanjukim.homeworkmonster.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static void showTopSnackBar(Activity activity,String message){
        View rootView=activity.getWindow().getDecorView().findViewById(android.R.id.content);
        TSnackbar topSnackbar=TSnackbar.make(rootView,message,TSnackbar.LENGTH_SHORT);
        View snackView=topSnackbar.getView();
        TextView snackText=snackView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        snackText.setTextColor(Color.WHITE);
        topSnackbar.show();
    }

    public static long getDday(Date deadline){
        long diff = deadline.getTime()-new Date().getTime();
        long dDay = (long) diff / (24 * 60 * 60 * 1000);
        return dDay;
    }

    public static Date getDate(Date date){
        int dif=(int)(date.getTime()%(60*60*1000))/(60*1000);
        if(dif!=0){
            date.setTime(date.getTime()+(60-dif)*60*1000); // one hour more than date
        }
        return date;
    }
}
