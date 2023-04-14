package com.Meenan.Term_App.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "Mentors",
         foreignKeys = @ForeignKey(
                 entity = Course.class,
                 parentColumns = "courseID",
                 childColumns = "courseID_FK",
                 onDelete = ForeignKey.CASCADE
         ))
public class Mentor {

    @PrimaryKey (autoGenerate = true)
    private int mentorID;

    private String mentorName;
    private String phone;
    private String email;
    private int courseID_FK;

    public int getCourseID_FK() {
        return courseID_FK;
    }

    public void setCourseID_FK(int courseID_FK) {
        this.courseID_FK = courseID_FK;
    }

    public Mentor(String mentorName, String phone, String email, int courseID_FK) {
        this.mentorName = mentorName;
        this.phone = phone;
        this.email = email;
        this.courseID_FK = courseID_FK;
    }

    public Mentor(int mentorID, String mentorName, String phone, String email, int courseID_FK) {
        this.mentorID = mentorID;
        this.mentorName = mentorName;
        this.phone = phone;
        this.email = email;
        this.courseID_FK = courseID_FK;
    }

    public Mentor() {}

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
