package com.example.wanjukim.homeworkmonster.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Semester;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.utils.EventListenSpinner;

import java.util.UUID;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Wanju Kim on 2018-03-08.
 */

public class MakeSubjectActivity extends BaseActivity implements EventListenSpinner.OnSpinnerEventsListener {
    @BindView(R.id.etv_subject)
    EditText etvSubject;
    @BindView(R.id.spinner_semester)
    EventListenSpinner spinnerSemester;
    @BindView(R.id.etv_professor)
    EditText etvProfessor;
    @BindView(R.id.etv_classroom)
    EditText etvClassroom;
    @BindView(R.id.etv_semester_memo)
    EditText etvMemo;
    @BindColor(R.color.colorAmber)
    int colorAmber;
    @BindColor(R.color.colorMediumGray)
    int colorGray;


    private final static String TAG = MakeSubjectActivity.class.getSimpleName();
    private final static String TITLE = "Subject";

    private String subjectId = null;
    private Subject subjectItem;
    private RealmResults<Semester> semesters;
    private Semester semester;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_subject);
        setSubActionBar(TITLE);
        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        semesters = realm.where(Semester.class).findAll();
        Semester defSemster = semesters.where().equalTo("defaultFlag", true).findFirst();

        SpinnerAdapter semesterSpinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, semesters);
        spinnerSemester.setAdapter(semesterSpinnerAdapter);
        spinnerSemester.setSpinnerEventListener(this);
        spinnerSemester.setSelection(semesters.indexOf(defSemster));

        if (getIntent().getExtras() != null) {
            //
        }

        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = (Semester) spinnerSemester.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                semester = (Semester) spinnerSemester.getSelectedItem();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.setting_save:
                if (subjectId == null) {
                    addSubjectItem();
                } else {
                    modifySubjectItem();
                }
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addSubjectItem() {
        String name = etvSubject.getText().toString();
        String prof = etvProfessor.getText().toString();
        String classroom = etvClassroom.getText().toString();
        String memo = etvMemo.getText().toString();

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Subject subject = realm.createObject(Subject.class, UUID.randomUUID().toString());
        subject.setSubject(name);
        subject.setSemester(semester);
        subject.setProfessor(prof);
        subject.setClassroom(classroom);
        subject.setMemo(memo);

        realm.commitTransaction();
    }

    private void modifySubjectItem() {

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
