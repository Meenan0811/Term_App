package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Assesment;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAssesment extends AppCompatActivity {

    private Intent intent;
    private EditText editTitle;
    private Spinner assTypeSpinner;
    private EditText editStartDate;
    private EditText editEndDate;
    private TextView assLabel;
    private int courseId;
    private int assId;
    private String assTitle;
    private String assType;
    private String assStart;
    private String assEnd;
    private Button saveButton;
    private Assesment mAss;
    private List<Course> allCourse = new ArrayList<>();
    private List<Term> allTerms = new ArrayList<>();
    Repository repository;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseNotes;
    private String instName;
    private String instEmail;
    private String instPhone;
    private int courseTerm;
    private int termId;
    private String termName;
    private String termStart;
    private String termEnd;
    private DateTimeFormatter formatter;
    private String currTime;
    private String endTime;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    final Calendar calStart = Calendar.getInstance(Locale.US);
    final Calendar calEnd = Calendar.getInstance(Locale.US);
    private SimpleDateFormat sFormatter = new SimpleDateFormat("MM/dd/yy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assesment);

        //Assign local variables to activity ID
        editTitle = findViewById(R.id.editassesmenttitle);
        editEndDate = findViewById(R.id.editassesmentend);
        assLabel = findViewById(R.id.assesmentlabel);
        saveButton = findViewById(R.id.saveassesmentbutton);
        editStartDate = findViewById(R.id.assstartedit);
        assTypeSpinner = findViewById(R.id.asstypespinner);

        //Retrieve Today's Date and 1 month out using format MM/dd/yy
        formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        currTime = LocalDate.now().format(formatter);
        endTime = LocalDate.now().plusMonths(6).format(formatter);

        //Assign data passed from previoous screen to local variables
        courseId = getIntent().getIntExtra("courseID", -1);
        termId = getIntent().getIntExtra("termID", -1);
        assTitle = getIntent().getStringExtra("assTitle");
        assEnd = getIntent().getStringExtra("assEnd");
        assId = getIntent().getIntExtra("assID", -1);
        assStart = getIntent().getStringExtra("assStart");
        assType = getIntent().getStringExtra("assType");

        //Populate Assessment type spinner
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this, R.array.ass_spinner, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        ad.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        assTypeSpinner.setAdapter(ad);

        Toast.makeText(this, "Ass ID: " + assId + " Ass Name: " + assTitle, Toast.LENGTH_LONG).show();

        if (assId != -1) {
            editTitle.setText(assTitle);
            editEndDate.setText(assEnd);
            editStartDate.setText(assStart);
            assLabel.setText("Edit Assessment");
        } else { assLabel.setText("Add Assessment"); }

        try {
            repository = new Repository(getApplication());
            allCourse = repository.getAllCourses();
            allTerms = repository.getAllTerms();
            for (Course c : allCourse) {
                if (courseId == c.getCourseID()) {
                    courseName = c.getCourseName();
                    courseStart = c.getStartDate();
                    courseEnd = c.getEndDate();
                    courseStatus = c.getCourseStatus();
                    courseTerm = c.getTermID_FK();
                    courseNotes = c.getNotes();
                    instName = c.getInstName();
                    instEmail = c.getInstEmail();
                    instPhone = c.getInstPhone();
                }
            }
            for (Term t : allTerms) {
                if (termId == t.getTermID()) {
                    termName = t.getTermName();
                    termEnd = t.getEndDate();
                    termStart = t.getStartDate();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AddAssesment.this, ModifyCourses.class);
                intent.putExtra("courseID", courseId);
                intent.putExtra("courseName", courseName);
                intent.putExtra("status", courseStatus);
                intent.putExtra("startDate", courseStart);
                intent.putExtra("endDate", courseEnd);
                intent.putExtra("course_termID", courseTerm);
                intent.putExtra("courseNotes", courseNotes);
                intent.putExtra("instName", instName);
                intent.putExtra("instEmail", instEmail);
                intent.putExtra("instPhone", instPhone);
                intent.putExtra("termID", termId);
                intent.putExtra("termName", termName);
                intent.putExtra("termStart", termStart);
                intent.putExtra("termEnd", termEnd);

                Repository repository = new Repository(getApplication());
                assTitle = editTitle.getText().toString();
                assEnd = editEndDate.getText().toString();
                assStart = editStartDate.getText().toString();
                assType = assTypeSpinner.getSelectedItem().toString();
                if (assId != -1) {
                    mAss = new Assesment(assId, assTitle, assStart,  assEnd,assType, courseId);
                    try {
                        repository.update(mAss);
                        Toast.makeText(AddAssesment.this, "Assessment updated successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    mAss = new Assesment(assTitle, assStart, assEnd, assType, courseId);
                    try {
                        repository.insert(mAss);
                        Toast.makeText(AddAssesment.this, "Assessment added successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String sd = editStartDate.getText().toString();
                if (sd.equals("")) sd = currTime;
                try {
                    calStart.setTime(sFormatter.parse(sd));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AddAssesment.this, startDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dom) {
                calStart.set(Calendar.YEAR, year);
                calStart.set(Calendar.MONTH, month);
                calStart.set(Calendar.DAY_OF_MONTH, dom);

                updateCal(editStartDate, calStart);
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String sd = editEndDate.getText().toString();
                if (sd.equals("")) sd = endTime;
                try {
                    calEnd.setTime(sFormatter.parse(sd));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AddAssesment.this, endDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dom) {
                calEnd.set(Calendar.YEAR, year);
                calEnd.set(Calendar.MONTH, month);
                calEnd.set(Calendar.DAY_OF_MONTH, dom);

                updateCal(editEndDate, calEnd);
            }
        };
    }

    public void updateCal(EditText et, Calendar cal) {
        String format = "MM/dd/yy";
        SimpleDateFormat sFormat = new SimpleDateFormat(format, Locale.US);
        et.setText(sFormat.format(cal.getTime()));
    }



    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.assesment_menu, menu);
            return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.assback:
                intent = new Intent(AddAssesment.this, ModifyCourses.class);
                intent.putExtra("courseID", courseId);
                intent.putExtra("courseName", courseName);
                intent.putExtra("status", courseStatus);
                intent.putExtra("startDate", courseStart);
                intent.putExtra("endDate", courseEnd);
                intent.putExtra("course_termID", courseTerm);
                intent.putExtra("courseNotes", courseNotes);
                intent.putExtra("instName", instName);
                intent.putExtra("instEmail", instEmail);
                intent.putExtra("instPhone", instPhone);
                intent.putExtra("termID", termId);
                intent.putExtra("termName", termName);
                intent.putExtra("termStart", termStart);
                intent.putExtra("termEnd", termEnd);
                Toast.makeText(AddAssesment.this, "Term Name: " + termName + " Term ID: " + termId, Toast.LENGTH_LONG).show();
                startActivity(intent);
                return true;

            case R.id.assstartnotifcation:

                Intent intent = new Intent(AddAssesment.this, CourseReceiver.class);
                if (assId > 0) {
                    String startDate = editStartDate.getText().toString();
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Date date = null;
                    Long trigger = date.getTime();
                    try {
                        date = sdf.parse(startDate);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    intent.putExtra("courseNotification", editTitle.getText().toString() + " Begins Today, " + startDate);
                    PendingIntent sender = PendingIntent.getBroadcast(AddAssesment.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(AddAssesment.this, "Notification set", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.assendnotifcation:

                if (assId > 0) {
                    String endDate = editEndDate.getText().toString();
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Date date;
                    try {
                        date = sdf.parse(endDate);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Long trigger = date.getTime();
                    intent = new Intent(AddAssesment.this, CourseReceiver.class);
                    intent.putExtra("courseNotification", editTitle.getText().toString() + " Ends Today, " + endDate);
                    PendingIntent sender = PendingIntent.getBroadcast(AddAssesment.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                    Toast.makeText(AddAssesment.this, "Notification set", Toast.LENGTH_LONG).show();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}

