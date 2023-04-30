package com.Meenan.Term_App.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.Meenan.Term_App.Entities.Assesment;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Mentor;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private Button addMentor;
    private int termId;
    private String termName;
    private String termStart;
    private String termEnd;
    private String assesment;
    private TextView assTextView;
    private List<Assesment> allAssesmentList = new ArrayList<>();
    private List<Course> allCourses = new ArrayList<>();


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
        assTextView = findViewById(R.id.assignedassesment);

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


        //Populate Term Spinner FIXME: Delete if not necessary
        repository = new Repository(getApplication());
        List<String> termList;
        try {
            allTerms = repository.getAllTerms();
            allCourses = repository.getAllCourses();
            termList = new ArrayList<>();

            for (Term t : allTerms) {
                String curTerm = t.getTermName();
                termList.add(curTerm);
                for (Course c : allCourses) {
                    if (c.getTermID_FK() == t.getTermID()) termId = t.getTermID();
                    termName = t.getTermName();
                    termStart = t.getStartDate();
                    termEnd = t.getEndDate();
                }
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

        //List of All Assesments
        try {
            allAssesmentList = repository.getAllAssesments();
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
            addAssesment(courseId);

        }


        saveCourseButton = findViewById(R.id.addcoursebutton);
        saveCourseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    allTerms = repository.getAllTerms();
                    String termName = termSpinner.getSelectedItem().toString();
                    for (Term t : allTerms) {
                        String curTerm = t.getTermName();
                        if (termName.equals(curTerm)) {
                            courseStatus = courseStatusSpinner.getSelectedItem().toString();
                            termId = t.getTermID();
                            termName = t.getTermName();
                            termStart = t.getStartDate();
                            termEnd = t.getEndDate();
                            mCourse = new Course(courseId, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), courseStatus, termId);
                            try {
                                repository.update(mCourse);
                                // FIXME: addCourseMentor(mentorNamesSpinner.getSelectedItem().toString(), courseId);
                                Toast.makeText(ModifyCourses.this, "Course has been Updated", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ModifyCourses.this, TermDetails.class);
                                intent.putExtra("termID", termId);
                                intent.putExtra("termName", termName);
                                intent.putExtra("termStart", termStart);
                                intent.putExtra("termEnd", termEnd);
                                startActivity(intent);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                    //}
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
                intent.putExtra("courseID", courseId);
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
            } else {
                mentorName.setText("Add Mentor");
                mentorEmail.setText("Add Mentor Email");
                mentorPhone.setText("Add Mentor Phone");
                mentorId = -1;
            }
        }
    }

    //Searches for any Assessments matching course ID and populates assessment label
    public void addAssesment(int courseId) {
        int i = 0;
        for (Assesment a : allAssesmentList) {
            ++i;
            Toast.makeText(ModifyCourses.this, "Loop Name: " + a.getName() + "Loop #: " + i, Toast.LENGTH_LONG).show();
            if (a.getCourseID_FK() == courseId) {
                assTextView.setText(a.getName());
                Toast.makeText(ModifyCourses.this, "Assessment Name: " + a.getName(), Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (courseId > 0) {
            getMenuInflater().inflate(R.menu.course_menu, menu);
        } else {
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
                        List<Mentor> mentorList = repository.getAllMentors();
                        termId = getIntent().getIntExtra("termID", -1);
                        termName = getIntent().getStringExtra("termName");
                        termStart = getIntent().getStringExtra("termStart");
                        termEnd = getIntent().getStringExtra("termEnd");
                        for (Course c : courseList) {
                            if (c.getCourseID() == courseId) {
                                repository.delete(c);
                                for (Mentor m : mentorList)
                                    if (m.getCourseID_FK() == courseId) repository.delete(m);
                                Toast.makeText(ModifyCourses.this, "Course " + c.getCourseName() + " deleted", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ModifyCourses.this, TermDetails.class);
                                intent.putExtra("termID", termId);
                                intent.putExtra("termName", termStart);
                                intent.putExtra("termStart", termStart);
                                intent.putExtra("termEnd", termEnd);
                                startActivity(intent);
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            case R.id.setnotifcation:
                String startDate = editCourseStart.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date date = null;

                try {
                    allTerms = repository.getAllTerms();
                    String termName = termSpinner.getSelectedItem().toString();
                    for (Term t : allTerms) {
                        String curTerm = t.getTermName();
                        if (termName.equals(curTerm)) {
                            courseStatus = courseStatusSpinner.getSelectedItem().toString();
                            termId = t.getTermID();
                            termName = t.getTermName();
                            termStart = t.getStartDate();
                            termEnd = t.getEndDate();

                            date = sdf.parse(startDate);

                            mCourse = new Course(courseId, editCourseName.getText().toString(), startDate, editCourseEnd.getText().toString(), courseStatus, termId);
                            try {
                                repository.update(mCourse);
                                // FIXME: addCourseMentor(mentorNamesSpinner.getSelectedItem().toString(), courseId);
                                Toast.makeText(ModifyCourses.this, "Course has been Updated", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ModifyCourses.this, TermDetails.class);
                                intent.putExtra("termID", termId);
                                intent.putExtra("termName", termName);
                                intent.putExtra("termStart", termStart);
                                intent.putExtra("termEnd", termEnd);
                                startActivity(intent);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (InterruptedException | ParseException e) {
                    e.printStackTrace();
                }

                Long trigger = date.getTime();
                Intent intent = new Intent(ModifyCourses.this, CourseReceiver.class);
                intent.putExtra("key", startDate + "Course Start Date is Today");
                PendingIntent sender = PendingIntent.getBroadcast(ModifyCourses.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);


                    return true;

                    case R.id.backtoterm:
                        Toast.makeText(ModifyCourses.this, "Back To Term ID: " + termId, Toast.LENGTH_LONG).show();

                        intent = new Intent(ModifyCourses.this, TermDetails.class);
                        intent.putExtra("termID", termId);
                        intent.putExtra("termName", termName);
                        intent.putExtra("termStart", termStart);
                        intent.putExtra("termEnd", termEnd);
                        startActivity(intent);
                        return true;
                }
            /*case R.id.back:
                intent = new Intent(ModifyCourses.this, ViewTerm.class);
                startActivity(intent);
                return true;*/

                return true;
        }

    }

