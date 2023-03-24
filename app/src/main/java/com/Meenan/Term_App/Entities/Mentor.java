package com.Meenan.Term_App.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Mentors")
public class Mentor {

    @PrimaryKey (autoGenerate = true)
    private int mentorID;

    private String mentorName;
    private String phone;
    private String email;

    public Mentor(String mentorName, String phone, String email) {
        this.mentorName = mentorName;
        this.phone = phone;
        this.email = email;
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
