package com.example.wanjukim.homeworkmonster.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Image;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.utils.ClearEditText;
import com.example.wanjukim.homeworkmonster.utils.EventListenSpinner;
import com.example.wanjukim.homeworkmonster.utils.Utils;

import java.util.Calendar;
import java.util.Date;
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

public class MakeWorkActivity extends BaseActivity implements EventListenSpinner.OnSpinnerEventsListener {
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

    private final static String TAG = MakeWorkActivity.class.getSimpleName();
    private final static String TITLE="New Homework";
    public final static int RESULT=1994;

    private String workId=null;
    private WorkItem workItem;
    private RealmResults<Subject> subjects;
    private Subject subject;
    private int alarm;
    private Date date;
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

        final SpinnerAdapter alarmSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Utils.alarms);
        spinnerAlarm.setAdapter(alarmSpinnerAdapter);
        spinnerAlarm.setSpinnerEventListener(this);

        /* Branch : Add or Modify workItem */

        if(getIntent().getExtras()==null) { // workId==null
            date = new Date();
            Log.d(TAG,"if extras is null");

            int dif = (int) (date.getTime() % (60 * 60 * 1000)) / (60 * 1000);
            if (dif != 0) {
                date.setTime(date.getTime() + (60 - dif) * 60 * 1000); // one hour more than date
            }
            tvDate.setText(Utils.dateFormat.format(date));
            tvTime.setText(Utils.timeFormat.format(date));
        } else {
            workId=getIntent().getExtras().getString(GetWorkActivity.EXTRA);

            workItem=realm.where(WorkItem.class).equalTo("id",workId).findFirst();

            etvWork.setHint(workItem.getWork());
            etvMemo.setHint(workItem.getMemo());
            date=workItem.getDeadline();
            tvDate.setText(Utils.dateFormat.format(date));
            tvTime.setText(Utils.timeFormat.format(date));
            image=workItem.getImage();
            if(image!=null) {
                Glide.with(this).load(image.getPath()).into(ivImage);
            }

            spinnerSubject.setSelection(subjects.indexOf(workItem.getSubject()));
            spinnerAlarm.setSelection(workItem.getAlarm()-1);

        }

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = (Subject) spinnerSubject.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject = (Subject) spinnerSubject.getSelectedItem(); // TODO : default subject exists? null???
            }
        });

        spinnerAlarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alarm = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                alarm = spinnerAlarm.getSelectedItemPosition();
            }
        });

    }

    @OnClick(R.id.tv_date)
    public void onClickDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                date = calendar.getTime();
                tvDate.setText(Utils.dateFormat.format(date)); //
                Log.d(TAG, " time : " + Utils.timeFormat.format(date));
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
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(year,month,day, hourOfDay, minute);
                date = calendar.getTime();
                tvTime.setText(Utils.timeFormat.format(date)); //
                Log.d(TAG, " time : " + Utils.dateFormat.format(date));
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
                if(workId==null) {
                    addWorkItem();
                } else {
                    modifyWorkItem();
                }
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

        WorkItem work = realm.createObject(WorkItem.class, UUID.randomUUID().toString());
        work.setWork(workname);
        work.setMemo(memo);
        work.setSubject(subject);
        work.setAlarm(alarm);
        work.setDeadline(date);

        if(image==null) {
            work.setImage(null);
        } else {
            Image imageItem = realm.copyToRealm(image);
            work.setImage(imageItem);
        }

        realm.commitTransaction();
    }

    private void modifyWorkItem(){
        String workname = etvWork.getText().toString();
        String memo = etvMemo.getText().toString();

        if(workname.equals("")){
            workname=workItem.getWork();
        }
        if(memo.equals("")){
            memo=workItem.getMemo();
        }

        Realm realm = Realm.getDefaultInstance(); // opens default db

        realm.beginTransaction();

        workItem.setWork(workname);
        workItem.setMemo(memo);
        workItem.setSubject(subject);
        workItem.setAlarm(alarm);
        workItem.setDeadline(date);

        if(image==null) {
            workItem.setImage(null);
        } else {
            Image imageItem = realm.copyToRealm(image);
            workItem.setImage(imageItem);
        }

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
