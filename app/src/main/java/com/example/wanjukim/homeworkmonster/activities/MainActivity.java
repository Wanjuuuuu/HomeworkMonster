package com.example.wanjukim.homeworkmonster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends BaseActivity {
    @BindView(R.id.main_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.add_button) FloatingActionButton addWorkButton;

    private WorkItemAdapter adapter;
    private RealmResults<WorkItem> workItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        workItems = realm.where(WorkItem.class).equalTo("state",WorkItem.BEFORE).greaterThan("deadline",new Date()).findAll(); //

        adapter=new WorkItemAdapter(this,workItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_button:
                return true;
            case R.id.menu_button:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_button)
    public void onClickAddButton() {
        Intent intentAddWork=new Intent(this, AddWorkActivity.class);
        startActivity(intentAddWork);
    }
}
