package com.Meenan.Term_App.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assesments",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseID",
                childColumns = "courseID_FK",
                onDelete = ForeignKey.CASCADE
        ))
public class Assesment {

    @PrimaryKey(autoGenerate = true)
    private int AssesmentId;
    private String Name;
    private String endDate;
    private String type;
    private int courseID_FK;

    public int getCourseID_FK() {
        return courseID_FK;
    }

    public void setCourseID_FK(int courseID_FK) {
        this.courseID_FK = courseID_FK;
    }

    public Assesment(String name, String endDate, String type, int courseID_FK) {
        Name = name;
        this.endDate = endDate;
        this.type = type;
        this.courseID_FK = courseID_FK;
    }

    public Assesment() {}

    public int getAssesmentId() {
        return AssesmentId;
    }

    public void setAssesmentId(int id) { AssesmentId = id; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
}
