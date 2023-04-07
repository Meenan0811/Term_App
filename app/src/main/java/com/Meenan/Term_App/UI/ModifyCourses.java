package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ModifyCourses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_courses);

        EditText editCourseName;
        EditText editCourseStart;
        EditText editCourseEnd;
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

        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());
        List<Course> allCourses = null;
        try {
            allCourses = repository.getAllCourses();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        courseAdapter.setTerms(allCourses);

    }

    public void updateCourse(int courseId) {

    }
}