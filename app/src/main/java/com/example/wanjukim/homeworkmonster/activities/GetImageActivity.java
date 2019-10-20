package com.example.wanjukim.homeworkmonster.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.adapters.ImageAdapter;
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

    private final static String TITLE = "Get Image";
    private final static String MESSAGE = "Please choose the image!";
    public final static String EXTRA = "IMAGE";
    private final static int STORAGE_PERMISSION_REQUEST = 2018;
    private final static String TAG = GetImageActivity.class.getSimpleName();

    private ImageAdapter adapter;
    private Image image = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        initActionBar(view, TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        adapter = new ImageAdapter(this);
        adapter.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, STORAGE_PERMISSION_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case R.id.setting_save:
                if (image == null) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), MESSAGE
                            , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    return false;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA, image);
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_REQUEST) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                runGalleryScan();
            }
        }
    }

    private void runGalleryScan() {
        new Thread(() -> {
            final List<Image> images = GalleryScanner.photoScan(GalleryScanner.ALL_PHOTO_BUCKET);
            runOnUiThread(() -> {
                adapter.addImages(images);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    public void onClickImage(Image image) {
        this.image = image;
    }
}
