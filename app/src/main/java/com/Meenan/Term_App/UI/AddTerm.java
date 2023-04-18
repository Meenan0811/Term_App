package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;

public class AddTerm extends AppCompatActivity {
    private EditText termName;
    private EditText termStart;
    private EditText termEnd;
    private String tName;
    private String tStart;
    private String tEnd;
    private Term mTerm;
    private Button saveTermButton;
    private Repository repository;
    private Intent intent;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        termName = findViewById(R.id.termNameEdit);
        termStart = findViewById(R.id.termStartEdit);
        termEnd = findViewById(R.id.termEndEdit);
        repository = new Repository(getApplication());

        saveTermButton = findViewById(R.id.savetermbutton);
        saveTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = termName.getText().toString();
                String start = termStart.getText().toString();
                //Toast.makeText(AddTerm.this, "NAme: " + name + " Start: " + start, Toast.LENGTH_LONG).show();
                String end = termEnd.getText().toString();
                if (start.length() == 10 && end.length() == 10 && !name.isEmpty()) {
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
                }else {
                    Toast.makeText(AddTerm.this, "Unable to Save Term, please ensure all fields are filled out and Date fields contain MM/DD/YYYY", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}