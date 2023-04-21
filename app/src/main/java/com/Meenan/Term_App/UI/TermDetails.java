package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TermDetails extends AppCompatActivity {

    private TextView editName;
    private TextView editStart;
    private TextView editEnd;
    private String mTermName;
    private int termId;
    private String startDate;
    private String endDate;
    private Term mTerm;
    private Repository repository;
    Term currTerm;
    int numTerms;
    List<Term> allTerms;
    List<Term> filteredTerms;
    Button editTerm;
    FloatingActionButton addCourse;
    DatePickerDialog.OnDateSetListener startDateCal;
    DatePickerDialog.OnDateSetListener endDateCale;
    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editName = findViewById(R.id.termnamelabel);
        editStart = findViewById(R.id.termstartdate);
        editEnd = findViewById(R.id.termenddate);
        termId = getIntent().getIntExtra("termID", -1);
        mTermName = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");
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
                courseAdapter.setCourses(allTermCourses);
            } else {
                allTermCourses = null;
                courseAdapter.setCourses(allTermCourses);
            }
        }



            editTerm = findViewById(R.id.edittermbutton);
            editTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TermDetails.this, ViewTerm.class);
                    intent.putExtra("termId", termId);
                    intent.putExtra("termName", mTermName);
                    intent.putExtra("termStart", startDate);
                    intent.putExtra("termEnd", endDate);
                    startActivity(intent);
                }
            });

            addCourse = findViewById(R.id.addcoursefb);
            addCourse.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TermDetails.this, ViewCourses.class);
                    intent.putExtra("termID", termId);
                    intent.putExtra("termName", mTermName);
                    intent.putExtra("termStart", startDate);
                    intent.putExtra("termEnd", endDate);
                    startActivity(intent);
                }
            });


            /*String calFormat = "MM/dd/yyyy";
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
            };*/


    }

    /*private void updateLabelStart() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.US);
        //editStart.setText(sdf.format(calStart.getTime()));
    }*/

    protected void onResume() {
        super.onResume();

        Toast.makeText(TermDetails.this, "ON Resume called. TermID: " + Integer.toString(termId) +  mTermName, Toast.LENGTH_LONG).show();
        termId = getIntent().getIntExtra("termID", -1);
        mTermName = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");
        repository = new Repository(getApplication());

        if (termId != -1) {
            editName.setText(mTermName);
            editStart.setText(startDate);
            editEnd.setText(endDate);

            RecyclerView cRecyclerView = findViewById(R.id.assignedcourserecyclerview);
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            cRecyclerView.setAdapter(courseAdapter);
            cRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            courseAdapter.notifyDataSetChanged();


            List<Course> allTermCourses = new ArrayList<>();
            try {
                for (Course c : repository.getAllCourses())
                    if (c.getTermID_FK() == termId) allTermCourses.add(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (allTermCourses.size() != 0) {
                courseAdapter.setCourses(allTermCourses);
            } else {
                allTermCourses = null;
                courseAdapter.setCourses(allTermCourses);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_details_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.termback:
                Intent intent = new Intent(TermDetails.this, ViewTerm.class);
                startActivity(intent);
                return true;

            case R.id.deleteterm:
                List<Course> allTermCourses = new ArrayList<>();
                repository = new Repository(getApplication());
                Term curTerm = new Term();
                curTerm = repository.getTermById(termId);
                Toast.makeText(TermDetails.this, "Term ID: " + termId + " term Name: " + curTerm, Toast.LENGTH_LONG).show();
                /*try {
                    for (Course c : repository.getAllCourses())
                        if (c.getTermID_FK() == termId) allTermCourses.add(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (allTermCourses.size() != 0) {
                    Toast.makeText(TermDetails.this, "This will Delete all Courses associated with this term", Toast.LENGTH_LONG).show();
                    for (Course c : allTermCourses) {
                        try {
                            repository.delete(c);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        curTerm = repository.getTermById(termId);
                        try {
                            repository.delete(curTerm);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }*/
        }
        return super.onOptionsItemSelected(item);
    }
}