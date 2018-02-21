package com.example.wanjukim.homeworkmonster.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.wanjukim.homeworkmonster.R;

/**
 * Created by Wanju Kim on 2017-11-29.
 */

public class BaseActivity extends AppCompatActivity {
    public void initActionBar(View view,String title){
        ActionBar actionBar=getSupportActionBar();
        ActionBar.LayoutParams params=new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textView=(TextView)view.findViewById(R.id.action_bar_title);
        textView.setText(title);
        actionBar.setCustomView(view,params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    };

    public void showSnackbar(String message){
        TSnackbar topSnackbar=TSnackbar.make(findViewById(android.R.id.content),message,TSnackbar.LENGTH_SHORT);
        View snackView=topSnackbar.getView();
        TextView snackText=snackView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        snackText.setTextColor(Color.WHITE);
        topSnackbar.show();
    }
}
