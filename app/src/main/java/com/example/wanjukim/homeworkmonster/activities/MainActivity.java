package com.example.wanjukim.homeworkmonster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.daimajia.swipe.util.Attributes;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.WorkItem;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.example.wanjukim.homeworkmonster.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    @BindView(R.id.main_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.add_button) FloatingActionButton addWorkButton;

    private WorkItemAdapter adapter;
    private ArrayList<WorkItem> workItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        workItems=new ArrayList<>();
        workItems.add(new WorkItem("Web Client Upgrade","Computer Network",1,new Date()));
        workItems.add(new WorkItem("Virtual World","Software Engineering",2,new Date()));
        workItems.add(new WorkItem("Microprocessor exercise #5","Microprocessor",3,new Date()));

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
        //when option is selected
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_button)
    public void onClickAddButton() {
        Intent intentAddWork=new Intent(this, AddWorkActivity.class);
        startActivity(intentAddWork);
    }
}
