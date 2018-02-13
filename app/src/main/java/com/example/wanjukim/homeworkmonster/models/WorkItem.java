package com.example.wanjukim.homeworkmonster.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Wanju Kim on 2018-01-07.
 */

public class WorkItem extends RealmObject {
    @PrimaryKey
    private String id;
    private String work;
    private Subject subject;
    private Date deadline;
    private int alarm;
    private String memo;
    private int state;

    public static final int BEFORE = 0;
    public static final int SUBMIT = 1;
    public static final int GIVEUP = 2;
    public static final int END = 3;
    public static final int DELETED = 4;

    @Ignore
    private boolean swipeState; //

    public WorkItem() {

    }

    public WorkItem(String id, String work, Subject subject, Date deadline, int alarm, String memo) {
        this.id = id;
        this.work = work;
        this.subject = subject;
        this.deadline = deadline;
        this.alarm = alarm;
        this.memo = memo;
        if (deadline.compareTo(new Date()) > 0) { // deadline > now
            this.state = BEFORE;
        } else {
            this.state = END;
        }
        this.swipeState = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getState() {
        if (state == SUBMIT || state == GIVEUP || state == DELETED) {
            return state;
        }

        if (deadline.compareTo(new Date()) <= 0) {
            setState(END);
        }
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean getSwipeState() {
        return swipeState;
    }

    public void setSwipeState(boolean flag) {
        swipeState = flag;
    }

    public String getdDay() {
        long diff = deadline.getTime()-new Date().getTime();
        long dDay = (long) diff / (24 * 60 * 60 * 1000);
        return String.format("D-%02d", dDay);
    } // 임의로 넣어둠
}
