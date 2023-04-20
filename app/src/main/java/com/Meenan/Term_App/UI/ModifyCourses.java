package com.Meenan.Term_App.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Mentor;
import com.Meenan.Term_App.Entities.Term;
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
    private int mentorId;
    private FloatingActionButton addAssesmentFb;
    private TextView mentorName;
    private Spinner courseStatusSpinner;
    private Spinner termSpinner;
    private Course mCourse;
    private Repository repository;
    private List<Mentor> allMentors;
    private Button saveCourseButton;
    private List<Term> allTerms = new ArrayList<>();
    private int maxCourseID;
    private Button addMentor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_courses);




        editCourseName = findViewById(R.id.editcoursename);
        editCourseStart = findViewById(R.id.editcoursestart);
        editCourseEnd = findViewById(R.id.editcourseend);
        courseStatusSpinner = findViewById(R.id.coursestatusspinner);
        mentorName = findViewById(R.id.mentorname);
        mentorEmail = findViewById(R.id.curinstructoremail);
        mentorPhone = findViewById(R.id.curinstructorphone);
        addAssesmentFb = findViewById(R.id.addassesmentfb);
        termSpinner = findViewById(R.id.termspinner);
        addMentor = findViewById(R.id.addmentorbutton);

        //Retrieve passed course information
        courseId = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStatus = getIntent().getStringExtra("status");
        courseStartDate = getIntent().getStringExtra("startDate");
        courseEndDate = getIntent().getStringExtra("endDate");


        //Populate Course Status Spinner
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.course_status, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(ad);


        //Populate Mentor Spinner - FIXME: Delete Me
        //repository = new Repository(getApplication());
        //List<String> mentorNamesList;
       /* try {

            allMentors = repository.getAllMentors();
            List<String> mentorNamesList = new ArrayList<>();
            for (Mentor m : allMentors) {
                String curMentor = m.getMentorName();
                mentorNamesList.add(curMentor);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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


        });*/



        //Populate Term Spinner
        repository = new Repository(getApplication());
        List<String> termList;
        try {
            allTerms = repository.getAllTerms();
            termList = new ArrayList<>();
            for (Term t : allTerms) {
                String curTerm = t.getTermName();
                termList.add(curTerm);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ArrayAdapter<CharSequence> termAd = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        termAd.addAll(termList);
        termSpinner.setAdapter(termAd);

        //List of All Mentors
        allMentors = new ArrayList<>();
        try {
            allMentors = repository.getAllMentors();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //Populate passed Course information if available
        if (courseId != -1) {
            editCourseName.setText(courseName);
            editCourseStart.setText(courseStartDate);
            editCourseEnd.setText(courseEndDate);
            int curposition = ad.getPosition(courseStatus);
            courseStatusSpinner.setSelection(curposition);
            populateMentor(courseId);
        }

        saveCourseButton = findViewById(R.id.addcoursebutton);
        saveCourseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (courseId == -1) {
                    int termID = 0;
                    try {
                        allTerms = repository.getAllTerms();
                        String termName = termSpinner.getSelectedItem().toString();
                        for (Term t : allTerms) {
                            String curTerm = t.getTermName();
                            Toast.makeText(ModifyCourses.this, "Course has been Added, Term Name: " + t.getTermName(), Toast.LENGTH_LONG).show();
                            if (termName.equals(curTerm)) {
                                termID = t.getTermID();
                                courseStatus = courseStatusSpinner.getSelectedItem().toString();
                                mCourse = new Course(editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), courseStatus, termID);
                                Toast.makeText(ModifyCourses.this, "Course has been Added, Term ID: " + termID, Toast.LENGTH_LONG).show();
                                try {
                                    repository.insert(mCourse);
                                    //FIXME: addCourseMentor(mentorNamesSpinner.getSelectedItem().toString(), courseId);
                                    Intent intent = new Intent(ModifyCourses.this, TermDetails.class);
                                    intent.putExtra("termId", termID);
                                    startActivity(intent);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    int termID = 0;
                    try {
                        allTerms = repository.getAllTerms();
                        String termName = termSpinner.getSelectedItem().toString();
                        for (Term t : allTerms) {
                            String curTerm = t.getTermName();
                            if (termName.equals(curTerm)) {
                                courseStatus = courseStatusSpinner.getSelectedItem().toString();
                                termID = t.getTermID();
                                mCourse = new Course(courseId, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), courseStatus, termID);
                                try {
                                    repository.update(mCourse);
                                    // FIXME: addCourseMentor(mentorNamesSpinner.getSelectedItem().toString(), courseId);
                                    Toast.makeText(ModifyCourses.this, "Course has been Updated", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ModifyCourses.this, ViewTerm.class);
                                    intent.putExtra("termId", termID);
                                    startActivity(intent);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        addMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyCourses.this, ViewMentors.class);
                intent.putExtra("courseID", courseId);
                intent.putExtra("mentorID", mentorId);
                startActivity(intent);
            }
        });

        addAssesmentFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyCourses.this, AddAssesment.class);
                intent.putExtra("Course_Id", courseId);
                startActivity(intent);
            }
        });


    }


    //Method to populate mentor email and phone fields with currently selected mentor
    public void populateMentor(int courseId) {
        for (Mentor m : allMentors) {
            if (courseId == m.getCourseID_FK()) {
                mentorName.setText(m.getMentorName());
                mentorEmail.setText(m.getEmail());
                mentorPhone.setText(m.getPhone());
                mentorId = m.getMentorID();
                break;
            }else {
                mentorName.setText("Add Mentor");
                mentorEmail.setText("Add Mentor Email");
                mentorPhone.setText("Add Mentor Phone");
            }
        }
    }

    // FIXME: delete Me
    /*public void addCourseMentor(String mentorName, int courseId) throws InterruptedException {
        for (Mentor m : allMentors) {
            if (mentorName.equals(m.getMentorName())) {
                if (courseId == m.getCourseID_FK() || courseId == -1) {
                    Mentor mentor = new Mentor(m.getMentorID(), mentorName, mentorEmail.getText().toString(), mentorPhone.getText().toString(), courseId);
                    repository.update(mentor);
                } else {
                    repository.delete(m);
                    Toast.makeText(ModifyCourses.this, "Deleted existing mentor", Toast.LENGTH_LONG).show();

                    Mentor mentor = new Mentor(mentorName, mentorEmail.getText().toString(), mentorPhone.getText().toString(), courseId);
                    repository.insert(mentor);

                }
            }
        }
    } */

    public boolean onCreateOptionsMenu(Menu menu) {
        if (courseId > 0) {
            getMenuInflater().inflate(R.menu.course_menu, menu);
        }else {
            getMenuInflater().inflate(R.menu.back_menu, menu);
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deletecourse:
                if (courseId > 0) {
                    try {
                        List<Course> courseList = repository.getAllCourses();
                        for (Course c : courseList) {
                            if (c.getCourseID() == courseId) {
                            repository.delete(c);
                            Toast.makeText(ModifyCourses.this, "Course " + c.getCourseName() + " deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            case R.id.setnotifcation:
                return true;

            case R.id.backtoterm:
                Intent intent = new Intent(ModifyCourses.this, ViewTerm.class);
                startActivity(intent);
                return true;

            case R.id.back:
                intent = new Intent(ModifyCourses.this, ViewTerm.class);
                startActivity(intent);
                return true;
        }
        return true;
    }

}