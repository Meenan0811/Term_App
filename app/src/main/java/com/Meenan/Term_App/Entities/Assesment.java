package com.Meenan.Term_App.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assesments")
public class Assesment {
    @PrimaryKey(autoGenerate = true)
    private int assesmentID;
    private String name;
    private String endDate;
    private String type;
    private int courseID_FK;



    public Assesment(String name, String endDate, String type, int courseID_FK) {
        this.name = name;
        this.endDate = endDate;
        this.type = type;
        this.courseID_FK = courseID_FK;
    }

    public Assesment(int assesmentId, String name, String endDate, String type, int courseID_FK) {
        this.assesmentID = assesmentId;
        this.name = name;
        this.endDate = endDate;
        this.type = type;
        this.courseID_FK = courseID_FK;
    }

    public Assesment() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCourseID_FK() {
        return courseID_FK;
    }

    public void setCourseID_FK(int courseID_FK) {
        this.courseID_FK = courseID_FK;
    }
    public int getAssesmentID() {
        return assesmentID;
    }

    public void setAssesmentID(int assesmentID) {
        this.assesmentID = assesmentID;
    }
}
