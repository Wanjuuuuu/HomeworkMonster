package com.example.wanjukim.homeworkmonster.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Image;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.utils.ClearEditText;
import com.example.wanjukim.homeworkmonster.utils.EventListenSpinner;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Wanju Kim on 2018-01-08.
 */

public class AddWorkActivity extends BaseActivity implements EventListenSpinner.OnSpinnerEventsListener {
    @BindView(R.id.etv_work)
    ClearEditText etvWork;
    @BindView(R.id.etv_memo)
    ClearEditText etvMemo;
    @BindView(R.id.spinner_subject)
    EventListenSpinner spinnerSubject;
    @BindView(R.id.spinner_alarm)
    EventListenSpinner spinnerAlarm;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.get_image_layout)
    ConstraintLayout imageLayout;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindColor(R.color.colorAmber)
    int colorAmber;
    @BindColor(R.color.colorMediumGray)
    int colorGray;

    private final static String TAG = AddWorkActivity.class.getSimpleName();
    private final static String TITLE="New Homework";
    public final static int RESULT=1994;

    private RealmResults<Subject> subjects;
    private String[] alarms = {"1 day", "2 days", "3 days", "4 days", "5 days", "6 days", "a week"};
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy (E)", Locale.ENGLISH);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
    private Subject subject;
    private int alarm;
    private Date now;
    private Image image=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.action_bar,null);
        initActionBar(view,TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        subjects = realm.where(Subject.class).findAll(); // TODO : filtering default semester!!

        SpinnerAdapter subjectSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects);
        spinnerSubject.setAdapter(subjectSpinnerAdapter);
        spinnerSubject.setSpinnerEventListener(this);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = (Subject) spinnerSubject.getSelectedItem();
                Log.d(TAG, subject.getSubject());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject = (Subject) spinnerSubject.getSelectedItem(); // TODO : default subject exists? null???
                Log.d(TAG, subject.getSubject());
            }
        });

        final SpinnerAdapter alarmSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alarms);
        spinnerAlarm.setAdapter(alarmSpinnerAdapter);
        spinnerAlarm.setSpinnerEventListener(this);
        spinnerAlarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alarm = position + 1;
//                Log.d(TAG,String.valueOf(alarm));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                alarm = spinnerAlarm.getSelectedItemPosition();
//                Log.d(TAG,String.valueOf(alarm));
            }
        });

        now = new Date();


        int dif = (int) (now.getTime() % (60 * 60 * 1000)) / (60 * 1000);
        if (dif != 0) {
            now.setTime(now.getTime() + (60 - dif) * 60 * 1000); // one hour more than now
        }
        tvDate.setText(dateFormat.format(now));
        tvTime.setText(timeFormat.format(now));
    }

    @OnClick(R.id.tv_date)
    public void onClickDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                now = calendar.getTime();
                tvDate.setText(dateFormat.format(now)); //
                Log.d(TAG, " time : " + timeFormat.format(now));
            }
        }, year, month, day);

        Calendar limitCal = Calendar.getInstance();
        limitCal.set(year, month, day);
        datePickerDialog.getDatePicker().setMinDate(limitCal.getTimeInMillis());

        datePickerDialog.show();
    }

    @OnClick(R.id.tv_time)
    public void onClickTime() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(year,month,day, hourOfDay, minute);
                now = calendar.getTime();
                tvTime.setText(timeFormat.format(now)); //
                Log.d(TAG, " time : " + dateFormat.format(now));
            }
        }, hour, min, false);

        timePickerDialog.show();
    }

    @OnClick(R.id.get_image_layout)
    public void onClickImage(){
        Intent intent=new Intent(this,GetImageActivity.class);
        startActivityForResult(intent,RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT){
            if(resultCode==RESULT_OK) {
                image = (Image) data.getSerializableExtra(GetImageActivity.EXTRA);
                Glide.with(this).load(image.getPath()).into(ivImage);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.setting_save:
                addWorkItem();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addWorkItem() {
        String workname = etvWork.getText().toString();
        String memo = etvMemo.getText().toString();
        // more attributes

        Realm realm = Realm.getDefaultInstance(); // opens default db

        realm.beginTransaction();

        WorkItem workItem = realm.createObject(WorkItem.class, UUID.randomUUID().toString());
        workItem.setWork(workname);
        workItem.setMemo(memo);
        workItem.setSubject(subject);
        workItem.setAlarm(alarm);
        workItem.setDeadline(now);

        Image imageItem=realm.copyToRealm(image);
        workItem.setImage(imageItem);
        
        realm.commitTransaction();
    }

    @Override
    public void onSpinnerOpened(Spinner spinner) {
        spinner.getBackground().setColorFilter(colorAmber, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onSpinnerClosed(Spinner spinner) {
        spinner.getBackground().setColorFilter(colorGray, PorterDuff.Mode.SRC_ATOP);
    }
}
