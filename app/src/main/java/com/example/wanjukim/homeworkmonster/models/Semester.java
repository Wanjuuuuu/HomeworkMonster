package com.example.wanjukim.homeworkmonster.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Wanju Kim on 2018-01-13.
 */

public class Semester extends RealmObject {
    @PrimaryKey
    private String id;
    private String semester;
    private Date startDate;
    private Date endDate;
    private boolean defaultFlag;

    public Semester() {

    }

    public Semester(String id, String semester, Date startDate, Date endDate, boolean defaultFlag) {
        this.id = id;
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
        this.defaultFlag = defaultFlag;
    }

    public String getId() {
        return id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isDefaultFlag() {
        return defaultFlag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    /* Necessary : spinner shows this as an item */
    @Override
    public String toString() {
        return getSemester();
    }
}
