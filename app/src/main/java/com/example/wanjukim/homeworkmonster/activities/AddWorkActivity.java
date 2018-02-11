package com.example.wanjukim.homeworkmonster.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.utils.ClearEditText;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Wanju Kim on 2018-01-08.
 */

public class AddWorkActivity extends BaseActivity {
    @BindView(R.id.etv_work)ClearEditText etvWork;
    @BindView(R.id.etv_memo)ClearEditText etvMemo;
    @BindView(R.id.spinner_subject)NiceSpinner spinnerSubject;
    @BindView(R.id.spinner_alarm)NiceSpinner spinnerAlarm;

    private List<Subject> subjects;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        subjects=new ArrayList<>();

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
}
