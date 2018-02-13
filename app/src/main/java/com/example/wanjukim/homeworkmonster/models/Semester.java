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
    private Date startDate;
    private Date endDate;

    public Semester(){

    }

    public Semester(String  id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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
}
