package com.example.wanjukim.homeworkmonster.models;

/**
 * Created by Wanju Kim on 2018-02-20.
 */

public class Image {
    private int bucketId;
    private String path;
    private boolean flag=false;

    public int getBucketId() {
        return bucketId;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
