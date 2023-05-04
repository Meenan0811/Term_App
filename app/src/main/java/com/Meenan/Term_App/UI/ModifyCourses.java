package com.Meenan.Term_App.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Assesment;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class ModifyCourses extends AppCompatActivity {

    private EditText editCourseName;
    private EditText editCourseStart;
    private EditText editCourseEnd;
    private EditText editCourseNotes;
    private int courseId;
    private String courseName;
    private String courseStatus;
    private String courseStartDate;
    private String courseEndDate;
    private String courseNotes;
    private String instName;
    private String instEmail;
    private String instPhone;
    private int courseTermId;
    private EditText mentorEmail;
    private EditText mentorPhone;
    private FloatingActionButton addAssesmentFb;
    private EditText mentorName;
    private Spinner courseStatusSpinner;
    private Course mCourse;
    private Repository repository;
    private Button saveCourseButton;
    private List<Term> allTerms = new ArrayList<>();
    private int termId;
    private String termName;
    private String termStart;
    private String termEnd;
    private Spinner courseAssSpinner;
    private DateTimeFormatter formatter;
    private String currTime;
    private String endTime;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    final Calendar calStart = Calendar.getInstance(Locale.US);
    final Calendar calEnd = Calendar.getInstance(Locale.US);
    private SimpleDateFormat sFormatter = new SimpleDateFormat("MM/dd/yy");
    private List<String> assString = new ArrayList<>();
    private List<Assesment> allAssesmentList;
    private List<Course> allCourses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_courses);


        editCourseName = findViewById(R.id.editcoursename);
        editCourseStart = findViewById(R.id.editcoursestart);
        editCourseEnd = findViewById(R.id.editcourseend);
        editCourseNotes = findViewById(R.id.editcoursenotes);
        courseStatusSpinner = findViewById(R.id.coursestatusspinner);
        mentorName = findViewById(R.id.mentorname);
        mentorEmail = findViewById(R.id.curinstructoremail);
        mentorPhone = findViewById(R.id.curinstructorphone);
        addAssesmentFb = findViewById(R.id.addassesmentfb);
        courseAssSpinner = findViewById(R.id.assignedassesment);

        //Retrieve passed course information
        courseId = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStatus = getIntent().getStringExtra("status");
        courseStartDate = getIntent().getStringExtra("startDate");
        courseEndDate = getIntent().getStringExtra("endDate");
        courseNotes = getIntent().getStringExtra("courseNotes");
        courseTermId = getIntent().getIntExtra("course_termID", -1);
        instName = getIntent().getStringExtra("instName");
        instEmail = getIntent().getStringExtra("instEmail");
        instPhone = getIntent().getStringExtra("instPhone");

        //Retrieve passed Term information
        termId = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        termStart = getIntent().getStringExtra("termStart");
        termEnd = getIntent().getStringExtra("termEnd");

        //Retrieve Local Time and create formatter
        formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        currTime = LocalDate.now().format(formatter);
        endTime = LocalDate.now().plusMonths(1).format(formatter);

        //Populate Course Status Spinner
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.course_status, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(ad);

        repository = new Repository(getApplication());

        //List of All Assesments
        allAssesmentList = new ArrayList<>();
        try {
            allAssesmentList = repository.getAllAssesments();
        } catch (InterruptedException | NullPointerException e) {
            throw new RuntimeException(e);
        }


        //Populate passed Course information if available
        if (courseId != -1) {
            editCourseName.setText(courseName);
            editCourseStart.setText(courseStartDate);
            editCourseEnd.setText(courseEndDate);
            editCourseNotes.setText(courseNotes);
            int curposition = ad.getPosition(courseStatus);
            courseStatusSpinner.setSelection(curposition);
            mentorName.setText(instName);
            mentorEmail.setText(instEmail);
            mentorPhone.setText(instPhone);
            addAssesment(courseId);
        } else {
            editCourseStart.setText(currTime);
            editCourseEnd.setText(endTime);
        }


        saveCourseButton = findViewById(R.id.addcoursebutton);
        saveCourseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int curTerm = 0;
                try {
                    allTerms = repository.getAllTerms();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //Add new Course Object if no Course ID is not passed from Term Screen
                if (courseId == -1) {
                    courseStatus = courseStatusSpinner.getSelectedItem().toString();
                    mCourse = new Course(editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), courseStatus, editCourseNotes.getText().toString(), mentorName.getText().toString(), mentorEmail.getText().toString(), mentorPhone.getText().toString(),  termId);
                    try {
                        repository.insert(mCourse);
                        Toast.makeText(ModifyCourses.this, "Course has been Added", Toast.LENGTH_LONG).show();
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
                // If Course ID is passed from previous screen update existing course
                else {
                    for (Term t : allTerms) {
                        if (courseTermId == t.getTermID()) {
                            curTerm = t.getTermID();
                        }
                        if (courseTermId == curTerm) {
                            //if (courseId != -1) {
                            courseStatus = courseStatusSpinner.getSelectedItem().toString();
                            termId = t.getTermID();
                            termName = t.getTermName();
                            termStart = t.getStartDate();
                            termEnd = t.getEndDate();
                            mCourse = new Course(courseId, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), courseStatus, editCourseNotes.getText().toString(),mentorName.getText().toString(), mentorEmail.getText().toString(), mentorPhone.getText().toString(), termId);
                            try {
                                repository.update(mCourse);
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
                }
            }
        });


        addAssesmentFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository = new Repository(getApplication());

                if (courseId != -1) {
                    if (courseAssSpinner.getSelectedItem() != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyCourses.this);
                        AlertDialog alertDialog = null;
                        builder.setMessage("Would you like to edit the selected Assessment or Add a new one?");
                        builder.setTitle("Assessment");
                        builder.setPositiveButton("Edit Selected", (DialogInterface.OnClickListener) (dialog, which) -> {
                            String curAss = courseAssSpinner.getSelectedItem().toString();
                            String assTitle = "";
                            String assStart = "";
                            String assEnd = "";
                            int assId = 0;
                            for (Assesment a : allAssesmentList) {
                                if (a.getName().equals(curAss)) {
                                    assId = a.getAssesmentID();
                                    assTitle = a.getName();
                                    assStart = a.getStartDate();
                                    assEnd = a.getEndDate();
                                }
                            }
                            Intent intent = new Intent(ModifyCourses.this, AddAssesment.class);
                            intent.putExtra("courseID", courseId);
                            intent.putExtra("termID", courseTermId);
                            intent.putExtra("assTitle" , assTitle);
                            intent.putExtra("assStart", assStart);
                            intent.putExtra("assEnd", assEnd);
                            intent.putExtra("assID", assId);
                            Toast.makeText(ModifyCourses.this, "Ass ID: " + assId + " Ass Name: " + assTitle, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        });
                        builder.setNeutralButton("Add New", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Intent intent = new Intent(ModifyCourses.this, AddAssesment.class);
                            intent.putExtra("courseID", courseId);
                            intent.putExtra("termID", courseTermId);
                            startActivity(intent);
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        Intent intent = new Intent(ModifyCourses.this, AddAssesment.class);
                        intent.putExtra("courseID", courseId);
                        intent.putExtra("termID", courseTermId);
                        startActivity(intent);
                    }
                }else { Toast.makeText(ModifyCourses.this, "Please Save Course before Adding Assessments", Toast.LENGTH_LONG).show(); }
            }
        });



        //Set DatePickers
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String sd = editCourseStart.getText().toString();
                if (!sd.equals("01/01/2023") | !sd.equals("01/01/23")) sd = currTime;
                try {
                    calStart.setTime(sFormatter.parse(sd));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(ModifyCourses.this, startDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dom) {
                calStart.set(Calendar.YEAR, year);
                calStart.set(Calendar.MONTH, month);
                calStart.set(Calendar.DAY_OF_MONTH, dom);

                updateCal(editCourseStart, calStart);
            }
        };

        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String sd = editCourseEnd.getText().toString();
                if (!sd.equals("01/01/2023") | !sd.equals("01/01/23")) sd = endTime;
                try {
                    calEnd.setTime(sFormatter.parse(sd));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(ModifyCourses.this, endDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dom) {
                calEnd.set(Calendar.YEAR, year);
                calEnd.set(Calendar.MONTH, month);
                calEnd.set(Calendar.DAY_OF_MONTH, dom);

                updateCal(editCourseEnd, calEnd);
            }
        };
    }

    public void updateCal(EditText et, Calendar cal) {
        String format = "MM/dd/yy";
        SimpleDateFormat sFormat = new SimpleDateFormat(format, Locale.US);
        et.setText(sFormat.format(cal.getTime()));
    }


    //Searches for any Assessments matching course ID and populates assessment label
    public void addAssesment(int courseId) {
        for (Assesment a : allAssesmentList) {
            if (a.getCourseID_FK() == courseId) {
                assString.add(a.getName());
            }
        }
        ArrayAdapter<CharSequence> assAd = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        assAd.addAll(assString);
        courseAssSpinner.setAdapter(assAd);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.course_menu, menu);
         /*else {
            getMenuInflater().inflate(R.menu.back_menu, menu);
        }*/
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.deletecourse:
                allTerms = new ArrayList<>();
                try {
                    allTerms = repository.getAllTerms();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (Term t : allTerms) {
                    if (t.getTermID() == courseTermId) {
                        termId = t.getTermID();
                        termName = t.getTermName();
                        termStart = t.getStartDate();
                        termEnd = t.getEndDate();
                    }
                }

                if (courseId > 0) {
                    try {
                        List<Course> courseList = repository.getAllCourses();

                        for (Course c : courseList) {
                            if (c.getCourseID() == courseId) {
                                repository.delete(c);
                                Toast.makeText(ModifyCourses.this, "Course " + c.getCourseName() + " deleted", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ModifyCourses.this, TermDetails.class);
                                intent.putExtra("termID", termId);
                                intent.putExtra("termName", termName);
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
            case R.id.setstartnotifcation:
                if (courseId > 0) {
                    String startDate = editCourseStart.getText().toString();
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Date date = null;

                /*try {
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } */

                    try {
                        date = sdf.parse(startDate);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Long trigger = date.getTime();
                    Intent intent = new Intent(ModifyCourses.this, CourseReceiver.class);
                    intent.putExtra("courseNotification", editCourseName.getText().toString() + " Begins Today, " + startDate);
                    PendingIntent sender = PendingIntent.getBroadcast(ModifyCourses.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(ModifyCourses.this, "Notification set", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ModifyCourses.this, "Please save course before setting notifications", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.setendnotifcation:
                if (courseId > 0) {
                    String endDate = editCourseEnd.getText().toString();
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Date date = null;

                /*try {
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
                            mCourse = new Course(courseId, editCourseName.getText().toString(), startDate, editCourseEnd.getText().toString(), courseStatus, termId);
                            try {
                                repository.update(mCourse);
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
                    e.printStackTrace();
                } */

                    try {
                        date = sdf.parse(endDate);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Long trigger = date.getTime();
                    Intent intent = new Intent(ModifyCourses.this, CourseReceiver.class);
                    intent.putExtra("courseNotification", editCourseName.getText().toString() + " Ends Today, " + endDate);
                    PendingIntent sender = PendingIntent.getBroadcast(ModifyCourses.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(ModifyCourses.this, "Notification set", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ModifyCourses.this, "Please save course before setting notifications", Toast.LENGTH_LONG).show();
                }
                return true;


                case R.id.backtoterm:
                    allTerms = new ArrayList<>();
                    try {
                        allTerms = repository.getAllTerms();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    for (Term t : allTerms) {
                        if (t.getTermID() == courseTermId) {
                            termId = t.getTermID();
                            termName = t.getTermName();
                            termStart = t.getStartDate();
                            termEnd = t.getEndDate();
                        }
                    }

                    Intent intent = new Intent(ModifyCourses.this, TermDetails.class);
                    intent.putExtra("termID", termId);
                    intent.putExtra("termName", termName);
                    intent.putExtra("termStart", termStart);
                    intent.putExtra("termEnd", termEnd);
                    startActivity(intent);
                    return true;

                    case R.id.back:
                        intent = new Intent(ModifyCourses.this, ViewTerm.class);
                        startActivity(intent);
                        return true;
                }
                return super.onOptionsItemSelected(item);
        }

        protected void onResume() {
            super.onResume();

    }
}

