package com.example.wanjukim.homeworkmonster;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wanju Kim on 2018-01-07.
 */

public class WorkItem {
    private String work;
    private String subject;
    private int dDay;
    private Date deadline;
    private boolean swipeState;

    public WorkItem(String work,String subject,int dDay,Date deadline){
        this.work=work;
        this.subject=subject;
        this.dDay=dDay;
        this.deadline=deadline;
        this.swipeState=false;
    }

    public String getWork() {
        return work;
    }

    public String getSubject() {
        return subject;
    }

    public String getdDay() {
        return String.format("D-%02d",new Integer(dDay));
    }

    public String getDeadline() {
        SimpleDateFormat format=new SimpleDateFormat("MM-DD hh:mm"); // 시간으로 변경?
        return format.format(deadline).toString();
    }

    public boolean getSwipeState(){
        return swipeState;
    }

    public void setSwipeState(boolean flag){
        swipeState=flag;
    }
}
