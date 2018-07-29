package com.example.wanjukim.homeworkmonster.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Wanju Kim on 2018-01-13.
 */

public class Subject extends RealmObject {
    @PrimaryKey
    private String id;
    private String subject;
    private Semester semester;
    private String professor;
    private String classroom;
    private String memo;

    public Subject(){

    }

    public Subject(String id, String subject, Semester semester, String professor, String classroom, String memo) {
        this.id = id;
        this.subject = subject;
        this.semester = semester;
        this.professor = professor;
        this.classroom = classroom;
        this.memo = memo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /* Necessary : spinner shows this as an item */
    @Override
    public String toString() {
        return getSubject();
    }
}
