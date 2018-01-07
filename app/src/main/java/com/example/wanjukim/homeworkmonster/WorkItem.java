package com.example.wanjukim.homeworkmonster;

import java.util.Date;

/**
 * Created by Wanju Kim on 2018-01-07.
 */

public class WorkItem {
    private String work;
    private String subject;
    private int dDay;
    private Date deadline;

    public WorkItem(String work,String subject,int dDay,Date deadline){
        this.work=work;
        this.subject=subject;
        this.dDay=dDay;
        this.deadline=deadline;
    }

    public String getWork() {
        return work;
    }

    public String getSubject() {
        return subject;
    }

    public String getdDay() {
        return "D-"+new Integer(dDay).toString();
    }

    public String getDeadline() {
        return deadline.toString();
    }
}
