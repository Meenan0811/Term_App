package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ModifyCourses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_courses);

        EditText editCourseName;
        EditText editCourseStart;
        EditText editCourseEnd;
        int courseId;
        String courseName;
        String courseStatus;
        String courseStartDate;
        String courseEndDate;
        String mentorName;
        String mentorPhone;
        String mentorEmail;
        TextView curMentorEmail;
        TextView curMentorPhone;
        FloatingActionButton addMentorFb;
        Spinner mentorNamesSpinner;
        Spinner courseStatusSpinner;
        Course course;
        Repository repository;
        



        editCourseName = findViewById(R.id.editcoursename);
        editCourseStart = findViewById(R.id.editcoursestart);
        editCourseEnd = findViewById(R.id.editcourseend);
        courseStatusSpinner = findViewById(R.id.coursestatusspinner);
        mentorNamesSpinner = findViewById(R.id.mentornamespinner);
        curMentorEmail = findViewById(R.id.curinstructoremail);
        curMentorPhone = findViewById(R.id.curinstructorphone);
        addMentorFb = findViewById(R.id.editmentorfb);

        courseId = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStatus = getIntent().getStringExtra("status");
        courseStartDate = getIntent().getStringExtra("startDate");
        courseEndDate = getIntent().getStringExtra("endDate");

        //Populate Spinner
        ArrayList<String> courseArrayList = new ArrayList<>();
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.course_status, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(ad);




        if (courseId != -1) {
            editCourseName.setText(courseName);
            editCourseStart.setText(courseStartDate);
            editCourseEnd.setText(courseEndDate);
            //FIXME: add spinner information for course Status
        }


    }

    public void updateCourse(int courseId) {


    }
}