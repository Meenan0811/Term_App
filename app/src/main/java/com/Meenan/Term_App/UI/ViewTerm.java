package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewTerm extends AppCompatActivity {
    private Term mTerm;
    private String currTime;
    private String endTime;
    private DateTimeFormatter formatter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        FloatingActionButton fButton = findViewById(R.id.addtermbutton);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrieve Today's Date and 1 month out using format MM/dd/yyyy
                formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                currTime = LocalDate.now().format(formatter);
                endTime = LocalDate.now().plusMonths(1).format(formatter);

                mTerm = new Term(currTime, endTime, "New Term");
                repository = new Repository(getApplication());
                try {
                    repository.insert(mTerm);

                    //Refresh Term List RecyclerView
                    RecyclerView recyclerView = findViewById(R.id.termrecyclerview);
                    final TermAdapter termAdapter = new TermAdapter(ViewTerm.this);
                    recyclerView.setAdapter(termAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewTerm.this));
                    List<Term> allTerms = repository.getAllTerms();
                    termAdapter.setTerms(allTerms);
                    termAdapter.notifyDataSetChanged();

                    Toast.makeText(ViewTerm.this, "New Term Added",Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        //Set Term List RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());
        List<Term> allTerms = null;
        try {
            allTerms = repository.getAllTerms();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        termAdapter.setTerms(allTerms);
    }
}