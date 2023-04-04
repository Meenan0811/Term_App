package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;

import java.util.List;

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
    Button addCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_term);

        editName = findViewById(R.id.termNameEdit);
        editStart = findViewById(R.id.termStartEdit);
        editEnd = findViewById(R.id.termEndEdit);
        termId = getIntent().getIntExtra("id", -1);
        mTermName = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        repository = new Repository(getApplication());

        saveTerm = findViewById(R.id.savetermbutton);
        saveTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (termId == -1) {
                    mTerm = new Term (editStart.getText().toString(), editEnd.getText().toString(), editName.getText().toString());
                    try {
                        repository.insert(mTerm);
                        Toast.makeText(ModifyTerm.this, "Term has Been Saved", Toast.LENGTH_LONG);
                    } catch (InterruptedException e) {
                        Toast.makeText(ModifyTerm.this, "Term has not been saved, please ensure all fields are filled out", Toast.LENGTH_LONG);
                        e.printStackTrace();
                    }
                    else {
                        termId = currTerm.getTermID();
                        mTerm = mTerm.
                                //FIXME Pass Term and update when termId is found
                    }

                }
            }
        });

    }
}