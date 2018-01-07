package com.example.wanjukim.homeworkmonster.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.swipe.util.Attributes;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.WorkItem;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.example.wanjukim.homeworkmonster.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {
    @BindView(R.id.main_recyclerview) RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
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
        ((WorkItemAdapter) adapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)); // vertically

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        Utils.test();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //when option is selected
        return super.onOptionsItemSelected(item);
    }
}
