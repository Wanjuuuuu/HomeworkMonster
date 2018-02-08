package com.example.wanjukim.homeworkmonster.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Wanju Kim on 2018-01-13.
 */

public class Subject extends RealmObject {
    @PrimaryKey
    private int id;
    private String subject;
    private Semester semester;

    public Subject(){

    }

    public Subject(int id, String subject, Semester semester) {
        this.id = id;
        this.subject = subject;
        this.semester=semester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
