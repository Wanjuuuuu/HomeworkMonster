package com.example.wanjukim.homeworkmonster;

import java.util.Date;

/**
 * Created by Wanju Kim on 2018-01-13.
 */

public class Semester {
    private int semId;
    private Date startDate;
    private Date endDate;

    public Semester(int semId, Date startDate, Date endDate) {
        this.semId = semId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSemId() {
        return semId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setSemId(int semId) {
        this.semId = semId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
