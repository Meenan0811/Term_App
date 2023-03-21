package com.Meenan.Term_App.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Term")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String startDate;
    private String endDate;
    private String termName;

    public Term(String startDate, String endDate, String termName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.termName = termName;
    }

    public Term() {}

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
