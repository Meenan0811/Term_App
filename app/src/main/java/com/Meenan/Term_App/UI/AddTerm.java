package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
    private EditText termName;
    private EditText termStart;
    private EditText termEnd;
    private String tName;
    private String tStart;
    private String tEnd;
    private int tId;
    private Term mTerm;
    private Button saveTermButton;
    private Repository repository;
    private Intent intent;
    private String currTime;
    private String endTime;
    private DateTimeFormatter formatter;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    final Calendar calStart = Calendar.getInstance(Locale.US);
    final Calendar calEnd = Calendar.getInstance(Locale.US);
    private SimpleDateFormat sFormatter = new SimpleDateFormat("MM/dd/yy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        //Retrieve passed information
        intent = new Intent();
        tName = getIntent().getStringExtra("termName");
        tStart = getIntent().getStringExtra("termStart");
        tEnd = getIntent().getStringExtra("termEnd");
        tId = getIntent().getIntExtra("termID", -1);


        //Assign variables to activity Id's
        termName = findViewById(R.id.termNameEdit);
        termStart = findViewById(R.id.termStartEdit);
        termEnd = findViewById(R.id.termEndEdit);
        repository = new Repository(getApplication());

        //Retrieve Today's Date and 1 month out using format MM/dd/yy
        formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        currTime = LocalDate.now().format(formatter);
        endTime = LocalDate.now().plusMonths(6).format(formatter);


        //Set EditText fields with pass Term data
        if (tId != -1) {
            termName.setText(tName);
            termStart.setText(tStart);
            termEnd.setText(tEnd);
        }else {
            termStart.setText(currTime);
            termEnd.setText(endTime);
        }

        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String sd = termStart.getText().toString();
                if (!sd.equals("01/01/2023") | !sd.equals("01/01/23")) sd = currTime;
                try {
                    calStart.setTime(sFormatter.parse(sd));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AddTerm.this, startDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dom) {
                calStart.set(Calendar.YEAR, year);
                calStart.set(Calendar.MONTH, month);
                calStart.set(Calendar.DAY_OF_MONTH, dom);

                updateCal(termStart, calStart);
            }
        };

        termEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String sd = termEnd.getText().toString();
                if (!sd.equals("01/01/2023") | !sd.equals("01/01/23")) sd = endTime;
                try {
                    calEnd.setTime(sFormatter.parse(sd));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AddTerm.this, endDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dom) {
                calEnd.set(Calendar.YEAR, year);
                calEnd.set(Calendar.MONTH, month);
                calEnd.set(Calendar.DAY_OF_MONTH, dom);

                updateCal(termEnd, calEnd);
            }
        };

        saveTermButton = findViewById(R.id.savetermbutton);
        saveTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = termName.getText().toString();
                String start = termStart.getText().toString();
                String end = termEnd.getText().toString();
                Toast.makeText(AddTerm.this, "Term Save Button Clicked", Toast.LENGTH_LONG).show();
                if (start.length() == 8 && end.length() == 8 && !name.isEmpty()) {
                    if (tId != -1) {
                        mTerm = new Term(tId, termStart.getText().toString(), termEnd.getText().toString(), termName.getText().toString());
                        try {
                            repository.update(mTerm);
                            Toast.makeText(AddTerm.this, "Term Saved", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Toast.makeText(AddTerm.this, "Unable to Save Term, please ensure all fields are filled out correctly", Toast.LENGTH_LONG).show();
                        }
                        intent = new Intent(AddTerm.this, ViewTerm.class);
                        startActivity(intent);
                    }else {
                        mTerm = new Term(termStart.getText().toString(), termEnd.getText().toString(), termName.getText().toString());
                        try {
                            repository.insert(mTerm);
                            Toast.makeText(AddTerm.this, "Term Saved", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Toast.makeText(AddTerm.this, "Unable to Save Term, please ensure all fields are filled out correctly", Toast.LENGTH_LONG).show();
                        }
                        intent = new Intent(AddTerm.this, ViewTerm.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(AddTerm.this, "Unable to Save Term, please ensure all fields are filled out and Date fields contain MM/dd/yy", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void updateCal(EditText et, Calendar cal) {
        String format = "MM/dd/yy";
        SimpleDateFormat sFormat = new SimpleDateFormat(format, Locale.US);
        et.setText(sFormat.format(cal.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.back:
                Intent intent = new Intent(AddTerm.this, ViewTerm.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    };
}