package com.example.wanjukim.homeworkmonster.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Semester;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.utils.ClearEditText;
import com.example.wanjukim.homeworkmonster.utils.EventListenSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Wanju Kim on 2018-01-08.
 */

public class AddWorkActivity extends BaseActivity implements EventListenSpinner.OnSpinnerEventsListener{
    @BindView(R.id.etv_work)ClearEditText etvWork;
    @BindView(R.id.etv_memo)ClearEditText etvMemo;
    @BindView(R.id.spinner_subject)EventListenSpinner spinnerSubject;
    @BindView(R.id.spinner_alarm)EventListenSpinner spinnerAlarm;
    @BindColor(R.color.colorAmber)int colorAmber;
    @BindColor(R.color.colorMediumGray)int colorGray;

    private final static String TAG=AddWorkActivity.class.getSimpleName();
    private RealmResults<Subject> subjects;
    private List<String> subjectNames;
    private String[] alarms={"1 day","2 days","3 days","4 days","5 days","6 days","a week"};
    private Subject subject;
    private int alarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        Realm realm=Realm.getDefaultInstance();

        subjects=realm.where(Subject.class).findAll(); // TODO : filtering default semester!!

        SpinnerAdapter subjectSpinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,subjects);
        spinnerSubject.setAdapter(subjectSpinnerAdapter);
        spinnerSubject.setSpinnerEventListener(this);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject=(Subject)spinnerSubject.getSelectedItem();
                Log.d(TAG,subject.getSubject());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject=(Subject)spinnerSubject.getSelectedItem(); // TODO : default subject exists? null???
                Log.d(TAG,subject.getSubject());
            }
        });

        final SpinnerAdapter alarmSpinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,alarms);
        spinnerAlarm.setAdapter(alarmSpinnerAdapter);
        spinnerAlarm.setSpinnerEventListener(this);
        spinnerAlarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alarm=position+1;
//                Log.d(TAG,String.valueOf(alarm));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                alarm=spinnerAlarm.getSelectedItemPosition();
//                Log.d(TAG,String.valueOf(alarm));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.setting_save){
            addWorkItem();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addWorkItem() {
        String workname=etvWork.getText().toString();
        String memo=etvMemo.getText().toString();
        // more attributes

        Realm realm = Realm.getDefaultInstance(); // opens default db

        realm.beginTransaction();

        WorkItem workItem = realm.createObject(WorkItem.class, UUID.randomUUID().toString());
        workItem.setWork(workname);
        workItem.setMemo(memo);


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
