package com.Meenan.Term_App.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.Meenan.Term_App.Database.Repository;
import com.Meenan.Term_App.Database.TermCourseDataBase;
import com.Meenan.Term_App.Entities.Assesment;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Mentor;
import com.Meenan.Term_App.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.viewtermbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewTerm.class);
                startActivity(intent);
            }
        });

        //Test Items
        /*Term term = new Term("01/01/2023", "01/30/2023", "Term 1");
        Repository repository = new Repository(getApplication());
        try {
            repository.insert(term);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.viewterms:
                 intent = new Intent(MainActivity.this, ViewTerm.class);
                 startActivity(intent);
                 return true;


            case R.id.viewcourses:
                intent = new Intent(MainActivity.this, ModifyCourses.class);
                startActivity(intent);
                return true;

            case R.id.viewmentors:
                intent = new Intent(MainActivity.this, ViewMentors.class);
                startActivity(intent);
                return true;

            case R.id.addsampledata:
                Repository repository = new Repository(getApplication());
                Course course = new Course("Newer Course", "01/01/23", "01/31/23", "Completed", 1);
                Mentor mentor = new Mentor("New Mentor","1234567890", "mentor2@wgu.edu", 1);
                Assesment assesment = new Assesment("C146 Assesment", "01/31/23", "Performance",1);
                try {
                    repository.insert(course);
                    repository.insert(mentor);
                    repository.insert(assesment);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.deleteall:
                TermCourseDataBase termDb = TermCourseDataBase.getDataBase(getApplicationContext());
                termDb.clearAllTables();
        }
        return true;
    }
}