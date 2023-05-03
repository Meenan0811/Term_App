package com.Meenan.Term_App.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Courses")

public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String startDate;
    private String endDate;
    private String courseStatus;
    private String notes;
    private String instName;
    private String instEmail;
    private String instPhone;
    private int termID_FK;




    public Course(String courseName, String startDate, String endDate, String courseStatus, String notes, String instName, String instEmail, String instPhone, int termId_FK) {
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.notes = notes;
        this.instName = instName;
        this.instEmail = instEmail;
        this.instPhone = instPhone;
        this.termID_FK = termId_FK;
    }

    public Course(int courseID, String courseName, String startDate, String endDate, String courseStatus, String notes, String instName, String instEmail, String instPhone,  int termId_FK) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.notes = notes;
        this.instName = instName;
        this.instEmail = instEmail;
        this.instPhone = instPhone;
        this.termID_FK = termId_FK;
    }


    public Course() {}

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getTermID_FK() { return termID_FK; }

    public void setTermID_FK(int termId_FK) {
        this.termID_FK = termId_FK;
    }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public String getInstName() { return instName; }

    public void setInstName(String instName) { this.instName = instName; }

    public String getInstEmail() {
        return instEmail;
    }

    public void setInstEmail(String instEmail) {
        this.instEmail = instEmail;
    }

    public String getInstPhone() {
        return instPhone;
    }

    public void setInstPhone(String instPhone) {
        this.instPhone = instPhone;
    }
}
