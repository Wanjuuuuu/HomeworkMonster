package com.example.wanjukim.homeworkmonster.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.utils.Utils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
