package com.example.wanjukim.homeworkmonster.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.utils.ClearEditText;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

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
            addWorkItem();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addWorkItem() {
        String workname=etvWork.getText().toString();
        String memo=etvMemo.getText().toString();

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        WorkItem workItem = realm.createObject(WorkItem.class, (int) (Math.random() * 10000));
        workItem.setWork(workname);
        workItem.setMemo(memo);

        realm.commitTransaction();
    }
}
