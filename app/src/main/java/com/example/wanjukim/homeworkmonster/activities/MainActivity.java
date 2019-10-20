package com.example.wanjukim.homeworkmonster.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.WorkItem;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Date;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MainActivity extends BaseActivity {
    @BindView(R.id.main_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.main_invisible_layout)
    LinearLayout invisibleLayout;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
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
    private final static String TITLE = "HomeworkMonster";

    private WorkItemAdapter adapter;
    private RealmResults<WorkItem> workItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.main_action_bar, null);
        initActionBar(view, TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener((item) -> {
            item.setChecked(true);
            drawerLayout.closeDrawers();

            return true;
        });

        Realm realm = Realm.getDefaultInstance();

        // get rid of ended works which are not updated yet
        workItems = realm.where(WorkItem.class).equalTo("state", WorkItem.BEFORE).greaterThan(
                "deadline", new Date()).findAll().sort("deadline", Sort.ASCENDING);

        adapter = new WorkItemAdapter(this, workItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        /* hide fab when view goes down */
        /* issue : menu doesn't have hide or show method as normal fab */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // dy : the amount of vertical scroll
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

    @OnClick(R.id.iv_menu)
    public void onClickMenu() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @OnClick(R.id.main_invisible_layout)
    public void onCollapse() {
        fabMenu.collapse();
    }

    /* when back to main activity */

    @Override
    protected void onResume() { // 홈, 과제, 과목, 학기 ... // intent 내요을 받을 수 있는가
        super.onResume();
        onCollapse();

        // get rid of ended works which are not updated yet
        Realm realm = Realm.getDefaultInstance();
        workItems = realm.where(WorkItem.class).equalTo("state", WorkItem.BEFORE).greaterThan(
                "deadline", new Date()).findAll().sort("deadline", Sort.ASCENDING);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        searchView.setQuery("",false);
        searchView.clearFocus();
        return true;
    }

    // TODO : use realm to retrieve items
    private void performSearch(String query) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_button) {
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.action_add_item)
    public void onClickAddItem() {
        Intent intentAddWork = new Intent(this, MakeWorkActivity.class);
        startActivity(intentAddWork);
    }

    @OnClick(R.id.action_add_subject)
    public void onClickAddSubject() {
        Intent intentAddSubject = new Intent(this, MakeSubjectActivity.class);
        startActivity(intentAddSubject);
    }

    @OnClick(R.id.action_add_semester)
    public void onClickAddSemester() {
        Intent intentAddSemester = new Intent(this, MakeSemesterActivity.class);
        startActivity(intentAddSemester);
    }
}
