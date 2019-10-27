package com.example.wanjukim.homeworkmonster.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.utils.Utils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Wanju Kim on 2018-02-27.
 */

public class GetWorkActivity extends BaseActivity {
    @BindView(R.id.tv_details_work)
    TextView tvWork;
    @BindView(R.id.tv_details_subject)
    TextView tvSubject;
    @BindView(R.id.tv_details_date)
    TextView tvDate;
    @BindView(R.id.tv_details_time)
    TextView tvTime;
    @BindView(R.id.tv_details_alarm)
    TextView tvAlarm;
    @BindView(R.id.tv_details_memo)
    TextView tvMemo;
    @BindView(R.id.iv_details_image)
    ImageView ivImage;

    private final static String TITLE = "Get Details";
    public final static String EXTRA = "WORKID";

    private String workId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setSubActionBar(TITLE);
        ButterKnife.bind(this);

        workId = getIntent().getExtras().getString(WorkItemAdapter.EXTRA);
    }

    private void getWork() {
        Realm realm = Realm.getDefaultInstance();
        WorkItem workItem = realm.where(WorkItem.class).equalTo("id", workId).findFirst();

        tvWork.setText(workItem.getWork());
        if (workItem.getSubject() == null) {
            tvSubject.setText("");
        } else {
            tvSubject.setText(workItem.getSubject().getSubject());
        }
        Date deadline = workItem.getDeadline();
        tvDate.setText(Utils.dateFormat.format(deadline));
        tvTime.setText(Utils.timeFormat.format(deadline));
        tvAlarm.setText(Utils.alarms[workItem.getAlarm() - 1]);
        tvMemo.setText(workItem.getMemo());
        if (workItem.getImage() != null) {
            Glide.with(this).load(workItem.getImage().getPath()).into(ivImage);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.setting_modify:
                Intent intent = new Intent(this, MakeWorkActivity.class);
                intent.putExtra(EXTRA, workId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
