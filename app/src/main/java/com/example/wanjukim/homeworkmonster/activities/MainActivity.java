package com.example.wanjukim.homeworkmonster.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Date;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MainActivity extends BaseActivity {
    @BindView(R.id.main_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.main_invisible_layout)
    LinearLayout invisibleLayout;
    @BindView(R.id.add_button)
    FloatingActionsMenu fabMenu;
    @BindView(R.id.action_add_item)
    FloatingActionButton fabItem;
    @BindView(R.id.action_add_subject)
    FloatingActionButton fabSubject;
    @BindView(R.id.action_add_semester)
    FloatingActionButton fabSemester;
    @BindDrawable(R.drawable.ic_assignment_white_24dp)
    Drawable iconAssignment;
    @BindDrawable(R.drawable.ic_class_white_24dp)
    Drawable iconSubject;
    @BindDrawable(R.drawable.ic_date_range_white_24dp)
    Drawable iconSemester;

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String TITLE="HomeworkMonster";

    private WorkItemAdapter adapter;
    private RealmResults<WorkItem> workItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar(TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        workItems = realm.where(WorkItem.class).equalTo("state", WorkItem.BEFORE).greaterThan("deadline", new Date()).findAll().sort("deadline", Sort.ASCENDING); // get rid of ended works which are not updated yet

        adapter = new WorkItemAdapter(this, workItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        /* hide fab when view goes down */
        /* issue : menu doesn't have hide or show method as normal fab */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) { // dy : the amount of vertical scroll
                if (dy > 0) {
                    fabMenu.animate().translationY(fabMenu.getHeight())
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .setDuration(100);
                } else {
                    fabMenu.animate().translationY(0)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .setDuration(100);
                }
            }
        });

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                invisibleLayout.setVisibility(View.VISIBLE);
                adapter.closeItems();
            }

            @Override
            public void onMenuCollapsed() {
                invisibleLayout.setVisibility(View.INVISIBLE);
            }
        });

        fabItem.setIconDrawable(iconAssignment);
        fabSubject.setIconDrawable(iconSubject);
        fabSemester.setIconDrawable(iconSemester);
    }

    @OnClick(R.id.main_invisible_layout)
    public void onCollapse() {
        fabMenu.collapse();
    }

    /* when back to main activity */

    @Override
    protected void onResume() {
        super.onResume();
        onCollapse();

        Realm realm = Realm.getDefaultInstance();
        workItems = realm.where(WorkItem.class).equalTo("state", WorkItem.BEFORE).greaterThan("deadline", new Date()).findAll().sort("deadline", Sort.ASCENDING); // get rid of ended works which are not updated yet

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                return true;
            case R.id.menu_button:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.action_add_item)
    public void onClickAddItem() {
        Intent intentAddWork = new Intent(this, AddWorkActivity.class);
        startActivity(intentAddWork);
    }

    @OnClick(R.id.action_add_subject)
    public void onClickAddSubject() {

    }

    @OnClick(R.id.action_add_semester)
    public void onClickAddSemester() {

    }
}
