package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.Meenan.Term_App.DAO.CourseDAO;
import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Database.TermCourseDataBase;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ModifyTerm extends AppCompatActivity {

    EditText editName;
    EditText editStart;
    EditText editEnd;
    String mTermName;
    int termId;
    String startDate;
    String endDate;
    Term mTerm;
    Repository repository;
    Term currTerm;
    int numTerms;
    List<Term> allTerms;
    List<Term> filteredTerms;
    Button saveTerm;
    FloatingActionButton addCourse;
    DatePickerDialog.OnDateSetListener startDateCal;
    DatePickerDialog.OnDateSetListener endDateCale;
    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_term);

        editName = findViewById(R.id.termNameEdit);
        editStart = findViewById(R.id.termStartEdit);
        editEnd = findViewById(R.id.termEndEdit);
        termId = getIntent().getIntExtra("termID", -1);
        mTermName = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        repository = new Repository(getApplication());


        if (termId != -1) {
            editName.setText(mTermName);
            editStart.setText(startDate);
            editEnd.setText(endDate);

            RecyclerView cRecyclerView = findViewById(R.id.assignedcourserecyclerview);
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            cRecyclerView.setAdapter(courseAdapter);
            cRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            List<Course> allTermCourses = new ArrayList<>();
            try {
                for (Course c : repository.getAllCourses())
                    if (c.getTermID_FK() == termId) allTermCourses.add(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (allTermCourses.size() != 0) {
                courseAdapter.setTerms(allTermCourses);
            } else {
                allTermCourses = null;
                courseAdapter.setTerms(allTermCourses);
            }
        }



            saveTerm = findViewById(R.id.savetermbutton);
            saveTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (termId == -1) {
                        mTerm = new Term(editStart.getText().toString(), editEnd.getText().toString(), editName.getText().toString());
                        try {
                            repository.insert(mTerm);
                            Toast.makeText(ModifyTerm.this, "Term has Been Saved", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            Toast.makeText(ModifyTerm.this, "Term has not been saved, please ensure all fields are filled out", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    } else {
                        mTerm = new Term(editStart.getText().toString(), editEnd.getText().toString(), editName.getText().toString());
                        try {
                            repository.update(mTerm);
                            Toast.makeText(ModifyTerm.this, "Term has Been Updated", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            Toast.makeText(ModifyTerm.this, "Term has not been updated, please ensure all fields are filled out", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            });

            addCourse = findViewById(R.id.addcoursefb);
            addCourse.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ModifyTerm.this, ModifyCourses.class);
                    startActivity(intent);
                }
            });

            String calFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(calFormat, Locale.US);
            editStart.setText(sdf.format(new Date()));
            editStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String initialDate = editStart.getText().toString();
                    if (initialDate.equals("")) initialDate = "01/01/1973";

                    try {
                        calStart.setTime(sdf.parse(initialDate));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    new DatePickerDialog(ModifyTerm.this, startDateCal, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();

                }
            });
            startDateCal = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calStart.set(Calendar.YEAR, year);
                    calStart.set(Calendar.MONTH, month);
                    calStart.set(Calendar.DAY_OF_MONTH, day);
                    updateLabelStart();
                }
            };


    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
        //editStart.setText(sdf.format(calStart.getTime()));
    }
}