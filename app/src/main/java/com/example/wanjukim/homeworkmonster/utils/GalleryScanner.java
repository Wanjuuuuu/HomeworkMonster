package com.example.wanjukim.homeworkmonster.utils;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.wanjukim.homeworkmonster.HMApplication;
import com.example.wanjukim.homeworkmonster.models.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Wanju Kim on 2018-02-20.
 */

public class GalleryScanner {
    public static final int ALL_PHOTO_BUCKET = 0;

    private static final String[] projection = {
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
    };

    public static List<Image> photoScan(int albumId) {
        String selection = null;

        if (albumId != ALL_PHOTO_BUCKET)
            selection = String.format("%s=%d", MediaStore.Images.Media.BUCKET_ID, albumId);

        Cursor cursor = MediaStore.Images.Media.query(
                HMApplication.getContext().getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC"); // 뛰어쓰기

        int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
        int pathColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

        List<Image> photoList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int bucketId = cursor.getInt(bucketIdColumn);
            String path = cursor.getString(pathColumn);

            Image photo = new Image();
            photo.setId(UUID.randomUUID().toString());
            photo.setBucketId(bucketId);
            photo.setPath(path);

            photoList.add(photo);
        }
        return photoList;
    }
}
