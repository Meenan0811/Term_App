package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Mentor;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ModifyCourses extends AppCompatActivity {

    private EditText editCourseName;
    private EditText editCourseStart;
    private EditText editCourseEnd;
    private int courseId;
    private String courseName;
    private String courseStatus;
    private String courseStartDate;
    private String courseEndDate;
    private TextView mentorEmail;
    private TextView mentorPhone;
    private FloatingActionButton addMentorFb;
    private Spinner mentorNamesSpinner;
    private Spinner courseStatusSpinner;
    Course course;
    private Repository repository;
    private List<Mentor> allMentors = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_courses);




        editCourseName = findViewById(R.id.editcoursename);
        editCourseStart = findViewById(R.id.editcoursestart);
        editCourseEnd = findViewById(R.id.editcourseend);
        courseStatusSpinner = findViewById(R.id.coursestatusspinner);
        mentorNamesSpinner = findViewById(R.id.mentornamespinner);
        mentorEmail = findViewById(R.id.curinstructoremail);
        mentorPhone = findViewById(R.id.curinstructorphone);
        addMentorFb = findViewById(R.id.editmentorfb);

        //Retrieve passed course information
        courseId = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStatus = getIntent().getStringExtra("status");
        courseStartDate = getIntent().getStringExtra("startDate");
        courseEndDate = getIntent().getStringExtra("endDate");


        //Populate Course Status Spinner
        ArrayList<String> courseArrayList = new ArrayList<>();
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.course_status, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(ad);

        //Populate Mentor Spinner
        repository = new Repository(getApplication());
        List<String> mentorNamesList;
        try {
            allMentors = repository.getAllMentors();
            mentorNamesList = new ArrayList<>();
            for (Mentor m : allMentors) {
                String curMentor = m.getMentorName();
                mentorNamesList.add(curMentor);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ArrayAdapter<CharSequence> mentorAd = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        mentorAd.addAll(mentorNamesList);
        mentorNamesSpinner.setAdapter(mentorAd);
        mentorNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String curMentor = mentorNamesSpinner.getSelectedItem().toString();
                populateMentor(curMentor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Populate passed Course information if available
        if (courseId != -1) {
            editCourseName.setText(courseName);
            editCourseStart.setText(courseStartDate);
            editCourseEnd.setText(courseEndDate);
            int curposition = ad.getPosition(courseStatus);
            courseStatusSpinner.setSelection(curposition);
        }


    }

    public void updateCourse(int courseId) {

    }

    public void addCourse() {

    }


    //Method to populate mentor email and phone fields with currently selected mentor
    public void populateMentor(String mentorName) {
        for (Mentor m : allMentors) {
            if (mentorName.equals(m.getMentorName())) {
                mentorEmail.setText(m.getEmail());
                mentorPhone.setText(m.getPhone());
            }
        }
    }
}