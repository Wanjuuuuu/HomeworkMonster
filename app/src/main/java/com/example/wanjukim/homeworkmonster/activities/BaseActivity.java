package com.example.wanjukim.homeworkmonster.activities;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.wanjukim.homeworkmonster.R;

/**
 * Created by Wanju Kim on 2017-11-29.
 */

public class BaseActivity extends AppCompatActivity {
    public void initActionBar(View view, String title) {
        ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textView = view.findViewById(R.id.action_bar_title);
        textView.setText(title);
        actionBar.setCustomView(view, params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
