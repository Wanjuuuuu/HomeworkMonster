package com.example.wanjukim.homeworkmonster;

/**
 * Created by Wanju Kim on 2018-01-13.
 */

public class Subject {
    private int subId;
    private String subject;
    private int semId;

    public Subject(int subId, String subject, int semId) {
        this.subId = subId;
        this.subject = subject;
        this.semId = semId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSemId() {
        return semId;
    }

    public void setSemId(int semId) {
        this.semId = semId;
    }
}
