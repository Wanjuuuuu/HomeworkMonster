package com.example.wanjukim.homeworkmonster.utils;

import android.app.DatePickerDialog;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.wanjukim.homeworkmonster.activities.MakeWorkActivity;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by Wanju Kim on 2018-07-18.
 */

public class DatePickerDialogImpl implements DatePickerDialog.OnDateSetListener {
    private Date date;
    private TextView textView;

    public DatePickerDialogImpl(Date date, TextView textView) {
        // is it okay to bring this data from activity?
        this.date = date;
        this.textView = textView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        textView.setText(Utils.dateFormat.format(calendar.getTime()));
        date.setTime(calendar.getTimeInMillis());
//        Log.d(MakeWorkActivity.class.getSimpleName(),"IMPL date : "+Utils.dateFormat.format(date));
    }
}
