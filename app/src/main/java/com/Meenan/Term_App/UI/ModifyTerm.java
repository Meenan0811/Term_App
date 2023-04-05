package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
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

            List<Course> allCourses;

//FIXME: Added toast method to test loop, cannot return
            try {
                //allCourses = repository.getAllTermCourses(termId);
                allCourses = repository.getAllCourses();
                int numMatches = 0;
                for (Course c : allCourses) {
                    if (c.getTermID_FK() == termId) {
                        ++numMatches;
                    }
                }
                Toast.makeText(this,"Total: " + numMatches, Toast.LENGTH_LONG).show();
                if (allCourses != null) {
                    courseAdapter.setTerms(allCourses);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


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
                   /* else {
                        termId = currTerm.getTermID();
                        mTerm = mTerm.
                                //FIXME Pass Term and update when termId is found
                    }*/

                }
            }
        });

    }
}