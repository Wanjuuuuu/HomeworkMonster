package com.example.wanjukim.homeworkmonster;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wanju Kim on 2018-01-07.
 */

public class WorkItem {
    private int workId;
    private String work;
    private int subId;
    private Date deadline;
    private int alarm;
    private String memo;
    private WorkState state;
    private boolean swipeState; //

    public WorkItem(int workId, String work, int subId, Date deadline, int alarm, String memo) {
        this.workId = workId;
        this.work = work;
        this.subId = subId;
        this.deadline = deadline;
        this.alarm = alarm;
        this.memo = memo;
        this.state = state;
        this.swipeState = false;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDeadline() {
        SimpleDateFormat format=new SimpleDateFormat("MM-DD hh:mm"); // 시간으로 변경?
        return format.format(deadline).toString();
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

    public WorkState getState() {
        return state;
    }

    public void setState(WorkState state) {
        this.state = state;
    }

    public String getdDay() {
        long diff=new Date().getTime()-deadline.getTime();
        int dDay=(int)diff/(24*60*60*1000);
        return String.format("D-%02d",dDay);
    } // 임의로 넣어둠

    public boolean getSwipeState(){
        return swipeState;
    }

    public void setSwipeState(boolean flag){
        swipeState=flag;
    }
}
