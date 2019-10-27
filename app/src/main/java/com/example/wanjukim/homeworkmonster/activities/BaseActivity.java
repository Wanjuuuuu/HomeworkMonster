package com.example.wanjukim.homeworkmonster.activities;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjukim.homeworkmonster.R;

/**
 * Created by Wanju Kim on 2017-11-29.
 */

public class BaseActivity extends AppCompatActivity {
    private View actionBarView;
    private TextView textTitleView;
    private ImageView menuIconView;
    private ActionBar actionBar;

    private void initActionBar() {
        actionBarView = getLayoutInflater().inflate(R.layout.main_action_bar, null);
        textTitleView = actionBarView.findViewById(R.id.action_bar_title);
        menuIconView = actionBarView.findViewById(R.id.iv_menu);
        actionBar = getSupportActionBar();
    }

    private boolean isActionBarNotInitialized() {
        return (actionBarView == null || textTitleView == null
                || menuIconView == null || actionBar == null);
    }

    private void setDefaultActionBar() {
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setCustomView(actionBarView, params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void setMainActionBar(String title) {
        if (isActionBarNotInitialized()) {
            initActionBar();
            if (isActionBarNotInitialized()) {
                return;
            }
        }
        setDefaultActionBar();
        textTitleView.setText(title);
    }

    public void setSubActionBar(String title) {
        if (isActionBarNotInitialized()) {
            initActionBar();
            if (isActionBarNotInitialized()) {
                return;
            }
        }
        setDefaultActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        textTitleView.setText(title);
        menuIconView.setVisibility(View.GONE);
    }

    public void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
