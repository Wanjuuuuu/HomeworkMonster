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
import com.example.wanjukim.homeworkmonster.Semester;
import com.example.wanjukim.homeworkmonster.Subject;
import com.example.wanjukim.homeworkmonster.WorkItem;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.example.wanjukim.homeworkmonster.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//        semesters=new ArrayList<>();
//        subjects=new ArrayList<>();
        workItems=new ArrayList<>();

//        subjects.add(new Subject(1,"Computer network",1));
//        subjects.add(new Subject(2,"Software Engineering",1));
//        subjects.add(new Subject(3,"Microprocessor",1));

        workItems.add(new WorkItem(1,"Web Client upgrade",1,new Date(),3,"Important"));
        workItems.add(new WorkItem(2,"Virtual World implementation",2,new Date(),2,"very good"));
        workItems.add(new WorkItem(3,"Microprocessor exercise #4",3,new Date(),2,"hate it"));

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
