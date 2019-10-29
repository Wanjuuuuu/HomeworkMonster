package com.example.wanjukim.homeworkmonster.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Semester;
import com.example.wanjukim.homeworkmonster.utils.ClearEditText;
import com.example.wanjukim.homeworkmonster.utils.DatePickerDialogImpl;
import com.example.wanjukim.homeworkmonster.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Wanju Kim on 2018-03-08.
 */

public class MakeSemesterActivity extends BaseActivity {
    @BindView(R.id.etv_semester)
    ClearEditText etvSemester;
    @BindView(R.id.tv_startdate)
    TextView tvStartDate;
    @BindView(R.id.tv_enddate)
    TextView tvEndDate;
    @BindView(R.id.switch_def)
    SwitchCompat swDefault;

    private final static String TAG = MakeSemesterActivity.class.getSimpleName();
    private final static String TITLE = "Semester";

    private String semesterId;
    private Semester semesterItem;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_semester);
        setSubActionBar(TITLE);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            startDate = Utils.getDate(new Date());
            endDate = Utils.getDate(new Date());
            tvStartDate.setText(Utils.dateFormat.format(startDate));
            tvEndDate.setText(Utils.dateFormat.format(endDate));
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar, menu);
        return true;
    }

    @OnClick(R.id.tv_startdate)
    public void onClickStartDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialogImpl(startDate, tvStartDate), year, month, day);
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_enddate)
    public void onClickEndDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialogImpl(endDate, tvEndDate), year, month, day);
        datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.setting_save:
                if (semesterId == null) {
                    if (swDefault.isChecked()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.dialog_default_change)
                                .setCancelable(false)
                                .setPositiveButton(R.string.dialog_positive_button, (dialog, which) -> {
                                    dialog.dismiss();
                                    changeDefSemester();
                                    addSemesterItem();
                                    finish();
                                })
                                .setNegativeButton(R.string.dialog_negative_button, (dialog, which) -> dialog.cancel())
                                .show();
                    } else {
                        addSemesterItem();
                        finish();
                    }
                } else{
                    modifySemesterItem();
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeDefSemester() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Semester> prevDefSemesters = realm.where(Semester.class).equalTo(
                "defaultFlag", true).findAll();

        if (!prevDefSemesters.isEmpty()) {
            realm.beginTransaction();

            for (Semester semester : prevDefSemesters)
                semester.setDefaultFlag(false);

            realm.commitTransaction();
        }
    }

    private void addSemesterItem() {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Semester semester = realm.createObject(Semester.class, UUID.randomUUID().toString());
        semester.setSemester(etvSemester.getText().toString());
        semester.setStartDate(startDate);
        semester.setEndDate(endDate);
        semester.setDefaultFlag(swDefault.isChecked());

        realm.commitTransaction();
    }

    private void modifySemesterItem() {

    }
}
