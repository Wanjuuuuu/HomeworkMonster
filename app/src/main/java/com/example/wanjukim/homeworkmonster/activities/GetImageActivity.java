package com.example.wanjukim.homeworkmonster.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.adapters.ImageAdapter;
import com.example.wanjukim.homeworkmonster.adapters.WorkItemAdapter;
import com.example.wanjukim.homeworkmonster.models.Image;
import com.example.wanjukim.homeworkmonster.utils.GalleryScanner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wanju Kim on 2018-02-20.
 */

public class GetImageActivity extends BaseActivity implements ImageAdapter.ImageClickListener {
    @BindView(R.id.image_recyclerview)
    RecyclerView recyclerView;

    private final static String TITLE="Get Image";
    private final static int STORAGE_PERMISSION_REQUEST=2018;
    private final static String TAG=GetImageActivity.class.getSimpleName();

    private ImageAdapter adapter;
    private String path=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar(TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        adapter = new ImageAdapter(this);
        adapter.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,permissions,STORAGE_PERMISSION_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.setting_save:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST:
                if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    runGalleryScan();
                }
        }
    }

    private void runGalleryScan(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Image> images= GalleryScanner.photoScan(GalleryScanner.ALL_PHOTO_BUCKET);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addImages(images);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClickImage(Image image) {
        path=image.getPath();
    }
}
