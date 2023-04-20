package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewCourses extends AppCompatActivity {

    private FloatingActionButton addcourse;
    private Intent intent;
    private Course mCourse;
    private int termId;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        termId = getIntent().getIntExtra("termId", -1);

        addcourse = findViewById(R.id.addnewcoursefb);
        addcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository = new Repository(getApplication());
                mCourse = new Course("New Course","01/01/1973", "01/30/1973", "In Progress", termId);
                try {
                    repository.insert(mCourse);
                    Toast.makeText(ViewCourses.this, "New Course Added", Toast.LENGTH_LONG).show();

                    RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
                    final CourseAdapter courseAdapter = new CourseAdapter(ViewCourses.this);
                    recyclerView.setAdapter(courseAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewCourses.this));

                    Repository repository = new Repository(getApplication());
                    List<Course> allCourses = new ArrayList<>();
                    allCourses = repository.getAllCourses();
                    courseAdapter.setCourses(allCourses);
                    courseAdapter.notifyDataSetChanged();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(ViewCourses.this, "Unable to add Course, please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Repository repository = new Repository(getApplication());
        List<Course> allCourses = new ArrayList<>();
        try {
            allCourses = repository.getAllCourses();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        courseAdapter.setCourses(allCourses);
    }



    protected void onResume() {
        super.onResume();
        List<Course> allCourses = new ArrayList<>();
        repository= new Repository(getApplication());
        try {
            allCourses = repository.getAllCourses();
        }catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.back:
                intent = new Intent(ViewCourses.this, TermDetails.class);
                intent.putExtra("termID", termId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}