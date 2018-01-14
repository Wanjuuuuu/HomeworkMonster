package com.example.wanjukim.homeworkmonster.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.Subject;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wanju Kim on 2018-01-08.
 */

public class AddWorkActivity extends AppCompatActivity {
    @BindView(R.id.subject_spinner) NiceSpinner subjectSpinner;
    @BindView(R.id.work_edittext) EditText workEditText;
//    deadline
    @BindView(R.id.alarm_spinner) NiceSpinner alarmSpinner;
    @BindView(R.id.memo_edittext) EditText memoEditText;
//    @BindView(R.id.) image 삽입
    private List<Subject> subjects;
    private String subject,workname,memo;
    private int alarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        subjects=new ArrayList<>();
//        ArrayAdapter subjectAdapter=new SpinnerAdapter();

//        subjectSpinner.setOnClickListener(new AdapterView.OnItemSelectedListener(){
//
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.setting_save){
            workname=workEditText.getText().toString();
            memo=memoEditText.getText().toString();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
